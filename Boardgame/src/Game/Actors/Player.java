package Game.Actors;

import Game.Config;
import Game.HexGraph;

/**
 * Created by Nibbla on 13.04.2016.
 */
public abstract class Player {
    private boolean isPlayerRed;
    private boolean isPlayerBlue;
    boolean inPlay = false;
    String name;
    int id;
    static int idCounter;
    private boolean pregame;




    public abstract void nextMove(HexGraph graph);


    public Player(String name, boolean player1){
        this.name = name;
        this.id = idCounter++;
        if (player1) this.isPlayerBlue = true;
        else this.isPlayerRed = true;

    }

    public String getName() {
        return name;
    }




}
