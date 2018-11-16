package com.test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
	// write your code here
        ImageProcessor fuckMe = new ImageProcessor("graphinput.txt");
        ArrayList<ImageProcessor.Pixel> q = fuckMe.MinVC();
        System.out.println(q);

        for (ImageProcessor.Pixel p : q) {
            System.out.print("(" + p.getX() + ", " + p.getY() + ") cost: " + p.getDist() + " -> ");
        }
        /*WGraph graph = new WGraph("graphinput.txt");
        ArrayList<Integer> S1 = new ArrayList<>();
        ArrayList<Integer> S2 = new ArrayList<>();
        S1.add(1);
        S1.add(2);

        S1.add(2);
        S1.add(2);


        S2.add(5);
        S2.add(6);

        S2.add(4);
        S2.add(4);

        S2.add(3);
        S2.add(4);

        ArrayList<Integer> i = graph.V2V(1,2, 5,6);
        //for (int j = 0; j < i.size() -2; j += 2) {
          //  System.out.println("(" + i.get(j) + ", " + i.get(j+1) + ")");
        //}
        System.out.println(i + " is the shortest path from u to v");
        */
    }
}
