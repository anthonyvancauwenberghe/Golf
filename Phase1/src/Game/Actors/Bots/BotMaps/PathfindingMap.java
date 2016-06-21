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
    Coordinate ballSH;
    Coordinate holeSH;
    MapCellDetails[][] mapSH;
    ArrayList<MapCellDetails> treatedCells;
    ArrayList<MapCellDetails> visitedCells;
    ArrayList<MapCellDetails>cleanMap;
    public final int shrink = 4;

    public PathfindingMap(Course c, Coordinate b, Coordinate h) {
        course = c;
        ballSH = new Coordinate((int)b.getX()/shrink, (int)b.getY()/shrink, (int)b.getZ()/shrink);
        holeSH = new Coordinate((int)h.getX()/shrink, (int)h.getY()/shrink, (int)h.getZ()/shrink);
        mapSH = new MapCellDetails[c.playfield.length/shrink][c.playfield[0].length/shrink];
        treatedCells = new ArrayList<>();
        visitedCells = new ArrayList<>();
        cleanMap = new ArrayList<>();
        c.calculateHeightMap();
        makeInitialMap();
        completeCounters();
    }

    public void makeInitialMap() {
        for (int i = 0; i < mapSH.length; i++) {
            for (int j = 0; j < mapSH[0].length; j++) {
                int h = course.heightMap[i*shrink][j*shrink]/shrink;
                Type t = course.playfield[i*shrink][j*shrink][course.heightMap[i*shrink][j*shrink]];

                int c = 0;
                //Giving all objects counter "-2"
                if(t == Type.OBJECT) c = -2;

                mapSH[i][j] = new MapCellDetails(t, h, c, i, j);
                mapSH[i][j].visited=false;
            }
        }
        //Giving hole counter "-1" (unique)
        mapSH[(int) holeSH.getX()][(int) holeSH.getY()].setCounter(-1);
        makeCounter((int) holeSH.getX(), (int) holeSH.getY());

    }

    public void makeCounter(int x, int y) {
        if (mapSH[x][y].counter != -2) {
            ArrayList<MapCellDetails> adjCells = getadjCells(x, y);
            for (int i = 0; i < adjCells.size(); i++) {
                int deltaH = Math.abs(adjCells.get(i).height/shrink - mapSH[x][y].height/shrink);
                int distance = (int) (Math.sqrt(1 + deltaH * deltaH) * 10);
                int total = distance + mapSH[x][y].counter;
                if (((total < adjCells.get(i).counter) && (adjCells.get(i).counter != -2)) || adjCells.get(i).counter == 0){
                    adjCells.get(i).counter = total;
                }
                treatedCells.add(adjCells.get(i));
            }
            cleanMap.add(mapSH[x][y]);
        }
        mapSH[x][y].visited = true;
        visitedCells.add(mapSH[x][y]);
    }

    public ArrayList<MapCellDetails> getadjCells(int x, int y) {
        ArrayList<MapCellDetails> list = new ArrayList<>();
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if ((i >= 0) && (j >= 0) && (i < mapSH.length) && j < mapSH[0].length) {
                    if ((i == x) && (j == y)) {
                    } else {
                        list.add(mapSH[i][j]);
                    }
                }
            }
        }
        return list;
    }
    MapCellDetails getNextToVisit(){
        int returnCell = 0;
        for (int i = 0; i < treatedCells.size(); i++){
            if (treatedCells.get(i).visited) {
                //if (treatedCells.get(i).counter == -2) System.out.println("STOP getNextToVisit found Cell(visited, -2");
                treatedCells.remove(i);
            }else{
                returnCell = i;
                return treatedCells.get(returnCell);
            }

        }
        return treatedCells.get(returnCell);
    }
    void completeCounters(){
        double ratio;
        double total = mapSH.length * mapSH[0].length;
        while (visitedCells.size() < total){
            MapCellDetails next = getNextToVisit();
            makeCounter(next.x, next.y);
            ratio = (visitedCells.size()/total)*100;
            System.out.println(ratio);
        }
        System.out.println("-----------------------------------");
        System.out.println("Evaluated " + visitedCells.size() + " Cells.");
        System.out.println("-----------------------------------");
    }
    public ArrayList<MapCellDetails> getMapSH(){

//        for (MapCellDetails cell:visitedCells) {
//            if (cell.counter!=-2)cleanMap.add(cell);
//        }

        return cleanMap;
    }
}