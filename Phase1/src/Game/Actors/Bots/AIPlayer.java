package Game.Actors.Bots;


import Game.*;
import Game.Actors.Move;
import Game.Actors.Player;
import Game.Model.*;

import java.util.ArrayList;


/**
 * Created by Nibbla on 13.04.2016.
 */
public abstract class AIPlayer extends Player {
   protected static Course course = Game.course;


    public AIPlayer(String name) {
        super(name);

    }
    public void setCourse(Course course2){
        course = course2;
        System.out.println("ai loaded new course");
    }
    public void shootBall(int[]xyz){
        shootBall(xyz[0], xyz[1], xyz[2]);
    }
    public int[] getDelta(Coordinate ball, Coordinate hole){
        int[]delta = {
                ((int)hole.getX() - (int)ball.getX()),
                ((int)hole.getY() - (int)ball.getY()),
                ((int)hole.getZ() - (int)ball.getZ()),
        };
        return delta;
    }
    public int[] getCorrectedShootData(int[]delta, double multi){
        int[] deltaShoot = { (int) Math.round(delta[0]*multi), (int) Math.round(delta[1]*multi), (int) Math.round(delta[2]*multi)};
        return deltaShoot;
    }
    public int getDistance(int[] delta){
        return ((int)Math.sqrt(delta[0]*delta[0]+delta[1]*delta[1]+delta[2]*delta[2]));
    }

    public Move evaluate(PhysicsEngine p, Move[] m, Evaluationfunction f, double... i){
        double ratio; //for the hybrid;
        if (i == null){
            ratio = 0.5;
        }else{
            ratio = i[0];
        }
        System.out.println("AI move evalutate");
        //execute all the moves
        PhysicsEngine[] ps = new PhysicsEngine[m.length];
        Coordinate[] c = new Coordinate[ps.length];
        for (int j = 0; j < m.length; j++) {
            if (j==7) {
                System.out.println("wlej");
            }
            ps[j] = p.getAlternativBoardForTest();
            Ball b = ps[j].getBallOfPlayer(this);
            b.shootBall(m[j]);
            ps[j].calculateUntilNoBallIsMoving();
            c[j] = ps[j].getBallOfPlayer(this).getCoordinate();

            System.out.println("AI move " + j);
            b.printBallInfo();
        }


        Game.dp.setPreviewCoordinates(c);




        //do the moves
        //value 1 means best
        //value 0 means worst
        double maximumDistance = getMaximumDistance(course.getHole(),course.getWidth(),course.getHeight());

        int indexOfBest = 0;
        double valOfBest = 0;
        switch (f){
            case enemyFurthest:
                for (int j = 0; j < ps.length; j++) {
                    double val = evalueteEnemyFurthest(ps[j],maximumDistance,course.getHole());
                    if (val >= valOfBest){
                        valOfBest = val;
                        indexOfBest = j;
                    }
                }
                break;
            case playerClosest:
                for (int j = 0; j < ps.length; j++) {
                    double val = evaluetePlayerClosest(ps[j],maximumDistance,course.getHole());
                    if (val >= valOfBest){
                        valOfBest = val;
                        indexOfBest = j;
                    }
                }
                break;
            case hybrid:
                for (int j = 0; j < ps.length; j++) {
                    double val1 = evalueteEnemyFurthest(ps[j],maximumDistance, course.getHole());
                    double val2 = evaluetePlayerClosest(ps[j],maximumDistance, course.getHole());
                    double val = val2 * ratio + val1 * ( 1-ratio);
                    if (val >= valOfBest){
                        valOfBest = val;
                        indexOfBest = j;
                    }
                }
                break;
        }
        Coordinate[] c2 = new Coordinate[1];
        c2[0] = ps[indexOfBest].getBallOfPlayer(this).getCoordinate();
        Game.dp.setPreviewCoordinates2(c2);
        return m[indexOfBest];
    }



    private double getMaximumDistance(Hole hole, int length, int height) {
        double max=0;
        double x1 = Math.sqrt((length-hole.getX())*(length-hole.getX())+(height-hole.getY())*(height-hole.getY()));
        double x2 = Math.sqrt((0-hole.getX())*(0-hole.getX())+(height-hole.getY())*(height-hole.getY()));
        double x3 = Math.sqrt((length-hole.getX())*(length-hole.getX())+(0-hole.getY())*(0-hole.getY()));
        double x4 = Math.sqrt((0-hole.getX())*(0-hole.getX())+(0-hole.getY())*(0-hole.getY()));

        if (x1>max) max =x1;
        if (x2>max) max =x2;
        if (x3>max) max =x3;
        if (x4>max) max =x4;

        return max;
    }

    private double evalueteEnemyFurthest(PhysicsEngine p, double maximumDistance, Hole hole) {
        int indexP = p.getBallIndexOfPlayer(this);
        ArrayList<Ball> balls = p.getBalls();
        double summedDistance = 0;
        for (int i = 0; i < balls.size(); i++) {
            if (i==indexP) continue;
             summedDistance = Coordinate.getDistance(balls.get(i).getCoordinate(),hole.getCoordinate())/maximumDistance;

        }
        summedDistance/= balls.size()-1;
        return summedDistance;

    }

    private double evaluetePlayerClosest(PhysicsEngine p, double maximumDistance, Hole hole) {
        int indexP = p.getBallIndexOfPlayer(this);
        ArrayList<Ball> balls = p.getBalls();
        double summedDistance = 0;

        summedDistance = maximumDistance-Coordinate.getDistance(balls.get(indexP).getCoordinate(),hole.getCoordinate());


        summedDistance/=maximumDistance;
        return summedDistance;
    }

    protected void repaintAndWait(int i) {
        try {
            Game.dp.repaint();
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
