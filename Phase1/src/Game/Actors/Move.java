package Game.Actors;

import Game.Model.Coordinate;

/**
 * Created by Nibbla on 12.06.2016.
 */
public class Move {
    public Coordinate c = new Coordinate();
    public Coordinate[] c2 = new Coordinate[1];
    public Move(double x, double y, double z){
        c.setX(x);
        c.setY(y);
        c.setZ(z);
        c2[0]=c;
    }
    public Move(double angleRadians, double power){
        c.setX(Math.cos(angleRadians)*power);
        c.setY(Math.sin(angleRadians)*power);
        c.setZ(0);
        c2[0]=c;
    }


    public double getZ() {
        return c.getZ();
    }

    public double getY() {
        return c.getY();
    }

    public double getX() {
        return c.getX();
    }

    public Coordinate[] getCoordinate() {
        return c2;
    }
}
