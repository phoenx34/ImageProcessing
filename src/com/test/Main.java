package com.test;

import java.util.ArrayList;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
	// write your code here
        WGraph graph = new WGraph("graphinput.txt");
        ArrayList<Integer> S = new ArrayList<Integer>();
        S.add(5);
        S.add(6);

//        S.add(1);
//        S.add(2);

        S.add(3);
        S.add(4);

//        S.add(7);
//        S.add(8);

        S.add(9);
        S.add(10);




        ArrayList<Integer> i = graph.V2V(1, 2, 5, 6);
        //for (int j = 0; j < i.size() -2; j += 2) {
          //  System.out.println("(" + i.get(j) + ", " + i.get(j+1) + ")");
        //}
        System.out.println(i + " is the shortest path from u to v");
    }
}
