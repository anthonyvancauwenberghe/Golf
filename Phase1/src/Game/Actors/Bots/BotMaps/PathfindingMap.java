package Game.Actors.Bots.BotMaps;

import Game.Model.Coordinate;
import Game.Model.Course;
import Game.Model.Tile;
import Game.Model.Type;

import java.util.*;

/**
 * Created by lukas on 08/06/16.
 */
public class PathfindingMap {
    Course course;
    Coordinate ball;
    Coordinate hole;
    MapDetails[][] map;

    public PathfindingMap(Course c, Coordinate b, Coordinate h) {
        course = c;
        ball = b;
        hole = h;
        map = new MapDetails[course.playfield.length][course.playfield[0].length];
        c.calculateHeightMap();
        makeInitialMap();
    }

    public void makeInitialMap() {
        Type type = course.playfield[1][2][course.heightMap[1][2]];
        System.out.println(type.toString());

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                int h = course.heightMap[i][j];
                Type t = course.playfield[i][j][course.heightMap[i][j]];
                int c = 0;
                map[i][j] = new MapDetails(t, h, c);
            }
        }

    }

    public void makeCounter(int x, int y) {
        int[][] adjCells = getadjCells(x, y);
        for (int i = 0; i < adjCells.length; i++){
            int deltaH = Math.abs(map[adjCells[i][0]][adjCells[i][1]].height - map[x][y].height);
            int distance = (int)(Math.sqrt(1+deltaH*deltaH)*10);
            map[adjCells[i][0]][adjCells[i][1]].counter = distance;
        }


    }

    public int[][] getadjCells(int x, int y) {
        int[][] adjCells = new int[8][2];
        int listCounter = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if ((i >= 0) && (j >= 0)) {
                    if ((i == x) && (j == y)) {

                    } else {
                        adjCells[listCounter][0] = i;
                        adjCells[listCounter][1] = j;
                        listCounter++;
                    }
                }
            }
        }
        return adjCells;
    }
}