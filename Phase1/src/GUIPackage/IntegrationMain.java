package GUIPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import Game.*;
import Game.Bots.TestBot;

/**
 * Created by tony on 16/03/2016.
 */
public class IntegrationMain extends JPanel{
    public Type selectedType = Type.Grass;
    public int selectedSize = 1;
    public Course course;
    public int pensize = 20;
    DrawPanel dp;
    JFrame frame;

    public IntegrationMain() {
        start();
    }

    JFrame getFrame(){
        return frame;
    }

    JPanel getPanel(){
        return dp;
    }

    public void start(){

        int counter = 0;

        course = new Course("Golf Deluxe", 800, 600, 1, Type.Grass, 1 );
        course.addRectangle(40, 30, 50,1, 0, Type.Sand);
        course.addSquircle(20,60,20,4,0,Type.Water);
        course.setTile(400,400,0,Type.Hole);
        course.setTile(300,400,0,Type.Start);
        ArrayList<Player> pp = new ArrayList<>(2);
        Player p = new HumanPlayer("PlayerEins");
        Player p2 = new TestBot("PlayerZwei");

        pp.add(p);
        pp.add(p2);

        frame = new JFrame();
        addMenues(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
        frame.setSize(800, 600);

        dp = new DrawPanel();
        frame.add(dp);
        Tile t = course.getStartTile();
        p.setBallPosition(t.getX(),t.getY(),t.getZ());
        dp.setPlayers(pp);
        dp.setCurrentPlayer(p);
        dp.setCourse(course);
        dp.repaint();

        PhysicsEngine physics = new PhysicsEngine();
        ArrayList<Ball> balls = new ArrayList<>(8);
        for (int i = 0; i < pp.size(); i++) {
            balls.add(pp.get(i).getBall());
        }
        physics.init(course, balls);
        dp.repaint();
        while(true){
            if(p.getBall().isMoving){
                p.getBall().getPhysics().init(course,balls);
                p.getBall().getPhysics().processPhysics(Config.timeStep);
                p.getBall().getPhysics().processNaturalForces();
                p.getBall().checkBallStopped();
                p.getBall().printBallInfo();
                try {
                    dp.repaint();
                    Thread.sleep(500);
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

    private void addMenues(JFrame frame) {
        JMenuBar jmb = new JMenuBar();
        JMenu jm = new JMenu("Type");
        JMenu jm2 = new JMenu("Files");
        JMenuItem saveCourse = new JMenuItem("Save Course");
        JMenuItem loadCourse = new JMenuItem("Load Course");
        JMenu selectType = new JMenu("Select Type");
        JMenuItem selectSize = new JMenuItem("Select Size");

        frame.setJMenuBar(jmb); jmb.add(jm);
        jmb.add(jm2);
        jm2.add(saveCourse);
        jm2.add(loadCourse);
        jm.add(selectType);
        jm.add(selectSize);
        selectSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] possibilities = null;
                try {
                    pensize = Integer.parseInt((String) JOptionPane.showInputDialog(
                            frame,
                            "Set Size","Woop",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            possibilities,
                            "10"));
                }catch (NumberFormatException esd){
                    pensize = 10;
                }


            }
        });
        saveCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] possibilities = null;
                String s = (String)JOptionPane.showInputDialog(
                        frame,
                        "Set Weg","Woop",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        possibilities,
                        "Course");

                course.setName(s);
                course.saveCourse();
            }
        });
        loadCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path ="";
                JFileChooser chooser = new JFileChooser();

                int returnVal = chooser.showOpenDialog(frame);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    path = chooser.getSelectedFile().getAbsolutePath();
                    course.loadCourse(path);
                }

            }
        });
        for (int i = 0; i < Type.values().length; i++) {
            JMenuItem typei = new JMenuItem(Type.values()[i].name());
            selectType.add(typei);
            final int finalI = i;
            typei.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedType = Type.values()[finalI];
                }
            });
        }

        //jmb.add()
        //frame.add(new)
    }

    public void addObject(Point point) {

        course.addSquircle(point.x, point.y, pensize, 4, 0, selectedType);

        dp.setCourse(course);
        dp.repaint();
    }
}

