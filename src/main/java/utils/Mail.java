package utils;

import utilisateurs.Utilisateur;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * Created by user on 11/01/16.
 */
public class Mail {

    private final String username;
    private final String password;
    private Properties props;
    private Session session;

    public Mail() {
        this.username = "nextbuscasparf@gmail.com";
        this.password = "polytechnicesophia";
        this.props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        session = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    /**
     * send a mail to the address (to) with the object and the mess
     * @param to
     * @param object
     * @param mess
     */
    public void sendMail(String to, String object, String mess){
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(object);
            message.setText(mess);

            Transport.send(message);

            System.out.println("Email send");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    /**
     * send a mail with a bill in pdf to the user
     * @param utilisateur
     * @param object
     */
    public void sendFacture(Utilisateur utilisateur, String object) {
        Multipart multipart = new MimeMultipart();
        BodyPart messageBodyPart = new MimeBodyPart();
        try {
            messageBodyPart.setText(Utils.FactureMail);
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            FileDataSource source = new FileDataSource(utilisateur.getNom() + utilisateur.getNumTransactions() + ".pdf");
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("Facture_" + utilisateur.getNom() + "_" + utilisateur.getNumTransactions() + ".pdf");
            multipart.addBodyPart(messageBodyPart);


            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(utilisateur.getMail()));
            msg.setSubject(object);
            msg.setContent(multipart);

            Transport.send(msg);

            System.out.println("Facture sent successfully...");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
