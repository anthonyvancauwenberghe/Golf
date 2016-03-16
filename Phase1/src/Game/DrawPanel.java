package Game;

import javax.swing.*;
import java.awt.*;
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
    private ArrayList<Ball> balls;

    private  BufferedImage createImage()
    {
        Tile[][][] pf = course.getPlayfield();
        int[] d = course.getDimension();
        BufferedImage bufferedImage =
                new BufferedImage(d[0], d[1], BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        Random r = new Random(0);

        for (int x=0; x<d[0]; x++)
        {
            for (int y=0; y<d[1]; y++)
            {
                for (int z=0; z<d[2]; z++)
                {
                    Tile t = pf[x][y][z];
                    if (t.getType()==Type.Empty)continue;
                    g.setColor(t.getColor());
                    g.fillRect(x, y, 10, 10);
                }
            }

        }
        g.dispose();
        return bufferedImage;
    }

    public void setCourse(Course c){
        this.course = c;
        managedBufferedImage = createImage();
    }
    public void setBalls(ArrayList<Ball> balls){
        this.balls = balls;
        managedBufferedImage = createImage();


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
            if (managedBufferedImage!=null) g.drawImage(managedBufferedImage, 0, 0, null);
            for (int i = 0; i < balls.size(); i++) {
                Ball b = balls.get(i);
                g.setColor(Color.CYAN);
                Coordinate c = b.getCoordinate();
                double radius = b.getRadius();
                g.fillOval((int) (c.getX()-radius),(int)(c.getY()-radius),(int)radius*2,(int)radius*2);

            }

    }


    @Override
    @Transient
    public Dimension getPreferredSize() {
        return new Dimension(managedBufferedImage.getWidth(), managedBufferedImage.getHeight());
    }
}
