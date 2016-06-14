package Game.Actors.Bots.BotMaps;

import Game.Model.Coordinate;
import Game.Model.Course;
import Game.Model.Tile;
import Game.Model.Type;

import java.util.Map;

/**
 * Created by lukas on 08/06/16.
 */
public class PathfindingMap {
    Course course;
    Coordinate ball;
    Coordinate hole;
    MapDetails[][]map = new MapDetails[course.playfield.length][course.playfield[0].length];

    public PathfindingMap(Course c, Coordinate b,Coordinate h){
        course = c;
        ball = b;
        hole = h;
    }
    public void makeCounter(){
        Type type = course.playfield[1][2][course.heightMap[1][2]];
        System.out.println(type.toString());

        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++){
                int h = course.heightMap[i][j];
                Type t = course.playfield[i][j][course.heightMap[i][j]];
                int c = 0;
                map[i][j] = new MapDetails(t, h, c);
            }
        }

    }
}
