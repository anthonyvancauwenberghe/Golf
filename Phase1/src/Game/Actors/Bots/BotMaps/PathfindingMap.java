package Game.Actors.Bots.BotMaps;

import Game.Model.Coordinate;
import Game.Model.Course;
import Game.Model.Type;

import java.util.*;

/**
 * Created by lukas on 08/06/16.
 */
public class PathfindingMap {
    Course course;
    Coordinate ball;
    Coordinate hole;
    MapCellDetails[][] map;
    ArrayList<MapCellDetails> treatedCells;
    ArrayList<MapCellDetails> visitedCells;

    public PathfindingMap(Course c, Coordinate b, Coordinate h) {
        course = c;
        ball = b;
        hole = h;
        map = new MapCellDetails[course.playfield.length/4][course.playfield[0].length/4];
        treatedCells = new ArrayList<>();
        visitedCells = new ArrayList<>();
        c.calculateHeightMap();
        makeInitialMap();
        makeCounter((int)hole.getX(), (int)hole.getY());
        completeCounters();
        Collections.sort(visitedCells);
    }

    public void makeInitialMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                int h = course.heightMap[i*4][j*4];
                Type t = course.playfield[i*4][j*4][course.heightMap[i*4][j*4]];
                int c = -1;
                if(t == Type.OBJECT) c = -2;
                map[i][j] = new MapCellDetails(t, h, c, i, j);
                map[i][j].visited=false;
            }
        }
        map[(int)hole.getX()][(int)hole.getY()].setCounter(0);

    }

    public void makeCounter(int x, int y) {
        ArrayList<MapCellDetails> adjCells = getadjCells(x, y);
        for (int i = 0; i < adjCells.size(); i++){
            int deltaH = Math.abs(adjCells.get(i).height - map[x][y].height);
            int distance = (int)(Math.sqrt(1+deltaH*deltaH)*10);
            int total = distance + map[x][y].counter;
            if ((total < adjCells.get(i).counter && adjCells.get(i).counter != -2) || adjCells.get(i).counter == -1){
                adjCells.get(i).counter = total;
            }
            treatedCells.add(adjCells.get(i));
        }
        map[x][y].visited = true;
    }

    public ArrayList<MapCellDetails> getadjCells(int x, int y) {
        ArrayList<MapCellDetails> list = new ArrayList<>();
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if ((i >= 0) && (j >= 0) && (i < map.length) && j < map[0].length) {
                    if ((i == x) && (j == y)) {
                    } else {
                        list.add(map[i][j]);
                    }
                }
            }
        }
        return list;
    }
    MapCellDetails getNextToVisit(){
        int returnCell = -1;
        for (int i = 0; i < treatedCells.size(); i++){
            if (treatedCells.get(i).visited == true) {
                if (treatedCells.get(i).counter!=-2)visitedCells.add(treatedCells.get(i));
                treatedCells.remove(i);
            }else if(treatedCells.get(i).visited == false){
                returnCell = i;
            }

        }
        return treatedCells.get(returnCell);
    }
    void completeCounters(){
        double ratio;
        double total = map.length * map[0].length;
        while (visitedCells.size() < total){
            makeCounter(getNextToVisit().x, getNextToVisit().y);
            ratio = (visitedCells.size()/total)*100;
            System.out.println(ratio);
        }
    }
    public ArrayList<MapCellDetails> getMap(){
        return visitedCells;
    }
}