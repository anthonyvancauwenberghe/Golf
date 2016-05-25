package WorkingUglyThing.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Created by tony on 16/03/2016.
 * @deprecated
 */
public class Main extends JPanel {
    private static Type selectedType = Type.Grass;
    private static Course course;
    private static int pensize = 20;
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

        Player p = new HumanPlayer("Player 1");
        Player p2 = new HumanPlayer("Player 2");


        //course.addRectangle(20, 40, 30, 50, Type.Sand);
        //course.addSquircle(20, 60, 20, 4, 0, Type.Water);

        course.setTile(400, 400, 0, Type.Hole);
        course.setTile(300, 400, 0, Type.Start);

        pp.add(p);
        pp.add(p2);


        addMenues(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(Config.getWidth()+ Config.OFFSET_X_EDITOR, Config.getHeight() + Config.OFFSET_Y_EDITOR);
        frame.add(dp);


        Tile t = course.getStartTile();
        p.setBallPositionAndSpeed0(t.x,t.y,t.z);
        dp.setPlayers(pp);
        dp.setCurrentPlayer(p);
        dp.setCourse(course);
        dp.repaint();


        physics.init(pp,course);
        dp.repaint();
        while (true) {
            if (p.getBall().isMoving) {

                physics.processPhysics(1);


                //p.getBall().printBallInfo();
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

    private static void addMenues(JFrame frame) {
        JMenuBar jmb = new JMenuBar();
        JMenu jm = new JMenu("Type");
        JMenu jm2 = new JMenu("Files");
        JMenuItem saveCourse = new JMenuItem("Save Course");
        JMenuItem loadCourse = new JMenuItem("Load Course");
        JMenu selectType = new JMenu("Select Type");
        JMenuItem selectSize = new JMenuItem("Select Size");

        frame.setJMenuBar(jmb);
        jmb.add(jm);
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
                            "Set Size", "Woop",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            possibilities,
                            "10"));
                } catch (NumberFormatException esd) {
                    pensize = 10;
                }


            }
        });
        saveCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] possibilities = null;
                String s = (String) JOptionPane.showInputDialog(
                        frame,
                        "Set Weg", "Woop",
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
                String path = "";
                JFileChooser chooser = new JFileChooser();

                int returnVal = chooser.showOpenDialog(frame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
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

    public static void addObject(Point point) {

        course.addSquircle(point.x, point.y, pensize, 4, 0, selectedType);

        dp.setCourse(course);
        dp.repaint();
    }
}

