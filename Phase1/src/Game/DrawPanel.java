package Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    private ArrayList<Player> players = new ArrayList<>();
    private int firstXClick = 0;
    private int firstYClick = 0;
    private Player currentPlayer;
    private BufferedImage grassTexture;
    private BufferedImage sandTexture;
    private BufferedImage waterTexture;
    private HashMap<Point, Color> GrassText = new HashMap<>();
    private HashMap<Point, Color> SandText= new HashMap<>();
    private HashMap<Point, Color> WaterText= new HashMap<>();

    private BufferedImage holeTexture;
    private HashMap<Point, Color> HoleText= new HashMap<>();


    public void setPlayers(ArrayList<Player> p) {
        this.players = p;
    }

    public void setCurrentPlayer(Player p) {

        this.currentPlayer = p;
        p.setInPlay(true);
    }

    public DrawPanel() {
        loadTextures();
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

    private void loadTextures() {



        try {
            File f =new File("Phase1/src/Game/textures/grass.jpg");
            grassTexture = ImageIO.read(f);
            sandTexture = ImageIO.read(new File("Phase1/src/Game/textures/sand.jpg"));
            waterTexture = ImageIO.read(new File("Phase1/src/Game/textures/water.jpg"));
            holeTexture = ImageIO.read(new File("Phase1/src/Game/textures/hole.jpg"));
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
            }
        }

    }

    private BufferedImage createImage() {
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

        Hole t = course.getHole();

        g.fillOval((int) (t.getX() - t.radius), (int) (t.getY() - t.radius), (int) (t.radius*2), (int) (t.radius*2));
        for (int i = 0; i < players.size(); i++) {
            if (!players.get(i).isInPlay())
                continue;
            Ball b = players.get(i).getBall();
            g.setColor(Color.WHITE);
            Coordinate c = b.getCoordinate();
            double radius = b.getRadius();
            //if (c != null)
                g.fillOval((int) (c.getX() - radius), (int) (c.getY() - radius), (int) radius*2, (int) radius*2);

        }

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
