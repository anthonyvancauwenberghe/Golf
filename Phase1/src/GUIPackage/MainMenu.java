package GUIPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenu{
    JFrame frame = new JFrame();

    final int FRAME_WIDTH = 600;
    final int FRAME_HEIGTH = 400;


    public JPanel getRight(){
        //Panel
        JPanel panel = new JPanel();
        panel.setBackground((new Color(0, 90, 0)));
        frame.add(panel, BorderLayout.EAST);
        //Button 1(editor)
        JButton editor = new JButton("Start Editor");
        editor.addActionListener(new ClickListener());
        panel.add(editor);

        //Button 2(game)
        JButton game = new JButton("Start Game");
        game.addActionListener(new ClickListener());
        panel.add(game);



        return panel;
    }

    public JPanel getLeft(){
        //Panel
        JPanel panel = new JPanel();
        panel.setBackground((new Color(0, 90, 0)));
        frame.add(panel, BorderLayout.CENTER);
        //Rest
        frame.setTitle("Main Menu");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        return panel;
    }


}
