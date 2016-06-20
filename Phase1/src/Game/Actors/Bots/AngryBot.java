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
    public int testMoves;

    public AngryBot(String name, int testMoves) {
        super(name);
        this.testMoves = testMoves;
    }



    @Override
    public void nextMove(PhysicsEngine p) {
        Move[] moves = new Move[testMoves];
        Ball b = p.getBallOfPlayer(this);
        double power = Coordinate.getDistance(b.getCoordinate(),course.getHole().getCoordinate())*Config.AI_OFFSET;
        for (int i = 0; i < testMoves; i++) {
            moves[i] = new Move(i*1.0/(testMoves)*2*Math.PI, power,b.getCoordinate());
        }
        double[] ratio = {0.1};
        Move m = evaluate(p,moves, Evaluationfunction.botClosest,ratio);


        repaintAndWait(1000);
        Game.dp.setPreviewMoves(null);
        Game.dp.repaint();


        double length = Coordinate.getDistance(m.attainedTarget,course.getHole().getCoordinate())*Config.AI_OFFSET;
        moves[0] = m;
        for (int i = 1; i < testMoves; i++) {
            moves[i] = m.modifyAndClone(length);
        }
        m = evaluate(p,moves, Evaluationfunction.botClosest,ratio);

        repaintAndWait(1000);
        repaintAndWait(500);
        Game.dp.setPreviewMoves(null);
        Game.dp.repaint();
        shootBall(m);
        Game.dp.setPreviewMove(null);


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
