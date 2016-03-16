package GUIPackage;

import javax.swing.*;
import Game.Game;

public class GUI{

    JFrame guiFrame;
    public GUIMainMenu main;

    public GUI(){
        guiFrame = new JFrame();
        guiFrame.setSize(800,600);
    }

    public void getMainMenu() {
        main = new GUIMainMenu();
        guiFrame = main.getFrame();
        guiFrame.setSize(800,600);
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setVisible(true);
    }

    public void getEditor()  {

        GUIEditor edit = new GUIEditor();
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
