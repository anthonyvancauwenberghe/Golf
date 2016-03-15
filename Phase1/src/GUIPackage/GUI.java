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
        frame.setTitle("Main Menu");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        ActionListener listener = new ClickListener();


        //Panel
        JPanel left = new JPanel();
        JPanel right = new JPanel();
        left.setBackground(new Color(0,90,0));
        right.setBackground(new Color(0,90,0));
        //add panel to frame
        frame.add(right, BorderLayout.EAST);
        frame.add(left, BorderLayout.CENTER);

        //Button 1
        JButton editor = new JButton("Start Editor");
        right.add(editor);
        editor.addActionListener(listener);

        //Button 2
        JButton game = new JButton("Start Game");
        right.add(game);
        editor.addActionListener(listener);

    }

    public void showEditor()  {

        JFrame frame = new JFrame();

        final int FRAME_WIDTH = 600;
        final int FRAME_HEIGTH = 400;


        //Panel
        JPanel right = new JPanel();
        JPanel center = new JPanel();
        center.setBackground(new Color(0,90,0));
        right.setBackground(new Color(0,90,0));
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
