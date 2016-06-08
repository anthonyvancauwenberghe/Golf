package Game.Actors;

import Game.Model.Ball;
import Game.Model.Course;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by nibbla on 14.03.16.
 */
public class HumanPlayer extends Player {

    @Override
    public void nextMove(Course c, ArrayList<Ball> balls) {
        JOptionPane.showMessageDialog(null, "It is " +  name + " turn", "Player", JOptionPane.INFORMATION_MESSAGE);
    }

    public HumanPlayer(String name) {
        super(name);
    }
}
