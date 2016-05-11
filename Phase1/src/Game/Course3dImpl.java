package Game;

import java.util.ArrayList;

/**
 * Created by nibbla on 26.04.16.
 */
public class Course3dImpl {
    private final String name;
    private final int[] dimension;
    public ArrayList<Float> world_normals;
    public ArrayList<Float> world_vertices;
    public ArrayList<Integer> world_indices;
    public ArrayList<Type> world_types;

    public Course3dImpl(String name, int length, int width, int height, Type standartType, int par) {
        this.name = name;

        //playfield = new Type[length][width][height];
        dimension = new int[3];
        dimension[0] = length;
        dimension[1] = width;
        dimension[2] = height;

    }

    public static Course loadCourse(String path){
        String content = Utils.readFile(path);
        if (content == null) return null;
        String[] lines = content.split(System.lineSeparator());
        String name = lines[0];

        int par = Integer.parseInt(lines[1].split(":")[1]);
        int length = Integer.parseInt(lines[2].split(":")[1]);
        int width = Integer.parseInt(lines[3].split(":")[1]);
        int height = Integer.parseInt(lines[4].split(":")[1]);
        Course c = new Course(name, length,width,height,Type.Empty,par);

        for (int i = 5; i < lines.length; i++) {
            String currentLine = lines[i];
            int indexPosX = currentLine.indexOf("x:");
            int indexPosY = currentLine.indexOf("y:");
            int indexPosZ = currentLine.indexOf("z:");
            int indexType = currentLine.indexOf("Type:");
            int indexSTOP = currentLine.indexOf("STOP");

            int x = Integer.parseInt(currentLine.substring(indexPosX + 2, indexPosY));
            int y = Integer.parseInt(currentLine.substring(indexPosY+2,indexPosZ));
            int z = Integer.parseInt(currentLine.substring(indexPosZ+2,indexType));
            Type type = Type.valueOf(currentLine.substring(indexType + 5, indexSTOP));
            c.setTile(x,y,z,type);



        }
        if (c.startTile == null){

            c.setTile(20, 20, 0, Type.Start);
            c.startTile = new Tile(Type.Start,20, 20, 0);
        }
        if (c.hole == null){
            int x = (int) (length*0.8);
            int y = (int) (width*0.8);
            c.setTile(x, y, 0, Type.Hole);
            c.hole = new Hole(Config.getHoleRadius(),x, y, 0);
        }
        return c;
    }

}
