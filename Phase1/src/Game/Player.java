package Game;

/**
 * Created by nibbla on 14.03.16.
 */
public class Player {
    String name;
    int id;
    static int idCounter;

    int currentStrokes;
    int totalStrokes;

    public Player(String name){
        this.name = name;
        this.id = idCounter++;
        this.currentStrokes=0;
        this.totalStrokes=0;
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
}
