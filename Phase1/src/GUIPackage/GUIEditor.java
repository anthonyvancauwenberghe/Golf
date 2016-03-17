package GUIPackage;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUIEditor {
    private JFrame frame;
    private JPanel panel1;
    private JPanel mainPane;
    private JButton EXITButton;
    private JPanel rightPane;
    private JPanel testPane;
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

    public JPanel getComboPanel(int MAX_COMBOS){

        String[] options = {"0","1","2","3","4","5","6","7","8","9","10"};
        JPanel comboPanel = new JPanel(new GridLayout(10, 10));
        JComboBox[] combos = new JComboBox[MAX_COMBOS]; // MAX_... is a constant
        for (int i = 0; i < combos.length; i++) {
            combos[i] = new JComboBox(options);
            //combos[i].addActionListener(someActionListener);
            comboPanel.add(combos[i]);
        }
        return comboPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        testPane = getComboPanel(100);

    }
}
