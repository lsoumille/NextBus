package graph;

import utils.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 *
 */
public class Field {

    // The depth and width of the field.
    private int depth, width;

    //content of the positions
    private Map<Position, List<IFieldItem>> mapField;

    /**
     * Represent a field of the given dimensions.
     *
     * @param depth
     * @param width
     */
    public Field(int depth, int width) {
        this.depth = depth;
        this.width = width;

        mapField = new HashMap<Position, List<IFieldItem>>();
    }

    /**
     * remove the object from the position in param
     * @param location
     * @param object
     */
    public void clear(Position location, IFieldItem object) {
        List<IFieldItem> currentObjects =  mapField.get(location);
        currentObjects.remove(object);
        mapField.put(location, currentObjects);
    }

    /**
     * Place the item at the given location.
     *
     * @param o
     * @param location
     */
    public void place(IFieldItem o, Position location) {
        if (!mapField.containsKey(location)) {
            mapField.put(location, new ArrayList<IFieldItem>());
        }
        mapField.get(location).add(o);
    }

    /**
     * place the item in the first place of the list for the location
     * @param o
     * @param location
     */
    public void placeInFirstPlace(IFieldItem o, Position location) {
        if (!mapField.containsKey(location)) {
            mapField.put(location, new ArrayList<IFieldItem>());
        }
        mapField.get(location).add(0, o);
    }

    public List<IFieldItem> getObjectAt(Position location) {
        return mapField.get(location);
    }

    /**
     * Return the depth of the field.
     *
     * @return The depth of the field.
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Return the width of the field.
     *
     * @return The width of the field.
     */
    public int getWidth() {
        return width;
    }
}
