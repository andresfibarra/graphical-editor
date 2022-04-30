import java.util.Map;
import java.util.TreeMap;

/**
 * Class to hold the current shape list
 * @author Andres Ibarra, Spring 2021, CS10 PSet 6
 */
public class Sketch {
    private TreeMap<Integer, Shape> idMap; // map (shapeID --> shape)

    /**
     * Constructor to initialize idMap
     */
    public Sketch() {
        idMap = new TreeMap<>();
    }

    /**
     * getter for idMap
     * @return
     */
    public synchronized TreeMap<Integer, Shape> getIdMap() {
        return idMap;
    }

    /**
     * Class to find ID number of topmost shape clicked on
     * @param x
     * @param y
     * @return - the ID number of the topmost shape containing the point clicked, returns -1 if none are a hit
     */
    public synchronized int containsShape(int x, int y) {
        int returnId = -1;
        for (Integer id: idMap.navigableKeySet()) {
            if (idMap.get(id).contains(x, y)) {
                returnId = id;
            }
        }
        return returnId;
    }

}
