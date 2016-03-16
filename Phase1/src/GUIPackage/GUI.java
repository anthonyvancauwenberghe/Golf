package GUIPackage;

import javax.swing.*;
import Game.Game;

import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JPanel{

    public GUI(){
        showMainMenu();
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
        JButton editor = new JButton("Start GUIEditor");
        right.add(editor);
        editor.addActionListener(listener);

        //Button 2
        JButton game = new JButton("Start Game");
        right.add(game);
        editor.addActionListener(listener);

    }

    public void showEditor()  {

        GUIEditor editor = new GUIEditor();

    }

    public void showGame(Game g) {

    }
}
