package Game.Actors.Bots;


import Game.Actors.HumanPlayer;
import Game.Actors.Move;
import Game.Actors.Player;
import Game.Game;
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
        double[] ratio; //for the hybrid;
        if (i == null){
            ratio = new double[]{0.3, 0.3, 0.3};
        }else{
            ratio = i;

        }
        //System.out.println("AI move evalutate");
        //execute all the moves
        PhysicsEngine[] ps = new PhysicsEngine[m.length];

        Game.dp.setPreviewMoves(m);
        for (int j = 0; j < m.length; j++) {
            tryOutMoves(m[j],p);
            //System.out.println("AI move " + j);
        }


        //do the moves
        //value 1 means best
        //value 0 means worst
        double maximumDistance = getMaximumDistance(course.getHole(),course.getWidth(),course.getHeight());

        int indexOfBest = 0;
        double valOfBest = 0;
        for (int j = 0; j < m.length; j++) {
            double val = 0;
            switch (f){
                case botClosest:
                    val = evalueteBotFriendsClosest(m[j].getModel(),maximumDistance,course.getHole());
                    break;
                case enemyFurthest:

                val = evalueteEnemyFurthest(m[j].getModel(),maximumDistance,course.getHole());

                break;
                case playerClosest:
                    val = evaluetePlayerClosest(m[j].getModel(),maximumDistance,course.getHole());

                break;
                case hybrid:
                    double[] vals = new double[3];
                    vals[0] = evaluetePlayerClosest(m[j].getModel(),maximumDistance, course.getHole());
                    vals[1] = evalueteEnemyFurthest(m[j].getModel(),maximumDistance, course.getHole());
                    vals[2] = evalueteBotFriendsClosest(m[j].getModel(),maximumDistance, course.getHole());
                    for (int k = 0; k < ratio.length; k++) {
                        val = vals[k] * ratio[k];
                    }

                break;
            }
            if (val >= valOfBest){
                valOfBest = val;
                indexOfBest = j;
            }
        }


        Game.dp.setPreviewMove(m[indexOfBest]);
        return m[indexOfBest];
    }



    private void tryOutMoves(Move move, PhysicsEngine p) {
        PhysicsEngine ps = p.getAlternativBoardForTest();
        Ball b = ps.getBallOfPlayer(this);
        b.shootBall(move);
        ps.calculateUntilNoBallIsMoving(0,0);
        move.attainedTarget = ps.getBallOfPlayer(this).getCoordinate();
        move.setModel(ps);
        Game.dp.repaint();
        //b.printBallInfo();
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
             summedDistance += Coordinate.getDistance(balls.get(i).getCoordinate(),hole.getCoordinate())/maximumDistance;

        }
        summedDistance/= balls.size()-1;
        return summedDistance;

    }

    private double evalueteBotFriendsClosest(PhysicsEngine p, double maximumDistance, Hole hole) {
        int indexP = p.getBallIndexOfPlayer(this);
        ArrayList<Ball> balls = p.getBalls();
        double summedDistance = 0;
        int skipped = 0;
        for (int i = 0; i < balls.size(); i++) {
            if (balls.get(i).getPlayer()instanceof HumanPlayer||i==indexP) {
                skipped++;
                continue;
            }
            summedDistance += maximumDistance-Coordinate.getDistance(balls.get(indexP).getCoordinate(),hole.getCoordinate());

        }
        if (balls.size()-skipped==0)return 0;
        summedDistance/= maximumDistance*(balls.size()-skipped);
        return summedDistance;

    }


    private double evaluetePlayerClosest(PhysicsEngine p, double maximumDistance, Hole hole) {
        int indexP = p.getBallIndexOfPlayer(this);
        ArrayList<Ball> balls = p.getBalls();
        double summedDistance;
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
