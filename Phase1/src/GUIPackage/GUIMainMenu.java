package GUIPackage;



import Game.Game;
import Game.Editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


/**
 * Created by lukas on 16/03/16.
 */
public class GUIMainMenu extends JFrame {
    public JFrame frame;
    private JPanel panel1;
    private JButton exitB;
    private JButton editorB;
    private JButton startB;
    private JPanel panelwithIMG;
    private BufferedImage img;

    Icon start1 = new ImageIcon("out/GUIPackage/media/start1.png");
    Icon start2 = new ImageIcon("out/GUIPackage/media/start2.png");
    Icon editor1 = new ImageIcon("out/GUIPackage/media/editor1.png");
    Icon editor2 = new ImageIcon("out/GUIPackage/media/editor2.png");
    Icon exit1 = new ImageIcon("out/GUIPackage/media/exit1.png");
    Icon exit2 = new ImageIcon("out/GUIPackage/media/exit2.png");

    static GUIMainMenu g;
    public GUIMainMenu() {

        g = this;
        this.getContentPane().add(panel1);

        startB.setOpaque(false);
        startB.setFocusPainted(false);
        startB.setBorderPainted(false);
        startB.setContentAreaFilled(false);
        exitB.setOpaque(false);
        exitB.setFocusPainted(false);
        exitB.setBorderPainted(false);
        exitB.setContentAreaFilled(false);
        editorB.setOpaque(false);
        editorB.setFocusPainted(false);
        editorB.setBorderPainted(false);
        editorB.setContentAreaFilled(false);




        //ACTIONLISTENERS
        editorB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Editor.main(null);


            }
        });
        startB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //IntegrationMain main = new IntegrationMain();
                //Middleman dude = new Middleman();
                //frame.getContentPane().removeAll();
                //frame.getContentPane().add(new IntegrationMain());
                    Game g = new Game();


                    //this.getContentPane().add(panel1);



            }
        });
        exitB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        //MOUSELISTENERS
        startB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startB.setIcon(start2);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startB.setIcon(start1);
            }
        });
        editorB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editorB.setIcon(editor2);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editorB.setIcon(editor1);
            }
        });
        exitB.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitB.setIcon(exit2);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitB.setIcon(exit1);
            }
        });
    }

    public void createUIComponents() {



        panelwithIMG = new JPanelIMG(new ImageIcon("src/GUIPackage/media/caddy.jpg").getImage());


        //b.setIcon(Icon x);
        //b.setDisabledIcon(Icon x);
        //b.setPressedIcon(Icon x);
        //b.setSelectedIcon(Icon x);
        //b.setDisabledSelectedIcon(Icon x);


    }

    public JFrame getFrame() {
        JFrame frame = new JFrame("GUIEditor");
        frame.setContentPane(new GUIMainMenu().panel1);

        return frame;
    }


}


