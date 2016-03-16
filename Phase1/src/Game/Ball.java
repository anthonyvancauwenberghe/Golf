package Game;

public class Ball {

    //Color c;
    double mass = 1;
    double radius = 20;
    double bounciness;
    double speedX, speedY, speedZ;
    double fX, fY, fZ;
    boolean isMoving = false;
    private Coordinate coordinate;


    public Ball(double mass, double radius) {
        this.mass=mass;
        this.radius= radius;
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

    public double getSpeedY() {
        return speedY;
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getRadius() {
        return radius;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

}