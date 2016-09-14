package Game;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nibbla on 10.09.2016.
 */
public class Game {
    private Board board;

    public static void main(String[] args){
        int size = 7;
        //board = Board.loadCourse2_5d(Config.CourseLocation + "hex"+ size + ".hex");
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        //board.getGameSate().saveHistory(Config.HistoryLocation + "Game" + timeStamp + ".his");
       // board.getGameSate().addObserver(new DrawPanel());
        HexGraph hexGraph = new HexGraph();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        DrawPanel dp = new DrawPanel();
        hexGraph.addObserver(dp);

        int height = (int) (Config.getYSpacer()*2+ 1.5*Config.getPieceRadius()*(Config.getBoardSize()+2) + Config.OFFSET_Y_GAME);
        frame.setSize((int) (Config.getXSpacer()*2+Config.getPieceRadius()*Math.sqrt(3)*Config.getBoardSize()*1.5 + Config.OFFSET_X_GAME), height);
        frame.add(dp, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
