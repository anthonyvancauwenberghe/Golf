package MarkusTestLab;



import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.ScatterMultiColor;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Nibbla on 09.04.2016.
 */
public class TestClassMarkusDontDeleteOrDoomIsNear {



    public static void main(String[] args){
        System.out.println("Welcome to Markus' Testlab\n");
        boolean scenario1 = true;
        boolean scenario2 = true;
        boolean scenario3 = true;
        boolean scenario4 = true;
        boolean scenario5 = true;

        boolean scenario6 = true;
        boolean scenario7 = true;
        boolean scenario8 = true;
        boolean scenario9 = false;

        System.out.println("Creating and filling different Array Types\n");
        Tile[][][] playfield;
        Tile[] playfield2;
        Type[][][] playfield3;
        Type[] playfield4;
        ArrayList<ArrayList<ArrayList<Type>>> playfield5;

        double startTime;
        double endTime;
        double passedTime;



        if(scenario1) {
                Scenario1();
        }

        if(scenario2) {
                Scenario2();

        }


        if(scenario3) {

                Scenario3();



        }

        if(scenario4) {

            Scenario4();


        }

        if(scenario5) {
            Scenario5();

        }


        System.out.println("");
        System.out.println("Retrieving different Array Types\n");

        if(scenario6) {
            Scenario6();

        }

        if(scenario7) {

                Scenario7();





        }

        if(scenario8) {

                Scenario8();





        }
        if(scenario9) {
            //Print out Data
            printOut(Scenario3());
        }


    }

    private static void printOut(Type[][][] types) {
        int size = 100000;
        float x;
        float y;
        float z;
        Coord3d[] points = new Coord3d[size];

        // Create scatter points
        for(int i=0; i<size; i++){
            x = (float)Math.random() - 0.5f;
            y = (float)Math.random() - 0.5f;
            z = (float)Math.random() - 0.5f;
            points[i] = new Coord3d(x, y, z);
        }

        // Create a drawable scatter with a colormap
        ScatterMultiColor scatter = new ScatterMultiColor( points, new ColorMapper( new ColorMapRainbow(), -0.5f, 0.5f ) );

        // Create a chart and add scatter
        Chart chart = new Chart();
        chart.getAxeLayout().setMainColor(Color.WHITE);
        chart.getView().setBackgroundColor(Color.BLACK);
        chart.getScene().add(scatter);

        ChartLauncher.openChart(chart);
    }

    private static void Scenario8() {
        Tile[][][] playfield;
        double startTime;
        double endTime;
        double passedTime;//
        // Scenario Three

        int n = 100000;
        int length = 800;
        int width = 400;
        int height = 100;
        int e = length * width * height;
        playfield = new Tile[length][width][height];

        for (int x = 0; x < length; x++) {
            for (int y = 0; y < width; y++) {
                playfield[x][y][0] = new Tile(Type.Grass,x,y,0);
                for (int z = 1; z < height; z++) {
                    playfield[x][y][z] = new Tile(Type.Empty,x,y,z);
                }
            }
        }
        startTime = System.currentTimeMillis();
        Random x = new Random();
        for (int i = 0; i < n; i++) {
            Tile s = playfield[x.nextInt(length)][x.nextInt(width)][x.nextInt(height)];
        }

        endTime = System.currentTimeMillis();
        passedTime = endTime - startTime;

        System.out.format("Version Retrieval of %d random elements from 3dim Array of %d elements in Dimension %d,%d,%d Tiles took %n \t %.3f Milliseconds%n",n, e, length, width, height, passedTime);
    }

    private static void Scenario7() {
        Type[][][] playfield3;
        double startTime;
        double endTime;
        double passedTime;//
        // Scenario Three

        int n = 100000;
        int length = 800;
        int width = 400;
        int height = 100;
        int e = length * width * height;
        playfield3 = new Type[length][width][height];
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < width; y++) {
                playfield3[x][y][0] = Type.Grass;
                for (int z = 1; z < height; z++) {
                    playfield3[x][y][z] = Type.Empty;
                }
            }
        }
        startTime = System.currentTimeMillis();
        Random x = new Random();
        for (int i = 0; i < n; i++) {
            Type s = playfield3[x.nextInt(length)][x.nextInt(width)][x.nextInt(height)];
        }

        endTime = System.currentTimeMillis();
        passedTime = endTime - startTime;

        System.out.format("Version Retrieval of %d random elements from 3dim Array of %d elements in Dimension %d,%d,%d enums took %n \t %.3f Milliseconds%n",n, e, length, width, height, passedTime);
    }

    private static void Scenario6() {
        Type[] playfield4;
        double startTime;
        double endTime;
        double passedTime;//
        // Scenario Five Retrieving from 1dim enum

        int n = 100000;
        int length = 800;
        int width = 400;
        int height = 100;
        int e = length * width * height;
        playfield4 = new Type[e];
        for (int i = 0; i < e; i++) {
            playfield4[i] = Type.Empty;
        }
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < width; y++) {
                playfield4[x*y*height] = Type.Grass;
            }
        }
        startTime = System.currentTimeMillis();
        Random x = new Random();
        for (int i = 0; i < n; i++) {
            Type s = playfield4[x.nextInt(length) + width * (x.nextInt(width) + height * x.nextInt(height))];
        }

        endTime = System.currentTimeMillis();
        passedTime = endTime - startTime;

        System.out.format("Version Retrieval of %d random elements from 1dim Array of %d elements in Dimension %d,%d,%d enums took %n \t %.3f Milliseconds%n",n, e, length, width, height, passedTime);
    }

    private static void Scenario5() {
        double startTime;
        ArrayList<ArrayList<ArrayList<Type>>> playfield5;
        double endTime;
        double passedTime;//
        // Scenario Five

        startTime = System.currentTimeMillis();

        int length = 800;
        int width = 400;
        int height = 100;
        int e = length * width * height;
        playfield5 = new ArrayList<>(length);
        for (int x = 0; x < length; x++) {
            playfield5.add(new ArrayList<>(width));
            for (int y = 0; y < width; y++) {
                playfield5.get(x).add(new ArrayList<>(height));
                playfield5.get(x).get(y).add(Type.Grass);
                for (int z = 1; z < height; z++) {
                    playfield5.get(x).get(y).add(Type.Empty);
                }

            }
        }

        endTime = System.currentTimeMillis();
        passedTime = endTime - startTime;

        System.out.format("Version 3dim ArrayList<Type> for %d elements in Dimension %d,%d,%d enums took %n \t %.3f Milliseconds%n", e, length, width, height, passedTime);
    }

    private static void Scenario4() {
        double startTime;
        Type[] playfield4;
        double endTime;
        double passedTime;//
        // Scenario Four
        startTime = System.currentTimeMillis();

        int length = 800;
        int width = 400;
        int height = 100;
        int e = length * width * height;
        playfield4 = new Type[e];
        for (int i = 0; i < e; i++) {
            playfield4[i] = Type.Empty;
        }
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < width; y++) {
                playfield4[x + width * (y + height * 0)] = Type.Grass;
            }
        }

        endTime = System.currentTimeMillis();
        passedTime = endTime - startTime;

        System.out.format("Version 1dim Array for %d elements in Dimension %d,%d,%d enums took %n \t %.3f Milliseconds%n", e, length, width, height, passedTime);
    }

    private static Type[][][] Scenario3() {
        double startTime;
        Type[][][] playfield3;
        double endTime;
        double passedTime;//
        // Scenario Three
        startTime = System.currentTimeMillis();

        int length = 800;
        int width = 400;
        int height = 100;
        int e = length * width * height;
        playfield3 = new Type[length][width][height];
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < width; y++) {
                playfield3[x][y][0] = Type.Grass;
                for (int z = 1; z < height; z++) {
                    playfield3[x][y][z] = Type.Empty;
                }
            }
        }

        endTime = System.currentTimeMillis();
        passedTime = endTime - startTime;

        System.out.format("Version 3nested Array for %d elements in Dimension %d,%d,%d with enums took %n \t %.3f Milliseconds%n", e, length, width, height, passedTime);

        return  playfield3;
    }

    private static void Scenario2() {
        double startTime;
        Tile[] playfield2;
        double endTime;
        double passedTime;//
        // Scenario Two
        startTime = System.currentTimeMillis();

        int length = 800;
        int width = 400;
        int height = 100;
        int e = length * width * height;
        int i = 0;
        playfield2 = new Tile[e];
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < width; y++) {
                for (int z = 0; z < height; z++) {
                    playfield2[i++] = Tile.newTile(Type.Empty, x, y, z);
                }
            }
        }

        endTime = System.currentTimeMillis();
        passedTime = endTime - startTime;

        System.out.format("Version 1dim Array for %d elements in Dimension %d,%d,%d took %n \t %.3f Milliseconds%n", e, length, width, height, passedTime);
    }

    private static void Scenario1() {
        double startTime;
        Tile[][][] playfield;
        double endTime;
        double passedTime;//
        // Scenario One
        startTime = System.currentTimeMillis();

        int length = 800;
        int width = 400;
        int height = 100;
        int e = length * width * height;
        playfield = new Tile[length][width][height];
        for (int x = 0; x < length; x++) {
            for (int y = 0; y < width; y++) {
                for (int z = 0; z < height; z++) {
                    playfield[x][y][z] = Tile.newTile(Type.Empty, x, y, z);
                }
            }
        }

        endTime = System.currentTimeMillis();
        passedTime = endTime - startTime;

        System.out.format("Version 3nested Array for %d elements in Dimension %d,%d,%d took %n \t %.3f Milliseconds%n", e, length, width, height, passedTime);
    }
}
