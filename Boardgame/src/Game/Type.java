package Game;

import Game.Config;

import java.awt.*;

/**
 * Created by nibbla on 14.03.16.
 */
public enum Type {
    Empty(Color.GREEN),
    Water(Color.BLUE),
    Red(Color.RED),
    Blue(Color.BLUE),
    Grass(Color.GREEN),
    Gray(Color.gray),
    Wood(new Color(164,42,42)),
    OBJECT(Color.gray);

    Color g;
    Type(Color g) {
        this.g = g;
    }
    public Color getColor(){
        return g;
    }


}
