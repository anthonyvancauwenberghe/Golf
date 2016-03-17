package GUIPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ComboBoxJPanel extends JPanel {

    int MAX_COMBOS;
    ActionListener someActionListener;
    String[] options = {"1","2","3","4","5","6","7","8","9","10"};


    public ComboBoxJPanel() {

    }

    public JPanel getComboPanel(){

        JPanel comboPanel = new JPanel(new GridLayout(0, 1));
        JComboBox[] combos = new JComboBox[MAX_COMBOS]; // MAX_... is a constant
        for (int i = 0; i < combos.length; i++) {
            combos[i] = new JComboBox(options);
            combos[i].addActionListener(someActionListener);
            comboPanel.add(combos[i]);
        }
        return comboPanel;
    }
}
