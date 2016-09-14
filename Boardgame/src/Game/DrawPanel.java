package Game;



import Game.Actors.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.beans.Transient;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by nibbla on 16.03.16.
 */
public class DrawPanel extends JPanel implements Observer {



    BufferedImage courseImage;
    Board Board;
    public ArrayList<Player> players = new ArrayList<>();

    public int firstXClick = 0;
    public int firstYClick = 0;
    public Player currentPlayer;
    public static BufferedImage grassTexture;
    public static BufferedImage sandTexture;
    public static BufferedImage waterTexture;
    public static BufferedImage blueMarbleTexture;
    public static BufferedImage redMarbleTexture;
    public static BufferedImage woodTexture;

    public static HashMap<Point, Color> GrassText = new HashMap<>();
    public static HashMap<Point, Color> SandText= new HashMap<>();
    public static HashMap<Point, Color> WaterText= new HashMap<>();
    //public static HashMap<Point, Color> BlueText = new HashMap<>();
    //public static HashMap<Point, Color> RedText = new HashMap<>();
    //public static HashMap<Point, Color> WoodText = new HashMap<>();

    private static TexturePaint blueMarbleTexturePaint;
    private static TexturePaint redMarbleTexturePaint;
    private static TexturePaint woodTexturePaint;


    public HashMap<Point, Color> HoleText= new HashMap<>();
    public static HashMap<Point, Color> ObjectText = new HashMap<>();

   // public static TexturePaint ballP;
   // public static BufferedImage ballTexture;
   // public static BufferedImage ballImage;

    private static BufferedImage bluePieceImage;
    private static BufferedImage redPieceImage;

    private BufferedImage previewObject;
    private Graphics2D g2;
    private BoardCoordinate previewMove;
    private BoardCoordinate lastHover = new BoardCoordinate(-1,-1);


    public void setPlayers(ArrayList<Player> p) {
        this.players = p;
    }

    public void setCurrentPlayer(Player p) {

        this.currentPlayer = p;

    }

    public DrawPanel() {
        loadTextures();

        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                BoardCoordinate coordinate = BoardCoordinate.ScreenToBoard(e.getX(),e.getY());
                if (lastHover != coordinate) {
                    if (coordinate == null) return;
                    lastHover = coordinate;
                    repaint();
                }

                if (previewObject!=null) repaint();
            }
        });
        DrawPanel dp = this;
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("mouseCordY" + e.getY());

                System.out.println(BoardCoordinate.ScreenToBoard(e.getX(),e.getY()));
                if(e.getButton()== MouseEvent.BUTTON3){
                    System.out.print("SurfaceNormal:" + Board.getSurfaceNormals()[e.getX()][e.getY()].toString());
                    System.out.print("Shading%:" + Board.getShadingMap()[e.getX()][e.getY()]);


                }

                if (previewObject != null){
                    Point mp = MouseInfo.getPointerInfo().getLocation();
                    Point pp = dp.getLocationOnScreen();

                    Point mousePosition = new Point(mp.x-pp.x,mp.y-pp.y);

                }

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton()!= MouseEvent.BUTTON3) {
                    setFirstXClick(e.getX());
                    setFirstYClick(e.getY());

                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public static void loadTextures() {



        try {
            String current = new File( "." ).getCanonicalPath();
            System.out.println("Current dir:"+current);
            String t = Config.getTexturePath();
            File f =new File(t+"grass.jpg");
            grassTexture = ImageIO.read(f);
            sandTexture = ImageIO.read(new File(t+"sand.jpg"));
            waterTexture = ImageIO.read(new File(t+"water.jpg"));
            redMarbleTexture= ImageIO.read(new File(t+"BLUEMarble.jpg"));
            blueMarbleTexture= ImageIO.read(new File(t+"REDMarble.jpg"));
            woodTexture = ImageIO.read(new File(t+"wood.jpg"));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (int x = 0; x < 32; x++) {
            for (int y = 0; y < 32; y++) {
                Point p = new Point(x,y);
                GrassText.put(p,new Color(grassTexture.getRGB(x , y )));
                SandText.put(p,new Color(sandTexture.getRGB(x, y )));
                WaterText.put(p,new Color(waterTexture.getRGB(x , y)));
            }
        }

        blueMarbleTexturePaint = new TexturePaint(blueMarbleTexture,new Rectangle(0,0,blueMarbleTexture.getWidth(),blueMarbleTexture.getHeight()));
        redMarbleTexturePaint = new TexturePaint(redMarbleTexture,new Rectangle(0,0,redMarbleTexture.getWidth(),redMarbleTexture.getHeight()));
        woodTexturePaint = new TexturePaint(woodTexture, new Rectangle(0, 0, woodTexture.getWidth(), woodTexture.getHeight()));

        precalcPieceImage();
    }

    public static void precalcPieceImage() {
        bluePieceImage = new BufferedImage((int)(Config.getPieceRadius()*2),(int)(Config.getPieceRadius()*2),BufferedImage.TYPE_INT_ARGB);
        redPieceImage = new BufferedImage((int)(Config.getPieceRadius()*2),(int)(Config.getPieceRadius()*2),BufferedImage.TYPE_INT_ARGB);

        double radius = Config.getPieceRadius();
        //Graphics2D g2 = ballImage.createGraphics();
        Color[][] colorMap = new Color[(int) radius*2][(int) radius*2];

        /*
        for (int x = (int) -radius; x < radius; x++) {
            for (int y = (int) -radius; y < radius; y++) {
                for (int z = 0; z < radius; z++) {
                    double length = Math.sqrt(x*x+y*y+z*z);
                    double xt = x;
                    double yt = y;
                    double zt = z;
                    if (length<=radius){

                        xt /= length;
                        yt /= length;
                        zt /= length;
                        float value = (float) Math.acos(Config.getLightningVector3d()[0] * xt + Config.getLightningVector3d()[1] *  yt+Config.getLightningVector3d()[2] *  zt);
                        System.out.println("x: " + x + " y: " + y + " has the angle two north west " + Math.toDegrees(value) );
                        value = (float)(value/Math.PI);
                        colorMap[(int)(x+radius)][(int)(y+radius)] = new Color(0,0,0,value*0.7f);
                    }
                }
            }

        }

        for (int x = 0; x < colorMap.length; x++) {
            for (int y = 0; y < colorMap[0].length; y++) {
                Color c = colorMap[x][y];
                if (c!=null){
                    g2.setColor(c);
                    g2.fillRect(x, y, 1, 1);
                    g2.fillRect(x, y, 1, 1);
                }

            }

        }
        */
    }


    public void setBoard(Board c) {
        this.Board = c;
        courseImage = c.getManagedBufferedImage();
    }

    public static BufferedImage createImageStandart(Board Board) {
        if (grassTexture==null)DrawPanel.loadTextures();
        Type[][][] pf = Board.getPlayfield();
        int[][] hm = Board.getHeightMap();
        if (hm==null){
            Board.calculateHeightMapSafe();
            hm = Board.getHeightMap();
        }
        float[][] sm = Board.getShadingMap();
        if (sm ==null) {
            Board.calculateShadingMap();
            sm = Board.getShadingMap();
        }
        Coordinate[][] normals = Board.getSurfaceNormals();
        if (normals == null){
            Board.calculateSurfaceNormalsSafe();
            normals = Board.getSurfaceNormals();

        }
        int[] d = Board.getDimension();
        BufferedImage bufferedImage =
                new BufferedImage(d[0], d[1], BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();

        Random r = new Random(0);
        HashMap<Integer,Color> hightColorFromZ = new HashMap<>(100);
        HashMap<Float,Color> shadingColorFromZ = new HashMap<>(100);
        Color shadingC;
        Color zshadow;

            for (int x = 0; x < d[0]; x++) {
                for (int y = 0; y < d[1]; y++) {
                    int z = hm[x][y];
                    Type t = pf[x][y][z];
                    if (t == Type.Empty) continue;
                    Point p = new Point((x)%32,(y)%32);
                    if (t == Type.Grass) g.setColor(GrassText.get(p));
                    if (t == Type.Water) g.setColor(WaterText.get(p));
                    if (t  == Type.OBJECT) g.setColor(ObjectText.get(p));


                    g.fillRect(x, y, 1, 1);

                    zshadow = hightColorFromZ.get(z);
                    if (zshadow == null){
                        float f = (float) ((z*1f/d[2])*0.3);
                        zshadow = new Color(0,0,0,f);
                        hightColorFromZ.put(z,zshadow);
                    }
                    g.setColor(zshadow);

                   g.fillRect(x, y, 1, 1);

                    //schraffurShaddow
                    float ss =  sm[x][y];
                    shadingC = shadingColorFromZ.get(ss);
                    if (shadingC == null){
                        shadingC = new Color(0,0,0,ss*0.9f);

                        shadingColorFromZ.put(ss,shadingC);
                    }


                    g.setColor(shadingC);
                     g.fillRect(x, y,  1,  1);

                }

            }





        g.dispose();
        return bufferedImage;
    }

    public static BufferedImage createImage(Board Board) {
        if (grassTexture==null)DrawPanel.loadTextures();
        Type[][][] pf = Board.getPlayfield();
        int[][] hm = Board.getHeightMap();
        float[][] sm = Board.getShadingMap();
        Coordinate[][] normals = Board.getSurfaceNormals();
        if (hm==null){
            Board.calculateHeightMapSafe();
            hm = Board.getHeightMap();
        }

        if (sm ==null) {
            Board.calculateShadingMap();
            sm = Board.getShadingMap();
        }

        if (normals == null){
            Board.calculateSurfaceNormalsSafe();
            normals = Board.getSurfaceNormals();

        }

        int[] d = Board.getDimension();
        BufferedImage bufferedImage =
                new BufferedImage(d[0], d[1], BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = bufferedImage.createGraphics();

        Random r = new Random();
        HashMap<Integer,Color> hightColorFromZ = new HashMap<>(100);
        HashMap<Float,Color> shadingColorFromZ = new HashMap<>(100);
        Color shadingC;
        Color zshadow;



        for (int x = 0; x < d[0]; x++) {
            for (int y = 0; y < d[1]; y++) {
                int z = hm[x][y];
                Type t = pf[x][y][z];
                if (t == Type.Empty) continue;
                Point p = new Point((x)%32,(y)%32);
                if (t == Type.Grass) g.setColor(GrassText.get(p));
                if (t == Type.Water) g.setColor(WaterText.get(p));
                if (t  == Type.OBJECT) g.setColor(ObjectText.get(p));
                g.fillRect(x, y, 1, 1);

            }
        }


        for (int x = 0; x < d[0]; x++) {
            for (int y = 0; y < d[1]; y++) {
                int z = hm[x][y];
                zshadow = hightColorFromZ.get(z);
                if (zshadow == null){
                    float f = (float) ((z*1f/d[2])*0.3);
                    zshadow = new Color(0,0,0,f);
                    hightColorFromZ.put(z,zshadow);
                }
                g.setColor(zshadow);

                g.fillRect(x, y, 1, 1);

                //schraffurShaddow
                float ss =  sm[x][y];
                shadingC = shadingColorFromZ.get(ss);
                if (shadingC == null){
                    shadingC = new Color(0,0,0,ss*0.9f);

                    shadingColorFromZ.put(ss,shadingC);
                }


                g.setColor(shadingC);
                g.fillRect(x, y,  1,  1);

            }

        }

        g.dispose();
        return bufferedImage;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        g2 = (Graphics2D)g;//this new object g2,will get the
        Stroke oldStroke = g2.getStroke();

        if (courseImage != null) g.drawImage(Board.getManagedBufferedImage(), 0, 0, null);

        if (previewObject!=null){
            Point mp = MouseInfo.getPointerInfo().getLocation();
            Point pp = this.getLocationOnScreen();

            Point mousePosition = new Point(mp.x-pp.x,mp.y-pp.y);
            g.drawImage(previewObject, mousePosition.x, mousePosition.y, null);

        }

        int bs = Config.getBoardSize();

        g.setColor(Color.RED);
        int i = 0;
        for (int j = 1; j<= bs+1;j++){
             Polygon p = BoardCoordinate.getHexPolygon(new BoardCoordinate(i,j));
             g.fillPolygon(p);
        }
        i = bs+1;
        for (int j = 1; j<= bs;j++){
            Polygon p = BoardCoordinate.getHexPolygon(new BoardCoordinate(i,j));
            g.fillPolygon(p);
        }


        g.setColor(Color.BLUE);
        int j = 0;
        for (i = 1; i<= bs+1;i++){
            Polygon p = BoardCoordinate.getHexPolygon(new BoardCoordinate(i,j));
            g.fillPolygon(p);
        }
        j = bs+1;
        for (i = 1; i<= bs;i++){
            Polygon p = BoardCoordinate.getHexPolygon(new BoardCoordinate(i,j));
            g.fillPolygon(p);
        }

        g.setColor(Color.BLACK);
        for (i = 1; i<= bs;i++){
            for (j = 1; j<= bs;j++){
                Polygon p = BoardCoordinate.getHexPolygon(new BoardCoordinate(i,j));
                g.drawPolygon(p);
            }
        }

        g.setColor(Color.BLUE);
        if (lastHover.isWithinPlayfield()){
            Polygon p = BoardCoordinate.getHexPolygon(lastHover);
            g.fillPolygon(p);
        }
    }







    @Override
    @Transient
    public Dimension getPreferredSize() {
        return new Dimension(courseImage.getWidth(), courseImage.getHeight());
    }

    public int getFirstXClick() {

        return firstXClick;
    }

    public void setFirstXClick(int firstXClick) {
        this.firstXClick = firstXClick;
    }

    public int getFirstYClick() {

        return firstYClick;
    }

    public void setFirstYClick(int firstYClick) {
        this.firstYClick = firstYClick;
    }


    public void setPreviewMove(BoardCoordinate previewMove) {
        this.previewMove = previewMove;
    }



    @Override
    public void update(Observable o, Object arg) {
        this.repaint();
    }
}
