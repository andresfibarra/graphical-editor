import java.awt.*;
import java.util.TreeMap;

/**
 * Class with static methods to handle messages between editors and servers
 * @author Andres Ibarra, Spring 2021, CS10 PSet 6
 */
public class MessageHandler {
    public static int newShapeId = 0;   //ID number for shape to be added

    /**
     * static method to handle "draw" requests by updating the shapeMap
     * @param shapeMap - Treemap mapping ID numbers -> shape
     * @param split - the command line, split into words/ numbers for parsing
     */
    public static void handleDraw(TreeMap<Integer, Shape> shapeMap, String[] split) {
        String shapeType = split[1];
        if (shapeType.equals("ellipse")) {
            int x1 = Integer.parseInt(split[2]);
            int y1 = Integer.parseInt(split[3]);
            int x2 = Integer.parseInt(split[4]);
            int y2 = Integer.parseInt(split[5]);
            Color color = new Color (Integer.parseInt(split[6]));

            shapeMap.put(newShapeId, new Ellipse(x1, y1, x2, y2, color));   //put new shape into the shapeMap
            newShapeId++;   //ensure that the next time an object is drawn it gets a unique ID number
        }
        else if (shapeType.equals("rectangle")) {
            int x1 = Integer.parseInt(split[2]);
            int y1 = Integer.parseInt(split[3]);
            int x2 = Integer.parseInt(split[4]);
            int y2 = Integer.parseInt(split[5]);
            Color color = new Color (Integer.parseInt(split[6]));

            shapeMap.put(newShapeId, new Rectangle(x1, y1, x2, y2, color));//put new shape into the shapeMap
            newShapeId++;   //ensure that the next time an object is drawn it gets a unique ID number
        }
        else if (shapeType.equals("polyline")) {
            int startX = Integer.parseInt(split[2]);
            int startY = Integer.parseInt(split[3]);
            Color color = new Color (Integer.parseInt(split[split.length-1]));
            shapeMap.put(newShapeId, new Polyline(startX, startY, color));    //put new empty Polyline into the shapeMap

            if (split.length > 5) { //if there are more points to add to the polyline
                for (int i = 6; i < split.length-1; i+=4) {
                    int x1 = Integer.parseInt(split[i]);
                    int y1 = Integer.parseInt(split[i+1]);
                    int x2 = Integer.parseInt(split[i+2]);
                    int y2 = Integer.parseInt(split[i+3]);

                    ((Polyline)shapeMap.get(newShapeId)).addSeg(x1, y1, x2, y2);//add this segment to the mapped polyline
                }
            }

            newShapeId++;   //ensure that the next time an object is drawn it gets a unique ID number
        }
        else if (shapeType.equals("segment")) {
            int x1 = Integer.parseInt(split[2]);
            int y1 = Integer.parseInt(split[3]);
            int x2 = Integer.parseInt(split[4]);
            int y2 = Integer.parseInt(split[5]);
            Color color = new Color (Integer.parseInt(split[6]));

            shapeMap.put(newShapeId, new Segment(x1, y1, x2, y2, color));    //put new empty Polyline into the shapeMap
            newShapeId++;   //ensure that the next time an object is drawn it gets a unique ID number
        }
    }

    /**
     * static method to handle "move" requests
     * @param shapeMap -  Treemap mapping ID numbers -> shape
     * @param shapeId - ID number of the shape to be moved
     * @param dx - distance in x direction to be moved
     * @param dy - distance in y direction to be moved
     */
    public static void handleMove(TreeMap<Integer, Shape> shapeMap, int shapeId, int dx, int dy) {
        shapeMap.get(shapeId).moveBy(dx, dy);
    }

    /**
     * static method to handle "recolor" requests
     * @param shapeMap - reemap mapping ID numbers -> shape
     * @param shapeId - ID number of hte shape to be recolored
     * @param colorRGB - int of RGB value of the new color
     */
    public static void handleRecolor(TreeMap<Integer, Shape> shapeMap, int shapeId, int colorRGB) {
        Color color = new Color(colorRGB);
        shapeMap.get(shapeId).setColor(color);
    }

    /**
     * static method to handle "delete" requests
     * @param shapeMap - reemap mapping ID numbers -> shape
     * @param shapeId - ID number of hte shape to be deleted
     */
    public static void handleDelete(TreeMap<Integer, Shape> shapeMap, int shapeId) {
        shapeMap.remove(shapeId);
    }

}
