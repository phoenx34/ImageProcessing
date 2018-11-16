package com.test;

public class Pixel extends WGraph.Node {

    //Should this variable be final int
    private  int r, g, b;
    private int imp;




    public Pixel(final int r, final int g, final int b, int x, int y )
    {
        super(x, y);
        this.r = r;
        this.g = g;
        this.b = b;
        //initialize importance variable to 0
        imp = 0;


        public int getR()
        {
            return r;
        }

        public int getF()
        {
            return g;
        }

        public int getB()
        {
            return b;
        }


        public int getImportance()
        {
            return imp;
        }

        public void setImportance(int num)
        {
            this.imp = num;
        }

    }
}
