package Deprecated.GUIPackage;

import Deprecated.GUIPackage.IntegrationMain;
import Game.DrawPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by lukas on 18/03/16.
 */
public class Middleman {

    IntegrationMain main;
    JFrame frame;

    public Middleman() {


        JPanel main = new IntegrationMain();

        frame.add(main, BorderLayout.CENTER);
        frame = new JFrame();
        frame.setSize(900,688);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }
    JFrame getFrame(){
        return frame;
    }
}
