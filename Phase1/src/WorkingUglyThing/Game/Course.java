package WorkingUglyThing.Game; /**
 * Created by nibbla on 14.03.16.
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/** A course is defined by a 3 dimensional grid containing of tiles
 * A typical course would be 800*600*100 tiles, mostly empty
 * A tile is 1cm³
 * @author ??
 *
 */
public class Course {
 String name;

    Type[][][] playfield;
    Type[][][] copy;
    int[] dimension;

    int par;
    public Tile startTile;
    public Hole hole;
    public Stack<Tile> oldTiles = new Stack<Tile>();
    private BufferedImage managedBufferedImage;


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
        playfield = new Type[length][width][height];
        dimension = new int[3]; dimension[0] =length; dimension[1] =width; dimension[2]=height;



        //fills the playfield with empty tiles
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < width; y++) {
                for (int z = 0; z < height; z++) {
                    playfield[x][y][z] = Type.Empty;
                }
            }
        }
        Random r = new Random(0);
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < width; y++) {
                    Type typ;
                    if (standartType == null) typ = Type.values()[r.nextInt(Type.values().length)];
                    else typ =standartType;

                    playfield[x][y][0] = typ;
            }
        }


        this.par = par;

    }



    /**
     * setter to set the tiles
     * @param x
     * @param y
     * @param z
     * @param t
     */
    public void setTile(int x, int y, int z, Type t){
        Type originalTile;
        try {
            originalTile = playfield[x][y][z];
        }catch (ArrayIndexOutOfBoundsException a){
            a.printStackTrace();
            return;
        }


        Type newTile = originalTile;


        newTile=t;
        if (t== Type.Hole){
            hole = new Hole(Config.getHoleRadius(), x,y,z) ;
        }else if (t == Type.Start) {
            startTile = new Tile(newTile,x,y,z);

        }else{

        }
        playfield[x][y][z] = t;
    }

    Type getTile(int x, int y, int z){
        return playfield[x][y][z];
    }

    /**
     * getter to get the playfield
     * @return playfield
     */
    public Type[][][] getPlayfield(){
        return playfield;
    }

    /**
     * method loadCourse created the new course
     * @return c, the new course
     */
    public static Course loadCourse(String path){
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        String content = Utils.readFile(path);
        if (content==null||content=="")return null;
        String[] lines = content.split(System.lineSeparator());
        String name = lines[0].split(":")[1];

        int par = Integer.parseInt(lines[1].split(":")[1]);
        int length = Integer.parseInt(lines[2].split(":")[1]);
        int width = Integer.parseInt(lines[3].split(":")[1]);
        int height = Integer.parseInt(lines[4].split(":")[1]);
        Course c = new Course(name, length,width,height, Type.Empty,par);

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


        try {
           c.setBufferedImage(ImageIO.read(new File(c.getName()+".png")));
        } catch (IOException e) {
            e.printStackTrace();
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
                    if (!playfield[x][y][z].equals(Type.Empty))
                    s.append("x:" + x + "y:" + y + "z:" + z + "Type:" + playfield[x][y][z].name() + "STOP\n");
                }
            }
        }

        Utils.saveFile(name + ".gol", s.toString());
        if (managedBufferedImage==null){//(true){//
            managedBufferedImage = DrawPanel.createImage(this);
        }
        Utils.saveManagedBufferedImage(name,"png",managedBufferedImage);





    }

    /**
     * getter to get the name of the course
     * @return name
     */
    public String getName() {
        return name;
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
    public void addRectangle(int x1, int y1, int z1, int width, int height, Type type) {
        oldTiles.add(new Tile(Type.$MARKER,-1,-1,-1));

        int initialX=x1;
        int initalY=y1;
        for (int x = x1; x < initialX+width; x++) {
            for (int y = y1; y < initalY+height; y++) {

                oldTiles.add(new Tile(getTile(x,y,z1), x, y, z1));
                setTile(x,y,z1,type);
            }
        }

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
    public void addCuboid(int x1, int y1, int z1, int length, int width, int height, Type type) {
        oldTiles.add(new Tile(Type.$MARKER,-1,-1,-1));

        int initialX=x1;
        int initalY=y1;
        int initalZ=z1;
        for (int x = x1; x < initialX+length; x++) {
            for (int y = y1; y < initalY+width; y++) {
                for (int z = z1; z < initalZ+height; z++) {
                    oldTiles.add(new Tile(getTile(x,y,z), x, y, z));
                    setTile(x,y,z,type);
                }

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
    public void addSquircle(int a, int b, int r, double n, int z, Type t) {
        oldTiles.add(new Tile(Type.$MARKER,-1,-1,-1));
        for (int x = -r+a; x < r+a; x++) {
            for (int y = -r+b; y < r+b; y++) {
              if(Math.pow(x-a,n)+Math.pow(y-b,n)<Math.pow(r,n)){
                  oldTiles.add(new Tile(getTile(x,y,z), x, y, z));
                  setTile(x,y,z,t);
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
        if (oldTiles.size() == 0) return;
        Tile t = oldTiles.pop();

        do{


            this.setTile(t.getX(), t.getY(), t.getZ(), t.getType());
            t = oldTiles.pop();

        }while(t.t != Type.$MARKER);
    }

    public void addFrustrum(int initialX, int initialY, int initialZ, int length, int height, int depth, int leftXDeltaPerLayer, int rightXDeltaPerLayer, int topYDeltaPerLayer, int bottomYDeltaPerLayer, Type t){
        oldTiles.add(new Tile(Type.$MARKER,-1,-1,-1));
        int x=initialX;
        int y=initialY;

        for (int z = initialZ; z < initialZ+depth; z++) {

            addRectangle(x,y,z,length,height,t);

            oldTiles.pop(); //marker get set after every adding... as we add many objects
            x+=leftXDeltaPerLayer;
            if (leftXDeltaPerLayer>0)length-=leftXDeltaPerLayer; else length+=leftXDeltaPerLayer;
            if (rightXDeltaPerLayer<0)length+=rightXDeltaPerLayer; else length-=rightXDeltaPerLayer;
            y+=topYDeltaPerLayer;
            if (topYDeltaPerLayer>0) height-=topYDeltaPerLayer; else  height+=topYDeltaPerLayer;
            if (bottomYDeltaPerLayer<0) height+=bottomYDeltaPerLayer; else height-=bottomYDeltaPerLayer;

            if (height<=0||length<=0)break;
        }

    }

    public void addPyramid(int x, int y, int z, int length, int height, int depth, Type t) {
        oldTiles.add(new Tile(Type.$MARKER,-1,-1,-1));
        int initialX=x;
        int initalY=y;
        int initalZ=z;
        int initialLength=x;
        int initalHeight=y;
        for (z = initalZ; z < initalZ+depth; z++) {
            addRectangle(x,y,z,length,height,t);
            oldTiles.pop(); //marker get set after every adding... as we add many objects
            x++;
            y++;
            length--;
            height--;
            length--;
            height--;
            if (height<=0||length<=0)break;
        }

    }
    public void addHill(int a, int b, int r, double n, int z, int depth, Type t) {
        oldTiles.add(new Tile(Type.$MARKER,-1,-1,-1));
        int initialX=a;
        int initalY=b;
        int initalZ=z;

        for (z = initalZ; z < initalZ+depth; z++) {
            addSquircle(a,b,r,n,z,t);
            oldTiles.pop(); //marker get set after every adding... as we add many objects
            r--;

            if (r<=0)break;
        }

    }


    public BufferedImage getManagedBufferedImage() {
        return managedBufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.managedBufferedImage = bufferedImage;
    }
}