package Game;
/**
 * Created by tony on 16/03/2016.
 */
public class Main {
    public static void main(String [] args){
        Course course = new Course("Golf Deluxe", 800, 600, 1, Type.Grass, 1 );
        Ball ball = new Ball(1, 20);
        PhysicsEngineFinal physics = new PhysicsEngineFinal();

        physics.init(course, ball);
        while(true){
            if(ball.isMoving){

            }
        }
    }
}
