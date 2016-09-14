package Game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;

/**
 * Created by Nibbla on 10.09.2016.
 */
public class HexGraph extends Observable{
    public LinkedList<String> history;
    public HexNode[] nodes;
    public void saveHistory(String s) {
        //TODO store the history
    }
}
