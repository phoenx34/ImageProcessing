package com.test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class ImageProcessor {

    Stack<Pixel> nodes;
    ArrayList<Pixel>[] pix;
    final int height;
    int width;


    public ImageProcessor(String FName)
    {
        this.height = fileParse(FName);
    }

    private int fileParse(String FName)
    {
        //Is this the correct delimeter to use?????????
        String delimiters = "[ ]+";

        try {
            //PROBABLY WILL USE TRY CATCH HERE
            BufferedReader br = new BufferedReader(new FileReader(FName));
            String line = br.readLine();
            int h = Integer.parseInt(line);

            line = br.readLine();
            width = Integer.parseInt(line);

            pix = new ArrayList[h];

            for(int i = 0; i < h; i++)
            {
                line = br.readLine();
                String[] values = line.split(delimiters);
                for(int j = 0; j < values.length; j+=3)
                {
                    Integer r = Integer.parseInt(values[j]);
                    Integer g = Integer.parseInt(values[j+1]);
                    Integer b = Integer.parseInt(values[j+2]);
                    Pixel pixieDust = new Pixel(r, g, b, j, i);
                    pix[i].add(pixieDust);
                    nodes.push(pixieDust);

                }
            }

            br.close();

            return h;
        }

        catch(Exception eio)
        {
            eio.printStackTrace();
        }
        return -1;
    }



}
