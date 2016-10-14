package graph;

import bus.Bus;
import bus.BusStop;
import bus.Road;
import data.Manager;
import data.ManagerHoraires;
import javafx.geometry.Pos;
import utilisateurs.Utilisateur;
import utils.Position;
import utils.Utils;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.util.Map;

/**
 * @author Lucas Martinez
 */
public class Simulator {
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 100;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 100;

    // List of bus stops in the field.
    private List<BusStop> busStops;

    //bus in field
    private Bus bus;

    //Roads on field
    private List<Road> allRoads;
    // The current state of the field.
    private Field field;
    // A graphical view of the simulation.
    private GridView view;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     *
     * @param depth
     *            Depth of the field. Must be greater than zero.
     * @param width
     *            Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        if (width <= 0 || depth <= 0) {
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        busStops = new ArrayList<BusStop>();
        allRoads = new ArrayList<Road>();
        field = new Field(depth, width);

        bus = Utils.busL2;
        bus.setField(field);

        view = new GridView(depth, width);

        ManagerHoraires mh = new ManagerHoraires();
        createBusStop(mh);
        for(int nb : Utils.allBusNumbers)
            createRoad(nb, mh);

        reset();

    }

    /**
     * Place the bus stops on the field
     * @param mh
     */
    private void createBusStop(ManagerHoraires mh){
        for (String name : mh.getAllBusStopsWithPos().keySet()) {
            BusStop bs = new BusStop(name, mh.getAllBusStopsWithPos().get(name),field);
            busStops.add(bs);
        }
    }

    /**
     * Place the roads on the field
     * @param nbLine
     * @param mh
     */
    private void createRoad(int nbLine, ManagerHoraires mh){
        Map<String, Position> allBusStopsWithPos = mh.getBusStopWithPos(nbLine);
        Position oldPos = null;
        Position first = null;
        Position last = null;
        for(Map.Entry<String,Position> entry : allBusStopsWithPos.entrySet()){
            if(oldPos == null){
                oldPos = entry.getValue();
                first = entry.getValue();
                continue;
            }
            List<Position> pos = getPositionsFromTo(oldPos, entry.getValue());
            for(Position p : pos){
                allRoads.add(new Road(p, field));
            }
            oldPos = entry.getValue();
            last = entry.getValue();
        }
        List<Position> pos = getPositionsFromTo(last, first);
        for(Position p : pos){
            allRoads.add(new Road(p, field));
        }
    }

    /**
     * return the positions between two points : from and to
     * @param from
     * @param to
     * @return
     */
    public List<Position> getPositionsFromTo(Position from, Position to){
        List<Position> allPositions = new ArrayList<Position>();
        allPositions.add(from);
        Position oldPos = from;
        while(oldPos.getLatitude() != to.getLatitude()){
            if(oldPos.getLatitude() < to.getLatitude())
                oldPos = new Position(oldPos.getLongitude(), oldPos.getLatitude() + 1);
            else
                oldPos = new Position(oldPos.getLongitude(), oldPos.getLatitude() - 1);
            allPositions.add(oldPos);
        }

        while(oldPos.getLongitude() != to.getLongitude()){
            if(oldPos.getLongitude() < to.getLongitude())
                oldPos = new Position(oldPos.getLongitude() + 1, oldPos.getLatitude());
            else
                oldPos = new Position(oldPos.getLongitude() - 1, oldPos.getLatitude());
            allPositions.add(oldPos);
        }

        return allPositions;
    }

    /**
     * move the object in param from the oldPostion to the new one
     * @param objet
     * @param oldPosition
     * @param newPositon
     */
    public void move(IFieldItem objet, Position oldPosition, Position newPositon){
        field.clear(oldPosition, objet);
        field.placeInFirstPlace(objet, newPositon);
    }

    /**
     * Refresh the position of the element of the on the field
     */
    public void updateGrid() {
        updateView();
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        busStops.clear();
        updateView();
    }

    /**
     * Update all existing views.
     */
    private void updateView() {
        view.showStatus(field);
    }

    public Field getField() {
        return field;
    }

}
