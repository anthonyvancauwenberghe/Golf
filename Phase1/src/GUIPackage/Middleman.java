package GUIPackage;

import Game.DrawPanel;

import javax.swing.*;

/**
 * Created by lukas on 18/03/16.
 */
public class Middleman {

    IntegrationMain main;
    JFrame frame;

    public Middleman() {


        IntegrationMain main = new IntegrationMain();
        frame = new JFrame();
        frame.add(main);
        frame.setSize(900,688);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }
}
