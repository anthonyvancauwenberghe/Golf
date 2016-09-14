package Game.Actors;


import Game.HexGraph;

import javax.swing.*;

/**
 * Created by nibbla on 14.03.16.
 */
public class HumanPlayer extends Player {

    @Override
    public void nextMove(HexGraph graph) {
        JOptionPane.showMessageDialog(null, "It is " +  name + " turn", "Player", JOptionPane.INFORMATION_MESSAGE);
    }



    public HumanPlayer(String name, boolean playerBlue) {
        super(name, playerBlue);
    }
}
