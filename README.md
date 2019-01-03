# ImageProcessing
CS 311 Assignment 02

This program will take as input a data file containing rgb values for pixels in an image. It represents the data in a directed, weighted importance graph, and uses a shortest path algorithm (modified bfs and dijkstra's) to find a path of the least important pixels from the top to the bottom of the image and remove these pixels. This process effectively resizes the image by removing a column of pixels. This process can be repeated k times depending on the number of pixels in the image. Also included is 3 different shortest path algorithms as follows: a shortest path from one single vertex to another, a shortest path from a set of vertices to one vertex, and a shortest path from a set of vertices to another set of vertices. 

The runtime and big o notation for each algorithm can be seen in the comments of the code. Please see the attached wgraph.pdf for the original assignment sheet and further specifications. To run the code, supply a txt file of image data and name it "graphinput.txt", formatted as in the pdf. Run main.java, and an output file will be generated with the subtraction of pixels. 
