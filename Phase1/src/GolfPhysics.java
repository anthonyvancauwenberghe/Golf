

public class GolfPhysics {

    public GolfPhysics(){

    }

    public double[] getVector(double angle){
        double[] vector = new double[2];
        vector[0] = Math.cos(angle);
        vector[1] = Math.sin(angle);
        return vector;

    }
}
