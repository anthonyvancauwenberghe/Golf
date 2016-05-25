package WorkingUglyThing.Game.Bots;

import Game.Game;
import WorkingUglyThing.Game.Ball;
import WorkingUglyThing.Game.Coordinate;
import WorkingUglyThing.Game.Course;
import WorkingUglyThing.Game.Type;
import WorkingUglyThing.Game.Config;
import WorkingUglyThing.Game.Hole;

import java.util.ArrayList;

/**
 * Created by lukas on 22/05/16.
 */



public class Stroke2Bot extends AIPlayer {
    public Coordinate alternative=null;


    public Stroke2Bot(String s) {
        super(s);
    }

    @Override
    public void nextMove(Course c, ArrayList<Ball> notPlayerBall) {
        Ball b = this.getBall();
        Hole h = course.getHole();
        Type[][][] playfield = course.getPlayfield();

        Coordinate coordBall = b.getCoordinate();
        Coordinate coordHole = h.getCoordinate();

        if (course.wayIsObstacleFree(coordBall, coordHole)) {
            int[] delta = getDelta(coordBall, coordHole);
            shootBall(getCorrectedShootData(delta, Config.AI_OFFSET));
        } else {
            Coordinate alternative = findAlternative(coordBall, coordHole);
            int[] delta = getDelta(coordBall, alternative);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            shootBall(getCorrectedShootData(delta, Config.AI_OFFSET));
            System.out.println("bot shooting");
        }


    }
    public Coordinate getShootLocation(){
        return this.alternative;
    }
    public Coordinate findAlternative(Coordinate coordBall, Coordinate coordHole) {

        do {
            int altX = (int) (Math.random() * Config.getWidth());
            int altY = (int) (Math.random() * Config.getHeight());
            int altZ = 0;
            alternative = new Coordinate(altX, altY, altZ);
            System.out.println(alternative.toString());
        }
        while ((!course.wayIsObstacleFree(coordBall, alternative) || !course.wayIsObstacleFree(alternative, coordHole)));



        return alternative;
    }
}
