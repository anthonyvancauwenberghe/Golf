package WorkingUglyThing.Game;

import java.awt.*;

/**
 * Created by nibbla on 14.03.16.
 */
public enum Type {
    Empty(Color.GREEN,Config.AIR_FRICTION,0),
    Water(Color.BLUE,1.0,0.1),
    Sand(Color.YELLOW,Config.SAND_FRICTION,0.3),
    Hole(Color.WHITE,0.9,1),
    Grass(Color.GREEN,Config.GRASS_FRICTION,0.3),
    Start(Color.RED,0,1),
    OBJECT(Color.gray, Config.OBJECT_FRICTION,0.3),
    $MARKER(Color.black,0,0);



    Color g;
    double friction;
    private double bounceDampness;

    Type(Color g, double v, double bd) {
        this.g = g;
        this.friction = v;
        this.bounceDampness = bd;
    }

    public double getFriction(){
        return friction;
    }
    public Color getColor(){
        return g;
    }

    public double getBounceDampness() {
        return bounceDampness;
    }
}
