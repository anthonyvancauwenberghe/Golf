package GUIPackage;



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUIEditor {
    private JFrame frame;
    private JPanel panel1;
    private JPanel mainPane;
    private JButton EXITButton;
    private JPanel rightPane;
    private JTextArea EDITORTextArea;
    private JComboBox comboBox1;
    private JTextPane welcomeTextPane;
    private JButton butExit;
    private JButton butBackMain;

    public GUIEditor() {


        butBackMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI gui = new GUI();
                gui.getMainMenu();
            }
        });
        butExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public JFrame getFrame() {
        JFrame frame = new JFrame("GUIEditor");
        frame.setContentPane(new GUIEditor().panel1);


        return frame;
    }

}
