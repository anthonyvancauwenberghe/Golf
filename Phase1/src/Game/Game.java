package Game;

import Game.Bots.AIPlayer;
import Game.Bots.TestBot;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import static java.lang.Math.floor;

/**
 * Created by tony on 16/03/2016.
 */
public class Game {
    public static Course course;
    public static JFrame frame;
    private static PhysicsEngine physics;

    public static DrawPanel dp;

    final static private int REFRESH_RATE = 33;
    private static ArrayList<Player> pp;
    private static boolean selectNextPlayer;
    private static int currentPlayer=0;




    public Game(){

        course = Course.loadCourse("GolfDeluxe.gol");
        if (course == null) {
            course = new Course("GolfDeluxe", Config.getWidth(), Config.getHeight(), Config.getDepth(), Type.Grass, 1);



            course.setTile(400, 400, 0, Type.Hole);
            course.setTile(300, 400, 0, Type.Start);
            course.addRectangle(600, 400, 50, 100, 0, Type.OBJECT);
            course.addRectangle(400, 400, 50, 100, 1, Type.OBJECT);
            course.addCuboid(400, 400, 50, 100, 30, 20, Type.OBJECT);
            course.addPyramid(200, 200, 50, 100, 30, 20, Type.OBJECT);
            course.addHill(152, 152, 150, 1.5, 0, 20, Type.OBJECT);
            course.addPyramid(400, 400, 0, 200, 200, 100, Type.OBJECT);
            course.saveCourse();
        }

        physics = new PhysicsEngine();
        frame = new JFrame();
        dp = new DrawPanel();
        Thread thread = new Thread();

        pp = new ArrayList<Player>(2);

        Player p = new HumanPlayer("Player 1");
        Player p2 = new TestBot("Player 2");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(Config.getWidth() + Config.OFFSET_X_GAME, Config.getHeight() + Config.OFFSET_Y_GAME);




        pp.add(p);
        pp.add(p2);

        loadCourse(course);


        dp.setPlayers(pp);
        dp.setCurrentPlayer(p);
        dp.setCourse(course);


        frame.add(dp);
        addMenues(frame);
        frame.setVisible(true);
        dp.repaint();
        ArrayList<Ball> balls = new ArrayList<>(8);
        for (int i = 0; i < pp.size(); i++) {
            balls.add(pp.get(i).getBall());
        }

        physics.init(course,balls);

        Thread gameThread = new Thread(){

            long elapsedTime = 0;
            long currentTime = 0;
            long leftOverTime = 0;
            long lastTime = System.currentTimeMillis();

            public void run(){

                while (true) {
                    if (isSomeBallMoving()) {
                        Player cp = pp.get(currentPlayer);

                        selectNextPlayer=true;
                        currentTime = System.currentTimeMillis();
                        long elapsedTime =  currentTime - lastTime;
                        lastTime = currentTime; // reset lastTime

                        // add time that couldn't be used last frame
                        elapsedTime += leftOverTime;

                        // divide it up in chunks of 16 ms
                        long timesteps = elapsedTime / Config.timeStep;

                        // store time we couldn't use for the next frame.
                        leftOverTime = elapsedTime - timesteps* Config.timeStep;

                        for (int i = 0; i < timesteps; i++) {
                            physics.processPhysics(Config.timeStepSeconds);
                        }


                        cp.getBall().printBallInfo();
                        try {
                            dp.repaint();
                            Thread.sleep(REFRESH_RATE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    } else {
                        lastTime = System.currentTimeMillis();

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

                            ArrayList<Ball> otherBalls = new ArrayList<>(8);

                            for (int i = 0; i < pp.size(); i++) {
                                if (i!=currentPlayer) otherBalls.add(pp.get(i).getBall());
                            }

                            dp.setCurrentPlayer(pp.get(currentPlayer));
                            pp.get(currentPlayer).nextMove(course,otherBalls);
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
        };
        gameThread.start();


    }

    private boolean isSomeBallMoving() {
        for (int i = 0; i < pp.size(); i++) {
            if (pp.get(currentPlayer).getBall().isMoving) return true;
        }


        return false;
    }

    public static void main(String[] args) {
        Game g = new Game();


    }

    private static boolean IsGameStillOn() {

        for (int i = 0; i < pp.size(); i++) {
            if (pp.get(i).isInPlay()) return true;
        }
        return false;
    }

    private static void addMenues(JFrame frame) {
        JMenuBar JMB = new JMenuBar();
        frame.setJMenuBar(JMB);
        JMenu setting = new JMenu("Players");
        JMB.add(setting);
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
            Tile t = course.getStartTile();
            p.setBallPosition(t.x,t.y,t.z+1);
        }

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



                Player p = new HumanPlayer(s);
                Tile t = course.getStartTile();
                p.setBallPosition(t.x,t.y,t.z);
                pp.add(p);
            }
        });
    }


}

