package Game.Actors.Bots;

/**
 * Created by lukas on 10/06/16.
 */

        import Game.Actors.Bots.BotMaps.MapCellDetails;
        import Game.Actors.Bots.BotMaps.PathfindingMap;
        import Game.Config;
        import Game.Model.*;

        import java.util.ArrayList;


        import Game.*;
        import Game.Model.Ball;
        import Game.Model.Coordinate;
        import Game.Model.Course;
        import Game.Model.Type;

        import java.util.ArrayList;

public class PathfindingBot extends AIPlayer {
    Coordinate holeCoord;
    Coordinate ballCoord;
    ArrayList<MapCellDetails> pMap;
    int indexList;

    public PathfindingBot(String s, Course c) {
        super(s);
        Ball b = this.getBall();
        Type[][][] playfield = c.getPlayfield();
        holeCoord = b.getCoordinate();
        ballCoord = b.getCoordinate();
        PathfindingMap map = new PathfindingMap(c, ballCoord, holeCoord);
        pMap = map.getMap();
        indexList = 0;
    }

    @Override
    public void nextMove(PhysicsEngine p) {
        Coordinate newCoord;
        do{
            indexList++;
            newCoord = new Coordinate(pMap.get(indexList).getX(), pMap.get(indexList).getY(), pMap.get(indexList).getHeight());
        }while (!course.wayIsObstacleFree(ballCoord, newCoord));
        int[] delta = getDelta(ballCoord, newCoord);
        indexList = 0;
        shootBall(getCorrectedShootData(delta, Config.AI_OFFSET));
    }




}

