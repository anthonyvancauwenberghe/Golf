/**
 * This is the coordinate class which holds the x, y, z coordinates and the type of object the coordinate is
 * referencing.
 */
public class Coordinate {

    private int xCoord, yCoord, zCoord;
    private Type type;//This will be the value that references the object at the coordinate space.
    private Coordinate next, previous;

    public Coordinate(){
        this(0, 0, 0, null);
    }

    public Coordinate(int x, int y, int z, Type type){
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
        this.type = type;
        this.next = null;
        this.previous = null;
    }

    public void setX(int x){
        this.xCoord = x;
    }

    public void setY(int y){
        this.yCoord = y;
    }

    public void setZ(int z){
        this.zCoord = z;
    }

    public void setType(Type type){
        this.type = type;
    }

    public void setNext(Coordinate next){
        this.next = next;
    }

    public void setPrevious(Coordinate previous){
        this.previous = previous;
    }

    public int getX(){
        return xCoord;
    }

    public int getY(){
        return yCoord;
    }

    public int getZ(){
        return zCoord;
    }

    public Type getType(){
        return type;
    }

    public Coordinate getNext(){
        return next;
    }

    public Coordinate getPrevious(){
        return previous;
    }
}