package Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
    private static ArrayList<Player> pp;
    private static boolean selectNextPlayer;
    private static int currentPlayer=0;



    public static void main(String[] args) {

        course = new Course("Golf Deluxe", Config.getWidth(), Config.getHeight(), 1, Type.Grass, 1);
        physics = new PhysicsEngine();
        frame = new JFrame();
        dp = new DrawPanel();
        Thread thread = new Thread();

        pp = new ArrayList<Player>(2);

        Player p = new Player("Player 1");
        Player p2 = new Player("Player 2");


        course.setTile(400, 400, 0, Type.Hole);
        course.setTile(300, 400, 0, Type.Start);
        course.addRectangle(600, 400, 50, 100, Type.OBJECT);




        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(Config.getWidth() + Config.OFFSET_X_GAME, Config.getHeight() + Config.OFFSET_Y_GAME);
        frame.add(dp);
        addMenues(frame);



        pp.add(p);
        pp.add(p2);

        loadCourse(course);


        dp.setPlayers(pp);
        dp.setCurrentPlayer(p);
        dp.setCourse(course);

        physics.init(course, p.getBall());
        dp.repaint();

        while (true) {
            if (pp.get(currentPlayer).getBall().isMoving) {
                Player cp = pp.get(currentPlayer);

                selectNextPlayer=true;
                cp.getBall().getPhysics().init(course, cp.getBall());
                cp.getBall().getPhysics().processPhysics();
                cp.getBall().getPhysics().processNaturalForces();
                cp.getBall().checkBallStopped();
                cp.getBall().printBallInfo();
                try {
                    dp.repaint();
                    Thread.sleep(REFRESH_RATE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            } else {
                if (selectNextPlayer){
                    if (!IsGameStillOn()){
                        selectNextPlayer = false;
                        JOptionPane.showMessageDialog(null, "Round Finished", "End Round", JOptionPane.INFORMATION_MESSAGE);
                        showScoreOnScreen();
                    }
                    selectNextPlayer = false;

                    do {
                        currentPlayer=(currentPlayer+1)%(pp.size());
                    }while (!pp.get(currentPlayer).getBall().inPlay);

                    pp.get(currentPlayer).getBall().getPhysics().init(course, pp.get(currentPlayer).getBall());
                    dp.setCurrentPlayer(pp.get(currentPlayer));
                    JOptionPane.showMessageDialog(null, "It is " +  pp.get(currentPlayer).getName() + " turn", "Player", JOptionPane.INFORMATION_MESSAGE);
                    dp.repaint();
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    private static boolean IsGameStillOn() {

        for (int i = 0; i < pp.size(); i++) {
            if (pp.get(i).isInPlay()) return true;
        }
        return false;
    }

    private static void addMenues(JFrame frame) {
        JMenuBar jmb = new JMenuBar();
        frame.setJMenuBar(jmb);
        JMenu setting = new JMenu("Players");
        jmb.add(setting);
        JMenuItem addPlayer = new JMenuItem("Add Player");
        JMenuItem removePlayer = new JMenuItem("Remove Player");
        JMenuItem selectCourse = new JMenuItem("Select Course");
        JMenuItem resetStrokes = new JMenuItem("Reset Strokes");
        setting.add(addPlayer);
        setting.add(removePlayer);
        setting.add(selectCourse);
        setting.add(resetStrokes);
        addListenerToAddPlayer(addPlayer);
        addListenerToRemovePlayer(removePlayer);
        addListenerToSelectCourse(selectCourse);
        addListenerToResetStrokes(resetStrokes);
        JMenuItem showScore = new JMenuItem("Show Scores");
        addListenerToShowScore(showScore);
        setting.add(showScore);
    }

    private static void addListenerToShowScore(JMenuItem showScore) {



        showScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showScoreOnScreen();

            }
        });

    }

    private static void showScoreOnScreen() {
        StringBuilder scores = new StringBuilder();
        for (int i = 0; i < pp.size(); i++) {
            Player p = pp.get(i);
            scores.append("Player: " + p.getName() + "  Total Strokes: " + p.getTotalStrokes() + " Current Strokes: "  + p.getCurrentStrokes() + System.lineSeparator());
        }
        JOptionPane.showMessageDialog(null, scores.toString(), "Scores", JOptionPane.INFORMATION_MESSAGE);


    }

    private static void addListenerToResetStrokes(JMenuItem resetStrokes) {
        resetStrokes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < pp.size(); i++) {
                    pp.get(i).resetStrokes();

                }
            }
        });

    }

    private static void addListenerToSelectCourse(JMenuItem selectCourse) {
       selectCourse.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               String path = "";

               JFileChooser chooser = new JFileChooser();
               File projectDir = new File(System.getProperty("user.dir"));
               chooser.setCurrentDirectory(projectDir);
               int returnVal = chooser.showOpenDialog(frame);
               if (returnVal == JFileChooser.APPROVE_OPTION) {
                   path = chooser.getSelectedFile().getAbsolutePath();

                   loadCourse(path);

               }
           }
       });

    }
    private static void loadCourse(String path) {
        Course course = Course.loadCourse(path);
        loadCourse(course);
    }
    private static void loadCourse(Course course) {


        for (int i = 0; i < pp.size(); i++) {
            Player p = pp.get(i);
            p.resetBall();
            p.resetCurrentStrokes();
            p.setInPlay(true);
            p.setBallPosition(course.getStartTile().getCoordinate());
        }
        physics.init(course, pp.get(0).getBall());
        dp.setCourse(course);
        dp.setCurrentPlayer(pp.get(0));
        currentPlayer=0;
        dp.repaint();
    }

    private static void addListenerToRemovePlayer(JMenuItem removePlayer) {
        removePlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] possibilities = getPlayerNames(pp);
                        if (possibilities==null||possibilities.length==0)return;
                String s = (String) JOptionPane.showInputDialog(
                        frame,
                        "Name", "Enter Name",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        possibilities,
                        possibilities[0]);

                for (int i = pp.size()-1; i >= 0; i--) {
                    if (pp.get(i).getName().equals(s))pp.remove(i);
                }
            }
        });
    }

    private static String[] getPlayerNames(ArrayList<Player> pp) {
        if (pp == null) return null;
        String[] names = new String[pp.size()];
        for (int i = 0; i < pp.size(); i++) {
            names[i] = pp.get(i).getName();
        }
        return names;
    }

    private static void addListenerToAddPlayer(JMenuItem addPlayer) {
        addPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = (String) JOptionPane.showInputDialog(
                        frame,
                        "Name", "Enter Name",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "Hans");



                Player p = new Player(s);
                p.setBallPosition(course.getStartTile().getCoordinate());
                pp.add(p);
            }
        });
    }
}

