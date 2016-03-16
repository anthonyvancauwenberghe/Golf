package GUIPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class GUIEditor {

    JFrame frame;
    JPanel right;
    JPanel center;
    final int FRAME_WIDTH = 600;
    final int FRAME_HEIGTH = 400;

    public GUIEditor(){
        frame = new JFrame();
        createRight();
        createCenter();
        createButtons();
        frame.setTitle("Main Menu");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    void createRight(){
        right = new JPanel();
        right.setBackground(new Color(0, 90, 0));
        frame.add(right, BorderLayout.EAST);

    }
    void createCenter(){
        center = new JPanel();
        center.setBackground(new Color(0, 120, 0));
        frame.add(center, BorderLayout.CENTER);
    }
    void createButtons(){
        JButton button = new JButton("Place");
        right.add(button);
        ActionListener listener = new ClickListener();
        button.addActionListener(listener);
    }

    //Button 1


    //Image


    //Rest


}
