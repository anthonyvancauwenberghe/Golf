package GUIPackage;

import com.sun.tools.javah.Util;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lukas on 16/03/16.
 */
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
        frame = new JFrame("GUIEditor");

        butBackMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUI().showMainMenu();
            }
        });
        butExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUIEditor");
        frame.setContentPane(new GUIEditor().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
