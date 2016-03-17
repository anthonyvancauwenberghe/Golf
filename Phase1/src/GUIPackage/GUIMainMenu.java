package GUIPackage;



import com.sun.glass.ui.Screen;
import com.sun.prism.Texture;
import Game.Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


/**
 * Created by lukas on 16/03/16.
 */
public class GUIMainMenu {
    private JPanel panel1;
    private JButton EXITButton;
    private JButton courseEditorButton;
    private JButton startDefaultGameButton;
    private JPanel panel2;
    //final private Texture texture = new Texture("grass.jpg");
    //final private Image image = new Image(texture);
    //private BufferedImage image2 = new BufferedImage(image);

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUIMainMenu");
        frame.setContentPane(new GUIMainMenu().panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public GUIMainMenu() {
try {

    startDefaultGameButton.setOpaque(false);
    startDefaultGameButton.setFocusPainted(false);
    startDefaultGameButton.setBorderPainted(false);
    startDefaultGameButton.setContentAreaFilled(false);
    //setBorder(BorderFactory.createEmptyBorder(0,0,0,0)); // Especially important
    courseEditorButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            GUI gui = new GUI();
            gui.getEditor();

        }
    });
} catch(Exception e){
    e.printStackTrace();
}

        startDefaultGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                IntegrationMain test = new IntegrationMain();
                JFrame frame = test.getFrame();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                test.start();

            }
        });
    }

    public void createUIComponents() {

    }

    public JFrame getFrame() {
        JFrame frame = new JFrame("GUIEditor");
        frame.setContentPane(new GUIMainMenu().panel1);

        return frame;
    }
}
