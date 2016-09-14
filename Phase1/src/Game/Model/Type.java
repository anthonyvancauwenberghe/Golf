package Game.Model;

import Game.Game;
import Game.Config;

import java.awt.*;

/**
 * Created by nibbla on 14.03.16.
 */
public enum Type {
    Empty(Color.GREEN, Config.AIR_FRICTION,0.1),
    Water(Color.BLUE,1.0,0.1),
    Sand(Color.YELLOW, Config.SAND_FRICTION, Config.SAND_DAMPNESS),
    Hole(Color.WHITE,0.9,1),
    Grass(Color.GREEN, Config.GRASS_FRICTION, Config.GRASS_DAMPNESS),
    Start(Color.RED,0,1),
    OBJECT(Color.gray, Config.OBJECT_FRICTION, Config.OBJECT_DAMPNESS);
    //$MARKER(Color.black,0,0);



    Color g;
    double friction;
    private double bounceDampness;
    private double dampness;

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

    public static void reset() {
        Empty.setFriction(Config.AIR_FRICTION);
        Empty.setDampness(0.0);

        Sand.setFriction(Config.SAND_FRICTION);
        Sand.setDampness(Config.SAND_DAMPNESS);
        Hole.setFriction(0.9);
        Hole.setDampness(1);
        Grass.setFriction(Config.GRASS_FRICTION);
        Grass.setDampness(Config.GRASS_DAMPNESS);
        Start.setFriction(0.);
        Start.setDampness(1);
        OBJECT.setFriction(Config.OBJECT_FRICTION);
        OBJECT.setDampness(Config.OBJECT_DAMPNESS);
       // $MARKER.setFriction(0.);
       // $MARKER.setDampness(0.);


    }

    public void setFriction(Double friction) {
        this.friction = friction;
    }

    public void setDampness(double dampness) {
        this.dampness = dampness;
    }
}
