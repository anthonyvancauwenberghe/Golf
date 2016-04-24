package Game;

import Game.Type;

import java.util.ArrayList;
import java.util.Vector;

/**
 * This is the coordinate class which holds the x, y, z coordinates and the type of object the coordinate is
 * referencing.  Coordinate is a node.
 * @author ??
 */
public class Coordinate {

    private double xCoord, yCoord, zCoord;


    private int xInt, yInt, zInt;
    private Type type;//This will be the value that references the object at the coordinate space.

    /**
     * Constructor of the Coordinate class
     */
    public Coordinate() {
        this(0, 0, 0);
    }

    /**
     * second constructor of the coordinate class
     *
     * @param x int x coordinate
     * @param y int y coordinate
     * @param z int z coordinate
     */
    public Coordinate(int x, int y, int z) {
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;

        this.xInt = x;
        this.yInt = y;
        this.zInt = z;
    }

    /**
     * third constructor of the coordinate class
     *
     * @param x double x coordinate
     * @param y double y coordinate
     * @param z double z coordinate
     */
    public Coordinate(double x, double y, double z) {
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;

        this.xInt = (int) Math.floor(x);
        this.yInt = (int) Math.floor(y);
        this.zInt = (int) Math.floor(z);
    }

    public int getxInt() {
        return xInt;
    }

    public int getyInt() {
        return yInt;
    }

    public int getzInt() {
        return zInt;
    }

    /**
     * setter to set the x-coordinate
     *
     * @param x coordinate
     */
    public void setX(double x) {

        this.xCoord = x;
        this.xInt = (int) Math.floor(x);
    }

    /**
     * setter to set the y-coordinate
     *
     * @param y coordinate
     */
    public void setY(double y) {

        this.yCoord = y;
        this.yInt = (int) Math.floor(y);
    }

    /**
     * setter to set the z-coordinate
     *
     * @param z coordinate
     */
    public void setZ(double z) {
        this.zCoord = z;
        this.zInt = (int) Math.floor(z);
    }

    /**
     * setter to set the type of class Type
     *
     * @param type
     */
    public void setType(Type type) {
        this.type = type;
    }


    /**
     * getter to get the x-coordinate
     *
     * @return xCoord
     */
    public double getX() {
        return xCoord;
    }

    /**
     * getter to get the y-coordinate
     *
     * @return yCoord
     */
    public double getY() {
        return yCoord;
    }

    /**
     * getter to get the z-coordinate
     *
     * @return zCoord
     */
    public double getZ() {
        return zCoord;
    }

    /**
     * Method to get the distance between ??
     *
     * @param x1
     * @param y1
     * @param z1
     * @param x2
     * @param y2
     * @param z2
     * @return result of formula to get the distance
     */
    public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
    }


    public static double getDistance(Coordinate c1, Coordinate c2) {

        return Math.sqrt((c1.getX() - c2.getX()) * (c1.getX() - c2.getX()) + (c1.getY() - c2.getY()) * (c1.getY() - c2.getY()) + (c1.getZ() - c2.getZ()) * (c1.getZ() - c2.getZ()));
    }

    public static Coordinate getNormal(Course playfield, Coordinate c) {
        int x = c.xInt;
        int y = c.yInt;
        int z = c.zInt;

        double avgX = 0;
        double avgY = 0;
        double avgZ = 0;


        for (int dx = -3; dx < 3; dx++) {
            for (int dy = -3; dy < 3; dy++) {
                for (int dz = -3; dz < 3; dz++) {
                    if (playfield.getType(x + dx,y + dy,z + dz) != Type.Empty) {
                        avgX -= dx;
                        avgY -= dy;
                        avgZ -= dz;
                    }
                }
            }
        }
        double length = Math.sqrt(avgX * avgX + avgY * avgY + avgZ * avgZ); // distance from avg to the center
        avgX /= length;
        avgZ /= length;
        avgY /= length;

        return new Coordinate(avgX, avgY, avgZ);

    }

    public static ArrayList<Coordinate> getPxelBetweenToPoints(Coordinate coordinate1, Coordinate coordinate2) {


        int xNew = 0;
        int yNew = 0;
        int zNew = 0;
        int dx, dy, dz = 0;
        int dx2, dy2, dz2 = 0;
        int aDx = 0;
        int aDy = 0;
        int aDz = 0;
        int x_inc, y_inc, z_inc = 0;
        int xxx, yyy, zzz = 0;
        int xOld, yOld, zOld = 0;
        int err_1, err_2 = 0;

        int expectedPoints = (int) (Math.abs(coordinate2.getX() - coordinate1.getX()) + Math.abs(coordinate2.getY() - coordinate1.getY()) + Math.abs(coordinate2.getZ() - coordinate1.getZ()));


        ArrayList<Coordinate> passedPoints = new ArrayList<>(expectedPoints * 2);

        xOld = coordinate1.getxInt();
        yOld = coordinate1.getyInt();
        zOld = coordinate1.getzInt();

        xNew = coordinate2.getxInt();
        yNew = coordinate2.getyInt();
        zNew = coordinate2.getzInt();
        passedPoints.add(new Coordinate(xOld,yOld,zOld));

        xxx = xOld;
        yyy = yOld;
        zzz = zOld;
        dx = xNew - xOld;
        dy = yNew - yOld;
        dz = zNew - zOld;

        if (dx < 0) {
            x_inc = -1;
        } else {
            x_inc = 1;
        }
        if (dy < 0) {
            y_inc = -1;
        } else {
            y_inc = 1;
        }
        if (dz < 0) {
            z_inc = -1;
        } else {
            z_inc = 1;
        }


        aDx = Math.abs(dx);
        aDy = Math.abs(dy);
        aDz = Math.abs(dz);

        dx2 = aDx * 2;
        dy2 = aDy * 2;
        dz2 = aDz * 2;

        if ((aDx >= aDy) && (aDx >= aDz)) {

            err_1 = dy2 - aDx;
            err_2 = dz2 - aDx;

            for (int cont = 0; cont < aDx - 1; cont++) {


                if (err_1 > 0) {
                    yyy += y_inc;
                    err_1 -= dx2;
                }

                if (err_2 > 0) {
                    zzz += z_inc;
                    err_2 -= dx2;
                }

                err_1 += dy2;
                err_2 += dz2;
                xxx += x_inc;

                passedPoints.add(new Coordinate(xxx, yyy, zzz));

            }

        }

        if ((aDy > aDx) && (aDy >= aDz)) {

            err_1 = dx2 - aDy;
            err_2 = dz2 - aDy;

            for (int cont = 0; cont <= aDy - 1; cont++) {


                if (err_1 > 0) {
                    xxx += x_inc;
                    err_1 -= dy2;
                }

                if (err_2 > 0) {
                    zzz += z_inc;
                    err_2 -= dy2;
                }

                err_1 += dx2;
                err_2 += dz2;
                yyy += y_inc;

                passedPoints.add(new Coordinate(xxx, yyy, zzz));

            }

        }

        if ((aDz > aDx) && (aDz > aDy)) {

            err_1 = dy2 - aDz;
            err_2 = dx2 - aDz;

            for (int cont = 0; cont <= aDz - 1; cont++) {


                if (err_1 > 0) {
                    yyy += y_inc;
                    err_1 -= dz2;
                }

                if (err_2 > 0) {
                    xxx += x_inc;
                    err_2 -= dz2;
                }

                err_1 += dy2;
                err_2 += dx2;
                zzz += z_inc;

                passedPoints.add(new Coordinate(xxx, yyy, zzz));

            }

            //xOld=xNew;
            //yOld=yNew;
            //zOld=zNew;


        }
        return passedPoints;
    }
}