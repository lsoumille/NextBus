package graph;

import utils.Position;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author
 */
public class GridView extends JFrame {
    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = new Color(176,242,182);

    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.white;

    private FieldView fieldView;


    /**
     * Create a view of the given width and height.
     *
     * @param height
     *            The simulation's height.
     * @param width
     *            The simulation's width.
     */
    public GridView(int height, int width) {

        setTitle("NextBusF Simulation");

        setLocation(20, 50);

        fieldView = new FieldView(height, width);

        Container contents = getContentPane();

        contents.add(fieldView, BorderLayout.CENTER);

        pack();
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * @return The color to be used for a given item.
     */
    private Color getColor(List<IFieldItem> cl) {
        Color col = cl.get(0).getColor();
        if (col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        } else {
            return col;
        }
    }

    /**
     * Show the current status of the field.
     * @param field
     *            The field whose status is to be displayed.
     */
    public void showStatus(Field field) {
        if (!isVisible()) {
            setVisible(true);
        }

        fieldView.preparePaint();

        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                List<IFieldItem> items = field.getObjectAt(new Position(col, row));
                if (items != null) {
                    fieldView.drawMark(col, row, getColor(items));
                } else {
                    fieldView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        fieldView.repaint();
    }

    /**
     * Provide a graphical view of a rectangular field. This is a nested class
     * (a class defined inside a class) which defines a custom component for the
     * user interface. This component displays the field. This is rather
     * advanced GUI stuff - you can ignore this for your project if you like.
     */
    private class FieldView extends JPanel {
        private final int GRID_VIEW_SCALING_FACTOR = 10;

        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;

        /**
         * Create a new FieldView component.
         */
        public FieldView(int height, int width) {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
         * Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                    gridHeight * GRID_VIEW_SCALING_FACTOR);
        }

        /**
         * Prepare for a new round of painting. Since the component may be
         * resized, compute the scaling factor again.
         */
        public void preparePaint() {
            if (!size.equals(getSize())) { // if the size has changed...
                size = getSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if (xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if (yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }

        /**
         * Paint on grid location on this field in a given color.
         */
        public void drawMark(int x, int y, Color color) {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale - 1, yScale - 1);
        }

        /**
         * The field view component needs to be redisplayed. Copy the internal
         * image to screen.
         */
        public void paintComponent(Graphics g) {
            if (fieldImage != null) {
                Dimension currentSize = getSize();
                if (size.equals(currentSize)) {
                    g.drawImage(fieldImage, 0, 0, null);
                } else {
                    // Rescale the previous image.
                    g.drawImage(fieldImage, 0, 0, currentSize.width,
                            currentSize.height, null);
                }
            }
        }
    }
}
