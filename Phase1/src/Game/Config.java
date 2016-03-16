package Game;

/** All the variables are static and used to determine the game flow
 * Created by nibbla on 16.03.16.
 */
public class Config {
    public static final double maxSpeedToFallIntoHole = 3;
    private static final double ballMass = 1;
    private static final double ballRadius = 20;
    private static final double holeRadius = 35;

    public static double getBallMass() {
        return ballMass;
    }

    public static double getBallRadius() {
        return ballRadius;
    }

    public static double getHoleRadius() {
        return holeRadius;
    }
}
