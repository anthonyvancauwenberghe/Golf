package Game; /**
 * Created by nibbla on 14.03.16.
 */

import java.awt.*;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Random;

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
    int[] dimension;
    ArrayList<ArrayList<Tile>> objectsOnPlayfield;
    int par;
    private Tile startTile;


    /**
     *
     * @param name Name of the course
     * @param length
     * @param width
     * @param height
     * @param standartType the lowest level of the level will be filled with this tile
     * @param par
     */
    public Course(String name, int length, int width, int height, Type standartType, int par){
        this.name = name;
        playfield = new Tile[length][width][height];
        dimension = new int[3]; dimension[0] =length; dimension[1] =width; dimension[2]=height;
        objectsOnPlayfield = new ArrayList<ArrayList<Tile>>();
        //Creates the arraylists for the different objecttypes
        for (int i = 0; i < Type.values().length; i++) {
            objectsOnPlayfield.add(new ArrayList<>());
        }

        //fills the playfield with empty tiles
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < width; y++) {
                for (int z = 0; z < height; z++) {
                    playfield[x][y][z] = Tile.newTile(Type.Empty,x,y,z);
                }
            }
        }
        Random r = new Random(0);
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < width; y++) {
                    Type typ;
                    if (standartType == null) typ = Type.values()[r.nextInt(Type.values().length)];
                    else typ =standartType;
                    Tile t =Tile.newTile(typ,x,y,0);
                    playfield[x][y][0] = t;

                    if (typ!= Type.Empty) objectsOnPlayfield.get(typ.ordinal()).add(t);
            }
        }

        if (startTile == null){

            setTile(20,20,0,Type.Start);
            startTile = getTile(20,20,0);
        }

        this.par = par;

    }

    ArrayList<Tile> getObjectsOfType(Type t){
        return objectsOnPlayfield.get(t.ordinal());
    }


    void setTile(int x, int y, int z, Type t){
        Tile originalTile = playfield[x][y][z];
        Tile newTile = originalTile;
        if (originalTile.getType()!=Type.Empty){
            objectsOnPlayfield.get(originalTile.getType().ordinal()).remove(originalTile);

        }else{
            newTile.setType(t);
        }

        playfield[x][y][z] = newTile;
        objectsOnPlayfield.get(t.ordinal()).add(newTile);
    }

    Tile getTile(int x, int y, int z){
        return playfield[x][y][z];
    }

    Tile[][][] getPlayfield(){
        return playfield;
    }

    static Course loadCourse(String path){
        String content = Utils.readFile(path);
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

            c.setTile(20,20,0,Type.Start);
            c.startTile = c.getTile(20,20,0);
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

    public ArrayList<ArrayList<Tile>> getObjects() {
        return objectsOnPlayfield;
    }

    public int[] getDimension() {

        return dimension;
    }

    public int getLength() {

        return dimension[0];
    }

    public int getWidth() {

        return dimension[1];
    }

    public int getHeight() {

        return dimension[2];
    }

    public Tile getStartTile() {
        return startTile;
    }

    //dimension,
}