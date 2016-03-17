package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by tony on 16/03/2016.
 */
public class Editor {

    private static Course course;
    public static JFrame frame;
    public static DrawPanel dp;
    private static int penWidth = 20;
    private static int penHeight = 20;
    private static shapeType selectedShape = shapeType.Squircle;
    private static Type selectedType = Type.Grass;

    public static void main(String[] args) {

        course = new Course("Golf Deluxe", Config.getWidth(), Config.getHeight(), 1, Type.Grass, 1);

        frame = new JFrame();
        dp = new DrawPanel();

        ArrayList<Player> pp = new ArrayList<>(2);

        Player p = new Player("Player 1");
        Player p2 = new Player("Player 2");


        //course.addRectangle(20, 40, 30, 50, Type.Sand);
        //course.addSquircle(20, 60, 20, 4, 0, Type.Water);

        course.setTile(400, 400, 0, Type.Hole);
        course.setTile(300, 400, 0, Type.Start);


        addMenues(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(Config.getWidth() + Config.OFFSET_X_EDITOR, Config.getHeight() + Config.OFFSET_Y_EDITOR);
        frame.setSize(Config.getWidth() + Config.OFFSET_X_GAME, Config.getHeight() + Config.OFFSET_Y_GAME);
        frame.add(dp);


        dp.setCourse(course);
        dp.repaint();


        while (true) {

            try {
                dp.repaint();
                Thread.sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
        JMenu selectShape = new JMenu("Select Shape");
        JMenuItem selectWidth = new JMenuItem("Select Width or Diameter");
        JMenuItem selectHeight = new JMenuItem("Select Height");


        selectShapeAddStuff(selectShape);
        frame.setJMenuBar(jmb);
        jmb.add(jm);
        jmb.add(jm2);
        jm2.add(saveCourse);
        jm2.add(loadCourse);
        jm.add(selectType);

        jm.add(selectWidth);
        jm.add(selectHeight);
        selectHeight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] possibilities = null;
                try {
                    penHeight = Integer.parseInt((String) JOptionPane.showInputDialog(
                            frame,
                            "Set Size", "Woop",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            possibilities,
                            "10"));
                } catch (NumberFormatException esd) {
                    penHeight = 10;
                }
            }
        });
        jm.add(selectShape);
        selectWidth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] possibilities = null;
                try {
                    penWidth = Integer.parseInt((String) JOptionPane.showInputDialog(
                            frame,
                            "Set Size", "Woop",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            possibilities,
                            "10"));
                } catch (NumberFormatException esd) {
                    penWidth = 10;
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
                File projectDir = new File(System.getProperty("user.dir"));
                chooser.setCurrentDirectory(projectDir);
                int returnVal = chooser.showOpenDialog(frame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    path = chooser.getSelectedFile().getAbsolutePath();
                    course = Course.loadCourse(path);
                    dp.setCourse(course);
                    dp.repaint();
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

        JMenuItem removeLast = new JMenuItem("Remove Last Object");
        jmb.add(removeLast);
        removeLast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                course.removeLastObject();
                dp.setCourse(course);
            }
        });
    }

    private static void selectShapeAddStuff(JMenu selectType) {

        JMenuItem selectCircle = new JMenuItem("Circle");
        JMenuItem selectSquircle = new JMenuItem("Squircle");
        JMenuItem selectRectangle = new JMenuItem("Rectangle");

        selectType.add(selectCircle);
        selectType.add(selectSquircle);
        selectType.add(selectRectangle);

        selectCircle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedShape = shapeType.Circle;
            }
        });
        selectSquircle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedShape = shapeType.Squircle;
            }
        });
        selectRectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedShape = shapeType.Rectangle;
            }
        });
    }

    public static void addObject(Point point) {
        switch (selectedShape) {
            case Rectangle:
                course.addRectangle(point.x, point.y, penWidth, penHeight, selectedType);
                break;
            case Squircle:
                course.addSquircle(point.x, point.y, penWidth, 4, 0, selectedType);
                break;
            case Circle:
                course.addSquircle(point.x, point.y, penWidth, 2, 0, selectedType);
                break;
        }



        dp.setCourse(course);
        dp.repaint();
    }

    public enum shapeType{
        Rectangle(),
        Squircle(),
        Circle()
    }
}



