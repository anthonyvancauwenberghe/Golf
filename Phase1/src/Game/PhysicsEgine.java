package Game;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class PhysicsEgine extends Applet implements Runnable, MouseListener {


    private static int test = 2;
    int radius = 20;
    private double speedX = 0;
    private double speedY = 0; // Change in value (Moving along the axis)
    private Image i;
    private boolean ballMoving = false;
    private Graphics doubleG;
    private int width = 800;
    private int height = 600;
    private int bounceCounter = 0;
    private boolean autoShoot = false;
    private int firstXClick=0;
    private int firstYClick=0;
    private int secondXClick=0;
    private int secondYClick=0;
    private double airFriction=0.99;

    double gravity = 25;
    double energyloss = 0.65;
    double dt = 0.2;
    double groundFriction = 0.8;
    double wallEnergyLoss = 0.7;
    private Course c;
    int x = radius;
    int y = getHeight() - radius;

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

    @Override
    public int getWidth() {
        return width;

    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void init() {
        setSize(width, height);

        c = new Course("Test2", getWidth(), getHeight(), 1, Type.Grass, 4);
        c.saveCourse();
        c = Course.loadCourse("Test2.txt");
        addMouseListener(this);
    }

    @Override
    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void processGravity() {

    }
public void processMovement(){
    if (y == this.getHeight() - radius ) {
        System.out.println("ball is on the ground");
        speedX *= groundFriction;

    }
    else {
        speedX *= airFriction;
    }

    //Collisions detect borders
    // X-axis

    if (x + speedX > this.getWidth() - radius) {
        x = this.getWidth() - radius; // 1 pixel (counting prob)
        speedX *= wallEnergyLoss;
        speedX = -speedX; // Change direction after hit
    } else if (x + speedX < 0 + radius) {
        x = 0 + radius;
        speedX = -speedX;
    }// same but for left border
    else {
        //speedX *= wallEnergyLoss;
        x += speedX;
    }

    // Y-axis, gravity goes along Y-axis
    if (y > this.getHeight() - radius) {
        System.out.println("ball hit bottom");
        y = this.getHeight() - radius;
        speedY *= wallEnergyLoss;
        speedY *= energyloss;
        speedY = -speedY;
    } // same but for left border
    else if (y <= 0) {
        System.out.println("ball hit top");
        y = radius + 1;
        speedY *= wallEnergyLoss;
        speedY *= energyloss;
        speedY = -speedY;
    } else {
        //System.out.println("speedY before: " + speedY);
        speedY += (gravity * dt) / (bounceCounter + 1); // velocity formula
        y += speedY * dt + 0.5 * gravity * dt * dt; // position formula
        //System.out.println("speedY after: " + speedY);
    }
}
    public void shootBall(int velocityX, int velocityY) {
        if (!ballMoving) {
            speedX = velocityX;
            speedY = velocityY;
        }
        ballMoving = true;
        //System.out.println("ball moving");



    }

    public void checkBallStopped() {
        int y2 = this.getHeight() - radius;

        //System.out.println("speedX = 0");

        if (Math.abs(speedX) < 2 && Math.abs(speedY) < 0.4 && (y2 - 1 <= y)) {
            ballMoving = false;
            autoShoot = false;
            bounceCounter=0;
            System.out.println("ball stopped");
        }
        if (Math.abs(speedY) < 5 && (y2 - 1 <= y)) {
            bounceCounter++;
            speedY *= 0.95;
            if(bounceCounter>3)
                speedY*=0.4;

                if(bounceCounter>5)
                    speedX*=0.95;


            System.out.println(bounceCounter);
        }
        if (!ballMoving)
            bounceCounter = 0;
    }

    @Override
    public void run() {

        while (true) {
            if (autoShoot)
                shootBall(30, 150);

            if (ballMoving) {

                System.out.println("speedX: " + speedX);
                processMovement();

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int y2 = this.getHeight() - radius;

                System.out.println("y: " + y);
                //System.out.println("y2: " + y2);
                checkBallStopped();
            }
            repaint();
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
        paintCourse(g, c);
        g.setColor(Color.BLUE);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2); // painted around center

    }

    private void paintCourse(Graphics doubleG, Course course) {

        ArrayList<ArrayList<Tile>> all = course.getObjects();
        for (int j = 0; j < all.size(); j++) {
            ArrayList<Tile> objectsOfSingleType = all.get(j);
            if (objectsOfSingleType.size() == 0) continue;
            if (Type.Empty.ordinal() == j) continue;
            doubleG.setColor(objectsOfSingleType.get(0).getColor());
            for (int k = 0; k < objectsOfSingleType.size(); k++) {
                Tile t = objectsOfSingleType.get(k);

                doubleG.fillRect(t.getX(), t.getY(), 1, 1);
            }

        }

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("first x: " + getFirstXClick());
        System.out.println("first Y: " + getFirstYClick());
        System.out.println("second x: " + e.getX());
        System.out.println("second Y: " + e.getY());
        System.out.println("deltaX: " + (getFirstXClick()- e.getX())/10);
        System.out.println("deltaY: " + (getFirstYClick()- e.getY()));
        shootBall((-(getFirstXClick()- e.getX()))/2, -(getFirstYClick() - e.getY()) );
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        setFirstXClick(e.getX());
        setFirstYClick(e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
}
