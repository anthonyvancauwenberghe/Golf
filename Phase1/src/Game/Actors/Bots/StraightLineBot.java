package Game.Actors.Bots;

import Game.*;
import Game.Model.Ball;
import Game.Model.Coordinate;
import Game.Model.Course;
import Game.Model.Type;


import java.util.ArrayList;

/**
 * Created by lukas on 20/05/16.
 */
public class StraightLineBot extends AIPlayer {
    @Override
    public void nextMove(Course c, ArrayList<Ball> notPlayerBall) {
        Ball b = this.getBall();
        Type[][][] playfield = c.getPlayfield();

        Coordinate currentCoord = b.getCoordinate();
        Coordinate HoleCoord = new Coordinate(400,400,0);

    /*public void euclideanDistance() {*/
        double p1 = (HoleCoord.getX() - currentCoord.getX());
        double p2 = (HoleCoord.getY() - currentCoord.getY());
        double p3 = (HoleCoord.getZ() - currentCoord.getZ());

        double p12 = Math.pow(p1, 2);
        double p22 = Math.pow(p2, 2);
        double p32 = Math.pow(p3, 2);
        double distance = Math.sqrt(p12 + p22 + p32);
        System.out.println("Euclidean distance " + distance);

        //max ball speed to get ball in the hole
        double maxBallSpeed = (2 * Config.getBallRadius() - Config.getHoleRadius()) * Math.sqrt(Type.Grass.getFriction() / 2 * Config.getHoleRadius());


        double vx = (HoleCoord.getX() - currentCoord.getX());
        double vy = (HoleCoord.getY() - currentCoord.getY());
        double vz = (HoleCoord.getZ() - currentCoord.getZ());
        System.out.printf("SLB gonna shot this vector X:%f%f%f here %n",vx,vy,vz);

        shootBall(1+(vx/Type.Grass.getFriction()),1+(vy/ Type.Grass.getFriction()),1+(vz/Config.AIR_FRICTION));
        int strokes = 0;
        if (distance > 1.72)
        {
            strokes++;
            System.out.println("Amount of strokes is " + strokes);
        }


        System.out.format("Shoot ball with XSpeed %f, YSpeed %f, Zspeed %f %n", getBall().getSpeedX(),getBall().getSpeedY(),getBall().getSpeedZ());

    }

    public StraightLineBot(String s) {
        super(s);
    }


}
