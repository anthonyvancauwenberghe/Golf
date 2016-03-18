package Game;

public class Ball {

    //Color c;
    double mass = 1;
    double radius = Config.getBallRadius();
    double bounciness;
    public double speedX, speedY, speedZ;
    public boolean isMoving = false;
    public boolean inHole = false;
    public boolean inPlay = true;
    private Coordinate coordinate = new Coordinate();
    private PhysicsEngine physics = new PhysicsEngine();
    private int speedLimiter = Config.speedLimiter;
    private double speedSlower = Config.speedSlower;



    public Ball(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public double getSign(double number){
        if(number>=0)
            return 1;
        else{
            return -1;
        }
    }

    public void shootBall(double speedX, double speedY, double speedZ) {
        System.out.println(inPlay());
        System.out.println(isMoving);
        if (!isMoving && inPlay()) {
            isMoving = true;
            this.speedX = (Math.abs(speedX)>=speedLimiter) ? getSign(speedX)*speedLimiter : speedX ;
            this.speedY = (Math.abs(speedY)>=speedLimiter) ? getSign(speedY)*speedLimiter : speedY;
            this.speedZ = (Math.abs(speedZ)>=speedLimiter) ? getSign(speedZ)*speedLimiter : speedZ;
            this.speedX = this.speedX/speedSlower;
            this.speedY = this.speedY/speedSlower;
            this.speedZ = this.speedZ/speedSlower;
            System.out.println("ball is still moving or not in play speedY: " + this.speedY);
        } else {
            System.out.println("ball is still moving or not in play");
        }
    }

    public void shootBall3D(double speed1, double speed2, double angle) {

    }

    public boolean inPlay() {
        return inPlay;
    }

    public boolean isInHole() {
        return inHole;
    }

    public void setInHole(boolean inHole) {
        this.inHole = inHole;
        this.inPlay = !inHole;
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

    public PhysicsEngine getPhysics() {
        return physics;
    }
    public boolean checkBallStopped(){
        if(Math.abs(speedX)<=0.2 && Math.abs(speedY)<=0.3 && Math.abs(speedZ)<=1){
            isMoving=false;
            System.out.println("ballStopped");
            return true;

        }
        return false;

    }

    public void printBallInfo(){
        System.out.println("X: " + getCoordinate().getX());
        System.out.println("Y: " + getCoordinate().getY());
        System.out.println("Z: " + getCoordinate().getZ());
        System.out.println("SpeedX: " + speedX);
        System.out.println("SpeedY: " + speedY);
        System.out.println("SpeedZ: " + speedZ);
        System.out.println("ball radius: " + getRadius());
        if(!isMoving)
            System.out.println("ballStopped");
    }

    public double getSpeed() {
       return Math.sqrt(speedX*speedX+speedY*speedY+speedZ*speedZ);
    }

    public void redirect(Coordinate c, double factor) {
        double redirectSpeed = 0;
        redirectSpeed += factor *Math.abs(speedX);
        redirectSpeed += factor *Math.abs(speedY);
        redirectSpeed += factor *Math.abs(speedZ);
        speedX*=1-factor;
        speedY*=1-factor;
        speedZ*=1-factor;
        double length = Math.sqrt(c.getX()*c.getX()+c.getY()*c.getY()+c.getZ()*c.getZ());
        speedX+=(c.getX()/length*redirectSpeed);
        speedY+=(c.getY()/length*redirectSpeed);
        speedZ+=(c.getZ() / length * redirectSpeed);





    }
}