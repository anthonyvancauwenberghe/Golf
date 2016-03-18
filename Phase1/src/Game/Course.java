package Game; /**
 * Created by nibbla on 14.03.16.
 */

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Random;

/** A course is defined by a 3 dimensional grid containing of tiles
 * A typical course would be 800*600*100 tiles, mostly empty
 * A tile is 1cm³
 * @author ??
 *
 */
public class Course {
 String name;

    Tile[][][] playfield;
    Tile[][][] copy;
    int[] dimension;
    ArrayList<ArrayList<Tile>> objectsOnPlayfield;
    int par;
    public Tile startTile;
    public Hole hole;
    public ArrayList<ArrayList<Tile>> oldTiles = new ArrayList<>(8);


    /**
     * Constructor of the Course class
     * which creates the new playfield with 3 dimensions
     * and filles the playfield with empty tiles
     * and which stores objects in an arraylist of the arraylist of the new playfield
     *
     * @param name Name of the course
     * @param length length of the new Tile
     * @param width width of the new Tile
     * @param height height of the new Tile
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



        this.par = par;

    }

    ArrayList<Tile> getObjectsOfType(Type t){
        return objectsOnPlayfield.get(t.ordinal());
    }

    /**
     * setter to set the tiles
     * @param x
     * @param y
     * @param z
     * @param t
     */
    public void setTile(int x, int y, int z, Type t){
        Tile originalTile = playfield[x][y][z];
        Tile newTile = originalTile;
        if (originalTile.getType()!=Type.Empty){
            objectsOnPlayfield.get(originalTile.getType().ordinal()).remove(originalTile);

        }
        newTile.setType(t);
        if (t== Type.Hole){
            hole = new Hole(Config.getHoleRadius(), x,y,z) ;
            playfield[x][y][z] = hole;
            objectsOnPlayfield.get(t.ordinal()).add(hole);
        }else if (t == Type.Start) {
            startTile = newTile;
            playfield[x][y][z] = newTile;
            objectsOnPlayfield.get(t.ordinal()).add(newTile);
        }else{
            playfield[x][y][z] = newTile;
            objectsOnPlayfield.get(t.ordinal()).add(newTile);
        }


    }

    Tile getTile(int x, int y, int z){
        return playfield[x][y][z];
    }

    /**
     * getter to get the playfield
     * @return playfield
     */
    public Tile[][][] getPlayfield(){
        return playfield;
    }

    /**
     * method loadCourse created the new course
     * @return c, the new course
     */
    public static Course loadCourse(String path){
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

            c.setTile(20, 20, 0, Type.Start);
            c.startTile = c.getTile(20, 20, 0);
        }
        if (c.hole == null){
            int x = (int) (length*0.8);
            int y = (int) (width*0.8);
            c.setTile(x, y, 0, Type.Hole);
            c.hole = (Hole) c.getTile(x, y, 0);
        }
        return c;

    }

    /**
     * method saveCourse saves the course as a .txt file
     */
    public void saveCourse(){
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

    /**
     * getter to get the name of the course
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * getter to get the objects of the ArrayList of the ArrayList Tile
     * @return objectsOnPlayfield
     */
    public ArrayList<ArrayList<Tile>> getObjects() {
        return objectsOnPlayfield;
    }

    /**
     * getter to get the dimension
     * @return dimension
     */
    public int[] getDimension() {
        return dimension;
    }

    /**
     * getter to get the length of the course
     * @return dimension[2]
     */
    public int getLength() {
        return dimension[2];
    }

    /**
     * getter to get the width of the course
     * @return dimension[0]
     */
    public int getWidth() {
        return dimension[0];
    }

    /**
     * getter to get the height of the course
     * @return dimension[1]
     */
    public int getHeight() {
        return dimension[1];
    }

    /**
     * getter to get the StartTile
     * @return startTile
     */
    public Tile getStartTile() {
        return startTile;
    }

    /**
     * method to add a rectangle to the course
     * To set a tile to an object
     * @param x1 x coordinate in the course to place the object
     * @param y1 y coordinate in the course to place the object
     * @param width of the course
     * @param height of the course
     * @param type
     */
    public void addRectangle(int x1, int y1, int width, int height, Type type) {
        ArrayList<Tile> newOldTile = new ArrayList<Tile>(2 * width * 2 * height);
        oldTiles.add(newOldTile);
        int initialX=x1;
        int initalY=y1;
        for (int x = x1; x < initialX+width; x++) {
            for (int y = y1; y < initalY+height; y++) {
                newOldTile.add(new Tile(getTile(x, y, 0).getType(), x, y, 0));
                setTile(x,y,0,type);
            }
        }

    }

    /**
     * method to add a squircle to the course
     * @param a
     * @param b
     * @param r
     * @param n
     * @param z
     * @param t
     */
    public void addSquircle(int a, int b, int r, int n, int z, Type t) {
        ArrayList<Tile> newOldTile = new ArrayList<Tile>(4*r*r);
        oldTiles.add(newOldTile);
        for (int x = -r+a; x < r+a; x++) {
            for (int y = -r+b; y < r+b; y++) {
              if(Math.pow(x-a,n)+Math.pow(y-b,n)<Math.pow(r,n)){
                  newOldTile.add(new Tile(getTile(x,y,z).getType(),x,y,z));
                  setTile(x, y, z, t);
              }
            }

        }
    }

    /**
     * getter to get the hole
     * @return hole
     */
    public Hole getHole() {
        return hole;
    }

    /**
     * setter to set the name of the course
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public void removeLastObject() {
        if (oldTiles.size()==0)return;
        ArrayList<Tile> od = oldTiles.get(oldTiles.size() - 1);
        oldTiles.remove(oldTiles.size() - 1);
        int s = od.size();
        for (int i = 0; i < s; i++) {
            Tile t = od.get(i);
            this.setTile(t.getX(),t.getY(),t.getZ(),t.getType());
        }
    }
}