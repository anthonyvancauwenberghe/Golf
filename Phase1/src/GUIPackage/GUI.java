package GUIPackage;

import javax.swing.*;
import Game.Game;


public class GUI{

    public GUI(){
    }

    public void showMainMenu() {
        JFrame frame = new JFrame();
        MainMenu frame = new MainMenu();
        JPanel panelright = frame.getRight();
        frame.setTitle("Main Menu");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void showEditor(){
        EditorMenu editor = new EditorMenu();
        editor.getRight();
        editor.getLeft();
    }

    public void showGame(Game g) {
    }
}
