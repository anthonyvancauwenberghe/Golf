package Game;

import java.io.File;

/**
 * Created by Nibbla on 07.09.2016.
 */
public class Config {
    public static String CourseLocation = "Boardgame" + File.separator + "src"+ File.separator +"Boards"+File.separator;
    public static String HistoryLocation = "Boardgame" + File.separator + "src"+ File.separator +"Games"+File.separator;
    public static String TextureLocation = "Boardgame" + File.separator + "src"+ File.separator +"Textures"+File.separator;
    public static String version;

    private static final double[] lightningVector3d = createLightningVector(-1,-1,1);
    private static int pieceRadius = 36; //should be an even number
    private static int XSpacer = 50;
    private static int YSpacer = 50;
    private static int boardSize = 11;
    public static  int OFFSET_X_GAME=50; //50
    public static  int OFFSET_Y_GAME=0;//130;

    private static double[] createLightningVector(double x, double y,double z) {
        double length = Math.sqrt(x * x + y * y+z*z);
        // normalize vector
        x /= length;
        y /= length;
        z /= length;
        double[] d = {x,y,z};
        return d;
    }

    public static double[] getLightningVector3d() {
        return lightningVector3d;
    }

    public static String getTexturePath() {
        return TextureLocation;
    }

    public static int getPieceRadius() {
        return pieceRadius;
    }

    public static int getXSpacer() {
        return XSpacer;
    }

    public static int getYSpacer() {
        return YSpacer;
    }

    public static int getBoardSize() {
        return boardSize;
    }
}
