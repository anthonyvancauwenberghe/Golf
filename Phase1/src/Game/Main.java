package Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by tony on 16/03/2016.
 */
public class Main {
    public static void main(String [] args){
        Course course = new Course("Golf Deluxe", 800, 600, 1, Type.Grass, 1 );
        Ball ball = new Ball(1, 20);
        ArrayList<Player> pp = new ArrayList<>(2);
        Player p = new Player("PlayerEins");
        Player p2 = new Player("PlayerZwei");
        pp.add(p);
        pp.add(p2);

        DrawPanel dp = new DrawPanel();
        dp.setPlayers(pp);
        dp.setCurrentPlayer(p);
        dp.setCourse(course);
        Frame fenster = new JFrame(); fenster.add(dp);
        PhysicsEngineFinal physics = new PhysicsEngineFinal();

        physics.init(course, ball);
        while(true){
            if(ball.isMoving){

            }
        }
    }
}
