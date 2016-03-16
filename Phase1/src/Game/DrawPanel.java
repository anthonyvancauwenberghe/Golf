package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.Transient;
import java.util.ArrayList;
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


    public void setPlayers(ArrayList<Player> p) {
        this.players = p;
    }

    public void setCurrentPlayer(Player p) {
        this.currentPlayer = p;
    }

    public DrawPanel() {
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                setFirstXClick(e.getX());
                setFirstYClick(e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("first x: " + getFirstXClick());
                System.out.println("first Y: " + getFirstYClick());
                System.out.println("second x: " + e.getX());
                System.out.println("second Y: " + e.getY());
                System.out.println("deltaX: " + (getFirstXClick() - e.getX()) / 10);
                System.out.println("deltaY: " + (getFirstYClick() - e.getY()));
                if (currentPlayer != null)
                    currentPlayer.getBall().shootBall((-(getFirstXClick() - e.getX())) / 2, -(getFirstYClick() - e.getY()), 0);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
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
                    g.setColor(t.getColor());
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
        for (int i = 0; i < players.size(); i++) {
            Ball b = players.get(i).getBall();
            g.setColor(Color.CYAN);
            Coordinate c = b.getCoordinate();
            double radius = b.getRadius();
            g.fillOval((int) (c.getX() - radius), (int) (c.getY() - radius), (int) radius * 2, (int) radius * 2);

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