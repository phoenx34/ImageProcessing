//package com.test;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.Stack;
//
//public class ImageProcessor {
//
//    private class Pixel {
//        int x, y, r, g, b;
//        Pixel(int r, int g, int b) {
//            this.r = r;
//            this.g = g;
//            this.b = b;
//        }
//
//        public int getR() {
//            return r;
//        }
//
//        public int getB() {
//            return b;
//        }
//
//        public int getG() {
//            return g;
//        }
//
//        public int getX() {
//            return x;
//        }
//
//        public int getY() {
//            return y;
//        }
//    }
//
//    ArrayList<ArrayList<Pixel>> m;
//    private static int H, W;
//
//
//    public ImageProcessor(String FName)
//    {
//        if (FName == null)
//            throw new NullPointerException("File is empty or non-existent");
//
//        m = new ArrayList<>();
//        fileParse(FName);
//    }
//
//    private void fileParse(String FName)
//    {
////        //Is this the correct delimeter to use?????????
////        String delimiters = "[ ]+";
//
//        File inputFile = new File (FName);
//
//        try {
//            FileReader fileReader = new FileReader (inputFile);
//            BufferedReader br = new BufferedReader(fileReader);
//
//            String line = br.readLine();
//            H = Integer.parseInt(line);
//
//            line = br.readLine();
//            W = Integer.parseInt(line);
//
//
//
//            for(int i = 0; i < H; i++)
//            {
//                line = br.readLine();
//                ArrayList<Pixel> px_line = new ArrayList<>();
//
//                while (!line.isEmpty()) {
//                    String temp = line.substring(0, line.indexOf(" "));
//                    int r = Integer.parseInt(temp);
//                    line = line.substring(line.indexOf(" ") + 1);
//
//                    temp = line.substring(0, line.indexOf(" "));
//                    int g = Integer.parseInt(temp);
//                    line = line.substring(line.indexOf(" ") + 1);
//
//                    temp = line.substring(0, line.indexOf(" "));
//                    int b = Integer.parseInt(temp);
//
//                    Pixel p = new Pixel(r, g, b);
//                    px_line.add(p);
//
//                    if (line.contains(" ")) {
//                        line = line.substring(line.indexOf(" ") + 1);
//                    } else {
//                        break;
//                    }
//                }
//
//                m.add(px_line);
//            }
//
//            br.close();
//
//        } catch(IOException e) {
//            System.out.println("Reading data from file failed.");
//        }
//    }
//
//    int getPDist(Pixel p, Pixel q) {
//
//        return -1;
//    }
//
//    int getYImportance(Pixel p) {
//        return -1;
//    }
//
//    int getXImportance(Pixel p) {
//        return -1;
//    }
//
//    int getPixelImportance(Pixel p) {
//
////        int yImp = 0;
////        int xImp = 0;
////        int x = p.ge
////        return -1;
//    }
//
//    /**
//     * Should calculate importance for every pixel in arraylist,
//     * and return a 2d array of said importance
//     * @return
//     */
//    ArrayList<ArrayList<Integer>> getImportance() {
//        return null;
//    }
//
//    void writeReduced(int k, String fname) {
//        return;
//    }
//
//
//
//}
//
