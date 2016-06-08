package Game.Actors.Bots;


import Game.*;
import Game.Actors.Player;
import Game.Model.Coordinate;
import Game.Model.Course;


/**
 * Created by Nibbla on 13.04.2016.
 */
public abstract class AIPlayer extends Player {
   protected static Course course = Game.course;

    public AIPlayer(String name) {
        super(name);

    }
    public void setCourse(Course course2){
        course = course2;
        System.out.println("ai loaded new course");
    }
    public void shootBall(int[]xyz){
        shootBall(xyz[0], xyz[1], xyz[2]);
    }
    public int[] getDelta(Coordinate ball, Coordinate hole){
        int[]delta = {
                ((int)hole.getX() - (int)ball.getX()),
                ((int)hole.getY() - (int)ball.getY()),
                ((int)hole.getZ() - (int)ball.getZ()),
        };
        return delta;
    }
    public int[] getCorrectedShootData(int[]delta, double multi){
        int[] deltaShoot = { (int) Math.round(delta[0]*multi), (int) Math.round(delta[1]*multi), (int) Math.round(delta[2]*multi)};
        return deltaShoot;
    }
    public int getDistance(int[] delta){
        return ((int)Math.sqrt(delta[0]*delta[0]+delta[1]*delta[1]+delta[2]*delta[2]));
    }

}
