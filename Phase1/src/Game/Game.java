package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by tony on 16/03/2016.
 */
public class Game {
    private static Course course;
    public static JFrame frame;
    private static PhysicsEngine physics;

    public static DrawPanel dp;

    final static private int REFRESH_RATE = 33;

    public static void main(String[] args) {

        course = new Course("Golf Deluxe", Config.getWidth(), Config.getHeight(), 1, Type.Grass, 1);
        physics = new PhysicsEngine();
        frame = new JFrame();
        dp = new DrawPanel();

        ArrayList<Player> pp = new ArrayList<>(2);

        Player p = new Player("Player 1");
        Player p2 = new Player("Player 2");


        course.setTile(400, 400, 0, Type.Hole);
        course.setTile(300, 400, 0, Type.Start);
        course.addRectangle(600, 400, 50, 100, Type.Water);

        pp.add(p);
        pp.add(p2);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(Config.getWidth() + Config.OFFSET_X_GAME, Config.getHeight() + Config.OFFSET_Y_GAME);
        frame.add(dp);
        frame.setResizable(false);


        p.setBallPosition(course.getStartTile().getCoordinate());

        dp.setPlayers(pp);
        dp.setCurrentPlayer(p);
        dp.setCourse(course);

        physics.init(course, p.getBall());
        dp.repaint();

        while (true) {
            if (p.getBall().isMoving) {
                p.getBall().getPhysics().init(course, p.getBall());
                p.getBall().getPhysics().processPhysics();
                p.getBall().getPhysics().processNaturalForces();
                p.getBall().checkBallStopped();
                p.getBall().printBallInfo();
                try {
                    dp.repaint();
                    Thread.sleep(REFRESH_RATE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }

    }
}

