package GUIPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EditorMenu{
    JFrame frame = new JFrame();

    final int FRAME_WIDTH = 600;
    final int FRAME_HEIGTH = 400;


    public JPanel getRight(){
        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.EAST);
        //Button 1
        JButton button = new JButton("Place");
        panel.add(button);
        ActionListener listener = new ClickListener();
        button.addActionListener(listener);
        //Rest
        frame.setTitle("Main Menu");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        return panel;
    }

    public JPanel getLeft(){
        //Panel
        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.WEST);
        //Rest
        frame.setTitle("Main Menu");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        return panel;
    }


}