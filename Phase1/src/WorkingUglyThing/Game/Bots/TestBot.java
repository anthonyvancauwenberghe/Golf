package WorkingUglyThing.Game.Bots;



import WorkingUglyThing.Game.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Nibbla on 13.04.2016.
 */
public class TestBot extends AIPlayer {
    @Override
    public void nextMove(Course c, ArrayList<Ball> notPlayerBall) {
        Ball b = this.getBall();
        Hole h = c.getHole();
        Type[][][] playfield = c.getPlayfield();
        ArrayList<Coordinate> pixelBetweenToPoints = Coordinate.getPixelBetweenToPoints(new Coordinate(0,0,0),new Coordinate(20,20,20));
        Random r = new Random();
        h.getCoordinate();
        double vx= r.nextDouble()*200-100;
        double vy= r.nextDouble()*200-100;
        double vz= r.nextDouble();
        System.out.format("Me gonna shot this vector X:%f%f%f here",vx,vy,vz);
        shoot(vx,vy,vz);
    }

    public TestBot(String s) {
        super(s);
    }


}
