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
    private static final double ballMass = 1;
    private static final double ballRadius = 14;
    private static final double holeRadius = 24;
    public static final double GRASS_FRICTION = 0.12;
    public static final double AIR_FRICTION = 0.01;
    public static final double GRAVITY_FORCE = 9.81;
    public static final double WALL_ENERGY_LOSS = 0.7;
    public static final double WATER_ENERGY_LOSS  = 0.3;
    public static final double SAND_ENERGY_LOSS = 0.3;
    public static final int OFFSET_X_GAME=16;
    public static final int OFFSET_Y_GAME=39;
    public static final int OFFSET_X_EDITOR=16;
    public static final int OFFSET_Y_EDITOR=62;
    public static final int speedLimiter=120;
    public static final double speedSlower=1.3;

    public static final boolean ENABLED3D=false;
    public static final float POWERLINEWIDTH = 3.f;
    private static final String texturePath = "Phase1/src/WorkingUglyThing/Game/textures/";
    public static final double MINSPEED = 1;
    public static final double DAMPING = 0.99;
    public static final int BALLRESOLUTION = 40;
    public static final double STEPSIZE = 0.016;

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
        return ballMass;
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
