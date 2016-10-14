package bus;

import graph.Field;
import graph.IFieldItem;
import utils.Position;

import java.awt.*;

/**
 * Created by user on 12/01/16.
 */
public class BusStop implements IFieldItem {

    private String name;
    private Position position;
    private Field field;

    public BusStop(String name, Position position, Field field){
        this.name = name;
        this.position = position;
        this.field = field;
        this.field.place(this,position);
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return Color.red;
    }

    public Position getPosition() {
        return position;
    }
}
