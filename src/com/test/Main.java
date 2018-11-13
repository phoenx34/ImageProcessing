package com.test;

import java.util.ArrayList;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
	// write your code here
        WGraph graph = new WGraph("graphinput.txt");
        ArrayList<Integer> S1 = new ArrayList<Integer>();
        ArrayList<Integer> S2 = new ArrayList<>();
        S1.add(1);
        S1.add(2);

        S1.add(7);
        S1.add(8);

        S1.add(5);
        S1.add(6);

        S2.add(3);
        S2.add(4);

        S2.add(9);
        S2.add(10);

        ArrayList<Integer> i = graph.S2S(S1, S2);
        //for (int j = 0; j < i.size() -2; j += 2) {
          //  System.out.println("(" + i.get(j) + ", " + i.get(j+1) + ")");
        //}
        System.out.println(i + " is the shortest path from u to v");
    }
}
