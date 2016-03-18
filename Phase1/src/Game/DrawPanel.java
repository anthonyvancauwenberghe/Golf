package Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.beans.Transient;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by nibbla on 16.03.16.
 */
public class DrawPanel extends JPanel {
    BufferedImage managedBufferedImage;
    Course course;
    public ArrayList<Player> players = new ArrayList<>();
    public int firstXClick = 0;
    public int firstYClick = 0;
    public Player currentPlayer;
    public BufferedImage grassTexture;
    public BufferedImage sandTexture;
    public BufferedImage waterTexture;
    public HashMap<Point, Color> GrassText = new HashMap<>();
    public HashMap<Point, Color> SandText= new HashMap<>();
    public HashMap<Point, Color> WaterText= new HashMap<>();

    public BufferedImage holeTexture;
    public BufferedImage objectTexture;
    public HashMap<Point, Color> HoleText= new HashMap<>();
    public HashMap<Point, Color> ObjectText = new HashMap<>();
    public TexturePaint holeP;
    public TexturePaint ballP;
    public BufferedImage ballTexture;
    public boolean prepareShoot;


    public void setPlayers(ArrayList<Player> p) {
        this.players = p;
    }

    public void setCurrentPlayer(Player p) {

        this.currentPlayer = p;

    }

    public DrawPanel() {
        loadTextures();
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()== MouseEvent.BUTTON3){
                    Editor.addObject(e.getPoint());

                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton()!= MouseEvent.BUTTON3) {
                    setFirstXClick(e.getX());
                    setFirstYClick(e.getY());
                    prepareShoot=true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton()!= MouseEvent.BUTTON3) {
                    /*System.out.println("first x: " + getFirstXClick());
                    System.out.println("first Y: " + getFirstYClick());
                    System.out.println("second x: " + e.getX());
                    System.out.println("second Y: " + e.getY());
                    System.out.println("deltaX: " + (getFirstXClick() - e.getX()) / 10);
                    System.out.println("deltaY: " + (getFirstYClick() - e.getY()));
                    */
                    if (currentPlayer != null)

                        currentPlayer.shootBall((-(getFirstXClick() - e.getX())) / 2, -(getFirstYClick() - e.getY()), 0);
                        prepareShoot = false;
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public void loadTextures() {



        try {
            File f =new File("Phase1/src/Game/textures/grass.jpg");
            grassTexture = ImageIO.read(f);
            sandTexture = ImageIO.read(new File("Phase1/src/Game/textures/sand.jpg"));
            waterTexture = ImageIO.read(new File("Phase1/src/Game/textures/water.jpg"));
            holeTexture = ImageIO.read(new File("Phase1/src/Game/textures/hole.jpg"));
            objectTexture = ImageIO.read(new File("Phase1/src/Game/textures/object.jpg"));
            ballTexture = ImageIO.read(new File("Phase1/src/Game/textures/ball.jpg"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (int x = 0; x < 32; x++) {
            for (int y = 0; y < 32; y++) {
                Point p = new Point(x,y);
                GrassText.put(p,new Color(grassTexture.getRGB(x , y )));
                SandText.put(p,new Color(sandTexture.getRGB(x, y )));
                WaterText.put(p,new Color(waterTexture.getRGB(x , y)));
                ObjectText.put(p,new Color(objectTexture.getRGB(x , y)));

            }
        }

        holeP = new TexturePaint(holeTexture,new Rectangle(0,0,32,32));
        ballP = new TexturePaint(ballTexture,new Rectangle(0,0,32,32));

    }

    public BufferedImage createImage() {
        Tile[][][] pf = course.getPlayfield();
        int[] d = course.getDimension();
        BufferedImage bufferedImage =
                new BufferedImage(d[0], d[1], BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        Random r = new Random(0);

        for (int x = 0; x < d[0]; x++) {
            for (int y = 0; y < d[1]; y++) {
                for (int z = 0; z < d[2]; z++) {
                    Tile t = pf[x][y][z];
                    if (t.getType() == Type.Empty) continue;
                    Point p = new Point(x%32,y%32);
                    if (t.getType() == Type.Grass) g.setColor(GrassText.get(p));
                    if (t.getType() == Type.Sand) g.setColor(SandText.get(p));
                    if (t.getType() == Type.Water) g.setColor(WaterText.get(p));
                    if (t.getType() == Type.OBJECT) g.setColor(ObjectText.get(p));

                    g.fillRect(x, y, 10, 10);

                }
            }

        }
        g.dispose();
        return bufferedImage;
    }


    public void setCourse(Course c) {
        this.course = c;
        managedBufferedImage = createImage();
    }


    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        if (managedBufferedImage != null) g.drawImage(managedBufferedImage, 0, 0, null);
        drawHole(g);

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).currentStrokes==0&&!currentPlayer.equals(players.get(i)))
                continue;

            Ball b = players.get(i).getBall();
            drawBall(g,b);


        }
        drawPowerLine(g,currentPlayer.getBall());

    }

    public void drawPowerLine(Graphics g, Ball b) {
        if (!prepareShoot) return;

        Point mp = MouseInfo.getPointerInfo().getLocation();
        Point pp = this.getLocationOnScreen();

        Point mousePosition = new Point(mp.x-pp.x,mp.y-pp.y);


        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(Config.POWERLINEWIDTH));
        g2.setColor(Color.red);
        Coordinate c = b.getCoordinate();
        g2.drawLine(firstXClick,firstYClick, mousePosition.x,mousePosition.y);



    }

    public void drawBall(Graphics g, Ball b) {
        Graphics2D g2 = (Graphics2D) g;

        Coordinate c = b.getCoordinate();
        double radius = b.getRadius();
        //if (c != null)
        g2.setPaint(ballP);
        g2.fillOval((int) (c.getX() - radius), (int) (c.getY() - radius), (int) radius*2, (int) radius*2);

    }

    public void drawHole(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Hole t = course.getHole();




        g2.setPaint(holeP);
        g2.fillOval((int) (t.getX() - t.radius), (int) (t.getY() - t.radius), (int) (t.radius*2), (int) (t.radius*2));


    }


    @Override
    @Transient
    public Dimension getPreferredSize() {
        return new Dimension(managedBufferedImage.getWidth(), managedBufferedImage.getHeight());
    }

    public int getFirstXClick() {

        return firstXClick;
    }

    public void setFirstXClick(int firstXClick) {
        this.firstXClick = firstXClick;
    }

    public int getFirstYClick() {

        return firstYClick;
    }

    public void setFirstYClick(int firstYClick) {
        this.firstYClick = firstYClick;
    }
}
