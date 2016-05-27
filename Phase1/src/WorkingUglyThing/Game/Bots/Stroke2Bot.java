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
    Course course;


    public Stroke2Bot(String s) {
        super(s);
    }

    @Override
    public void nextMove(Course c, ArrayList<Ball> notPlayerBall) {
        WorkingUglyThing.Game.Game.dp.repaint();
        Ball b = this.getBall();
        this.course = c;
        Hole h = course.getHole();
        Type[][][] playfield = course.getPlayfield();

        Coordinate coordBall = b.getCoordinate();
        Coordinate coordHole = h.getCoordinate();

        if (course.wayIsObstacleFree(coordBall, coordHole)) {
            int[] delta = getDelta(coordBall, coordHole);
            try {
                this.alternative = coordHole;
                WorkingUglyThing.Game.Game.dp.repaint();
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            shootBall(getCorrectedShootData(delta, Config.AI_OFFSET));
            this.alternative=null;
        } else {
            Coordinate alternative = findAlternative(coordBall, coordHole);
            int[] delta = getDelta(coordBall, alternative);
            try {
                WorkingUglyThing.Game.Game.dp.repaint();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            shootBall(getCorrectedShootData(delta, Config.AI_OFFSET));
            this.alternative=null;
            System.out.println("bot shooting");
        }


    }
    public Coordinate getShootLocation(){
        return this.alternative;
    }
    public Coordinate findAlternative(Coordinate coordBall, Coordinate coordHole) {
        int altX, altY, altZ;
        do {
            altX = (int) (Math.random() * Config.getWidth());
            altY = (int) (Math.random() * Config.getHeight());
            altZ = 16;
            alternative = new Coordinate(altX, altY, altZ);

            System.out.println(alternative.toString());
        }
        while (!(course.wayIsObstacleFree(coordBall, alternative,false,false) && course.wayIsObstacleFree(alternative, coordHole,false,true)));
        //&&course.getTile(altX, altY, altZ)==Type.OBJECT


        return alternative;
    }
}
