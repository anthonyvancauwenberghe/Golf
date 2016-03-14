import java.util.ArrayList;

public class Game {
    ArrayList<Player> Player;
    Player currentPlayer;
    Course c;
    ArrayList<Ball> b;
    Ball currentBall;
    Long time;

    public Game(){

    }



    public void changePlayer(){

    }

    public void shoot(double angle, double power){
        //Getting directional vector
        GolfPhysics physics = new GolfPhysics();
        double[] vector = physics.getVector(angle);

        //multiplying with power
        for (int i = 0; i < vector.length; i++){
            vector[i] = vector[i]*power;
        }

    }

    public boolean checkCollision(){
        return true;
    }

    public void moveBall(){

    }

}