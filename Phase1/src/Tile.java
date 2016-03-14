import java.awt.*;

/**
 * Created by nibbla on 14.03.16.
 */
public class Tile {
    Type t;

    double getFriction(){
        return t.getFriction();
    }
    Color getColor(){
        return t.getColor();
    }

}
