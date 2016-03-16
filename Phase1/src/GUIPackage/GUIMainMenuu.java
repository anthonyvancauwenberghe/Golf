package GUIPackage;

import javax.swing.*;

/**
 * Created by esther on 3/16/16.
 */
public class GUIMainMenuu extends JPanel{
    private JPanel panel1;
    private JButton startButton;
    private JButton editorButton;
    private JButton multiplayerButton;
    //SFD
    public GUIMainMenuu() {

    }

    private void createUIComponents() {

    }

    public JFrame getFrame() {
        JFrame frame = new JFrame("GUIEditor");
        frame.setContentPane(new GUIMainMenuu().panel1);


        return frame;
    }
}
