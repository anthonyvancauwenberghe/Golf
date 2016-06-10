package Game.Actors.Bots;

/**
 * Created by lukas on 10/06/16.
 */

import Game.Config;
import Game.Model.Ball;
import Game.Model.Coordinate;
import Game.Model.Course;
import Game.Model.Type;

import java.util.ArrayList;


import Game.*;
import Game.Model.Ball;
import Game.Model.Coordinate;
import Game.Model.Course;
import Game.Model.Type;


import java.util.ArrayList;

/**
 * Created by lukas on 20/05/16.
 */
public class PathfindingBot extends AIPlayer {
    @Override
    public void nextMove(Course c, ArrayList<Ball> notPlayerBall) {
        Ball b = this.getBall();
        Type[][][] playfield = c.getPlayfield();

        Coordinate currentCoord = b.getCoordinate();
        Coordinate HoleCoord = new Coordinate(400,400,0);
    }

    public PathfindingBot(String s) {
        super(s);
    }


}

