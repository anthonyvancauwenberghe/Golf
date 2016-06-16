package Game.Actors.Bots.BotMaps;

import Game.Model.Type;

/**
 * Created by esther on 6/14/16.
 */
public class MapCellDetails {
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

}
