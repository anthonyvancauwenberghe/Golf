package Testings;

import Deprecated.Game.Coordinate;
import Game.Actors.Bots.BotMaps.PathfindingMap;
import Game.Game;
import Game.Model.Course;
import org.apache.commons.math3.distribution.NormalDistribution;
public class TestNormalDistribution {
    public static void main(String[]args){
        NormalDistribution normal = new NormalDistribution(1.00, 0.20);
        System.out.println(normal.sample());

        System.out.println("----------------------");

    }
}
