import java.applet.Applet;
import java.awt.*;

public class PhysicsEgine extends Applet implements Runnable {

    int x = 400;
    int y = 400;
    int radius = 20;
    double dx = 1;
    double dy = 0; // Change in value (Moving along the axis)
    private Image i;
    private boolean ballMoving = true;
    private Graphics doubleG;
    private int width = 800;
    private int height = 600;


    double gravity = 15;
    double energyloss = 0.65;
    double dt = 0.2;
    double xFriction = 0.80;
    double wallEnergyLoss = 0.8;

    @Override
    public void init() {
        setSize(width, height);

    }

    @Override
    public void start() {
        Thread thread = new Thread(this);
        thread.start();

    }

    public void moveBall() {
        if (y == this.getHeight() - radius - 1) {
            System.out.println("check if ball hits bottom");
            dx *= xFriction;
            if (Math.abs(dx) < 0.8) {
                dx = 0;
            }
        }

        //Collisions detect borders
        // X-axis

        if (x + dx > this.getWidth() - radius - 1) {
            x = this.getWidth() - radius - 1; // 1 pixel (counting prob)
            dx = -dx; // Change direction after hit
        } else if (x + dx < 0 + radius) {
            x = 0 + radius;
            dx = -dx;
        }// same but for left border
        else {
            dx *= wallEnergyLoss;
            x += dx;

        }

        // Y-axis, gravity goes along Y-axis
        if (y > this.getHeight() - radius - 1) {
            y = this.getHeight() - radius - 1;
            dy *= energyloss;
            dy = -dy;
        } else {
            dy *= wallEnergyLoss;
            dy += gravity * dt; // velocity formula
            y += dy * dt + 0.5 * gravity * dt * dt; // position formula
        }

    }

    public void checkBallStopped() {

        if(dx==0 && y == this.getHeight() - radius - 1)
            System.out.println("dx  = 0");

        if (dx == 0 && getHeight()-y< 30) {
            ballMoving = false;
            System.out.println("ball stopped");

        }
    }

    @Override
    public void run() {
        while (ballMoving) {
System.out.println("dx: " + dx);
            System.out.println("dy: " + dy);
            moveBall();
            repaint();

            try {
                Thread.sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //checkBallStopped();
        }

    }

    @Override
    public void stop() {
    }


    @Override
    public void destroy() {
    }

    @Override
    public void update(Graphics g) {
        if (i == null) {
            i = createImage(this.getSize().width, this.getSize().height);
            doubleG = i.getGraphics();
        }
        doubleG.setColor(getBackground());
        doubleG.fillRect(0, 0, this.getSize().width, this.getSize().height);

        doubleG.setColor(getForeground());
        paint(doubleG);
        g.drawImage(i, 0, 0, this);


    }
    //removing flicking w double buffering

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2); // painted around center

    }


}
