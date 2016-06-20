package Testings;


import Game.Actors.Bots.BotMaps.MapCellDetails;

import java.util.ArrayList;
import java.util.Collections;

public class TestMain{
    public static void main(String[]args){
        Beer a = new Beer(1, 7, 3, 4);
        Beer b = new Beer(3, 8, 4, 4);
        Beer c = new Beer(2, 2, 7, 4);
        Beer d = new Beer(1, 5, 3, 4);

        ArrayList<Beer> l = new ArrayList<>();
        l.add(a);
        l.add(b);
        l.add(c);
        l.add(d);
        System.out.println("Before " + l.toString());
        Collections.sort(l);
        System.out.println("After " + l.toString());
    }
}