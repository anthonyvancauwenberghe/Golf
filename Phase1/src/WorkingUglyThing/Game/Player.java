package WorkingUglyThing.Game;

import java.util.ArrayList;

/**
 * Created by Nibbla on 13.04.2016.
 */
public abstract class Player {
    private boolean inPlay = false;
    String name;
    int id;
    static int idCounter;
    Ball b = new Ball(Config.getBallMass(), Config.getBallRadius());

    int currentStrokes;
    int totalStrokes;


    public Ball getBall(){
        return b;
    }


    public boolean isInPlay(){
        return b.inPlay;
    }
    public void setInPlay(boolean t) {
        b.inPlay = t;


    }

    public abstract void nextMove(Course c, ArrayList<Ball> balls);

    public void shoot(double speedX, double speedY, double speedZ){
        b.shootBall(speedX,speedY,speedZ);
        addStroke();
    }

    public void setBallPosition(double x, double y, double z){
        b.x = x;
        b.y=y;
        b.z=z;
    }

    public Player(String name){
        this.name = name;
        this.id = idCounter++;
        this.currentStrokes=0;
        this.totalStrokes=0;
        resetBall();
    }

    public void resetCurrentStrokes(){
        currentStrokes = 0;
    }
    public void addStroke(){
        currentStrokes++;
        totalStrokes++;
    }

    public int getCurrentStrokes() {
        return currentStrokes;
    }

    public int getTotalStrokes() {
        return totalStrokes;
    }

    public void shootBall(int x, int y, int z) {
        b.shootBall(x,y,z);
        addStroke();
    }

    public String getName() {
        return name;
    }

    public void resetStrokes() {
        totalStrokes = 0;
        currentStrokes = 0;

    }

    public void resetBall() {
        this.b = new Ball(Config.getBallMass(), Config.getBallRadius());
    }
}
