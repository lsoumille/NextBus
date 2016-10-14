package data;

import utils.Position;
import utils.Utils;

import java.io.IOException;
import java.util.*;

/**
 * Created by user on 11/01/16.
 */
public class ManagerHoraires extends Manager {

    private final static int START_HOURS = 3;

    public ManagerHoraires() {
        super();
    }

    /**
     * Return all the bus stops corresponding to the line in param
     * @param lineNb
     * @return
     */
    public List<String> getBusStop(int lineNb) {
        try {
            List<String[]> timeTable = gf.read(Utils.pathToTimetable+lineNb+Utils.CSV);
            List<String> busStop = new ArrayList<String>();
            for (String[] line : timeTable) {
                busStop.add(line[0]);
            }
            return busStop;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Return all the bus stops with their position corresponding to the line in param
     * @param lineNb
     * @return
     */
    public Map<String, Position> getBusStopWithPos(int lineNb) {
        try {
            List<String[]> timeTable = gf.read(Utils.pathToTimetable+lineNb+Utils.CSV);
            Map<String, Position> busStop = new LinkedHashMap<String, Position>();
            for (String[] line : timeTable) {
                Position pos = new Position(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                busStop.put(line[0], pos);
            }
            return busStop;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Return all the bus stops with their position
     * @return
     */
    public Map<String, Position> getAllBusStopsWithPos(){
        Map<String, Position> allStops = new HashMap<String, Position>();
        try {
            for(Integer i : Utils.allBusNumbers){
                List<String[]> timeTable = gf.read(Utils.pathToTimetable + i + Utils.CSV);
                for (String[] line : timeTable) {
                    Position pos = new Position(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                    allStops.put(line[0], pos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allStops;
    }

    /**
     * check if the name is a bus stop
     * @param name
     * @return
     */
    public boolean isBusStop(String name) {
        List<String> busStop = new ArrayList<String>();
        for(Integer i : Utils.allBusNumbers){
            busStop.addAll(getBusStop(i));
        }
        return busStop.contains(name.toUpperCase());
    }

    /**
     * Return the different times of passage for the line and the bus stop
     * @param lineNb
     * @param name
     * @return
     */
    public String[] getHours(int lineNb, String name) {
        if (isBusStop(name)) {
            try {
                List<String[]> timeTable = gf.read(Utils.pathToTimetable+lineNb+Utils.CSV);
                for (String[] line : timeTable) {
                    if (line[0].equals(name)) {
                        return line;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Arrêt inconnu");
        }
        return null;
    }

    /**
     * return the number of the line and the hour of the next bus
     * @param name
     * @return
     */
    public Map<String, String> getNextHour(String name){
        Map<String, String> nextHours = new HashMap<String, String>();
        for(Integer i : Utils.allBusNumbers) {
            System.out.println(i);
            List<String> allHours = new ArrayList<String>();
            if(getHours(i, name.toUpperCase()) == null)
                continue;
            allHours.addAll(Arrays.asList(getHours(i, name.toUpperCase())));
            Date current = new Date();
            //On start a 3 parce que nom + position
            for (int ind = START_HOURS ; ind < allHours.size() ; ++ind) {
                String[] dateInString = allHours.get(ind).split(":");
                if (Integer.parseInt(dateInString[0]) > current.getHours()
                        || (Integer.parseInt(dateInString[0]) == current.getHours()
                        && Integer.parseInt(dateInString[1]) >= current.getMinutes())){
                    nextHours.put(i.toString(), allHours.get(ind));
                    break;
                }
            }
        }
        return nextHours;
    }

    /**
     * Return all the information of the timetable corresponding to the line
     * @param lineNb
     * @return
     */
    public HashMap<String,List<String>> getTimetable(int lineNb) {
        try {
            List<String[]> timeTable = gf.read(Utils.pathToTimetable+lineNb+Utils.CSV);
            HashMap<String,List<String>> busStop = new LinkedHashMap<String, List<String>>();
            for (String[] line : timeTable) {
                List<String> hours = new ArrayList<String>();
                for (int i = START_HOURS; i<line.length; i++) {
                    hours.add(line[i]);
                }
                busStop.put(line[0],hours);
            }
            return busStop;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * return the name of the bus stop near the position in param
     * @param pos
     * @return
     */
    public String getNearestStop(Position pos){
        Map<String, Position> allStops = getAllBusStopsWithPos();
        String nearest = null;
        double minDist = 0.0;
        for(Map.Entry<String, Position> entry : allStops.entrySet()){
            if(nearest == null ||
                    Math.sqrt((Math.pow(pos.getLatitude() - entry.getValue().getLatitude() ,2))) + (Math.pow(pos.getLongitude() - entry.getValue().getLongitude() ,2))
                            < minDist){
                nearest = entry.getKey();
                minDist = Math.sqrt((Math.pow(pos.getLatitude() - entry.getValue().getLatitude() ,2))) + (Math.pow(pos.getLongitude() - entry.getValue().getLongitude() ,2));
            }
        }
        return nearest;
    }

    /**
     * return the name and the position of the bus stop near the position in param
     * @param pos
     * @return
     */
    public Map.Entry<String, Position> getNearestStopWithPos(Position pos){
        Map<String, Position> allStops = getAllBusStopsWithPos();
        Map.Entry<String, Position> nearest = null;
        double minDist = 0.0;
        for(Map.Entry<String, Position> entry : allStops.entrySet()){
            if(nearest == null ||
                    Math.sqrt((Math.pow(pos.getLatitude() - entry.getValue().getLatitude() ,2))) + (Math.pow(pos.getLongitude() - entry.getValue().getLongitude() ,2))
                            < minDist){
                nearest = entry;
                minDist = Math.sqrt((Math.pow(pos.getLatitude() - entry.getValue().getLatitude() ,2))) + (Math.pow(pos.getLongitude() - entry.getValue().getLongitude() ,2));
            }
        }
        return nearest;
    }

    /**
     * return the position for the bus stop in param
     * @param name
     * @return
     */
    public Position getPositionForStop(String name){
        Map<String, Position> allStops = getAllBusStopsWithPos();
        return allStops.get(name);
    }

}
