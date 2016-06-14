package Game.Actors.Bots.BotMaps;

import Game.Model.Type;

/**
 * Created by esther on 6/14/16.
 */
public class MapDetails {
    int height;
    int counter;
    Type type;

    public MapDetails(Type t, int h, int c){
        type = t;
        height = h;
        counter = c;
    }

}
