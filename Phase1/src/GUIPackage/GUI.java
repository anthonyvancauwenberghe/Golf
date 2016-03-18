package GUIPackage;

import javax.swing.*;
import Game.Game;
public class GUI{

    public JFrame guiFrame;
    public JFrame guiFrame2;
    public GUIMainMenu main;
    public GUIEditor edit;

    public GUI(){
        guiFrame = new JFrame();
        guiFrame.setSize(800,600);
    }

    public void close() {
        edit.disable();
    }

    public void getMainMenu() {
        main = new GUIMainMenu();
        guiFrame = main.getFrame();
        guiFrame.setSize(900,688);
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setVisible(true);
    }

    public void getEditor()  {

        edit = new GUIEditor();
        guiFrame = edit.getFrame();
        guiFrame.setSize(800,600);
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setVisible(true);

    }

    public void showGame(Game g) {

    }

    public void showTest(){

    }
}
