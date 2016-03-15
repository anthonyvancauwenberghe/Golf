package Game;

import java.awt.*;

/**
 * Created by nibbla on 14.03.16.
 */
public enum Type {
    Empty(Color.GREEN,0.1), Water(Color.BLUE,1.0), Sand(Color.YELLOW,0.8), Hole(Color.WHITE,0.1), Grass(Color.MAGENTA,0.3), Start(Color.RED,0.0);


    Color g;
    double friction;
    Type(Color g, double v) {
        this.g = g;
        this.friction = v;
    }

    public double getFriction(){
        return friction;
    }
    public Color getColor(){
        return g;
    }
}
