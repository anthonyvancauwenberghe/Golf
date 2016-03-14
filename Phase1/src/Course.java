/**
 * Created by nibbla on 14.03.16.
 */

/** A course is defined by a 3 dimensional grid containing of tiles
 * A typical course would be 800*600*100 tiles, mostly empty
 * A tile is 1cm³
 *
 *
 */
public class Course {
 String name;

    Tile[][][] playfield;
    Tile[][][] copy;
    int par;

    public Course(String name, int length, int width, int height, Type standartType, int par){
        this.name = name;
        playfield = new Tile[length][width][height];
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < width; y++) {
                for (int z = 0; z < height; z++) {
                    playfield[x][y][z] = new Tile(standartType);
                }
            }
        }
        this.par = par;

    }



    void setTile(int x, int y, int z, Type t){
        playfield[x][y][z] = new Tile(t);
    }

    Tile getTile(int x, int y, int z){
        return playfield[x][y][z];
    }

    Tile[][][] getPlayfield(){
        return playfield;
    }

    static Course loadCourse(String path){
        String content = Utils.readFile(path);
       String[] lines = content.split("\n");
        String name = lines[0];
        int par = Integer.parseInt(lines[1].split(":")[1]);
        int length = Integer.parseInt(lines[2].split(":")[1]);
        int width = Integer.parseInt(lines[3].split(":")[1]);
        int height = Integer.parseInt(lines[4].split(":")[1]);
        Course c = new Course("Test1", length,width,height,Type.Empty,par);

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

        return c;

    }

    void saveCourse(){
        StringBuilder s = new StringBuilder();
        int length = playfield.length;
        int width = playfield[0].length;
        int height = playfield[0][0].length;

        s.append("Name:" + name +"\n");
        s.append("Par:" + par +"\n");
        s.append("length:" + length +"\n");
        s.append("width:" + width +"\n");
        s.append("height:" + height +"\n");


        for (int x = 0; x < length; x++) {
            for (int y = 0; y < width; y++) {
                for (int z = 0; z < height; z++) {
                    if (!playfield[x][y][z].getType().equals(Type.Empty))
                    s.append("x:" + x + "y:" + y + "z:" + z + "Type:" + playfield[x][y][z].getType().name() + "STOP\n");
                }
            }
        }

        Utils.saveFile(name + ".txt", s.toString());



    }

    public String getName() {
        return name;
    }
}