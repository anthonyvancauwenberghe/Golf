import java.applet.Applet;
import java.awt.*;

public class PhysicsEgine extends Applet implements Runnable {

    int x =400;
    int y=25;
    int radius = 20;
    double dx = 20;
    double dy = 0; // Change in value (Moving along the axis)
    private Image i;
    private boolean ballMoving = true;
    private Graphics doubleG;


    double gravity = 15;
    double energyloss = 0.65;
    double dt = 0.2;
    double xFriction=0.80;

    @Override
    public void init(){
        setSize(800,600);

    }
    @Override
    public void start(){
        Thread thread = new Thread(this);
        thread.start();

    }

    @Override
    public void run(){
        while(ballMoving){
           // Floor friction X-axis


            if(y==this.getHeight()-radius-1){
                System.out.println("check if ball hits bottom");
                dx *= xFriction;
                if(Math.abs(dx)<0.8){
                    dx = 0;
                }
            }
            //Collisions detect borders
            // X-axis
            if(x+dx>this.getWidth()-radius-1){
                x = this.getWidth()-radius -1; // 1 pixel (counting prob)
                dx= -dx; // Change direction after hit
            }else if(x+dx<0+radius){
                x= 0 + radius;
                dx=-dx;
            }// same but for left border
            else
            {
                x += dx;
            }

            // Y-axis, gravity goes along Y-axis
            if(y>this.getHeight()-radius-1){
                y=this.getHeight()-radius-1;
                dy *=energyloss;
                dy=-dy;
            }else{
                dy += gravity*dt; // velocity formula
                y+= dy*dt + 0.5*gravity*dt*dt; // position formula
            }

            repaint();
            try {
                Thread.sleep(33);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            if(dx==0) {
                ballMoving=false;
                System.out.println("ball stopped");

            }
        }

    }

    @Override
    public void stop(){
    }


    @Override
    public void destroy(){
    }

    @Override
    public void update(Graphics g){
        if (i == null){
            i=createImage(this.getSize().width,this.getSize().height);
            doubleG = i.getGraphics();
        }
        doubleG.setColor(getBackground());
        doubleG.fillRect(0,0,this.getSize().width, this.getSize().height);

        doubleG.setColor(getForeground());
        paint(doubleG);
        g.drawImage(i,0,0,this);


    }
    //removing flicking w double buffering

    @Override
    public void paint(Graphics g){
        g.setColor(Color.BLUE);
        g.fillOval( x-radius,y-radius, radius*2,radius*2); // painted around center

    }


}
