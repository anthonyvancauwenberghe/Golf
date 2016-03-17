package Game;

/**
 * Created by nibbla on 16.03.16.
 */
public class Hole extends Tile {
    public double radius = 4;
    public Hole(double radius, int x, int y, int z) {
        super(Type.Hole, x, y, z);
        this.radius = radius;
    }

    boolean isBallInHole(Ball b){
        Coordinate bc = b.getCoordinate();
        if (Math.abs(z-bc.getZ())>b.radius){
            return false;
        }
        if (Coordinate.getDistance(x, y,z, bc.getX(), bc.getY(), bc.getZ()) < (radius-b.radius)) {
            if (b.speedX+b.speedY >= Config.maxSpeedToFallIntoHole) return false;
            return true;
        }
        return false;
    }

    /**
     *
     * @param b
     * @return true if the center of the ball is within the radius of the hole
     */
    boolean isBallIntersectingHole(Ball b){
        Coordinate bc = b.getCoordinate();
        if (Coordinate.getDistance(x, y,z, bc.getX(), bc.getY(), bc.getZ()) < (radius)) {
            return true;
        }
        return false;
    }



}
