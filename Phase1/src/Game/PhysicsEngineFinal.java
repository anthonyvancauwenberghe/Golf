package Game;

/**
 * Created by tony on 16/03/2016.
 */
public class PhysicsEngineFinal {
    private Course course;
    private Ball ball;

    private final double GROUND_FRICTION = 0.95;
    private final double AIR_FRICTION = 0.995;
    private final double GRAVITY_FORCE = 9.81;
    private final double WALL_ENERGY_LOSS = 0.7;

    public PhysicsEngineFinal() {
    }

    public void init(Course course, Ball ball) {
        this.course = course;
        this.ball = ball;
    }

    public void processNaturalForces() {

        /*********************************************/
        /** Process Ground & Air Friction + Gravity **/
        /*********************************************/

        /* Check if ball is on the ground else it's in the air */
        if (ball.getCoordinate().getY() == course.getHeight() - ball.getRadius()) {
            ball.speedX *= GROUND_FRICTION;
            ball.speedZ *= GROUND_FRICTION;
            System.out.println("ball is on the ground");
        } else {
            ball.speedX *= AIR_FRICTION;
            ball.speedZ *= AIR_FRICTION;
            ball.speedY *= AIR_FRICTION;
            ball.speedY *= GRAVITY_FORCE;
        }
    }

    public void processPhysics() {

        /*********************************/
        /** Process Border Colissions X **/
        /*********************************/

        // check right X border
        if (ball.getCoordinate().getX() > course.getWidth() - ball.getRadius()) {
            ball.getCoordinate().setX(course.getWidth() - ball.getRadius());
            ball.speedX *= WALL_ENERGY_LOSS;
            ball.reverseBallDirectionX();
            System.out.println("ball hit right border");
        }
        //check left X border
        else if (ball.getCoordinate().getX() + ball.getSpeedX() < ball.getRadius()) {
            ball.getCoordinate().setX(ball.getRadius());
            ball.speedX *= WALL_ENERGY_LOSS;
            ball.reverseBallDirectionX();
            System.out.println("ball hit left border");
        }
        // Add speed if no colission
        else {
            processMovement(0);
        }

        /*********************************/
        /** Process Border Colissions Y **/
        /*********************************/


        /*********************************/
        /** Process Border Colissions Z **/
        /*********************************/
        if (ball.getCoordinate().getZ() > course.getWidth() - ball.getRadius()) {
            ball.getCoordinate().setZ(course.getWidth() - ball.getRadius());
            ball.speedZ *= WALL_ENERGY_LOSS;
            ball.reverseBallDirectionZ();
            System.out.println("ball hit back border");
        }
        //check left X border
        else if (ball.getCoordinate().getZ() + ball.getSpeedZ() < ball.getRadius()) {
            ball.getCoordinate().setZ(ball.getRadius());
            ball.speedZ *= WALL_ENERGY_LOSS;
            ball.reverseBallDirectionZ();
            System.out.println("ball hit front border");
        }
        // Add speed if no colission
        else {
            processMovement(0);
        }

    }

    public void processMovement(int direction) {

        /** 0: X direction **/
        /** 1: Y direction **/
        /** 2: Z direction **/

        if (direction == 0) {
            ball.getCoordinate().setX(ball.getCoordinate().getX() + ball.getSpeedX());
        } else if (direction == 1) {
            ball.getCoordinate().setY(ball.getCoordinate().getY() + ball.getSpeedY());
        } else if (direction == 2) {
            ball.getCoordinate().setZ(ball.getCoordinate().getZ() + ball.getSpeedZ());
        }

    }

}
