package Game.Actors.Bots.BotMaps;

import Game.Model.Coordinate;
import Game.Model.Course;
import Game.Model.Tile;
import Game.Model.Type;

/**
 * Created by lukas on 08/06/16.
 */
public class PathfindingMap {
    Course course;
    Coordinate ball;
    Coordinate hole;

    public PathfindingMap(Course c, Coordinate b,Coordinate h){
        course = c;
        ball = b;
        hole = h;
    }
    public void makeCounter(){
        Type type = course.playfield[1][2][course.heightMap[1][2]];
        type.toString();
    }
}
