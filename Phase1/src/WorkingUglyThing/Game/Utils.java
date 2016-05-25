package WorkingUglyThing.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by nibbla on 14.03.16.
 */
public class Utils {

    static String readFile(String path){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {

            e.printStackTrace();
            return null;
        }
        String everything = "";
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
               if (br!=null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return everything;
    }

    static void saveFile(String path, String content){
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter( new FileWriter(path));
            writer.write(content);

        }
        catch ( IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if ( writer != null)
                    writer.close( );
            }
            catch ( IOException e)
            {
            }
        }
    }

    public static ArrayList<Coordinate> getPxelBetweenToPoints(Coordinate coordinate1, Coordinate coordinate2) {
        //todo
        /*
        Dim As Integer Cont, dx, dy, dz, Adx, Ady, Adz, x_inc, y_inc, z_inc,_
               err_1, err_2, dx2, dy2, dz2, xxx, yyy, zzz, Xold, Yold, Zold,_
               Xnew, Ynew, Znew

While (1)
   Input "X: ",  Xnew
   Input "Y: ",  Ynew
   Input "Z: ",  Znew

   xxx=Xold
   yyy=Yold
   zzz=Zold

   dx = xnew - Xold
   dy = ynew - Yold
   dz = znew - Zold

   If (dx < 0) Then
      x_inc = -1
   Else
      x_inc =  1
   EndIf

   If (dy < 0) Then
      y_inc = -1
   Else
      y_inc =  1
   EndIf

   If (dz < 0) Then
      z_inc = -1
   Else
      z_inc =  1
   EndIf

   Adx = Abs(dx)
   Ady = Abs(dy)
   Adz = Abs(dz)

   dx2 = Adx*2
   dy2 = Ady*2
   dz2 = Adz*2

   If ((Adx>= Ady) And (Adx>= Adz)) Then

      err_1 = dy2 - Adx
      err_2 = dz2 - Adx

      For Cont = 0 To Adx-1

         If (err_1 > 0) Then
            yyy += y_inc
            err_1 -= dx2
         EndIf

         If (err_2 > 0) Then
            zzz += z_inc
            err_2 -= dx2
         EndIf

         err_1 += dy2
         err_2 += dz2
         xxx += x_inc

         print xxx, yyy, zzz

      Next

   EndIf

   If ((Ady> Adx) And (Ady>= Adz)) Then

      err_1 = dx2 - Ady
      err_2 = dz2 - Ady

      For Cont = 0 To  Ady-1

         If (err_1 > 0) Then
            xxx += x_inc
            err_1 -= dy2
         EndIf

         If (err_2 > 0) Then
            zzz += z_inc
            err_2 -= dy2
         EndIf

         err_1 += dx2
         err_2 += dz2
         yyy += y_inc

         print xxx, yyy, zzz

      Next

   EndIf

   If ((Adz> Adx) And (Adz> Ady)) Then

      err_1 = dy2 - Adz
      err_2 = dx2 - Adz

      For Cont = 0 To Adz-1

         If (err_1 > 0) Then
            yyy += y_inc
            err_1 -= dz2
         EndIf

         If (err_2 > 0) Then
            xxx += x_inc
            err_2 -= dz2
         EndIf

         err_1 += dy2
         err_2 += dx2
         zzz += z_inc

         print xxx, yyy, zzz

      Next

   EndIf

   Xold=xnew
   Yold=ynew
   Zold=znew
    Wend

    End
         */
        return null;
    }

    public static void saveManagedBufferedImage(String name, String format, BufferedImage image) {
        try {

            File outputfile = new File(name+"."+format);
            ImageIO.write(image, format, outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void saveCourse(Course course) {
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter( new FileWriter(course.name+".gol"));

            int length = course.playfield.length;
            int width = course.playfield[0].length;
            int height = course.playfield[0][0].length;
            StringBuilder s = new StringBuilder(50);
            s.append("Name:").append(course.name).append("\n");
            s.append("Par:").append(course.par).append("\n");
            s.append("length:").append(length).append("\n");
            s.append("width:").append(width).append("\n");
            s.append("height:").append(height).append("\n");
            writer.write(s.toString());

            for (int x = 0; x < length; x++) {
                for (int y = 0; y < width; y++) {
                    for (int z = 0; z < height; z++) {
                        if (!course.playfield[x][y][z].equals(Type.Empty)) {
                            StringBuilder b = new StringBuilder(10);
                            b.append("x").append(x).append("y").append(y).append("z").append(z).append("T").append(course.playfield[x][y][z].ordinal()).append("$\n");
                            writer.write(b.toString());
                        }
                    }
                }
            }

        }
        catch ( IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if ( writer != null)
                    writer.close( );
            }
            catch ( IOException e)
            {
            }
        }



        if (course.managedBufferedImage==null){//(true){//
            course.managedBufferedImage = DrawPanel.createImage(course);
        }
        Utils.saveManagedBufferedImage(course.name,"png",course.managedBufferedImage);


    }
}
