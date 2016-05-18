package WorkingUglyThing.Game;


import WorkingUglyThing.Game.Bots.TestBot;

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




    public Game(){

        course = Course.loadCourse("GolfDeluxe.gol");
        if (course == null) {
            course = new Course("GolfDeluxe", Config.getWidth(), Config.getHeight(), Config.getDepth(), Type.Grass, 1);

            course.addFrustrum(200,0,0,160,440,20,2,0,0,0,Type.OBJECT);
            course.addFrustrum(420,220,0,160,340,20,1,-3,1,-1,Type.OBJECT);
            course.addFrustrum(620,320,0,330,240,40,2,-1,1,-4,Type.OBJECT);
            course.addFrustrum(620,120,0,330,140,60,15,-4,15,-4,Type.OBJECT);
            //course.addFrustrum(650,440,0,110,140,200,15,0,0,-10,Type.OBJECT);
            //course.addFrustrum(520,120,0,160,140,20,2,0,0,0,Type.OBJECT);
            //course.addFrustrum(520,120,0,160,140,20,2,0,0,0,Type.OBJECT);
            //course.addFrustrum(520,120,0,160,140,20,2,0,0,0,Type.OBJECT);

            course.setTile(800, 600, 0, Type.Hole);
            course.setTile(100, 100, 0, Type.Start);
           // course.addRectangle(600, 400, 50, 100, 0, Type.OBJECT);
           // course.addRectangle(400, 400, 50, 100, 1, Type.OBJECT);
           // course.addCuboid(400, 400, 50, 100, 30, 20, Type.OBJECT);
           //course.addPyramid(50, 50, 0, 100, 30, 20, Type.OBJECT);
           // course.addHill(152, 152, 150, 1.5, 0, 20, Type.OBJECT);
           // course.addPyramid(400, 400, 0, 200, 200, 100, Type.OBJECT);

            System.out.println("CalculateSurfaceNormals");
            course.calculateSurfaceNormals();
            System.out.println("CalculateHightMap");
            course.calculateHeightMap();
            System.out.println("CalculateShadingMap");
            course.calculateShadingMap();
            System.out.println("CalculateDrawPanel");
            course.setBufferedImage(DrawPanel.createImage(course));

            course.saveCourse();
        }

        physics = new PhysicsEngine();
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

        physics.init(course, p.getBall());
        frame.add(dp);
        addMenues(frame);
        frame.setVisible(true);
        dp.repaint();
        Thread gameThread = new Thread(){
            public void run(){
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
                            ArrayList<Ball> balls = new ArrayList<>(8);
                            ArrayList<Ball> otherBalls = new ArrayList<>(8);
                            for (int i = 0; i < pp.size(); i++) {
                                balls.add(pp.get(i).getBall());
                                if (i!=currentPlayer) otherBalls.add(pp.get(i).getBall());
                            }



                            pp.get(currentPlayer).getBall().getPhysics().init(course, pp.get(currentPlayer).getBall());
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
        JMenuItem resetCourse = new JMenuItem("Reset Course");
        JMenuItem removePlayer = new JMenuItem("Remove Player");
        JMenuItem selectCourse = new JMenuItem("Select Course");
        JMenuItem resetStrokes = new JMenuItem("Reset Strokes");
        setting.add(addPlayer);
        setting.add(removePlayer);
        setting.add(resetCourse);
        setting.add(selectCourse);
        setting.add(resetStrokes);
        addListenerToAddPlayer(addPlayer);
        addListenerToRemovePlayer(removePlayer);
        addListenerToResetCourse(resetCourse);
        addListenerToSelectCourse(selectCourse);
        addListenerToResetStrokes(resetStrokes);
        JMenuItem showScore = new JMenuItem("Show Scores");
        addListenerToShowScore(showScore);
        setting.add(showScore);
    }

    private static void addListenerToResetCourse(JMenuItem resetCourse) {
        resetCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCourse(course);


            }
        });
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
            p.setBallPosition(t.x,t.y,t.z);
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



                Player p = new HumanPlayer(s);
                Tile t = course.getStartTile();
                p.setBallPosition(t.x,t.y,t.z);
                pp.add(p);
            }
        });
    }


}
