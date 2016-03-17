package GUIPackage;

import javax.swing.*;

/**
 * Created by lukas on 17/03/16.
 */
public class Testing {

    IntegrationMain test;
    JFrame frame;


    public Testing() {

    }

    void go(){
        test = new IntegrationMain();
        frame = test.getFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        test.start();
    }
}
