package GUIPackage;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GUI {

    public GUI(){

    }

    public void showMainMenu() {

        JFrame frame = new JFrame();

        final int FRAME_WIDTH = 600;
        final int FRAME_HEIGTH = 400;


        //Panel
        JPanel container = new JPanel();
        frame.add(container);

        //Button 1
        JButton button = new JButton("Click me!");
        container.add(button);
        //ActionListener listener = new ClickListener();
        //button.addActionListener(listener);

        //Rest
        frame.setTitle("Main Menu");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public void showEditor(Game g) {



    }

    public void showGame(Game g) {

    }
}
