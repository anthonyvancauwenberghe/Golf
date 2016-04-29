package MarkusTestLab.NormalTesting;

import Game.Config;
import Game.Coordinate;
import Game.Course;
import Game.Type;

/**
 * Created by lukas on 27/04/16.
 */
public class Run {
    public static void main (String[] args){
        Course course = new Course("GolfDeluxe", Config.getWidth(), Config.getHeight(), Config.getDepth(), Type.Grass, 1);

        course.setTile(400, 400, 0, Type.Hole);
        course.setTile(300, 400, 0, Type.Start);
        course.addRectangle(600, 400, 50, 100, 0, Type.OBJECT);
        course.addRectangle(400, 400, 50, 100, 1, Type.OBJECT);
        course.addCuboid(400, 400, 50, 100, 30, 20, Type.OBJECT);
        course.addPyramid(200, 200, 50, 100, 30, 20, Type.OBJECT);
        course.addHill(152, 152, 150, 1.5, 0, 20, Type.OBJECT);
        course.addPyramid(400, 400, 0, 200, 200, 100, Type.OBJECT);
        course.saveCourse();

        Coordinate c = new Coordinate(400, 400, 50);
        Coordinate normal = c.normal(course);
    }
}
