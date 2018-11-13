package com.test;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * This class takes a file name as input, and scans that file for data.
 * It represents the data in the file as a graph.
 * It also calculates various shortest paths from given vertices
 * and sets of vertices.
 *
 * @author Marc Isaac (misaac34@iastate.edu)
 */
public class WGraph {

    /**
     * This class consists of an x and y coordinate,
     * and a list of all adjacent vertices to this node.
     *
     * Need to figure out what getter and setters we actually use
     * also need to fix the public declarations
     * Usually do both of these at the end of the project
     */
    public class Node implements Comparable<Node>{
        int x, y, index, dist;
        List<Edge> neighbors = new LinkedList<>();

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
            dist = Integer.MAX_VALUE;
        }

        public int getDist() {
            return dist;
        }

        public void setDist(int dist) {
            this.dist = dist;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public void addNeighbor(Edge e) {
            neighbors.add(e);
        }

        public List<Edge> getNeighbors() {
            return neighbors;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public int hashCode(){
            StringBuffer buffer = new StringBuffer();
            buffer.append(this.getX());
            buffer.append(this.getY());
            return buffer.toString().hashCode();
        }

        @Override
        public boolean equals(Object object){
            if (object == null) return false;
            if (object == this) return true;
            if (this.getClass() != object.getClass()) return false;
            Node v = (Node)object;
            if(this.hashCode()== v.hashCode())
                return true;
            return false;
        }

        @Override
        public int compareTo(Node v) {
            if (this.getDist() < v.getDist()) {
                return -1;
            } else if (this.getDist() == v.getDist()) {
                return 0;
            } else {
                return 1;
            }
        }
    }



    /**
     * This class consists of a start and end node,
     * and a weight that make up an edge, E = <start, end>
     * which represents the edge from start to end.
     */
    public class Edge {
        Node src, dest;
        int weight;

        public Edge(Node src, Node dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        public Node getSrc() {
            return src;
        }

        public void setSrc(Node src) {
            this.src = src;
        }

        public Node getDest() {
            return dest;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

    }


    private List<Node> nodes = new LinkedList<Node>() {
        @Override
        public boolean contains(Object o) {
            if(!nodes.isEmpty()) {
                for (Node v : nodes) {
                    if (v.equals((Node) o)) {
                        ((Node) o).setIndex(v.getIndex());
                        return true;
                    }
                }
            } return false;
        }
    };
    private List<Edge> edges = new LinkedList<>();
    private Map<Node,List<Edge>> adj = new HashMap<>();
    private int V, E;

    /**
     * This is the default constructor for WGraph.
     * It takes a file name and reads the data from that file,
     * populating a graph, G with the data from the file as
     * a list of nodes and the edges that connect them.
     *
     * @param FName
     */
    public WGraph(String FName) {
        if (FName == null)
            throw new NullPointerException("File is empty or non-existent");

        populateGraph(FName);
        adjPop();



    }

    /**
     * Currently everything is working upon basic testing besides the adjacency map.
     * However, each node will have a set of neighbors which is correct based on the edges
     * given in the file. All that is left to do is translate that set of neighbors into an adjacency list (map)
     * @param FName
     */
    private void populateGraph(String FName) {
        int ux, uy, vx, vy, wt;
        int i=0, j=0, r=0;
        File inputFile = new File (FName);

        try {
            FileReader fileReader = new FileReader (inputFile);
            BufferedReader br = new BufferedReader(fileReader);

            String line = br.readLine();
            V = Integer.parseInt(line);

            line = br.readLine();
            E = Integer.parseInt(line);

            while ((line = br.readLine()) != null) {
//                line = br.readLine();


                // Is there a better/less redundant way to do this?
                // If I use split, the runtime will get nuked.
                // If I use substring as below, we repeat many lines.
                // We should be able to remove this portion
                // from the constructor to a helper method at the very
                // least.

                String temp = line.substring(0, line.indexOf(" "));
                ux = Integer.parseInt(temp);
                line = line.substring(line.indexOf(" ") + 1);

                temp = line.substring(0, line.indexOf(" "));
                uy = Integer.parseInt(temp);
                line = line.substring(line.indexOf(" ") + 1);

                temp = line.substring(0, line.indexOf(" "));
                vx = Integer.parseInt(temp);
                line = line.substring(line.indexOf(" ") + 1);

                temp = line.substring(0, line.indexOf(" "));
                vy = Integer.parseInt(temp);
                line = line.substring(line.indexOf(" ") + 1);

                wt = Integer.parseInt(line);


                // needs a check to see if either node already exists
                Node u_node = new Node(ux, uy);
                Node v_node = new Node(vx, vy);

                // If u_node does not exist, we must add it to nodes
                // and also add it to the adjacency list as a new key-value pair

                Edge e = new Edge(u_node, v_node, wt);
                edges.add(e);

                for (Edge q : edges) {
                    if (q.getSrc().equals(u_node)) {
                        u_node.addNeighbor(q);
                    }
                }

                if (nodes.contains(u_node)) {
                    nodes.set(u_node.getIndex(), u_node);
                } else {
                    u_node.setIndex(j);
                    nodes.add(u_node);
                    j++;
                }


                if (!nodes.contains(v_node)) {
                    v_node.setIndex(j);
                    nodes.add(v_node);
                    j++;
                }

                // Need to check if adj contains u_node or not.
                // If so, we need to replace instead of put

                i += 2;

            }
        } catch (IOException e) {
            System.out.println("Reading file data failed.");

        }
    }

    public void adjPop() {
        for (Node v : nodes) {
            if (adj.isEmpty() || !adj.containsKey(v)) {
                adj.put(v, v.getNeighbors());
            }
        }
    }

    /**
     * Correctly outputs the shortest path's total weight if one exists
     *
     *
     * Efficiency: O(log(V)(V+E))
     *
     * @param ux
     * @param uy
     * @param vx
     * @param vy
     * @return
     */

    public ArrayList<Integer> V2V(int ux, int uy, int vx, int vy) {
        Node src = new Node(ux, uy);
        Node dest = new Node(vx, vy);

        ArrayList<Integer> paths = new ArrayList<>();

        boolean[] visited = new boolean[V];

        PriorityQueue<Node> queue = new PriorityQueue<>();

        src.setDist(0);
        queue.addAll(nodes);

        while (!queue.isEmpty()) {

            Node u = queue.poll();
            paths.add(u.getX());
            paths.add(u.getY());

            List<Edge> adjacentU = adj.get(nodes.get(u.getIndex()));

            for (Edge e : adjacentU) {
                Node v = e.getDest();
                if(!visited[v.index]) {
                    if ((u.getDist() + e.getWeight()) < v.getDist()) {
                        v.setDist(u.getDist() + e.getWeight());
                        visited[v.index] = true;
                        queue.add(v);
                    }
                }
                if (dest.equals(v)) {
                    paths.add(dest.getX());
                    paths.add(dest.getY());
                    return paths;
                }
            }
        }

        return null;
    }

    /**
     * This is just simple dijkstra's to get shortest path from source to a set of vertices.
     * Assuming the above implementation for the shortest path from a given vertex
     * to another vertex is correct, we must only remove the catch checking if a given
     * adjacent vertex is equal to the destination.
     *
     * Efficiency: O(Log(V)(V+E))
     *
     * @param ux
     * @param uy
     * @param S
     * @return
     */
    public ArrayList<Integer> V2S(int ux, int uy, ArrayList<Integer> S) {

        Node src = new Node(ux, uy);

        Set<Node> p = new HashSet<>();

        int minCost = Integer.MAX_VALUE;
        ArrayList<Integer> curPath = new ArrayList<>();
        Hashtable<Integer, ArrayList<Integer>> paths = new Hashtable<Integer, ArrayList<Integer>>();

        for (int i = 0; i < S.size(); i+=2) {
            Node d = new Node(S.get(i), S.get(i+1));
            p.add(d);
        }

        boolean[] visited = new boolean[V];

        PriorityQueue<Node> queue = new PriorityQueue<>();

        src.setDist(0);
        queue.add(src);

        curPath.add(src.getX());
        curPath.add(src.getY());

        while (!queue.isEmpty()) {
            Node u = queue.poll();
            List<Edge> adjacentU = adj.get(nodes.get(u.getIndex()));

            for (Edge e : adjacentU) {
                Node v = e.getDest();
                if (!visited[v.index]) {
                    if ((u.getDist() + e.getWeight()) < v.getDist()) {
                        v.setDist(u.getDist() + e.getWeight());
                        visited[v.index] = true;
                        queue.add(v);
                        if (!p.contains(v) && adj.get(nodes.get(v.getIndex())).isEmpty()) {
                            continue;
                        } else {
                            curPath.add(v.getX());
                            curPath.add(v.getY());
                        }
                    }
                    if (p.contains(v)) {
                        ArrayList<Integer> temp = (ArrayList<Integer>) curPath.clone();
                        if (v.getDist() < minCost) {
                            minCost = v.getDist();
                            paths.put(minCost, temp);
                        } else {
                            paths.put(v.getDist(), temp);
                        }
                        curPath.clear();
                        curPath.add(src.getX());
                        curPath.add(src.getY());
                    }
                }
            }
        }
        ArrayList<Integer> minPath = paths.get(minCost);
        if (!minPath.isEmpty()) {
            return minPath;
        }
        return null;
    }

    /**
     * Travis and I discussed in class that we should do this one by keeping two sets,
     *
     * Create two new vertices, one for each set. Say u exists in S1 and v exists in S2
     * Set the vertices to have a path to every edge in each set respectively,
     * with an edge weight of 0 for all of the new edges (adjacent to u or v)
     *
     * Find the shortest path between edge u and v. This path represents a
     * shortest path between some vertex in S1 and some vertex in S2.
     *
     * The order of the nodes in the ArrayList returned represent
     * the shortest overall path from a vertex in set 1 to a vertex in set 2.
     *
     * Efficiency: O(Elog(V))
     *
     * @param S1
     * @param S2
     * @return
     */
    public ArrayList<Integer> S2S(ArrayList<Integer> S1, ArrayList<Integer> S2) {
        Set<Node> s_1 = new HashSet<>();
        Set<Node> s_2 = new HashSet<>();
        boolean[] visited = new boolean[V];
        for (int i = 0; i < S1.size(); i += 2) {
            Node n = new Node(S1.get(i), S1.get(i+1));
            s_1.add(n);
        }
        for (int j = 0; j < S2.size(); j += 2) {
            Node m = new Node(S2.get(j), S2.get(j+1));
            s_2.add(m);
        }

        Node q = new Node(0, 0);
        Node s = new Node(0, 0);

        for (Node n : s_1) {
            Edge e = new Edge(q, n, 0);
            q.addNeighbor(e);
        }

        for (Node r : s_2) {
            Edge e = new Edge(s, r, 0);
            s.addNeighbor(e);
        }

        int minCost = Integer.MAX_VALUE;
        ArrayList<Integer> curPath = new ArrayList<>();
        Hashtable<Integer, ArrayList<Integer>> paths = new Hashtable<Integer, ArrayList<Integer>>();

        PriorityQueue<Node> queue = new PriorityQueue<>();
        q.setDist(0);
        queue.add(q);

        curPath.add(q.getX());
        curPath.add(q.getY());

        while (!queue.isEmpty()) {
            Node u = queue.poll();
            List<Edge> adjacentU = adj.get(nodes.get(u.getIndex()));

            for (Edge e : adjacentU) {
                Node v = e.getDest();
                if (!visited[v.index]) {
                    if ((u.getDist() + e.getWeight()) < v.getDist()) {
                        v.setDist(u.getDist() + e.getWeight());
                        visited[v.index] = true;
                        queue.add(v);
                        if (!s_2.contains(v) && adj.get(nodes.get(v.getIndex())).isEmpty()) {
                            continue;
                        } else {
                            curPath.add(v.getX());
                            curPath.add(v.getY());
                        }
                    }
                    if (s_2.contains(v)) {
                        ArrayList<Integer> temp = (ArrayList<Integer>) curPath.clone();
                        if (v.getDist() < minCost) {
                            minCost = v.getDist();
                            paths.put(minCost, temp);
                        } else {
                            paths.put(v.getDist(), temp);
                        }
                        curPath.clear();
                        curPath.add(u.getX());
                        curPath.add(u.getY());
                    }
                }
            }
        }
        ArrayList<Integer> minPath = paths.get(minCost);
        if (!minPath.isEmpty()) {
            return minPath;
        }
        return null;
    }
}
