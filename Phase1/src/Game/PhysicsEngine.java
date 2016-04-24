package Game;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by tony on 16/03/2016.
 */
public class PhysicsEngine {
    private Course course;
    //Testcomment

    ArrayList<Coordinate> forces = new ArrayList<>(4); //those forces are homogenious on the hole playing field
    ArrayList<Coordinate> directed = new ArrayList<>(4); //those forces direct the ball to this point.

    ArrayList<Ball> balls = new ArrayList<>(4);
    Ball ball;
    private final double GROUND_FRICTION = Config.GROUND_FRICTION;
    private final double AIR_FRICTION = Config.AIR_FRICTION;
    private final double GRAVITY_FORCE = Config.GRAVITY_FORCE;
    private final double WALL_ENERGY_LOSS = Config.WALL_ENERGY_LOSS;
    private final double SAND_ENERGY_LOSS = Config.SAND_ENERGY_LOSS;
    private final double WATER_ENERGY_LOSS = Config.WATER_ENERGY_LOSS;


    public static boolean enable3D = Config.ENABLED3D;

    public void init(Course course, ArrayList<Ball> ball) {
        this.course = course;
        this.balls = ball;
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
            //System.out.println("ball is on the ground");
        } else {
            ball.speedX *= AIR_FRICTION;
            if (enable3D)
                ball.speedZ *= AIR_FRICTION;
            ball.speedY *= AIR_FRICTION;

            if (enable3D)
                ball.speedZ -= GRAVITY_FORCE;
        }
    }

    public double calculateAngle(double speedX, double speedY) {
        double angle;
        angle = Math.atan(speedY / speedX);
        return angle;
    }


    public void checkColission() {
        double angle = calculateAngle(ball.getSpeedX(), ball.getSpeedY());
        double coordinateX, coordinateY, futureXCoordinate, futureYCoordinate;
        Type nextBallCoordinateType = null;

        if (Math.abs(ball.getSpeedX()) > Math.abs(ball.getSpeedY())) {
            System.out.println("SpeedX bigger");
            forloop:
            for (int i = 0; i <= Math.abs(ball.getSpeedX()); i += 5) {
                Game.dp.repaint();
                coordinateX = i;
                coordinateY = (i * Math.tan(angle));
                futureXCoordinate = (ball.getCoordinate().getX() + coordinateX);
                futureYCoordinate = (ball.getCoordinate().getY() + coordinateY);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (futureXCoordinate + ball.getRadius() >= Config.getWidth() || futureXCoordinate - ball.getRadius() <= 0 && futureYCoordinate + ball.getRadius() >= Config.getHeight() || futureYCoordinate - ball.getRadius() <= 0) {

                    System.out.println("bigger!!!!!");
                    break forloop;
                } else {

                    nextBallCoordinateType = course.getTile((int) (futureXCoordinate - ball.getRadius()), (int) (futureYCoordinate + ball.getRadius()), (int) ball.getCoordinate().getZ());
                    System.out.println(1 + (i / 5) + ". coordinateX: " + futureXCoordinate + " coordinateY: " + futureYCoordinate);
                    if (nextBallCoordinateType == Type.OBJECT) {
                        ball.getCoordinate().setX(futureXCoordinate);
                        ball.getCoordinate().setX(futureYCoordinate);
                        System.out.println("colission!");
                        ball.speedX *= WALL_ENERGY_LOSS;
                        ball.reverseBallDirectionX();
                        ball.speedY *= WALL_ENERGY_LOSS;
                        ball.reverseBallDirectionY();

                        break forloop;
                    }
                }

            }

        } else {
            System.out.println("SpeedY bigger");
            forloop:
            for (int i = 0; i <= Math.abs(ball.getSpeedY()); i += 1) {
                Game.dp.repaint();
                coordinateX = (i / Math.tan(angle));
                coordinateY = i;
                futureXCoordinate = (ball.getCoordinate().getX() + coordinateX);
                futureYCoordinate = (ball.getCoordinate().getY() + coordinateY);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (futureXCoordinate + ball.getRadius() >= Config.getWidth() || futureXCoordinate - ball.getRadius() <= 0 && futureYCoordinate + ball.getRadius() >= Config.getHeight() || futureYCoordinate - ball.getRadius() <= 0) {
                    System.out.println("bigger!!!!!");
                    break forloop;
                } else {
                    nextBallCoordinateType = course.getTile((int) (futureXCoordinate + ball.getRadius()), (int) (futureYCoordinate + ball.getRadius()), (int) ball.getCoordinate().getZ());
                    System.out.println(1 + (i / 5) + ". coordinateX: " + futureXCoordinate + " coordinateY: " + futureYCoordinate);
                    if (nextBallCoordinateType == Type.OBJECT) {
                        System.out.println("colission!");
                        ball.speedX *= WALL_ENERGY_LOSS;
                        ball.reverseBallDirectionX();
                        ball.speedY *= WALL_ENERGY_LOSS;
                        ball.reverseBallDirectionY();
                        break forloop;
                    }
                }

            }
        }
    }
    public void checkColissionMarkus() {
        double angle = calculateAngle(ball.getSpeedX(), ball.getSpeedY());
        double speed = ball.getSpeed();

        int coordinateX, coordinateY, futureXCoordinate, futureYCoordinate;
        coordinateX = (int) ball.getCoordinate().getX();
        coordinateY = (int) ball.getCoordinate().getY();
        futureXCoordinate = (int) (ball.getCoordinate().getX() + ball.getSpeedX());
        futureYCoordinate = (int) (ball.getCoordinate().getY() + ball.getSpeedY());


        checkIfObjectIsBetweenBallAndFutureBall(coordinateX,coordinateY,futureXCoordinate,futureYCoordinate,ball.radius,angle,speed);

        Type nextBallCoordinateType = null;

        if (Math.abs(ball.getSpeedX()) > Math.abs(ball.getSpeedY())) {
            System.out.println("SpeedX bigger");
            forloop:
            for (int i = 0; i <= Math.abs(ball.getSpeedX()); i += 5) {
                //futureXCoordinate = (ball.getCoordinate().getX() + coordinateX);
                //futureYCoordinate = (ball.getCoordinate().getY() + coordinateY);
                //coordinateX = i;
                //coordinateY = (i * Math.tan(angle));


                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (futureXCoordinate + ball.getRadius() >= Config.getWidth() || futureXCoordinate - ball.getRadius() <= 0 && futureYCoordinate + ball.getRadius() >= Config.getHeight() || futureYCoordinate - ball.getRadius() <= 0) {

                    System.out.println("bigger!!!!!");
                    break forloop;
                } else {

                    nextBallCoordinateType = course.getTile((int) (futureXCoordinate - ball.getRadius()), (int) (futureYCoordinate + ball.getRadius()), (int) ball.getCoordinate().getZ());
                    System.out.println(1 + (i / 5) + ". coordinateX: " + futureXCoordinate + " coordinateY: " + futureYCoordinate);
                    if (nextBallCoordinateType == Type.OBJECT) {
                        ball.getCoordinate().setX(futureXCoordinate);
                        ball.getCoordinate().setX(futureYCoordinate);
                        System.out.println("colission!");
                        ball.speedX *= WALL_ENERGY_LOSS;
                        ball.reverseBallDirectionX();
                        ball.speedY *= WALL_ENERGY_LOSS;
                        ball.reverseBallDirectionY();

                        break forloop;
                    }
                }

            }

        }
        }

    private void checkIfObjectIsBetweenBallAndFutureBall(int coordinateX, int coordinateY, int futureXCoordinate, int futureYCoordinate, double v, double angle, double radius) {
        int collisionX=-6666666;
        int collisionY=-6666666;
        ArrayList<int[]> pp = getPointsBetween(coordinateX,coordinateY,futureXCoordinate,futureYCoordinate);
        Point2D p = new Point();
        boolean collisionHappened = false;

        int ss = pp.size();
        loop:for (int i = 0; i < ss; i++) {
            if (course.getTile(pp.get(i)[0],pp.get(i)[1],0)==Type.OBJECT){
                collisionX=pp.get(i)[0];
                collisionY= pp.get(i)[1];
                collisionHappened = true;
                break loop;
            }
        }

        if (collisionHappened){
            for (int i = 0; i < 1000; i++) {
                System.out.println("Coolision happened at: " + collisionX +" " + collisionY);
            }
        }


    }

    private ArrayList<int[]> getPointsBetween(int x, int y, int x2, int y2) {
        ArrayList<int[]> p = new ArrayList<>(200);

            int w = x2 - x ;
            int h = y2 - y ;
            int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0 ;
            if (w<0) dx1 = -1 ; else if (w>0) dx1 = 1 ;
            if (h<0) dy1 = -1 ; else if (h>0) dy1 = 1 ;
            if (w<0) dx2 = -1 ; else if (w>0) dx2 = 1 ;
            int longest = Math.abs(w) ;
            int shortest = Math.abs(h) ;
            if (!(longest>shortest)) {
                longest = Math.abs(h) ;
                shortest = Math.abs(w) ;
                if (h<0) dy2 = -1 ; else if (h>0) dy2 = 1 ;
                dx2 = 0 ;
            }
            int numerator = longest >> 1 ;
            for (int i=0;i<=longest;i++) {
                p.add(new int[]{x, y}) ;
                numerator += shortest ;
                if (!(numerator<longest)) {
                    numerator -= longest ;
                    x += dx1 ;
                    y += dy1 ;
                } else {
                    x += dx2 ;
                    y += dy2 ;
                }
            }
        return p;
        }


        /*
        try {
            Type nextBallCoordinateType = null;

            if (ball.getSpeedX() > ball.getSpeedY()) {
                nextBallCoordinateType = course.getTile((int) (ball.getCoordinate().getX() + ball.getSpeedX()), (int) (ball.getCoordinate().getY() + ball.getSpeedY() - ball.getRadius()), (int) ball.getCoordinate().getZ()).getType();

            } else {
                nextBallCoordinateType = course.getTile((int) (ball.getCoordinate().getX() - ball.getRadius() + ball.getSpeedX()), (int) (ball.getCoordinate().getY() + ball.getSpeedY()), (int) ball.getCoordinate().getZ()).getType();
            }

            if (nextBallCoordinateType == Type.OBJECT) {
                System.out.println("colission!");
                ball.speedX *= WALL_ENERGY_LOSS;
                ball.reverseBallDirectionX();
                ball.speedY *= WALL_ENERGY_LOSS;
                ball.reverseBallDirectionY();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */


    public void processPhysics(double elapsedTime) {


        Type[][][] playfield = course.getPlayfield();
        for (int i = 0; i < balls.size(); i++) {

            Ball b = balls.get(i);

            double speedx = b.getSpeedX();
            double speedy = b.getSpeedY();
            double speedz = b.getSpeedZ();
            //applyForces



            //calculateV

            //calculateDeltaPos
            Coordinate newPosition = new Coordinate(b.getX() + b.getSpeedX() * elapsedTime,b.getY() + b.getSpeedY() * elapsedTime,b.getZ() + b.getSpeedZ() * elapsedTime);
            //check for Collision with surroundings
            checkForCollision(b, newPosition);
            //check for Collision with different balls
            for (int j = i+1; j < balls.size(); j++) {
                //check for collision between two balls
                Ball bd = balls.get(j);
               // if (Ball.collide(b,bd)){
                    //if collision reset postion and update vectors

                //}


            }

            //apply friction
            b.speedX=b.getSpeedX()*Type.Grass.getFriction();
            b.speedY=b.getSpeedY()*Type.Grass.getFriction();
            b.speedZ=b.getSpeedZ()*Type.Grass.getFriction();

            if (b.getSpeed()<Config.MINSPEED){
                b.speedX=0;
                b.speedY=0;
                b.speedZ=0;
                b.isMoving=false;
            }

        }
    }

    private void checkForCollision(Ball b, Coordinate newPosition) {
        ArrayList<Coordinate> checkThose = Coordinate.getPxelBetweenToPoints(b.getCoordinate(),newPosition);
        int indexOfLastFree = checkThose.size()-1;

        Coordinate collisionCoordinate=null;
        Coordinate lastFreeCoordinate = b.getCoordinate();

        ;
        for (int j = 0; j < checkThose.size(); j++) {
            Coordinate c = checkThose.get(j);
            Type positionType = course.getType(newPosition);
            if (positionType != Type.Empty){
                collisionCoordinate = c;
                if (j > 0)  lastFreeCoordinate = checkThose.get(j-1);
                else lastFreeCoordinate = checkThose.get(0);

                indexOfLastFree = j-1;
                break;
            }

        }
        if (collisionCoordinate==null) {
            b.setPosition(newPosition);
            return;
        }
        if (indexOfLastFree>=0) b.setPosition(lastFreeCoordinate);

        //updateSpeed
        try {

            Coordinate normal = Coordinate.getNormal(course, collisionCoordinate);

        double projection = b.getSpeedX()*normal.getX()+b.getSpeedY()*normal.getY()+b.getSpeedZ()*normal.getZ();
        projection *=2;
        double newSpeedX = -normal.getX()*projection;
        double newSpeedY = -normal.getY()*projection;
        double newSpeedZ = -normal.getZ()*projection;

        b.speedX = newSpeedX;
        b.speedY = newSpeedY;
        b.speedZ = newSpeedZ;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void processPhysicsOld(int timeStep) {

        Type ballCoordinateType = null;
        Coordinate b = ball.getCoordinate();
        double ballSpeed = ball.getSpeed();
        Hole h = course.getHole();

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


        if (Math.abs(b.getX() - h.getX()) <= ball.getRadius() + h.radius && Math.abs(b.getY() - h.getY()) <= ball.getRadius() + h.radius) {
            double distance = Math.sqrt((b.getX() - h.getX()) * (b.getX() - h.getX()) + (b.getY() - h.getY()) * (b.getY() - h.getY()));

            if (distance + ball.getRadius() < +h.radius) {
                //inAir
                if (ballSpeed < (2 * ball.radius - h.radius) * Math.sqrt(10 / 2 * h.radius)) {
                    ball.speedX = 0;
                    ball.speedZ = 0;
                    ball.speedY = 0;
                    ball.setInHole(true);
                }
            } else if (distance <= ball.getRadius() + h.radius) {
                Coordinate c = new Coordinate(h.getX() - b.getX(), h.getY() - b.getY(), h.getZ() - b.getZ());
                double factor = (1 - distance / (ball.getRadius() + h.radius)) * h.getFriction();
                ball.redirect(c, factor);


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

        /*************************************************/
        /** Process Object Colissions & Object Friction **/
        /************************************************/
        //checkColissionMarkus();
        if (Math.abs(ball.getSpeedX()) >= Math.abs(ball.getSpeedY())) {
            if (ball.getSpeedX() > 0) {
                try{
                    ballCoordinateType = course.getTile((int) (ball.getCoordinate().getX() - ball.getRadius()), (int) (ball.getCoordinate().getY() - ball.getRadius()), (int) ball.getCoordinate().getZ());

                } catch(Exception e) {
                    e.printStackTrace();
                }

                if (ballCoordinateType == Type.OBJECT) {
                    ball.getCoordinate().setX(ball.getCoordinate().getX() - ball.getSpeedX());
                    ball.getCoordinate().setY(ball.getCoordinate().getY() - ball.getSpeedY());
                    ball.reverseBallDirectionX();
                    ball.reverseBallDirectionY();
                    System.out.println("ball hit OBJECT from the left");
                }
                if(ballCoordinateType == Type.Water){
                    ball.speedX *= WATER_ENERGY_LOSS;
                    ball.speedY *= WATER_ENERGY_LOSS;
                    System.out.println("ball hit water left");
                }
                if(ballCoordinateType == Type.Sand){
                    ball.speedX *= SAND_ENERGY_LOSS;
                    ball.speedY *= SAND_ENERGY_LOSS;
                    System.out.println("ball hit sand left");
                }

            } else {
                try{
                    ballCoordinateType = course.getTile((int) (ball.getCoordinate().getX() - ball.getRadius()), (int) (ball.getCoordinate().getY() - ball.getRadius()), (int) ball.getCoordinate().getZ());

                } catch(Exception e) {
                    e.printStackTrace();
                }

                if (ballCoordinateType == Type.OBJECT) {
                    ball.getCoordinate().setX(ball.getCoordinate().getX() - ball.getSpeedX());
                    ball.getCoordinate().setY(ball.getCoordinate().getY() - ball.getSpeedY());
                    ball.reverseBallDirectionX();
                    ball.reverseBallDirectionY();
                    System.out.println("ball hit OBJECT from the right");

                }
                if(ballCoordinateType == Type.Water){
                    ball.speedX *= WATER_ENERGY_LOSS;
                    ball.speedY *= WATER_ENERGY_LOSS;
                    System.out.println("ball hit water right");
                }
                if(ballCoordinateType == Type.Sand){
                    ball.speedX *= SAND_ENERGY_LOSS;
                    ball.speedY *= SAND_ENERGY_LOSS;
                    System.out.println("ball hit sand right");
                }

            }
        } else {
            if (ball.getSpeedY() < 0) {
                try{
                    ballCoordinateType = course.getTile((int) (ball.getCoordinate().getX() - ball.getRadius()), (int) (ball.getCoordinate().getY() - ball.getRadius()), (int) ball.getCoordinate().getZ());

                } catch(Exception e) {
                    e.printStackTrace();
                }


                if (ballCoordinateType == Type.OBJECT) {
                   ball.getCoordinate().setX(ball.getCoordinate().getX() - 2*ball.getSpeedX());
                    ball.getCoordinate().setY(ball.getCoordinate().getY() -2* ball.getSpeedY());
                    ball.reverseBallDirectionX();
                    ball.reverseBallDirectionY();
                    System.out.println("ball hit OBJECT from below");

                }
                if(ballCoordinateType == Type.Water){
                    ball.speedX *= WATER_ENERGY_LOSS;
                    ball.speedY *= WATER_ENERGY_LOSS;
                    System.out.println("ball hit water below");
                }
                if(ballCoordinateType == Type.Sand){
                    ball.speedX *= SAND_ENERGY_LOSS;
                    ball.speedY *= SAND_ENERGY_LOSS;
                    System.out.println("ball hit sand below");
                }
            } else if (ball.getSpeedY() >= 0) {
                try{
                    ballCoordinateType = course.getTile((int) (ball.getCoordinate().getX() + ball.getRadius()), (int) (ball.getCoordinate().getY() + ball.getRadius()), (int) ball.getCoordinate().getZ());

                } catch(Exception e) {
                    e.printStackTrace();
                }


                if (ballCoordinateType == Type.OBJECT) {
                    ball.getCoordinate().setX(ball.getCoordinate().getX() - 2*ball.getSpeedX());
                    ball.getCoordinate().setY(ball.getCoordinate().getY() - 2*ball.getSpeedY());
                    ball.reverseBallDirectionX();
                    ball.reverseBallDirectionY();
                    System.out.println("ball hit OBJECT from up");

                }
                if(ballCoordinateType == Type.Water){
                    ball.speedX *= WATER_ENERGY_LOSS;
                    ball.speedY *= WATER_ENERGY_LOSS;
                    System.out.println("ball hit water up");
                }
                if(ballCoordinateType == Type.Sand){
                    ball.speedX *= SAND_ENERGY_LOSS;
                    ball.speedY *= SAND_ENERGY_LOSS;
                    System.out.println("ball hit sand up");
                }

            }
        }
    }
}
