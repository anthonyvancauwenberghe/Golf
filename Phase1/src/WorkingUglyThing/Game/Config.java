package WorkingUglyThing.Game;

/** All the variables are static and used to determine the game flow
 * Created by nibbla on 16.03.16.
 * @author ??
 */
public class Config {

    private static final int width=1024;
    private static final int height = 768;
    private static final int depth = 100;

    public static final double maxSpeedToFallIntoHole = 3;

    protected static double ballRadius = 14;
    private static  double holeRadius = 24;
    public static  double SAND_DAMPNESS = 0.3;
    public static  double AI_OFFSET = 0.65;
    public static  double GRASS_FRICTION = 0.8;
    public static  double AIR_FRICTION = 0.1;
    public static  double GRAVITY_FORCE = 9.81;
    public static  double WALL_ENERGY_LOSS = 0.04;
    public static  double WATER_ENERGY_LOSS  = 0.3;
    public static  double SAND_FRICTION = 0.95;
    public static  double OBJECT_FRICTION = 0.75;
    public static  int OFFSET_X_GAME=50;
    public static  int OFFSET_Y_GAME=70;
    public static  int OFFSET_X_EDITOR=16;
    public static  int OFFSET_Y_EDITOR=62;
    public static  int speedLimiter=120;
    public static  double speedSlower=1.3;

    public static  boolean ENABLED3D=false;
    public static  float POWERLINEWIDTH = 3.f;
    private static  String texturePath = "Phase1/src/WorkingUglyThing/Game/textures/";
    public static  double MINSPEED = 1;
    public static  double DAMPING = 0.99;
    public static  int BALLRESOLUTION = 40;
    public static  double STEPSIZE = 0.016;
    public static  double GRASS_DAMPNESS = 0.3;
    public static  double OBJECT_DAMPNESS = 0.3;
    public static double collitionSurfacePointRatio = 0.9;
    public static double hoverSurfacePointRatio = 1.1;
    public static double UPPush = 1.2;


    public static double[] getLightningVector3d() {
        return lightningVector3d;
    }

    private static final double[] lightningVector3d = createLightningVector(-1,-1,1);

    private static double[] createLightningVector(double x, double y,double z) {
        double length = Math.sqrt(x * x + y * y+z*z);
        // normalize vector
        x /= length;
        y /= length;
        z /= length;
        double[] d = {x,y,z};
        return d;
    }

    /**
     * getter to get the mass of the ball
     * @return ballMass
     */
    public static double getBallMass() {
        return 1;
    }

    /**
     * getter to get the radius of the ball
     * @return ballRadius
     */
    public static double getBallRadius() {
        return ballRadius;
    }

    /**
     * getter to get the radius of the hole
     * @return holeRadius
     */
    public static double getHoleRadius() {
        return holeRadius;
    }

    /**
     * getter to get width of ??
     * @return width
     */
    public static int getWidth() {
        return width;
    }

    /**
     * getter to get height of ??
     * @return height
     */
    public static int getHeight() {
        return height;
    }

    /**
     * getter to get depth of ??
     * @return depth
     */
    public static int getDepth() {
        return depth;
    }

    public static String getTexturePath() {
        return texturePath;
    }
}
