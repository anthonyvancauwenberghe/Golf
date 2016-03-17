package Game;

/**
 * Created by tony on 16/03/2016.
 */
public class PhysicsEngine {
    private Course course;
    private Ball ball;

    private final double GROUND_FRICTION = Config.GROUND_FRICTION;
    private final double AIR_FRICTION = Config.AIR_FRICTION;
    private final double GRAVITY_FORCE = Config.GRAVITY_FORCE;
    private final double WALL_ENERGY_LOSS = Config.WALL_ENERGY_LOSS;

    public static boolean enable3D = Config.ENABLED3D;


    public void init(Course course, Ball ball) {
        this.course = course;
        this.ball = ball;
    }

    public void processNaturalForces() {

        /*********************************************/
        /** Process Ground & Air Friction + Gravity **/
        /*********************************************/

        /* Check if ball is on the ground else it's in the air */
        if (ball.getCoordinate().getZ() <= 1) {
            ball.speedX *= GROUND_FRICTION;
            ball.speedY *= GROUND_FRICTION;
            if (enable3D)
                ball.speedZ *= GROUND_FRICTION;
            System.out.println("ball is on the ground");
        } else {
            ball.speedX *= AIR_FRICTION;
            if (enable3D)
                ball.speedZ *= AIR_FRICTION;
            ball.speedY *= AIR_FRICTION;

            if (enable3D)
                ball.speedZ -= GRAVITY_FORCE;
        }
    }

    public void processPhysics() {

        System.out.println(course.getWidth());
        System.out.println(course.getHeight());
        System.out.println(course.getLength());

        double ballSpeed = ball.getSpeed();
        /*********************************/
        /** Process Hole **/
        /*********************************/
        //t = (2*Rh - R)/vf
        //Rh = radius of hole
        //R = radius ball
        //        vf = speed of ball when it reaches the hole
        //g*t^2/2 > R
        //g = gravity = 9.81
        //or expressed in vf: vf < (2Rh - R)(g / 2R)^1/2

        Hole h = course.getHole();
        Coordinate b = ball.getCoordinate();
        if (Math.abs(b.getX() - h.getX()) <= ball.getRadius() + h.radius && Math.abs(b.getY() - h.getY()) <= ball.getRadius() + h.radius) {
            double distance = Math.sqrt((b.getX() - h.getX()) * (b.getX() - h.getX()) + (b.getY() - h.getY()) * (b.getY() - h.getY()));

            if (distance + ball.getRadius() < +h.radius) {
                //inAir
                if (ballSpeed < (2 * ball.radius - h.radius) * Math.sqrt(10 / 2 * h.radius)) {
                    ball.speedX = 0;
                    ball.speedZ = 0;
                    ball.speedY = 0;
                }
            } else if (distance <= ball.getRadius() / 2 + h.radius) {
                Coordinate c = new Coordinate(h.getX() - b.getX(), h.getY() - b.getY(), h.getZ() - b.getZ());
                double factor = 1 - distance / (ball.getRadius() + h.radius);
                c.setX(c.getX() * factor);
                c.setY(c.getY() * factor);


                ball.speedX += c.getX();
                ball.speedY += c.getY();
                //ball.speedY+=c.getZ();
                //touches wall

            }

        }


        /*********************************/
        /** Process Border Colissions X **/
        /*********************************/

        // check right X border
        if (ball.getCoordinate().getX() + ball.getSpeedX() >= course.getWidth() - ball.getRadius()) {
            ball.getCoordinate().setX(course.getWidth() - ball.getRadius());
            ball.speedX *= WALL_ENERGY_LOSS;
            ball.reverseBallDirectionX();
            System.out.println("ball hit right border");
        }
        //check left X border
        else if (ball.getCoordinate().getX() + ball.getSpeedX() <= ball.getRadius()) {
            ball.getCoordinate().setX(ball.getRadius());
            ball.speedX *= WALL_ENERGY_LOSS;
            ball.reverseBallDirectionX();
            System.out.println("ball hit left border");
        }
        // Add speed if no colission
        else {
            ball.getCoordinate().setX(ball.getCoordinate().getX() + ball.getSpeedX());
        }

        /*********************************/
        /** Process Border Colissions Y **/
        /*********************************/

        // check bottom border
        if (ball.getCoordinate().getY() + ball.getSpeedY() >= course.getHeight() - ball.getRadius()) {
            ball.getCoordinate().setY(course.getHeight() - ball.getRadius());
            ball.speedY *= WALL_ENERGY_LOSS;
            ball.reverseBallDirectionY();
            //System.out.println(ball.getRadius() + " ball hit bottom border");

        }

        //check top border
        else if (ball.getCoordinate().getY() + ball.getSpeedY() <= ball.getRadius()) {
            ball.getCoordinate().setY(ball.getRadius());
            ball.speedY *= WALL_ENERGY_LOSS;
            ball.reverseBallDirectionY();
            System.out.println("ball hit top border");
        } else {
            ball.getCoordinate().setY(ball.getCoordinate().getY() + ball.getSpeedY());
        }

        /*********************************/
        /** Process Border Colissions Z **/
        /*********************************/
        if (enable3D) {
            //check back border
            if (ball.getCoordinate().getZ() >= course.getLength() - ball.getRadius()) {
                ball.getCoordinate().setZ(course.getLength() - ball.getRadius());
                ball.speedZ *= WALL_ENERGY_LOSS;
                ball.reverseBallDirectionZ();
                System.out.println("ball hit back border");
            }
            //check front border
            else if (ball.getCoordinate().getZ() + ball.getSpeedZ() <= ball.getRadius()) {
                ball.getCoordinate().setZ(ball.getRadius());
                ball.speedZ *= WALL_ENERGY_LOSS;
                ball.reverseBallDirectionZ();
                System.out.println("ball hit front border");
            }
            // Add speed if no colission
            else {
                ball.getCoordinate().setZ(ball.getCoordinate().getZ() + ball.getSpeedZ());
            }

        }
    }


}
