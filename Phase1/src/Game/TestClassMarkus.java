package Game;

/**
 * Created by nibbla on 14.03.16.
 */
public class TestClassMarkus {

    public static void main(String[] args){
        Course c = new Course("Test1",200,100,200,Type.Grass,4);
        c.setTile(2,4,5,Type.Grass);
        c.setTile(2,4,5,Type.Grass);
        c.setTile(2,1,5,Type.Water);
        c.setTile(2,3,5,Type.Hole);

        c.saveCourse();

        c = Course.loadCourse("Test1.txt");
    }


}
