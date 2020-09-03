
Overview
--------

This program accepts city map data, a current location, and a
destination to print the sequence of intersections to traverse
the shortest path from a source to a destination. This program 
acts like a undirected weighted graph. A summary of the formatting 
requirements are in the csci 3901 course assignment #3 information 
in the course's Brightspace space.

The path from source to destination is achieved by making locally optimal
choices at each stage in the hope of finding the global optimum.
The solution uses several divisions between the kinds of information 
input by the user, to find a path between two intersections. The program works
in below three phases:

- Creating new intersection(s); each intersection has (x , y) coordinates 
   to tell where the intersection is.
- Defining a road; roads can be created by connecting two existing 
   intersections.
-  Finally, a navigation path can be derived by entering desired start and end 
   intersections.

Files and external data
-----------------------

There are two main files:
  - Map_UI.java -- main for the program that prompts the user for inputs and then
		 print the sequence of navigation path.
  - HalifaxMap.java -- class that calculates the actual navigation path.

Methods and their significance
-----------------------------------------

newIntersection( int x, int, y ) -- A method used to record a new intersection (x ,y) 
			for the city. It then returns true if we acknowledge 
			the intersection and didn’t know about it before. 
			Returns false otherwise.
defineRoad( int x1, int y1, int x2, int y2 ) -- A method used to record the existence 
			of a road from (x1, y1) to (x2, y2) in the city. It returns 
			true if the road is a new one for the map and has been 
			added to requests to consider. Returns false otherwise.
navigate( int x1, int y1, int x2, int y2 ) – This method prints to the screen the sequence of
			intersection coordinates to follow to go from (x1, y1) to (x2, y2)
			in the shortest distance while staying on roads. 
			It prints “no path” if it is not possible to get from the source to
			the destination.

Data structures and their relations to each other
-------------------------------------------------

The program accumulates the intersections into an array of array lists.
It uses a two-dimensional array, moreover an adjacencymatrix which is 
used to store the definitions of a road that connects two intersections. 
Various arrays are defined in the navigate method that are used to store 
information related to visited intersections and traversed path. 
It then uses Dijkstra's algorithm to determine the shortest path between 
two intersections, if it exists.

insList - This data object comprises all the intersections, which can be used 
	when we need to define a new intersection.
roads - This is the adjacency matrix that stores the graph vertex and edges.
	Only the intersections that are defined in insList will be allowed in
	this two-dimensional array. 

Assumptions
-----------

  - The roads are straight lines between the intersections. A road from (x1, y1) to (x2, y2)
    has length square_root( (x2 – x1)2 + (y2 – y1)2 )
  - The length of road are rounded off to nearest integer value.

Choices
----------------

- The total number of intersections to be recorded are to be mentioned in main method during object creation.
- Only positive values of intersections will be accepted.
- The user mentions the total number of intersections to be used at time of object creation.

Key algorithms and design elements
----------------------------------

The program inputs the intersection coordinates and stores them in
an array of arraylists one at a time.  In the output, a list of coordinates 
will be produced one per line. The x and y coordinates will be separated 
by a tab character. Only unique intersections are stored in the data structure insList.

After storing the intersections, the program allows the user to define
a road that is connecting two intersections, the program allows a road to be 
defined only if the specified intersections are already defined beforehand. 
Also duplicate defining of roads is not allowed.

The user can then use the "navigate" method to determine the sequence of
intersection coordinates to traverse from source to destination with the 
shortest distance while staying on roads. Again, the path will be printed only if the
mentioned intersections and the roads connecting them are already defined.

Firstly, the program sets the distance from source intersection to itself as zero,
and distance to every other intersection to be infinity. The navigate method then 
determines the shortest distance from the source intersection to every adjacent 
intersection in the graph and stores its weight in a seperate data structure. 
The program also keeps the track of all the intersections that are already visited 
in order to avoid re-visiting them.

Secondly, as that the source intersection has been visited already, the program then 
visits the next unvisited intersection with the smallest known distance from the source intersection.
As Dijkstra's shortest path algorithm is a greedy algorithm, the program again calculates the distance 
of each neighboring intersection from the new start intersection. If the calculated distance of an
intersection is smaller than the known distance, the shortest distance value is updated/replaced. 
The program repeats this process unless there are no more unvisited intersections.

Finally, the program prints the sequence of intersection coordinates to follow in order to 
reach from the source to the destination.

References 
--------------

https://www.youtube.com/watch?v=pVfj6mxhdMw
https://www.geeksforgeeks.org/printing-paths-dijkstras-shortest-path-algorithm/

Limitations
-----------

This program can accept a maximum of 500 intersections.
The current design is limited to alloting the total number of required intersections during object creation.