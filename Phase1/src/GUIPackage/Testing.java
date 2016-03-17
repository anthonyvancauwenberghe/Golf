package GUIPackage;

import javax.swing.*;

/**
 * Created by lukas on 17/03/16.
 */
public class Testing {
    public static void main(String[] args){

        IntegrationMain test = new IntegrationMain();
        JFrame frame = test.getFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}
