import java.awt.*;

/**
 * Created by nibbla on 14.03.16.
 */
public class Tile {
    Type t;

    public Tile(Type t) {
        this.t = t;
    }

    double getFriction(){
        return t.getFriction();
    }
    Color getColor(){
        return t.getColor();
    }

    public Type getType() {
        return t;
    }
}
