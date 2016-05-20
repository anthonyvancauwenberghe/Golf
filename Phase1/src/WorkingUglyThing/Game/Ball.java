package WorkingUglyThing.Game;



public class Ball {

    //Color c;
    double mass = 1;
    double radius = Config.getBallRadius();

    public double x, y, z;
    public double vX, vY, vZ;



    public double aX, aY, aZ;

    public boolean isMoving = false;
    public boolean inHole = false;
    public boolean inPlay = true;


    private int speedLimiter = Config.speedLimiter;
    private double speedSlower = Config.speedSlower;


    /**
     * constructor of the class Ball
     * @param mass the mass of the ball
     * @param radius the radius of the ball
     */
    public Ball(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
    }

    /**
     * Checks if the ball is moving.
     * If ball is moving, return true
     * @return isMoving
     */
    public boolean isMoving() {
        return isMoving;
    }

    /**
     * Check if ball is shot
     */
    public double getSign(double number){
        if(number>=0)
            return 1;
        else{
            return -1;
        }
    }

    public void shootBall(double vX, double vY, double vZ) {
        System.out.println(inPlay());
        System.out.println(isMoving);
        if (!isMoving && inPlay()) {
            isMoving = true;
            this.vX = (Math.abs(vX)>=speedLimiter) ? getSign(vX)*speedLimiter : vX ;
            this.vY = (Math.abs(vY)>=speedLimiter) ? getSign(vY)*speedLimiter : vY;
            this.vZ = (Math.abs(vZ)>=speedLimiter) ? getSign(vZ)*speedLimiter : vZ;
            this.vX = this.vX/speedSlower;
            this.vY = this.vY/speedSlower;
            this.vZ = this.vZ/speedSlower;
            System.out.println("ball is still moving or not in play speedY: " + this.vY);
        } else {
            System.out.println("ball is still moving or not in play");
        }
    }

    /**
     * What is shootBall3D doing?
     * @param speed1
     * @param speed2
     * @param angle
     */
    public void shootBall3D(double speed1, double speed2, double angle) {

    }

    /**
     * ??
     * @return true
     */
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

    /**
     * getter to get the speed in the X-axis
     * @return speedX
     */
    public double getSpeedX() {
        return vX;
    }

    /**
     * getter to get the speed in the Y-axis
     * @return vY
     */
    public double getSpeedY() {
        return vY;
    }

    /**
     * getter to get the speed in the Z-axis
     * @return speedZ
     */
    public double getSpeedZ() {
        return vZ;
    }

    /**
     * getter to get the radius
     * @return radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * formula to reverse the direction of the ball in X-axis
     */
    public void reverseBallDirectionX() {
        vX = -vX;
    }
    /**
     * formula to reverse the direction of the ball in Y-axis
     */
    public void reverseBallDirectionY() {
        vY = -vY;
    }
    /**
     * formula to reverse the direction of the ball in Z-axis
     */
    public void reverseBallDirectionZ() {
        vZ = -vZ;
    }

    /**
     * getter to get the coordinate of the ball of the Coordinate class
     * @return coordinate
     */
    public Coordinate getCoordinate() {
        return new Coordinate(x,y,z);
    }




    /**
     * check if ball is stopped, ball is stopped under following condition
     * if ball is stopped, isMoving is false
     */
    public boolean checkBallStopped(){
        if(Math.abs(vX)<=0.2 && Math.abs(vY)<=0.3 && Math.abs(vZ)<=1){
            isMoving=false;
            System.out.println("ballStopped");
            return true;

        }
        return false;

    }


    /**
     * method that prints the information about the ball
     */
    public void printBallInfo(){
        System.out.println("X: " +x);
        System.out.println("Y: " +y);
        System.out.println("Z: " +z);
        System.out.println("SpeedX: " + vX);
        System.out.println("SpeedY: " + vY);
        System.out.println("SpeedZ: " + vZ);
        System.out.println("ball radius: " + getRadius());
        if(!isMoving)
            System.out.println("ballStopped");
    }

    /**
     * getter to get the speed of the ball
     * @return formula to get the speed (with Pythagoras)
     */
    public double getSpeed() {
       return Math.sqrt(vX*vX+vY*vY+vZ*vZ);
    }

    /**
     *
     * @param c
     * @param factor
     */
    public void redirect(Coordinate c, double factor) {
        double redirectSpeed = 0;
        redirectSpeed += factor *Math.abs(vX);
        redirectSpeed += factor *Math.abs(vY);
        redirectSpeed += factor *Math.abs(vZ);
        vX*=1-factor;
        vY*=1-factor;
        vZ*=1-factor;
        double length = Math.sqrt(c.getX()*c.getX()+c.getY()*c.getY()+c.getZ()*c.getZ());
        vX+=(c.getX()/length*redirectSpeed);
        vY+=(c.getY()/length*redirectSpeed);
        vZ+=(c.getZ() / length * redirectSpeed);





    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public static boolean collide(Ball a, Ball b) {
        double r = a.getRadius();
        double r2 = b.getRadius();
        double dx = a.getX()-b.getX();
        double dy = a.getY()-b.getY();
        double dz = a.getZ()-b.getZ();
        return (r*r2<=dx*dx+dy*dy+dz*dz);
    }

    public double getaX() {
        return aX;
    }

    public void setaX(double aX) {
        this.aX = aX;
    }

    public double getaY() {
        return aY;
    }

    public void setaY(double aY) {
        this.aY = aY;
    }

    public double getaZ() {
        return aZ;
    }

    public void setaZ(double aZ) {
        this.aZ = aZ;
    }

}