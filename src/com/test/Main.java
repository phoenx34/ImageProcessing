package com.test;

public class Main {

    public static void main(String[] args) {
	// write your code here
        WGraph graph = new WGraph("graphinput.txt");
        int i = graph.V2V(1, 2, 5, 6);
        System.out.println(i + " is the shortest path from u to v");
    }
}
