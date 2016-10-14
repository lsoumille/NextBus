package command;

import bank.Transaction;
import bus.Transport;
import bus.Bus;
import bus.BusStop;
import data.ManagerBank;
import serveur.ServiceThread;
import utils.PDF;
import utils.Position;
import utils.Utils;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 14/01/16.
 */
public class OutCommand extends CommandOnTimetable {

    ManagerBank mb;
    Transaction transaction;

    public OutCommand() {
        super("OUT");
        mb = new ManagerBank();
    }

    /**
     * the user getting off the bus and a facture is sent for his ride
     * @param serveur
     * @param answer
     * @return
     */
    @Override
    public boolean use(ServiceThread serveur, StringBuffer answer) {
        Position posUser = serveur.getUser().getPos();
        Map.Entry<String, Position> arret = managerH.getNearestStopWithPos(posUser);
        boolean outBus = false;
        Transport currentTrans = null;
        for(Bus bus : Utils.allBus){
            if(bus.getPosBus().equals(posUser) && bus.getPosBus().equals(arret.getValue())){
                if(! ((currentTrans = bus.detachTransport(serveur.getUser())) == null)){
                    Transaction toPay = new Transaction(currentTrans);
                    mb.writeTransaction(toPay);
                    transaction = toPay;
                    outBus = true;
                }
            }
        }
        if(outBus){
            answer.append("Vous etes sorti du bus");
            if(transaction.getMontant() != 0.0) {
                PDF.createPDF(serveur.getUser());
                List<BusStop> stops = transaction.getTrans().getStopsOnJourney();
                PDF.addToDoc("FACTURE\n\n\nBonjour "
                        + serveur.getUser().getNom()
                        + ",\nVotre compte a été débité d'un montant de: "
                        + Double.toString(transaction.getMontant())
                        + " euros.\n\nLe trajet correspondant: "
                        + stops.get(0).getName() + " -> "
                        + stops.get(stops.size() - 1).getName()
                        + "\n\nCordialement,\nL'équipe NEXTBUSCASPARF.");
                serveur.getMailer().sendFacture(serveur.getUser(), Utils.OBJECT_MAIL_FACTURE);
                PDF.delete();
            }
            serveur.setAllCommands(Utils.commandsUserConnected);
        } else {
            answer.append("Impossible de sortir du bus");
        }
        return false;
    }
}
