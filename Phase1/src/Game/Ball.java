package Game;

public class Ball {

    //Color c;
    double mass = 1;
    double radius = 20;
    double bounciness;
    public double speedX, speedY, speedZ;
    public boolean isMoving = false;
    private Coordinate coordinate;


    public Ball(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void shootBall(double speedX, double speedY) {
        if (!isMoving && inPlay()) {
            isMoving = true;
            this.speedX = speedX;
            this.speedY = speedY;
        } else {
            System.out.println("ball is still moving or not in play");
        }
    }

    public void shootBall3D(double speed1, double speed2, double angle) {

    }

    public boolean inPlay() {
        return true;
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public double getSpeedZ() {
        return speedZ;
    }

    public double getRadius() {
        return radius;
    }

    public void reverseBallDirectionX() {
        speedX = -speedX;
    }
    public void reverseBallDirectionY() {
        speedY = -speedY;
    }
    public void reverseBallDirectionZ() {
        speedZ = -speedZ;
    }
    public Coordinate getCoordinate() {
        return coordinate;
    }

}