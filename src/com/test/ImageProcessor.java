package com.test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class ImageProcessor {

    private class Pixel {
        int x, y, r, g, b;
        Pixel(int r, int g, int b, int x, int y) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.x = x;
            this.y = y;
        }

        public int getR() {
            return r;
        }

        public int getB() {
            return b;
        }

        public int getG() {
            return g;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    ArrayList<ArrayList<Pixel>> m;
    private static int H, W;


    public ImageProcessor(String FName)
    {
        if (FName == null)
            throw new NullPointerException("File is empty or non-existent");

        m = new ArrayList<>();
        fileParse(FName);
    }

    private void fileParse(String FName)
    {
//        //Is this the correct delimeter to use?????????
//        String delimiters = "[ ]+";

        File inputFile = new File (FName);

        try {
            FileReader fileReader = new FileReader (inputFile);
            BufferedReader br = new BufferedReader(fileReader);

            String line = br.readLine();
            H = Integer.parseInt(line);

            line = br.readLine();
            W = Integer.parseInt(line);



            for(int i = 0; i < H; i++)
            {
                line = br.readLine();
                ArrayList<Pixel> px_line = new ArrayList<>();
                int j = 0;
                while (!line.isEmpty()) {
                    String temp = line.substring(0, line.indexOf(" "));
                    int r = Integer.parseInt(temp);
                    line = line.substring(line.indexOf(" ") + 1);

                    temp = line.substring(0, line.indexOf(" "));
                    int g = Integer.parseInt(temp);
                    line = line.substring(line.indexOf(" ") + 1);

                    temp = line.substring(0, line.indexOf(" "));
                    int b = Integer.parseInt(temp);

                    Pixel p = new Pixel(r, g, b, i, j);
                    px_line.add(p);

                    j++;

                    if (line.contains(" ")) {
                        line = line.substring(line.indexOf(" ") + 1);
                    } else {
                        break;
                    }
                }

                m.add(px_line);
            }

            br.close();

        } catch(IOException e) {
            System.out.println("Reading data from file failed.");
        }
    }

    int getPDist(Pixel p, Pixel q) {
        int dist = (p.getR() - q.getR()) + (p.getG() - q.getG()) + (p.getB() - q.getB());
        double d = Math.pow((double) dist, 2);
        return (int) d;
    }

    int getYImportance(Pixel p) {
        Pixel q,r;
        if (p.getX() == 0) {
            q = m.get(H - 1).get(p.getY());
            r = m.get(p.getX() + 1).get(p.getY());
            return getPDist(q, r);
        } else if (p.getX() == H - 1) {
            q = m.get(p.getX() - 1).get(p.getY());
            r = m.get(0).get(p.getY());
            return getPDist(q, r);
        } else {
            q = m.get(p.getX() - 1).get(p.getY());
            r = m.get(p.getX() + 1).get(p.getY());
            return getPDist(q, r);
        }
    }

    int getXImportance(Pixel p)
    {
        Pixel q,r;
        if (p.getY() == 0) {
            q = m.get(p.getX()).get(W - 1);
            r = m.get(p.getX()).get(p.getY() + 1);
            return getPDist(q, r);
        } else if (p.getY() == W - 1) {
            q = m.get(p.getX()).get(p.getY() - 1);
            r = m.get(p.getX()).get(0);
            return getPDist(q, r);
        } else {
            q = m.get(p.getX()).get(p.getY() - 1);
            r = m.get(p.getX()).get(p.getY() + 1);
            return getPDist(q, r);
        }
    }

    int getPixelImportance(Pixel p) {
        return getXImportance(p) + getYImportance(p);
    }

    /**
     * Should calculate importance for every pixel in arraylist,
     * and return a 2d array of said importance
     * @return
     */
    ArrayList<ArrayList<Integer>> getImportance() {
        ArrayList<Integer> B = new ArrayList<>();
        Pixel p;
        ArrayList<ArrayList<Integer>> I = new ArrayList<>();
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                p = m.get(i).get(j);
                B.add(j, getPixelImportance(p));
            }
            I.add(i, B);
        }
        return I;
    }

    void writeReduced(int k, String fname) {
        return;
    }



}

