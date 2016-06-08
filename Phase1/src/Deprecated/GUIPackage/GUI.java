package Deprecated.GUIPackage;

import javax.swing.*;
import Deprecated.Game.Game;
public class GUI{

    public JFrame guiFrame1;
    public JFrame guiFrame2;
    GUIMainMenu guiFrame;
    GUIEditor guiFrame0;
    public GUIMainMenu main;
    public GUIEditor edit;

    public GUI(){

    }

    public void close() {
        edit.disable();
    }

    public void getMainMenu() {
        guiFrame = new GUIMainMenu();
        guiFrame.setSize(900,688);
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setVisible(true);
    }

    public void getEditor()  {

        guiFrame0 = new GUIEditor();
        guiFrame0.setSize(800,600);
        guiFrame0.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame0.setVisible(true);

    }

    public void showGame(Game g) {

    }

    public void showTest(){

    }
}
