public class Ball {

    //Color c;
    double mass;
    double radius;
    double bounciness;
    double velX, velY, velZ;
    double fX, fY, fZ;

    long startTime;
    long endTime;
    static double deltaTime;
    double initialSpeed;
    boolean inMotion = true;
    Coordinate coords;
    double[] directionImpulses = new double[2];
    double y = 0, x = 0;
    final double REIBUNG = 25;
    boolean debug = false;


    public Ball() {

    }

    public void shoot(double angle, double speed){

        //Setting speed and time
        this.initialSpeed = speed;
        startTime = System.currentTimeMillis();

        //Setting directional vector
        directionImpulses[0] = Math.cos(angle);
        directionImpulses[1] = Math.sin(angle);
    }

    public double getPosition(){
        endTime = System.currentTimeMillis();
        deltaTime = (endTime - startTime);
        double dy = 0;
        double dx = 0;
        if (debug) System.out.println("Time: " + deltaTime);

        if (deltaTime < 500000000){
            //inMotion = false;
        }

        if (inMotion){

            dx = initialSpeed - REIBUNG * deltaTime * deltaTime;

            //dx = initialSpeed * deltaTime / REIBUNG*REIBUNG*deltaTime;
            dy = initialSpeed * deltaTime / REIBUNG*REIBUNG*deltaTime ;

            x = x + dx;
            y = y + dy;
        }

        return dx;

    }

    public void magic(long deltaT) {

    }

    public void magic(Course c, double angX, double angY, double power) {

    }

    public boolean inPlay() {
        return true;
    }
}