package Game.Actors.Bots;

import Game.Actors.Move;
import Game.Config;
import Game.Game;
import Game.Model.*;

import java.util.ArrayList;

/**
 * Created by Nibbla on 12.06.2016.
 */
public class AngryBot extends AIPlayer {
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
            moves[i] = new Move(i*1.0/(testMoves)*2*Math.PI, power);

        }
        double[] ratio = {0.1};
        Move m = evaluate(p,moves, Evaluationfunction.hybrid,ratio);
        repaintAndWait(1000);

        repaintAndWait(500);
        shootBall(m);
        //Game.dp.setPreviewCoordinates(null);
    }



}
