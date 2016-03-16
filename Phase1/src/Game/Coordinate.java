package Game;

import Game.Type;

/**
 * This is the coordinate class which holds the x, y, z coordinates and the type of object the coordinate is
 * referencing.  Coordinate is a node.
 */
public class Coordinate {

    private double xCoord, yCoord, zCoord;
    private Type type;//This will be the value that references the object at the coordinate space.

    public Coordinate(){
        this(0, 0, 0);
    }

    public Coordinate(int x, int y, int z){
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
    }
    public Coordinate(double x, double y, double z){
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
    }

    public void setX(double x){
        this.xCoord = x;
    }

    public void setY(double y){
        this.yCoord = y;
    }

    public void setZ(double z){
        this.zCoord = z;
    }

    public void setType(Type type){
        this.type = type;
    }



    public double getX(){
        return xCoord;
    }

    public double getY(){
        return yCoord;
    }

    public double getZ(){
        return zCoord;
    }


}