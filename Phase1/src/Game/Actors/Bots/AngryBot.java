package Game.Actors.Bots;

import Game.Actors.Move;
import Game.Config;
import Game.Game;
import Game.Model.Ball;
import Game.Model.Coordinate;
import Game.Model.Evaluationfunction;
import Game.Model.PhysicsEngine;

/**
 * Created by Nibbla on 12.06.2016.
 */
public class AngryBot extends AIPlayer  {
    private final Evaluationfunction evaluationfunction;
    private final double[] ratio;
    public int testMoves;

    public AngryBot(String name, int testMoves, Evaluationfunction eva, double... ratio) {
        super(name);
        this.testMoves = testMoves;
        this.evaluationfunction = eva;
        this.ratio = ratio;
    }



    @Override
    public void nextMove(PhysicsEngine p) {
        Game.Botthinking=true;
        Move[] moves = new Move[testMoves];
        Ball b = p.getBallOfPlayer(this);
        System.out.println("starting location ball: " + b.getCoordinate().toString());
        double power = Coordinate.getDistance(b.getCoordinate(),course.getHole().getCoordinate())*Config.AI_OFFSET;
        for (int i = 0; i < testMoves; i++) {
            moves[i] = new Move(i*1.0/(testMoves)*2*Math.PI, power,b.getCoordinate());
        }

        Move m = evaluate(p,moves, evaluationfunction,ratio);


        repaintAndWait(1000);
        Game.dp.setPreviewMoves(null);
        Game.dp.repaint();

        System.out.println("Target location ball: " + m.attainedTarget.toString());
        double length = Coordinate.getDistance(m.attainedTarget,course.getHole().getCoordinate())*Config.AI_OFFSET;
        moves[0] = m;
        for (int i = 1; i < testMoves; i++) {
            moves[i] = m.modifyAndClone(length);
        }
        m = evaluate(p,moves, evaluationfunction,ratio);

        repaintAndWait(1000);
        repaintAndWait(500);
        Game.dp.setPreviewMoves(null);
        Game.dp.repaint();
        shootBall(m);

        Game.dp.setPreviewMove(null);

        Game.Botthinking=false;

    }


    public void nextMoveOld(PhysicsEngine p) {
        Move[] moves = new Move[testMoves];
        Ball b = p.getBallOfPlayer(this);
        double power = Coordinate.getDistance(b.getCoordinate(),course.getHole().getCoordinate())*Config.AI_OFFSET;


        for (int i = 0; i < testMoves; i++) {
            moves[i] = new Move(i*1.0/(testMoves)*2*Math.PI, power,this.getBall().getCoordinate());
        }
        double[] ratio = {0.1};
        Move m = evaluate(p,moves, Evaluationfunction.hybrid,ratio);
        repaintAndWait(1000);

        repaintAndWait(500);
        shootBall(m);
        //Game.dp.setPreviewCoordinates(null);
    }




}
