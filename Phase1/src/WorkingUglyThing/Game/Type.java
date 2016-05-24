package WorkingUglyThing.Game;

import java.awt.*;

/**
 * Created by nibbla on 14.03.16.
 */
public enum Type {
    Empty(Color.GREEN,Config.AIR_FRICTION),
    Water(Color.BLUE,1.0),
    Sand(Color.YELLOW,Config.SAND_FRICTION),
    Hole(Color.WHITE,0.9),
    Grass(Color.GREEN,Config.GRASS_FRICTION),
    Start(Color.RED,0.0),
    OBJECT(Color.gray, Config.WALL_ENERGY_LOSS),$MARKER(Color.black,0);



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
