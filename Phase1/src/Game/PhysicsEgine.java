package Game;

import java.applet.Applet;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

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
    private int bounceCounter=0;
    private int bounceCounterLimit=2;


    double gravity = 15;
    double energyloss = 0.65;
    double dt = 0.2;
    double xFriction = 0.80;
    double wallEnergyLoss = 0.95;
    private Course c;

    @Override
    public void init() {
        setSize(width, height);
        c = new Course("Test2",getWidth(),getHeight(),1,Type.Grass,4);
        c.saveCourse();
        c = Course.loadCourse("Test2.txt");
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
            dx *= wallEnergyLoss;
            dx = -dx; // Change direction after hit
        } else if (x + dx < 0 + radius) {
            x = 0 + radius;
            dx = -dx;
        }// same but for left border
        else {
            //dx *= wallEnergyLoss;
            x += dx;
        }

        // Y-axis, gravity goes along Y-axis
        if (y > this.getHeight() - radius - 1) {
            System.out.println("check collission bottom");
            y = this.getHeight() - radius - 1;
            dy *= wallEnergyLoss;
            dy *= energyloss;
            dy = -dy;
        } else {
            System.out.println("dy before: " + dy);
            dy += (gravity * dt)/(bounceCounter+1); // velocity formula
            y += dy * dt + 0.5 * gravity * dt * dt; // position formula
            System.out.println("dy after: " + dy);
        }

    }

    public void checkBallStopped() {
        int y2 = this.getHeight() - radius - 1;

            //System.out.println("dx = 0");

        if(Math.abs(dx)==0 && Math.abs(dy)<0.3 && (y2-1<=y)){
            ballMoving = false;
            System.out.println("ball stopped");
        }
        if(Math.abs(dx)==0 && Math.abs(dy)<1 && (y2-1<=y)){
            bounceCounter++;
            dy*=0.9;
            System.out.println(bounceCounter);
        }
        if(!ballMoving)
        bounceCounter=0;
    }

    @Override
    public void run() {
        while (ballMoving) {
            System.out.println("dx: " + dx);
            moveBall();
            repaint();

            try {
                Thread.sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int y2 = this.getHeight() - radius - 1;

            System.out.println("y: " + y);
            System.out.println("y2: " + y2);
            checkBallStopped();
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
        paintCourse(g,c);
        g.setColor(Color.BLUE);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2); // painted around center

    }

    private void paintCourse(Graphics doubleG, Course course) {

        ArrayList<ArrayList<Tile>> all = course.getObjects();
        for (int j = 0; j < all.size(); j++) {
            ArrayList<Tile> objectsOfSingleType = all.get(j);
            if (objectsOfSingleType.size()==0) continue;
            if (Type.Empty.ordinal() == j)continue;
            doubleG.setColor(objectsOfSingleType.get(0).getColor());
            for (int k = 0; k < objectsOfSingleType.size(); k++) {
                Tile t = objectsOfSingleType.get(k);

                doubleG.fillRect(t.getX(),t.getY(),1,1);
            }

        }

    }
}
