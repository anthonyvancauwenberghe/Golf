package Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by tony on 16/03/2016.
 */
public class Main {
    public static void main(String [] args){
        int counter = 0;


        Course course = new Course("Golf Deluxe", 800, 600, 1, Type.Grass, 1 );
        course.addRectangle(20, 40, 30, 50, 0, Type.Sand);
        course.addSquircle(20,60,20,4,0,Type.Water);
        course.setTile(400,400,0,Type.Hole);
        course.setTile(20,20,0,Type.Start);
        ArrayList<Player> pp = new ArrayList<>(2);
        Player p = new Player("PlayerEins");
        Player p2 = new Player("PlayerZwei");
        pp.add(p);
        pp.add(p2);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
        frame.setSize(800, 600);

        DrawPanel dp = new DrawPanel();

        frame.add(dp);

        p.setBallPosition(course.getStartTile().getCoordinate());
        dp.setPlayers(pp);
        dp.setCurrentPlayer(p);
        dp.setCourse(course);
        dp.repaint();

        PhysicsEngineFinal physics = new PhysicsEngineFinal();

        physics.init(course, p.getBall());
        dp.repaint();
        while(true){
            if(p.getBall().isMoving){
                p.getBall().getPhysics().init(course, p.getBall());
            p.getBall().getPhysics().processPhysics();
                p.getBall().getPhysics().processNaturalForces();
                p.getBall().checkBallStopped();
                p.getBall().printBallInfo();
                try {
                    Runnable repaint = new Runnable(){

                        public void run(){
                            dp.repaint();
                        }
                    };
                    Thread paintThread = new Thread(repaint);
                    paintThread.start();
                    Thread.sleep(33);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
            else{
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            }

        }
    }

