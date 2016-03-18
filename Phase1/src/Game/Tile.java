package Game;

import java.awt.*;

/**
 * Created by nibbla on 14.03.16.
 */
public class Tile {
    Type t;



    int x,y,z;
    public Coordinate coordinate;


    public static Tile newTile(Type t, int x, int y, int z){
        Tile a;
        if (t == Type.Hole) {
            a = new Hole(Config.getHoleRadius(), x, y, z);
        }else{
            a = new Tile(t, x, y, z);
        }
        return a;


    }
    public Tile(Type t, int x, int y, int z) {
        this.t = t;
        this.x = x;
        this.y = y;
        this.z = z;
        this.coordinate = new Coordinate(x,y,z);

    }

    double getFriction(){
        return t.getFriction();
    }
    public Color getColor(){
        return t.getColor();
    }

    public Type getType() {
        return t;
    }

    public Type getT() {
        return t;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setType(Type type) {
        this.t = type;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
