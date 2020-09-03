import java.util.ArrayList;

public class HalifaxMap {
	// Define symbols to use within the program as a way of knowing what we're seeing in the text.
	static int insCount=0;													// count of intersections recoreded
	static int rdCount = 0;													// count of roads recorded	
	static boolean insFlag = false;
	static boolean addroadFlag = false;
	@SuppressWarnings("unchecked")
	public static ArrayList<Integer>[] insList = new ArrayList[500];
	public static ArrayList<ArrayList<Integer>> roads1; 
	public static int [][] roads;													// adjecency matrix	
	int rd_length = 100;
	
	HalifaxMap(int rd_length) {															// constructor
		this.rd_length=rd_length;
		roads = new int [rd_length][rd_length];
		for (int i =0;i<rd_length;i++) {	
			for (int j =0; j< rd_length; j++) {
				roads[i][j]=0; 															// intitializing the adjecency list
			}
		}
	}
	
	public boolean newIntersection(int x,int y){
		int duplicate_count = 0;
		if(x < 0 || y < 0) {																// check for negative input
			insFlag = false;
			return insFlag;
		}
		else {
			if (insCount == 0) {															// adding first intersection
				insList[insCount] = new ArrayList<Integer>();
				insList[insCount].add(x);
				insList[insCount].add(y);
				insCount++;
				insFlag = true;	
			} else {
				for (int i = 0; i < insCount ; i++) {										// validatng that input instersection is unique
					if( insList[i].get(0) != null && insList[i].get(1) != null ) {
						if( insList[i].get(0).equals(x) && insList[i].get(1).equals(y)) {
							duplicate_count++;
							insFlag = false;
						} 
					}
				}
				if(duplicate_count == 0) {													// recording a new intersection
						insList[insCount] = new ArrayList<Integer>();
						insList[insCount].add(x);
						insList[insCount].add(y);
						insCount++;
						insFlag = true;
						return insFlag;
				}
		} 
		}
		return insFlag;
	}//end of newIntersection method
	
	boolean defineRoad (int x1, int y1, int x2, int y2) {
		
		if(x1 < 0 || y1 < 0 || x2 < 0 || y2< 0) {											// check for negative input values
			addroadFlag = false;
			return addroadFlag;
		}
		else {
			int rd_start = 0;
			int rd_end = 0;
			int rd_weight = 0;
			boolean roadFlag = false;
			boolean roadFlag1 = false;
			rd_weight = (( (x2 - x1)*(x2 - x1)) + ((y2 - y1)*(y2 - y1)) );					// length of road
			
			for(int i = 0;i<insCount; i++) {												// validating input intersections
					if(x1 == insList[i].get(0)) {
						if(y1 == insList[i].get(1)) {
							rd_start = i;												
							roadFlag = true;	
						}
					}
					if(x2 == insList[i].get(0)) {
						if(y2 == insList[i].get(1)) {
							rd_end = i;
							roadFlag1 = true;
					}
					}
					if(roadFlag == true && roadFlag1 == true) {
						break;
					}
				
			}
			
			if(roadFlag == true && roadFlag1 == true) {										// check for identical source and destination
				if (rd_start == rd_end) {
					return false;
				}
			}
			
			if(roadFlag == true && roadFlag1 == true) {										// check for duplicate road entry
				for(int i=0;i<rd_length ;i++) {
					for(int j=0;j<rd_length ;j++) {
						if(i==rd_start && j == rd_end && roads[i][j] > 0) {
							return false;
						}
					}
				}
				for(int i=0;i<rd_length ;i++) {												// record new road	
					for(int j=0;j<rd_length ;j++) {
						if(i==rd_start && j == rd_end ) {
							roads[i][j]=rd_weight;
							roads[j][i]=rd_weight;
							addroadFlag = true;
						}	
					}
				}
			} 
			else {
				return false;
				}
		}
			return addroadFlag;
	}// end of defineRoad
			
	void navigate( int x1, int y1, int x2, int y2 ) {
		
		boolean navigate = true;
		int rd_start = 0;
		int rd_end = 0;
		boolean roadFlag = false;
		boolean roadFlag1 = false;
		
		if(x1 < 0 || y1 < 0 || x2 < 0 || y2< 0) {										// check for negative input
			navigate = false;
		}
		else {
			for(int i = 0;i<insCount; i++) {											//validating input intersections
				if(x1 == insList[i].get(0)) {
					if(y1 == insList[i].get(1)) {
						rd_start = i;
						roadFlag = true;
					}
				}
				if(x2 == insList[i].get(0)) {
					if(y2 == insList[i].get(1)) {
						rd_end = i;
						roadFlag1 = true;
					}
				}
				if(roadFlag == true && roadFlag1 == true) {
					break;
				}
			}
			
			if(roadFlag == false || roadFlag1 == false) {
				navigate = false;
			}
			
			if(roadFlag == true && roadFlag1 == true) {									//check for identical source and destination
				if (rd_start == rd_end) {
					navigate = false;
				}
			}

			int count=0;
			int count1 =0;
			for (int i =0;i<rd_length;i++) {											//check if a path exists between specified nodes
				if(i == rd_start){
					for (int j =0; j< rd_length; j++) {
						if(roads[i][j]==0) {
							count++;
							if(count==rd_length) {
							navigate = false;
							break;
							}
							}
					}
				}
				if (i == rd_end) {
					for (int j =0; j< rd_length; j++) {
						if(roads[i][j]==0) {
							count1++;
							if(count1==rd_length) {
								navigate = false;
								break;
							}
						}
					}
				}
			}
		}
		
		// Implementing Dijkstra's algorithm
		/* 	Reference : https://www.youtube.com/watch?v=pVfj6mxhdMw
			Reference : https://www.geeksforgeeks.org/printing-paths-dijkstras-shortest-path-algorithm/*/
		if (navigate == true){ 
			int v_number = roads[0].length;											// total number of recoreded intersections.
			int[] min_dis = new int[v_number]; 										// track of shortest distance.
			boolean[] isAdded = new boolean[v_number];								// true if shortest distance is finalized.
			for (int i = 0; i < v_number; i++) {										
				min_dis[i] = Integer.MAX_VALUE; 									// initializing infinity as distance.
				isAdded[i] = false; 												// initialize all intersections as unvisited.
		 	}
			min_dis[rd_start]=0;													// distance from source to itself is zero.
			int[] parentNode = new int[v_number]; 									// array to store shortest path.
			parentNode[rd_start] = -1;												// array to store preceeding intersection. 
		 
			for (int i = 1; i < v_number; i++)										// determining shortest path for all intersections.
			{
				int next_Ins = -1;
				int directPath = Integer.MAX_VALUE; 
				for (int j = 0; j < v_number; j++) { 
					if (!isAdded[j] && min_dis[j] <  directPath) {
						next_Ins = j; 
						directPath = min_dis[j]; 
					} 
				}
				
				if(next_Ins == -1) {
					next_Ins = rd_start;
				}
	        
				isAdded[next_Ins] = true; 											// flag current vertex as visited.

				// update distance of neighbouring intersection.
				for (int j = 0; j < v_number; j++) { 	
					int edgeDistance = roads[next_Ins][j]; 
						if (edgeDistance > 0 && ((directPath + edgeDistance) < min_dis[j])) { 
							parentNode[j] = next_Ins; 
							min_dis[j] = directPath + edgeDistance; 
						} 
				}
			}
			printSolution(rd_start, min_dis, parentNode, rd_start, rd_end);
		}
		else {
			System.out.println("no path");
		}
	}// end of navigate method
		
	// printing the constructed length array and shortest paths
	private static void printSolution(int startVertex, int[] width, int[] parentNode, int rd_start, int rd_end) { 
		int v_number = width.length; 
		
		for (int i = 0; i < v_number; i++)  
			{ 
				if (i != startVertex) {
					if(startVertex==rd_start && i == rd_end) {
						printPath(i, parentNode); 
					}
				} 
			} 
	} // end of printSolution 
	
	// printing shortest path from source to currentVertex using the parentNode array 
	private static void printPath(int currentVertex, int[] parentNode) {

		if (currentVertex == -1) 
			{ 
				return;
			}
		printPath(parentNode[currentVertex], parentNode);
		
		for(int i=0;i<2;i++) {
			System.out.print(insList[currentVertex].get(i) + "\t");
		}
			System.out.println();	
	} // end of printPath method
} //end of class HalifaxMap