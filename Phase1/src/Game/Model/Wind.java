package Game.Model;

import Game.Game;
import Game.Model.Coordinate;

import java.util.Random;

/**
 * Created by Nibbla on 14.06.2016.
 */
public class Wind extends Coordinate{
    public Wind(double minwind, double maxwind, double minAngle, double maxAngle) {
        Random r = new Random();
        double windPower = minwind + (maxwind - minwind) * r.nextDouble();
        double windAngle = minAngle + (maxAngle - minAngle) * r.nextDouble();
        xCoord = Math.cos(windAngle)*windPower;
        yCoord = Math.sin(windAngle)*windPower;
        zCoord = 0;
    }
}
