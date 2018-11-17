package com.test;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ImageProcessor {

    public class Pixel implements Comparable<Pixel> {
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

        public int getDist() {
            return dist;
        }

        public boolean getVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        public void setDist(int dist) {
            this.dist = dist;
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

        public void setY(int y) {
            this.y = y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
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
            if (this.getDist() < q.getDist()) {
                return -1;
            } else if (this.getDist() == q.getDist()) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    public class Edge {
        Pixel src, dest;
        int weight;

        Edge(Pixel src, Pixel dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        Pixel getSrc() {
            return src;
        }

        Pixel getDest() {
            return dest;
        }

        int getWeight() {
            return weight;
        }
    }

    ArrayList<ArrayList<Pixel>> m;
    HashMap<Pixel, Set<Edge>> adj = new HashMap<>();
    private static int H, W;


    public ImageProcessor(String FName)
    {
        if (FName == null)
            throw new NullPointerException("File is empty or non-existent");

        m = new ArrayList<>();
        fileParse(FName);
        populateGraph(getImportance());
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

    Set<Edge> e = new HashSet<>();

    void populateGraph(ArrayList<ArrayList<Integer>> I) {
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

    void adjPop() {

    }


    int getPDist(Pixel p, Pixel q) {
        double dist = Math.pow(p.getR() - q.getR(), 2) + Math.pow(p.getG() - q.getG(), 2) + Math.pow(p.getB() - q.getB(), 2);
        return (int) dist;
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


    ArrayList<Pixel> MinVC() {
        Set<Pixel> S1 = new HashSet<>();
        Set<Pixel> S2 = new HashSet<>();

        ArrayList<ArrayList<Integer>> I = getImportance();

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
            // COPY THIS PLEASE LEAVE A COPY UNCHANGED FOR ME TO RESUME WORK TM
            // CURRENT PROBLEMS: CurPath wiping unexpectedly, dist of destination vertex not correct, and vertices in same layer both added to curpath

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


//                if (S1.contains(v)) {
//                    v.setDist(0);
//                    curPath.clear();
//                    curPath.add(v);
//                    break;
//                }

            }
        }

        return paths.get(minCost);

    }

    void removeAndUpdate() {
        // Needs testing. Should remove the values in path from M<<>>
        // then update the x and y coords of all nodes after the one removed correctly
        ArrayList<Pixel> path = MinVC();
        for (Pixel p : path) {
            m.get(p.getX()).remove(p.getY());
            if (p.getY() == W - 1) {
                continue;
            }
            for (int i = p.getY(); i < W - 1; i++) {
                m.get(p.getX()).get(i).setY(m.get(p.getX()).get(i).getY() - 1);
            }
        }
    }

    void writeReduced(int k, String fname) {
        for (int i = 0; i < k; i++) {
            removeAndUpdate();
        }

        // After the for loop, print contents of m<<>> to file fname.
        //Esteban mentions to store into a string which I will try to do
        //Store into a string, then get the first two lines of the file?????
        //Then write to file

        File f = new File(fname);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream("Fname.txt"), true);
            pw.println(H);
            pw.println(W);

            for(int col = 0; col < m.size(); col++)
            {
                for(int row = 0; row < m.get(col).size(); row++)

                {
                    //Do I need getR, getG, and getB?
                    pw.write(m.get(row).get(col).getR() + " " + m.get(row).get(col).getG() + " " + m.get(row).get(col).getB());
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

        return;
    }









}

