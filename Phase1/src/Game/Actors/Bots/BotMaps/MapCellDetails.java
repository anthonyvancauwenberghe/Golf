package Game.Actors.Bots.BotMaps;

import Game.Model.Type;

/**
 * Created by esther on 6/14/16.
 */
public class MapCellDetails implements Comparable<MapCellDetails>{
    int height;
    int counter;
    Type type;
    boolean visited;



    int x;
    int y;

    public MapCellDetails(Type t, int h, int c, int xx, int yy){
        type = t;
        height = h;
        counter = c;
        x = xx;
        y = yy;
    }
    void setCounter(int i){
        this.counter = i;
    }

    @Override
    public int compareTo(MapCellDetails o) {
        if (this.counter == -2) return -1;
        return Integer.compare(this.counter, o.counter);
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getHeight() {
        return height;
    }

    public int getCounter() {
        return counter;
    }
}