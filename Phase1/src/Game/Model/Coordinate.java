package Game.Model;


import java.util.ArrayList;

/**
 * This is the coordinate class which holds the x, y, z coordinates and the type of object the coordinate is
 * referencing.  Coordinate is a node.
 * @author ??
 */
public class Coordinate {

    private double xCoord, yCoord, zCoord;
    private Type type;//This will be the value that references the object at the coordinate space.

    /**
     * Constructor of the Coordinate class
     */
    public Coordinate(){
        this(0, 0, 0);
    }

    /**
     * second constructor of the coordinate class
     * @param x int x coordinate
     * @param y int y coordinate
     * @param z int z coordinate
     */
    public Coordinate(int x, int y, int z){
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
    }

    /**
     * third constructor of the coordinate class
     * @param x double x coordinate
     * @param y double y coordinate
     * @param z double z coordinate
     */
    public Coordinate(double x, double y, double z){
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
    }

    /**
     * setter to set the x-coordinate
     * @param x coordinate
     */
    public void setX(double x){
        this.xCoord = x;
    }

    /**
     * setter to set the y-coordinate
     * @param y coordinate
     */
    public void setY(double y){
        this.yCoord = y;
    }

    /**
     * setter to set the z-coordinate
     * @param z coordinate
     */
    public void setZ(double z){
        this.zCoord = z;
    }

    /**
     * setter to set the type of class Type
     * @param type
     */
    public void setType(Type type){
        this.type = type;
    }

    /**
     * getter to get the x-coordinate
     * @return xCoord
     */
    public double getX(){
        return xCoord;
    }

    /**
     * getter to get the y-coordinate
     * @return yCoord
     */
    public double getY(){
        return yCoord;
    }

    /**
     * getter to get the z-coordinate
     * @return zCoord
     */
    public double getZ(){
        return zCoord;
    }

    /**
     * Method to get the distance between ??
     * @param x1
     * @param y1
     * @param z1
     * @param x2
     * @param y2
     * @param z2
     * @return result of formula to get the distance
     */
    public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2)+(z1-z2)*(z1-z2));
    }

    public String toString(){
        return "X: " + this.xCoord + " Y: " + this.yCoord + " Z: "  + this.zCoord;
    }

    public static ArrayList<Coordinate> getPixelBetweenToPoints(Coordinate coordinate1, Coordinate coordinate2) {


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

        xOld = (int) coordinate1.getX();
        yOld = (int) coordinate1.getY();
        zOld = (int) coordinate1.getZ();

        xNew = (int) coordinate2.getX();
        yNew = (int) coordinate2.getY();
        zNew = (int) coordinate2.getZ();
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

    public static ArrayList<int[]> getPixelBetweenToPoints2D(int[] coordinate1, int[] coordinate2) {


        int xNew = 0;
        int yNew = 0;
        int dx, dy = 0;
        int dx2, dy2 = 0;
        int aDx = 0;
        int aDy = 0;
        int x_inc, y_inc = 0;
        int xxx, yyy = 0;
        int xOld, yOld = 0;
        int err_1 = 0;

        int expectedPoints = (int) (Math.abs(coordinate2[0] - coordinate1[1]) + Math.abs(coordinate2[0] - coordinate1[1]));


        ArrayList<int[]> passedPoints = new ArrayList<>(expectedPoints * 2);

        xOld = (int) coordinate1[0];
        yOld = (int) coordinate1[1];

        xNew = (int) coordinate2[0];
        yNew = (int) coordinate2[1];

        passedPoints.add(new int[]{xOld, yOld});

        xxx = xOld;
        yyy = yOld;
        dx = xNew - xOld;
        dy = yNew - yOld;

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

        aDx = Math.abs(dx);
        aDy = Math.abs(dy);

        dx2 = aDx * 2;
        dy2 = aDy * 2;

        if (aDx >= aDy) {

            err_1 = dy2 - aDx;


            for (int cont = 0; cont < aDx - 1; cont++) {


                if (err_1 > 0) {
                    yyy += y_inc;
                    err_1 -= dx2;
                }

                err_1 += dy2;
                xxx += x_inc;

                passedPoints.add(new int[]{xxx, yyy});

            }

        }

        if (aDy > aDx){

            err_1 = dx2 - aDy;

            for (int cont = 0; cont <= aDy - 1; cont++) {


                if (err_1 > 0) {
                    xxx += x_inc;
                    err_1 -= dy2;
                }


                err_1 += dx2;
                yyy += y_inc;

                passedPoints.add(new int[]{xxx, yyy});

            }
        }
        return passedPoints;
    }
    public static ArrayList<int[]> getPixelBetween(int x1, int y1, int x2, int y2){
        ArrayList<int[]> passedPoints = new ArrayList<>();

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;

        int err = dx - dy;

        while (true) {
            int[]temp = {x1, y1};
            passedPoints.add(temp);

            if (x1 == x2 && y1 == y2) {
                break;
            }

            int e2 = 2 * err;

            if (e2 > -dy) {
                err = err - dy;
                x1 = x1 + sx;
            }

            if (e2 < dx) {
                err = err + dx;
                y1 = y1 + sy;
            }
        }
        return passedPoints;
    }
}