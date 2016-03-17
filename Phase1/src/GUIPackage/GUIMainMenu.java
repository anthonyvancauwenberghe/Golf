package GUIPackage;



import com.sun.glass.ui.Screen;
import com.sun.prism.Texture;
import Game.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Created by lukas on 16/03/16.
 */
public class GUIMainMenu {
    static JFrame frame;
    private JPanel panel1;
    private JButton EXITButton;
    private JButton courseEditorButton;
    private JButton startDefaultGameButton;
    private JPanel panel2;
    private JPanel panelwithIMG;
    private BufferedImage img;


    public GUIMainMenu() {

        BufferedImage img = null;

        try
        {
            img = ImageIO.read(new File("C:/ImageTest/pic2.jpg")); // eventually C:\\ImageTest\\pic2.jpg
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

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

                IntegrationMain main = new IntegrationMain();

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


