package Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by nibbla on 26.04.16.
 */
public class Editor_ThreeD {
    public static JFrame frame;
    public static DrawPanel dp;
    public static JPanel westInputPanel;
    public static JPanel northInputPanel;

    public static void main(String[] args) {

        frame = new JFrame();
        dp = new DrawPanel();
        frame.setLayout(new BorderLayout());
        frame.add(dp, BorderLayout.CENTER);
        frame.add(westInputPanel, BorderLayout.WEST);
        frame.add(northInputPanel, BorderLayout.NORTH);

        addMenues(frame);
    }

    private static void addMenues(JFrame frame) {
        frame.setVisible(true);
        frame.setSize(Config.getWidth() + Config.OFFSET_X_EDITOR, Config.getHeight() + Config.OFFSET_Y_EDITOR);
        frame.setSize(Config.getWidth() + Config.OFFSET_X_GAME, Config.getHeight() + Config.OFFSET_Y_GAME);



    }
}
