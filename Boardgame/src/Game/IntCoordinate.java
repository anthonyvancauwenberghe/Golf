package Game;

/**
 * Created by nibbla on 21.06.16.
 */
public class IntCoordinate {
    int x;
    int y;
    int z;

    public IntCoordinate(int x, int y, int z) {
        this.x=x;
        this.y = y;
        this.z = z;
    }

    public void set(int x, int y, int z) {
        this.x=x;
        this.y = y;
        this.z = z;

    }

    public int hashCode(){
        return (x * 102400) + (y * 76800) + (z);
    }

    public boolean equals(Object o){
        if (!(o instanceof IntCoordinate))return false;
        IntCoordinate i = (IntCoordinate) o;
        if (x ==i.x&&y==i.y&&z==i.z)return true;
        return false;
    }
}
