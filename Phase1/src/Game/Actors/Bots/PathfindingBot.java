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

import java.util.ArrayList;

/**
 * Created by lukas on 20/05/16.
 */
public class PathfindingBot extends AIPlayer {
    Coordinate holeCoord;
    Coordinate ballCoord;
    public Coordinate alternative=null;
    int indexList;
    PathfindingMap map;
    ArrayList<MapCellDetails> pMap;

    public PathfindingBot(String s, Course c) {
        super(s);
        Ball b = this.getBall();
        ballCoord = this.getBall().getCoordinate();
        holeCoord = new Coordinate(800, 650, 1);
        indexList = 0;

        map = new PathfindingMap(c, ballCoord, holeCoord);
    }

    @Override
    public void nextMove(Course c, ArrayList<Ball> notPlayerBall) {
        Game.Game.dp.repaint();
        ballCoord = new Coordinate(this.getBall().getCoordinate().getX(), this.getBall().getCoordinate().getY(),course.heightMap[(int)this.getBall().getCoordinate().getX()][(int)this.getBall().getCoordinate().getY()]);


        pMap = map.getMapSH();

        do{
            indexList++;
            this.alternative = new Coordinate(pMap.get(indexList).getX()*map.shrink, pMap.get(indexList).getY()*map.shrink, pMap.get(indexList).getHeight()+1);
        }while (!course.wayIsObstacleFree(ballCoord, alternative));
        indexList = 0;
        int[] delta = getDelta(ballCoord, alternative);
        try {
            this.alternative = holeCoord;
            Game.Game.dp.repaint();
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        shootBall(getCorrectedShootData(delta, Config.AI_OFFSET));
        this.alternative = null;
    }
}

