package GUIPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import Game.Game;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GUI extends JPanel{

    public GUI(){

    }

    public void showMainMenu() {

        JFrame frame = new JFrame();

        final int FRAME_WIDTH = 600;
        final int FRAME_HEIGTH = 400;


        //Panel
        JPanel left = new JPanel();
        JPanel right = new JPanel();
        frame.add(right, BorderLayout.EAST);
        frame.add(left, BorderLayout.WEST);

        //Button 1
        JButton button = new JButton("Start Editor");
        right.add(button);
        ActionListener listener = new ClickListener();
        button.addActionListener(listener);

        //Rest
        frame.setTitle("Main Menu");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public void showEditor()  {

        JFrame frame = new JFrame();

        final int FRAME_WIDTH = 600;
        final int FRAME_HEIGTH = 400;


        //Panel
        JPanel right = new JPanel();
        JPanel center = new JPanel();
        frame.add(right, BorderLayout.EAST);
        frame.add(center, BorderLayout.CENTER);

        //Button 1
        JButton button = new JButton("Place");
        right.add(button);
        ActionListener listener = new ClickListener();
        button.addActionListener(listener);

        //Image


        //Rest
        frame.setTitle("Main Menu");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public void showGame(Game g) {

    }
}
