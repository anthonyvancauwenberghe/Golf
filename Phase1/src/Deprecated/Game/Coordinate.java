package Deprecated.Game;

import Game.Model.Type;

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
}