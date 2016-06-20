package Game.Actors.Bots;

/**
 * Created by lukas on 10/06/16.
 */

import Game.Actors.Bots.BotMaps.MapCellDetails;
import Game.Actors.Bots.BotMaps.PathfindingMap;
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
    Coordinate holeCoord;
    Coordinate ballCoord;
    ArrayList<MapCellDetails> pMap;

    @Override
    public void nextMove(Course c, ArrayList<Ball> notPlayerBall) {
        for (MapCellDetails cell:pMap) {
            Coordinate newCoord = new Coordinate(cell.getX(), cell.getY(), cell.getHeight());


            if (course.wayIsObstacleFree(holeCoord, holeCoord)) {
                int[] delta = getDelta(holeCoord, holeCoord);
                try {
                    Game.Game.dp.repaint();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                shootBall(getCorrectedShootData(delta, Config.AI_OFFSET));
            }
        }

    }

    public PathfindingBot(String s, Course c) {
        super(s);
        Ball b = this.getBall();
        Type[][][] playfield = c.getPlayfield();
        holeCoord = b.getCoordinate();
        ballCoord = b.getCoordinate();
        PathfindingMap map = new PathfindingMap(c, ballCoord, holeCoord);
        pMap = map.getMap();
    }


}

