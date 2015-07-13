import java.util.ArrayList;
public class Road {
    String id;

    public ArrayList<Direction> directions;
    public Road(String id) {
            this.id = id;
            directions = new ArrayList();
    }
 
    public Direction getDirection(String xPos, String zPos) {
        float x = Float.parseFloat(xPos);
        float y = Float.parseFloat(zPos);
        
        for (Direction d : directions) {
            if (d.trigger(x, y)) return d;
        }
        
        return null;

    }

    


}