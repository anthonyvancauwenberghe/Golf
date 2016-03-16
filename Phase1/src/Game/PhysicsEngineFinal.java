package Game;

/**
 * Created by tony on 16/03/2016.
 */
public class PhysicsEngineFinal {
    private Course course;
    private Ball ball;

    private final double GROUND_FRICTION=0.95;
    private final double AIR_FRICTION=0.995;
    private final double GRAVITY_FORCE=9.81;

    public PhysicsEngineFinal() {
    }

    public void init(Course course, Ball ball) {
        this.course = course;
        this.ball = ball;
    }

    public void processNaturalForces() {

        /* SpeedX & SpeedZ SlowDown */
        /* Check if ball is on the ground else it's in the air */
        if (ball.getCoordinate().getY() + ball.getRadius() <= course.getHeight()) {
            ball.speedX*=GROUND_FRICTION;
            ball.speedZ*=GROUND_FRICTION;
            System.out.println("ball is on the ground");
        }
        else {
            ball.speedX*=AIR_FRICTION;
            ball.speedZ*=AIR_FRICTION;
            ball.speedY*= AIR_FRICTION;
            ball.speedY*= GRAVITY_FORCE;
        }
    }
    public void processMovement(){

    }
}
