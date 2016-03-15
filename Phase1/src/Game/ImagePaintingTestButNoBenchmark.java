package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.beans.Transient;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class ImagePaintingTestButNoBenchmark
{
    private static Course course;

    public static void main(String[] args)
    {

        //course = new Course("Test2",600,400,100,null,4);
        //course.saveCourse();
        course = Course.loadCourse("Test2.txt");

        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI()
    {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        BufferedImage managedBufferedImage = createImage();
        BufferedImage unmanagedBufferedImage = createImage();
        makeUnmanaged(unmanagedBufferedImage);

        final ImagePaintingPanel p = new ImagePaintingPanel(
                managedBufferedImage, unmanagedBufferedImage);
        f.add(p);

        startRepaintingThread(p);

        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private static void startRepaintingThread(final JComponent c)
    {
        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    c.repaint();
                    try
                    {
                        Thread.sleep(5);
                    }
                    catch (InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }

    private static BufferedImage createImage()
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

    private static void makeUnmanaged(BufferedImage bufferedImage)
    {
        DataBuffer dataBuffer = bufferedImage.getRaster().getDataBuffer();
        DataBufferInt dataBufferInt = (DataBufferInt)dataBuffer;
        int data[] = dataBufferInt.getData();
        System.out.println("Unmanaged "+data[0]);
    }

}


class ImagePaintingPanel extends JPanel
{
    private final BufferedImage managedBufferedImage;
    private final BufferedImage unmanagedBufferedImage;
    private int x;
    private int y;

    ImagePaintingPanel(
            BufferedImage managedBufferedImage,
            BufferedImage unmanagedBufferedImage)
    {
        this.managedBufferedImage = managedBufferedImage;
        this.unmanagedBufferedImage = unmanagedBufferedImage;
    }

    @Override
    @Transient
    public Dimension getPreferredSize()
    {
        return new Dimension(
                managedBufferedImage.getWidth()+
                        unmanagedBufferedImage.getWidth(),
                Math.max(
                        managedBufferedImage.getHeight(),
                        unmanagedBufferedImage.getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        long before = 0;
        long after = 0;

        before = System.nanoTime();
        g.drawImage(managedBufferedImage, 0, 0, null);
        after = System.nanoTime();
        double managedDuration = (after-before)/1e6;

        before = System.nanoTime();
        g.drawImage(unmanagedBufferedImage,
                managedBufferedImage.getWidth(), 0, null);
        after = System.nanoTime();
        double unmanagedDuration = (after-before)/1e6;
        g.setColor(Color.blue);
        g.fillOval(x,y,10,10);
        g.setColor(Color.BLACK);
        g.drawOval(x++, y++, 10, 10);
        if (x>500) x = 0;
        if (y>400) y = 0;
        System.out.printf("Managed   %10.5fms\n", managedDuration);
        System.out.printf("Unanaged  %10.5fms\n", unmanagedDuration);
    }

}