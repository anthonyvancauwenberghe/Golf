/**
 * Created by lukas on 14/03/2016.
 */
public class Coordinates {
    double x;
    double y;
    double z;

    public Coordinates(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public void setCoordinates(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double[] getCoordinates(){
        double[] coords = {x, y, z};
        return coords;
    }

    public void setCoordinates2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double[] getCoordinates2D(){
        double[] coords = {x, y};
        return coords;
    }
}
