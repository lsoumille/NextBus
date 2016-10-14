package bus;

import graph.Field;
import graph.IFieldItem;
import utils.Position;

import java.awt.*;

/**
 * Created by user on 18/01/16.
 */
public class Road implements IFieldItem {

    private Position posRoad;
    private Field field;

    public Road(Position posRoad, Field field) {
        this.posRoad = posRoad;
        this.field = field;
        this.field.place(this, posRoad);
    }

    public Color getColor() {
        return Color.gray;
    }

    public Position getPosRoad() {
        return posRoad;
    }
}
