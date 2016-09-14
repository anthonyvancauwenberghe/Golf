package Game;

import java.awt.*;

/** This class represents a coordinate on the playfield.
 * the x coordinates go from A to K at a 11*11 board
 * y from 1 to 11
 * Created by Nibbla on 11.09.2016.
 */
public class BoardCoordinate {
    private final int xCoord;
    private final int yCoord;

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public BoardCoordinate(int x, int y){
        this.xCoord = x;
        this.yCoord = y;
    }

    public BoardCoordinate(char x, int y){
        xCoord = Character.getNumericValue(x) - 9;
        System.out.println(x + " has been translated to " + xCoord);
        yCoord = y;
    }

    public String toString(){
        char c = (char) (xCoord+9);
        //return c+","+yCoord;
        return xCoord +","+yCoord;
    }


    public int[] toScreenCoordinates(){
        int r = Config.getPieceRadius();
        int xSpacer = Config.getXSpacer();
        int ySpacer = Config.getYSpacer();
        int wHalf = (int) (Math.sqrt(3)*r);
        int x = (int) (xSpacer  + Math.sqrt(3) *r *((xCoord-1) +(yCoord-1)/2.0 + 0.5));
        int y = (int) (ySpacer + r +  r * 3/2.0 * (yCoord-1));
        int[] p = {x,y};
        return p;
    }

    public static BoardCoordinate ScreenToBoard(int xScreen, int yScreen){
        int r = Config.getPieceRadius();
        int xSpacer = Config.getXSpacer();
        int ySpacer = Config.getYSpacer();
        int boardSize = Config.getBoardSize();
        xScreen -=xSpacer;
        yScreen -=ySpacer;


        int xCoord = (int) (1.5+ (xScreen * Math.sqrt(3)/3 - yScreen / 3) / r);
        int yCoord = (int) (1 + yScreen * 2.0/3.0 / r);


        /*
        xScreen /= r * Math.sqrt(3);
        yScreen /= r * Math.sqrt(3);

        double temp = Math.floor(xScreen + Math.sqrt(3) * yScreen + 1);
        int xCoord = 1+(int) Math.floor((Math.floor(2*xScreen+1) + temp) / 3);
        int yCoord = 1+(int) Math.floor((temp + Math.floor(-xScreen + Math.sqrt(3) * yScreen + 1))/3);
        */

        return new BoardCoordinate(xCoord,yCoord);
    }

    public static Polygon getHexPolygon(BoardCoordinate bc){
        int[] xCoords = new int[6];
        int[] yCoords = new int[6];
        int radius = Config.getPieceRadius();
        //int f = radius*Math.sqrt(3);
        Graphics g;

        int[] p = bc.toScreenCoordinates();
        int wHalf = (int) (Math.sqrt(3)*radius /2);
        xCoords[0] = p[0];
        xCoords[1] = p[0] + wHalf;
        xCoords[2] = p[0] + wHalf;
        xCoords[3] = p[0];
        xCoords[4] = p[0] - wHalf;
        xCoords[5] = p[0] - wHalf;

        yCoords[0] = p[1] -radius;
        yCoords[1] = (int) (p[1] - 0.5* radius);
        yCoords[2] = (int) (p[1] + 0.5* radius);
        yCoords[3] = p[1] +radius;
        yCoords[4] = (int) (p[1] + 0.5* radius);
        yCoords[5] = (int) (p[1] - 0.5* radius);

        return new Polygon(xCoords,yCoords,6);
    }

    public boolean isWithinPlayfield() {
        if (xCoord <1||xCoord>Config.getBoardSize())return false;
        if (yCoord <1||yCoord>Config.getBoardSize())return false;

        return true;
    }
}
