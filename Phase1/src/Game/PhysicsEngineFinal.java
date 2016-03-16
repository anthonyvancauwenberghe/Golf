package Game;

/**
 * Created by tony on 16/03/2016.
 */
public class PhysicsEngineFinal {
    private Course course;
    private Ball ball;

    public PhysicsEngineFinal() {
    }

    public void init(Course course, Ball ball) {
        this.course = course;
        this.ball = ball;
    }

    public void processMovement() {
        if (ball.getCoordinate().getY() + ball.getRadius() == course.getHeight()) {

        }
    }
}
