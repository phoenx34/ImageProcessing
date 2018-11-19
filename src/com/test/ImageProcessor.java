package com.test;

import java.io.*;
import java.util.*;

/**
 * This class takes some file containing pixel data
 * relating to an image and downsizes said image/data
 * while preserving the original's quality as much
 * as possible. This will do downsizing of the width
 * only; it will not change the height of the image.
 *
 * @author Marc Isaac (misaac34@iastate.edu)
 * @author Christian Hernandez (cah1@iastate.edu)
 */

class ImageProcessor {

    /**
     * This class represents an individual pixel in the image.
     * It consists of R,G,B values and X,Y values for each
     * pixel as well as a distance value and visited bool.
     */
    private class Pixel implements Comparable<Pixel> {
        int x, y, r, g, b, dist;
        boolean visited;
        Pixel(int r, int g, int b, int x, int y) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.x = x;
            this.y = y;
            this.dist = Integer.MAX_VALUE;
            this.visited = false;
        }

        int getDist() {
            return dist;
        }

        boolean getVisited() {
            return visited;
        }

        void setVisited(boolean visited) {
            this.visited = visited;
        }

        void setDist(int dist) {
            this.dist = dist;
        }

        int getR() {
            return r;
        }

        int getB() {
            return b;
        }

        int getG() {
            return g;
        }

        int getX() {
            return x;
        }

        void setY(int y) {
            this.y = y;
        }

        int getY() {
            return y;
        }

        @Override
        public int hashCode(){
            String buffer = String.valueOf(this.getX()) +
                    this.getY();
            return buffer.hashCode();
        }

        @Override
        public boolean equals(Object object){
            if (object == null) return false;
            if (object == this) return true;
            if (this.getClass() != object.getClass()) return false;
            Pixel v = (Pixel) object;
            return this.hashCode() == v.hashCode();
        }

        @Override
        public int compareTo(Pixel q) {
            return Integer.compare(this.getDist(), q.getDist());
        }
    }

    private class Edge {
        Pixel src, dest;
        int weight;

        Edge(Pixel src, Pixel dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        Pixel getDest() {
            return dest;
        }

        int getWeight() {
            return weight;
        }
    }

    // This is the 2D array we will store all the pixels in.
    private ArrayList<ArrayList<Pixel>> m;
    // This contains a list of each pixel in m and their respective adjacent edges.
    private HashMap<Pixel, Set<Edge>> adj = new HashMap<>();
    // Height and Width values
    private static int H, W;
    // This is a 2D array of integer values representing the pixel's importance at
    // its specific x and y coordinate.
    private ArrayList<ArrayList<Integer>> I;

    /**
     * This is the constructor for ImageProcessor.
     * It takes a file name as input and:
     *  - Constructs a 2D arraylist of pixels
     *  - Constructs a corresponding 2D Arraylist of pixel's importance\
     *  - Populates a graph with edge weights corresponding to importance of pixels
     * @param FName file name of data file
     */
    ImageProcessor(String FName)
    {
        if (FName == null)
            throw new NullPointerException("File is empty or non-existent");

        m = new ArrayList<>();
        fileParse(FName);
        this.I = getImportance();
        populateGraph(this.I);
    }

    /**
     * This parses data from file and
     * populates a 2D ArrayList of pixels
     * corresponding to this data.
     *
     * @param FName File name of input data
     */
    private void fileParse(String FName)
    {

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
                ArrayList<Pixel> px_line = new ArrayList<>();
                int j = 0;
                line = br.readLine();
                while (line  != null && line.contains(" ")) {
                    String temp = line.substring(0, line.indexOf(" "));
                    int r = Integer.parseInt(temp);
                    line = line.substring(line.indexOf(" ") + 1);

                    temp = line.substring(0, line.indexOf(" "));
                    int g = Integer.parseInt(temp);
                    line = line.substring(line.indexOf(" ") + 1);
                    int b;
                    if (line.contains(" ")) {
                        temp = line.substring(0, line.indexOf(" "));
                        b = Integer.parseInt(temp);
                        line = line.substring(line.indexOf(" ") + 1);
                    } else {
                        b = Integer.parseInt(line);
                    }

                    Pixel p = new Pixel(r, g, b, i, j);
                    px_line.add(p);

                    j++;


                }

                m.add(px_line);
            }

            br.close();

        } catch(IOException e) {
            System.out.println("Reading data from file failed.");
        }

    }

    // Set containing all edges used. May or may not be necessary for final calculations/output,
    // but was left in because it was useful for debugging.
    private Set<Edge> e = new HashSet<>();

    /**
     * Populates a graph based on the contents of our Importance matrix
     * and fills an adjacency list.
     * @param I Our importance matrix
     */
    private void populateGraph(ArrayList<ArrayList<Integer>> I) {
        Edge e1, e2, e3;
        Set<Edge> adjacent;
        for (int i = 0; i < H - 1; i++) {
            for (int j = 0; j < W; j++) {
                if (j == 0) {
                    e1 = new Edge(m.get(i).get(j), m.get(i+1).get(j), I.get(i).get(j) + I.get(i+1).get(j));
                    e2 = new Edge(m.get(i).get(j), m.get(i+1).get(j+1), I.get(i).get(j) + I.get(i+1).get(j+1));
                    e.add(e1);
                    e.add(e2);
                    adjacent = new HashSet<>();
                    adjacent.add(e1);
                    adjacent.add(e2);
                    adj.put(m.get(i).get(j), adjacent);
                } else if (j == W - 1) {
                    e1 = new Edge(m.get(i).get(j), m.get(i + 1).get(j), I.get(i).get(j) + I.get(i + 1).get(j));
                    e2 = new Edge(m.get(i).get(j), m.get(i + 1).get(j - 1), I.get(i).get(j) + I.get(i + 1).get(j - 1));
                    e.add(e1);
                    e.add(e2);
                    adjacent = new HashSet<>();
                    adjacent.add(e1);
                    adjacent.add(e2);
                    adj.put(m.get(i).get(j), adjacent);
                } else {
                    e1 = new Edge(m.get(i).get(j), m.get(i+1).get(j), I.get(i).get(j) + I.get(i+1).get(j));
                    e2 = new Edge(m.get(i).get(j), m.get(i+1).get(j+1), I.get(i).get(j) + I.get(i+1).get(j+1));
                    e3 = new Edge(m.get(i).get(j), m.get(i + 1).get(j - 1), I.get(i).get(j) + I.get(i + 1).get(j - 1));
                    e.add(e1);
                    e.add(e2);
                    e.add(e3);
                    adjacent = new HashSet<>();
                    adjacent.add(e1);
                    adjacent.add(e2);
                    adjacent.add(e3);
                    adj.put(m.get(i).get(j), adjacent);
                }


            }
        }
    }

    /**
     * Calculates a distance int between two given pixels (p, q)
     * using the equation as per the spec:
     * dist = (p.R - q.R)^2 + (p.G - q.G)^2 + (p.B - q.B)^2
     * @param p the first pixel
     * @param q the second pixel
     * @return dist between the two pixels given by the equation above
     */
    private int getPDist(Pixel p, Pixel q) {
        double dist = Math.pow(p.getR() - q.getR(), 2) + Math.pow(p.getG() - q.getG(), 2) + Math.pow(p.getB() - q.getB(), 2);
        return (int) dist;
    }

    /**
     * Calculates a given Pixel's
     * Y-Importance as per the
     * program specifications
     * @param p The pixel to be calculated
     * @return the Y-Importance for Pixel p
     */
    private int getYImportance(Pixel p) {
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

    /**
     * Calculates a given Pixel's
     * X-Importance as per the
     * program specifications
     * @param p The pixel to be calculated
     * @return the X-Importance for Pixel p
     */
    private int getXImportance(Pixel p)
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

    /**
     * Gets the importance of a specific pixel.
     * This importance represents the particular pixel's
     * importance value in the importance array, I
     * @param p the pixel to be calculated
     * @return the importance of pixel p
     */
    private int getPixelImportance(Pixel p) {
        return getXImportance(p) + getYImportance(p);
    }

    /**
     * Should calculate importance for every pixel in arraylist,
     * and return a 2d array of said importance
     * @return
     */
    private ArrayList<ArrayList<Integer>> getImportance() {
        ArrayList<Integer> B = new ArrayList<>();
        Pixel p;
        ArrayList<ArrayList<Integer>> I = new ArrayList<>();
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                p = m.get(i).get(j);
                B.add(j, getPixelImportance(p));
                m.get(i).get(j).setVisited(false);
                m.get(i).get(j).setDist(Integer.MAX_VALUE);
            }
            ArrayList<Integer> temp = (ArrayList<Integer>) B.clone();
            I.add(i, temp);
            B.clear();
        }
        this.I = I;
        return I;
    }

    /**
     * Calculates the minimum vertical cut of pixels
     * in the graph using a variation of the S2S
     * method in WGraph.
     * S1 is the first row of pixels
     * S2 is the last row of pixels
     * @return An arraylist of pixels that represents the minimum vertical cut
     */
    private ArrayList<Pixel> MinVC() {
        I.clear();
        I = getImportance();
        Set<Pixel> S1 = new HashSet<>();
        Set<Pixel> S2 = new HashSet<>();

        Pixel src = new Pixel(0,0,0,0,0);

        for (int i = 0; i < W; i++) {
            m.get(0).get(i).setDist(I.get(0).get(i));
            S1.add(m.get(0).get(i));
            S2.add(m.get(H - 1).get(i));
        }

        for (Pixel p : S1) {
            Edge root = new Edge(src, p, 0);
            e.add(root);
        }

        int minCost = Integer.MAX_VALUE;
        ArrayList<Pixel> curPath = new ArrayList<>();
        Hashtable<Integer, ArrayList<Pixel>> paths = new Hashtable<>();
        src.setDist(0);
        PriorityQueue<Pixel> queue = new PriorityQueue<Pixel>();
//        for (int i = 0; i < H - 1; i++) {
//            queue.addAll(m.get(i));
//
//        }
        queue.addAll(m.get(0));
        queue.peek().setDist(getPixelImportance(queue.peek()));

        while (!queue.isEmpty()) {
            Pixel u = queue.poll();
            queue.clear();

            if (u.getX() == 0) {
                u.setDist(getPixelImportance(u));
                curPath.clear();
                u.setVisited(true);
                S1.remove(u);
            }

            if (!curPath.contains(u)) {
                curPath.add(u);
            }

            // TOP OF QUEUE WILL CONTAIN VALUES IN SAME LAYER AS STARTING. ONLY ADD VERT'S TO CURPATH IF NOT IN SAME LAYER

//            curPath.add(u.getX());
//            curPath.add(u.getY());

            Set<Edge> adjacentU = adj.get(m.get(u.getX()).get(u.getY()));

            for (Edge e : adjacentU) {
                Pixel v = e.getDest();
//                if (visited[v.index]) {
//                    v.setDist(0);
//                    visited[v.index] = false;
//                }
//                if (S2.contains(v)) {
//                    curPath.add(v);
//                    Object o = curPath.clone();
//                    ArrayList<Pixel> temp = (ArrayList<Pixel>) curPath.clone();
//                    if (v.getDist() < minCost) {
//                        minCost = v.getDist();
//                        paths.put(minCost, temp);
//                    } else {
//                        paths.put(v.getDist(), temp);
//                    }
//                    v.setVisited(false);
//                    curPath.remove(v);
//                }
                if (!v.getVisited()) {
//                    if (!s_2.contains(v) && adj.get(nodes.get(v.getIndex())).isEmpty()) {
//                        break;
//                    }
                    if ((u.getDist() + e.getWeight()) < v.getDist()) {
                        v.setDist(u.getDist() + e.getWeight());
                        queue.add(v);
                        v.setVisited(true);
                    }
                }

                if (S2.contains(v)) {
                    curPath.add(v);
                    Object o = curPath.clone();
                    ArrayList<Pixel> temp = (ArrayList<Pixel>) curPath.clone();
                    if (v.getDist() < minCost) {
                        minCost = v.getDist();
                        paths.put(minCost, temp);
                    } else {
                        paths.put(v.getDist(), temp);
                    }
                    v.setVisited(false);
                    curPath.remove(v);
                    queue.clear();
                    queue.addAll(S1);
                }

            }
        }

        return paths.get(minCost);

    }

    /**
     * This helper method takes care of updating our data to
     * maintain functionality and accuracy after the removal
     * of a given vertical cut (from minVC()).
     *
     *  - removes the given pixel and index from m<<>> and I<<>>
     *    respectively.
     *  - Updates other pixels' x and y values to respect the removal of said pixel
     *  - Clears the adjacency array
     *  - Updates the width of our graph
     *  - And populates the adjacency list again with our new Importance array, I.
     */
    private void removeAndUpdate() {

        ArrayList<Pixel> path = MinVC();
        for (Pixel p : path) {
            m.get(p.getX()).remove(p.getY());
            I.get(p.getX()).remove(p.getY());
            if (p.getY() == W - 1) {
                continue;
            }
            for (int i = p.getY(); i < W - 1; i++) {
                m.get(p.getX()).get(i).setY(m.get(p.getX()).get(i).getY() - 1);
            }
        }
        adj.clear();
        W = W - 1;
        populateGraph(I);

    }

    /**
     * Resizes an image by k where k is a column of pixels to be
     * removed. Say k = 3, then the image will shrink 3 columns
     * and we will print the resulting image data to fname
     * in the same format as our input image data.
     * @param k the number of columns we will shrink the image by
     * @param fname the file name that we write our output to
     */
    void writeReduced(int k, String fname) {
        for (int i = 0; i < k; i++) {
            I = getImportance();
            removeAndUpdate();
        }

        // After the for loop, print contents of m<<>> to file fname.

        File f = new File(fname);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream("Fname.txt"), true);
            pw.println(H);
            pw.println(W);

            for(int row = 0; row < H; row++)
            {
                for(int col = 0; col < W; col++)

                {
                    pw.write(m.get(row).get(col).getR() + " " + m.get(row).get(col).getG() + " " + m.get(row).get(col).getB() + " ");
                }
                pw.println();
            }

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }finally {
            if(pw!=null)
            {
                pw.close();
            }
        }
    }
}

