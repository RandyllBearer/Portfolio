import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

/**
* Purpose of this program is to implement algorithms concerning graph representations
* Specifically, this program emulates an AirLine Company's information system.
* Algorithms used inclue Djikstra's (finding shortes path), Lazy Prim's (Finding minimum spanning tree),
* a recursive depth-first-search (find all trips < $xxx).  Structures used include priority queues, adjacency
* list, weighted-edges, graph representation.
*
* Author: Randyll Bearer	rlb97@pitt.edu
**/
public class Airline{
	static Airline airline = new Airline();
	int numberOfVertices = 0;
	
	/**
	* Simulates a weighted edge inside a graph.
	* Holds vertices, weights, and next (for adjacencyList implementation)
	**/
	public class Edge{
		int vertexA = 0;	//First city		IMPORTANT: In the graph representation, vertexA of an edge will always be the index of its corresponding EdgeList().  VertexB will always be the index of its destination edgeList().
		int vertexB = 0;	//Second city
		double price = 0;	//Weight based on price
		int distance = 0;	//Weight based on distance
		Edge next = null;	//What is the next object in the adjacency list
		
		/**
		*Default Constructor
		**/
		public Edge(){
			
		}
		
		/**
		*Detailed Constructor
		**/
		public Edge(int source, int target, double cost, int miles, Edge toNext){
			setVertexA(source);
			setVertexB(target);
			setPrice(cost);
			setDistance(miles);
			setNext(toNext);
		}
		
		/**
		*Checks to see if a source would make a valid vertex.
		* If YES, returns TRUE			if NO, returns an error message + FALSE
		**/
		public boolean setVertexA(int source){
			if(source > 0 && source <= numberOfVertices){	//The desired vertex needs to exist
				vertexA = source;
				return true;
			}else{
				System.out.println("ERROR: Vertex A is not within range / non-existant...\n");
				return false;
			}
		}
		
		public int getVertexA(){
			return vertexA;
		}
		
		/**
		*Checks to see if a source would make a valid vertex.
		* If YES, returns TRUE			if NO, returns an error message + FALSE
		**/
		public boolean setVertexB(int target){
			if(target > 0 && target <= numberOfVertices){
				vertexB = target;
				return true;
			}else{
				System.out.println("ERROR: Vertext B is not within range / non-existant...\n");
				return false;
			}
		}
		
		public int getVertexB(){
			return vertexB;
		}
		
		/**
		*Checks to see if a cost would make a valid price
		* If YES, returns TRUE			if NO, returns an error message + FALSE
		**/
		public boolean setPrice(double cost){
			if(cost > 0){
				price = cost;
				return true;
			}else{
				System.out.println("ERROR: Price for a flight cannot be negative...\n");
				return false;
			}
		}
		
		public double getPrice(){
			return price;
		}
		
		/**
		*Checks to see if a distance would make a valid distance field.
		* If YES, returns TRUE			if NO, returns an error message + FALSE
		**/
		public boolean setDistance(int miles){
			if(miles > 0){
				distance = miles;
				return true;
			}else{
				System.out.println("ERROR: Distance from A to B cannot be less than 0...\n");
				return false;
			}
		}
		
		public int getDistance(){
			return distance;
		}
		
		/**
		*Links an edge to this one in an adjacencylist implementation.
		**/
		public void setNext(Edge toNext){
			next = toNext;
		}
		
		public Edge getNext(){
			return next;
		}
		
		//Is there more to the linked list? return TRUE if yes, FALSE if no
		public boolean hasNext(){
			if(next != null){
				return true;
			}else{
				return false;
			}
		}
	}
	//END OF EDGE
	
	/**
	*Acts as a linked list in an adjacencyList implementation.  Holds a linked list of weightedEdge objects.
	**/
	public class EdgeList{
		Edge head = null;   //Start of the list
		int listSize = 0;	//How many edges are in this list
		
		/**
		*Default Constructor
		**/ 
		public EdgeList(){
			head = null;
			listSize = 0;
		}
		
		/**
		*Getters/Setters
		**/
		public int getListSize(){
			return listSize;
		}
		
		public Edge getHead(){
			if(head == null){
				return null;
			}
			Edge temp = new Edge(head.getVertexA(), head.getVertexB(), head.getPrice(), head.getDistance(), head.getNext() );	//Ensure a deep copy.
			return temp;
		}
		
		/**
		*Add an edge to the list (DEEP COPY)
		**/
		public boolean add(Edge toAdd){
			toAdd = new Edge(toAdd.getVertexA(), toAdd.getVertexB(), toAdd.getPrice(), toAdd.getDistance(), toAdd.getNext());
			if(listSize == 0){	//If this list is empty, just make toAdd the head
				head = toAdd;
			}else{
				Edge temp = new Edge(head.getVertexA(), head.getVertexB(), head.getPrice(), head.getDistance(), head.getNext() );
				head = toAdd;
				head.setNext(temp);
			}
			listSize += 1;
			return true;
		}
		
		/**
		*Iterates through EdgeList until it finds a match and then removes it
		**/
		public boolean remove(int source, int target){
			if(listSize <= 0){
				System.out.println("ERROR: Cannot remove from an empty adjacency list...\n");
				return false;
			}else{	//Attempt to find the edge
				Edge current = head;
				for(int i =1; i<= listSize; i++){	//iterate through everything in this list
					if(current.getVertexA() == source && current.getVertexB() == target){	//we found a match
						if(listSize == 1){	//If we are removing the only route
							head = null;
							listSize -= 1;
						}else if(current.getNext() != null){	//int the middle, get ready to swap
							current.setVertexA(current.getNext().getVertexA());
							current.setVertexB(current.getNext().getVertexB());
							current.setPrice(current.getNext().getPrice());
							current.setDistance(current.getNext().getDistance());
							current.setNext(current.getNext().getNext());
							listSize -=1;
						}else{	//if this is the last item in the list
							current = head;
							while(true){
								if(current.getNext().getNext() == null){	//second to last node
									current.setNext(null);
									listSize -= 1;
									break;
								}
								current = current.getNext();
							}
						}
						
						return true;
					}else{
						current = current.getNext();
					}
				}
				System.out.println("\nERROR: An edge from " + source + " to " + target + " does not exist...");
				return false;
			}
		}
		
		/**
		*Remove Head [DOES NOT RETURN]
		**/
		public void removeHead(){
			if(head == null){
				System.out.println("ERROR: Cannot remove the head from an empty list");
			}else{
				head = head.getNext();
			}
		}
		
	}
	//END OF EDGE LIST
	
	/**
	*Minimum-priority queue based off distance weight
	**/
	public class PQD{
		Edge[] pq = new Edge[numberOfVertices * 2];	//This should be safe enough
		int size = 0;
		
		/**
		*Default constructor
		**/
		public PQD(){
			
		}
		
		/**
		*Inserts edge (DEEP COPY) into the queue [UNSORTED]
		**/
		public void insert(Edge edgeToAdd){
			pq[size] = new Edge(edgeToAdd.getVertexA(), edgeToAdd.getVertexB(), edgeToAdd.getPrice(), edgeToAdd.getDistance(), edgeToAdd.getNext() );
			size += 1;
		}
		
		/**
		*Removes and Returns the item in the pq array with the lowest distance
		**/
		public Edge remove(){	//Return lowest edge based on distance, then fill in the gap
			if(size == 0){	//Cannot remove from an empty queue
				return null;
			}
			int currentLowest = 0;	//index with the lowest so far
			for(int i = 1; i < size; i++){	//Iterate through the priority queueue
				if(pq[i].getDistance() < pq[currentLowest].getDistance()){	//Find the lowest
					currentLowest = i;
				}
			}
			Edge result = new Edge(pq[currentLowest].getVertexA(), pq[currentLowest].getVertexB(), pq[currentLowest].getPrice(), pq[currentLowest].getDistance(), pq[currentLowest].getNext() );
			pq[currentLowest] = new Edge(pq[size-1].getVertexA(), pq[size-1].getVertexB(), pq[size-1].getPrice(), pq[size-1].getDistance(), pq[size-1].getNext() );	//SHOULD BE LAST ONE IN PQ
			pq[size-1] = null;					//Just to be safe
			size -= 1;
			
			return result;
		}
		
		/**
		*Is the priority Queue empty?
		*if YES, return TRUE		if NO, return FALSE
		**/
		public boolean isEmpty(){
			if(size == 0){
				return true;
			}
			return false;
		}
		
	}
	//END OF PQD
	
	/**
	*Representation of a Graph, contains the more complex algorithms.
	*Basically the workhorse of the program.
	* Please read IMPORTANT note next to adjacencyList
	**/
	public class Graph{
		int numberOfEdges = 0;	//number of UNIQUE edges (For example, an edge between a and b will go both from a-->b and b--->a, this only counts as 1)
		EdgeList[] adjacencyList;	//Will act as our main holder of graph information	IMPORTANT: an edge with vertexA[i] is guaranteed to be in adjacencyList[i], its formatted to guarantee this.
		String[] vertexNames; 		//Will hold the names of vertexes/cities		
		
		/**
		*Specific Constructor
		**/
		public Graph(int size){
			numberOfVertices = size;
			adjacencyList = new EdgeList[size + 1];	//We will ignore index 0 since our save/load files start from 1
			for(int i = 0; i < size+1; i++){
				adjacencyList[i] = new EdgeList();
			}
			vertexNames = new String[size + 1];
		}
		
		/**
		*Fills out a graph from a user file Scanner
		* Returns null if an error ocurred
		**/
		public Graph createGraph(Scanner loadIn){
			try{
				numberOfVertices = Integer.parseInt(loadIn.nextLine());	//HOW MANY VERTICES WE ARE DEALING WITH
				if(numberOfVertices > 0){	//Good
					adjacencyList = new EdgeList[numberOfVertices + 1];
					for(int i = 1; i <= numberOfVertices; i++){	//Initiate the edgelists
						adjacencyList[i] = new EdgeList();
					}
					vertexNames = new String[numberOfVertices + 1];
					for(int i=1; i <= numberOfVertices; i++){	//Read in destination/vertex names
						vertexNames[i] = loadIn.nextLine();
					}
					while(loadIn.hasNext()){				//Read in and create edges
						int vertex1 = loadIn.nextInt();
						if(vertex1 < 1 || vertex1 > numberOfVertices){	//NO GOOD
							System.out.println("ERROR: File (VertexA) not correctly formatted...\n");
							return null;
						}
						int vertex2 = loadIn.nextInt();
						if(vertex2 < 1 || vertex2 > numberOfVertices){	//Check to make sure if valid vertex
							System.out.println("ERROR: File (VertexB) not correctly formatted...\n");
							return null;
						}
						int miles = Integer.parseInt(loadIn.next());
						if(miles < 0){
							System.out.println("ERROR: Miles cannot be < 0...\n");
							return null;
						}
						double price = Double.parseDouble(loadIn.nextLine() );	//should consume line and move to next
						if(price < 0){
							System.out.println("ERROR: Price cannot be < $0.00...\n");
							return null;
						}
						Edge edgeToAdd = new Edge(vertex1, vertex2, price, miles, null );
						if(addEdge(edgeToAdd) == false ){
							System.out.println("ERROR: Cannot have repeat edges/routes... /n");
							return null;
						}
					}
				}else{
					System.out.println("ERROR: Cannot load in a graph with negative destinations/vertices...");
					return null;
				}
			}catch(NumberFormatException nfe){
				System.out.println("ERROR: Sorry, but the designated file is not properly formatted...\n");
				return null;
			}catch(NoSuchElementException nsee){
				System.out.println("ERROR: Cannot load from an empty file...\n");
				return null;
			}
			return this;
		}
		
		/**
		*Getters + Setters
		**/
		public int getNumberOfEdges(){
			return numberOfEdges;
		}
		
		/**
		*Checks to see if newEdge is not a repeat
		* If it IS, return FALSE + error message		If it ISN'T, return TRUE + adds edge into adjacencyList[]
		**/
		public boolean addEdge(Edge newEdge){
			final int vertex1 = newEdge.getVertexA();
			final int vertex2 = newEdge.getVertexB();
			
			//CHECK TO MAKE SURE NO REPEAT EDGES
			if(adjacencyList[vertex1].getListSize() != 0){	//if the adjacency list is not empty
				Edge current = adjacencyList[vertex1].getHead();
				while(true){	
					if(current.getVertexA() == vertex1 && current.getVertexB() == vertex2){	//if we have an equivalent edge already existing
						System.out.println("\nERROR: Cannot add repeat routes...\n");
						return false;
					}else{
						if(current.getNext() == null){
							break;
						}
						current = current.getNext();
					}
				}
			}
			//Add both directions of the edge
			adjacencyList[vertex1].add(newEdge);	//So if the edge is from 3, go to 3's adjacency list
			Edge newEdge2 = new Edge(vertex2, vertex1, newEdge.getPrice(), newEdge.getDistance(), newEdge.getNext());
			adjacencyList[vertex2].add(newEdge2);
			numberOfEdges += 1;
			return true;
		}
		
		/**
		*Checks to see if the given vertices actually make up an existing edge
		* if YES, removes them and returns true			if NO, returns false
		**/
		public boolean removeEdge(int vertexA, int vertexB){
			int vertex1 = vertexA;
			int vertex2 = vertexB;
			boolean result = false;
			//Check to make sure the edge even exists
			if(adjacencyList[vertex1].getListSize() <= 0){	//if the supposed city doesnt have any edges...
				System.out.println("\nERROR: No route between those two cities currently exists...\n");
				return false;
			}else{	//So the edgelist isnt empty, but that doesnt mean it includes our edge
				result = adjacencyList[vertex1].remove(vertex1, vertex2);
				if(result == true){	//successful removal
					//do nothing
				}else{
					return false;
				}
				result = adjacencyList[vertex2].remove(vertex2, vertex1);	//Do it in reverse now
				if(result == true){	//successful removal
					numberOfEdges -= 1;
					return true;
				}else{
					return false;
				}
				
			}
			
		}
		
		/**
		*Returns a deep copy of AdjacencyList
		**/
		public EdgeList[] copyEdgeList(EdgeList[] toCopy){
			EdgeList[] result = new EdgeList[numberOfVertices + 1];
			
			Edge current;
			for(int i = 1; i <= numberOfVertices; i++){	//iterate through edgeList[]
				if(toCopy[i].getListSize() != 0){	//We are not operating on an empty edgelist
					current = toCopy[i].getHead();
					result[i].add(new Edge(current.getVertexA(), current.getVertexB(), current.getPrice(), current.getDistance(), current.getNext() ) );
					for(int j = 2; j <= toCopy[i].getListSize(); j++){
						current = current.getNext();
						result[i].add(new Edge(current.getVertexA(), current.getVertexB(), current.getPrice(), current.getDistance(), current.getNext() ) );
					}
				
				}
			}
			
			return result;
		}
		
		/**
		*Displays the graph to console.
		**/
		public void displayGraph(){
			System.out.println("\n\n\nRoute Information");
			System.out.println("---------------------");
			System.out.println("Total Number of Cities: " + numberOfVertices + "        Total Number of UNIQUE Routes: " + this.getNumberOfEdges());
			//Start Displaying Cities + Trips
			Edge current;
			for(int i=1; i <= numberOfVertices; i++){	//iterate through edgelist[]
				System.out.println("\nFlights from " + vertexNames[i] + ":  Total [" + adjacencyList[i].getListSize() + "]");
				System.out.println("----------------------------------");
				if(adjacencyList[i].getHead() == null){
					//Do nothing
				}else{
					current = adjacencyList[i].getHead();
					System.out.println(1 + ". Flight from " + vertexNames[adjacencyList[i].getHead().getVertexA()] + " to " + vertexNames[adjacencyList[i].getHead().getVertexB()] + "- Distance: " + adjacencyList[i].getHead().getDistance() + " - Price: " + adjacencyList[i].getHead().getPrice());
					for(int j = 2; j <= adjacencyList[i].getListSize(); j++){	//iterate through specific edgelist
						current = current.getNext();
						System.out.println(j + ". Flight from " + vertexNames[current.getVertexA()] + " to " + vertexNames[current.getVertexB()] + "- Distance: " + current.getDistance() + " - Price: " + current.getPrice());
					}
				}
				
			}
		}
		
		/**
		*Djikstra shortest-path algorithm for distance
		* Start at source vertex, update distances + set parent 0, update distances, choose lowest, repeat 
		**/
		public int[] djikstraDistance(int source, int target){
			int[] tentative = new int[numberOfVertices+1];	//HOLDS OUR TENTATIVE DISTANCES
			int[] parent = new int[numberOfVertices+1];	//Where did we come from (CREATES THE FINAL PATH IN REVERSE)
			boolean marked[] = new boolean[numberOfVertices+1];	//have we visited this node before?
			Edge current;
			
			for(int i = 0; i <= numberOfVertices; i++){	//Initialize
				if(i == source){
					tentative[i] = 0;
				}else{
					tentative[i] = 999999;	//6 9's
				}
			}
			//UPDATE ALL EDGES AVAILABLE FROM SOURCE
			parent[source] = source;
			marked[source] = true;
			if(adjacencyList[source].getListSize() <= 0){	//We have a problem
				System.out.println("\nERROR: No route between the two specified cities exists...\n");
				return null;
			}else{
				current = adjacencyList[source].getHead();
				for(int i = 1; i <= adjacencyList[source].getListSize(); i++){	//update all edges
					if(tentative[current.getVertexA()] + current.getDistance() < tentative[current.getVertexB()] ){	//if we can update the edge
						tentative[current.getVertexB()] = tentative[current.getVertexA()] + current.getDistance();
						parent[current.getVertexB()] = current.getVertexA();
					}else{
						//do nothing
					}
					current = current.getNext();
				}
			}
			//Begin by choosing lowest edge, updating parent/marked + edge distances, 
			int lowestVertex = 0;
			int previousVertex = 0;
			int lowestVertexDistance = 9999999;
			for (int j = 1; j < numberOfVertices; j++){	//Should only have to do all this once per node
				lowestVertexDistance = 999999;
				//FIND SHORTEST EDGE
				for(int i = 1; i <= numberOfVertices; i++){
					if(tentative[i] == 999999 || marked[i] == true){
						//do nothing, unvisitable
					}else{
						if(tentative[i] <= lowestVertexDistance){
							lowestVertexDistance = tentative[i];
							lowestVertex = i;	//This is where we will begin 
 						}else{
							//do nothing
						}
					}
				}
				current = adjacencyList[lowestVertex].getHead();
				//UPDATE MARKED
				marked[lowestVertex] = true;
				//UPDATE DISTANCE
				for(int i = 1; i <= adjacencyList[lowestVertex].getListSize(); i++){	//update all edges
					if(marked[current.getVertexB()] == true ){	//If this edge takes us somewhere we've been before (BACKTRACKING MOST LIKE)
						//do nothing
					}else{
						if(tentative[current.getVertexA()] + current.getDistance() < tentative[current.getVertexB()] ){
							tentative[current.getVertexB()] = tentative[current.getVertexA()] + current.getDistance();
							parent[current.getVertexB()] = current.getVertexA();
						}
					}
					current = current.getNext();
				}
			}
			if (tentative[target] < 999999){	//If <, then we reached the targest successfully
				return parent;
			}else{
				System.out.println("\nERROR: No route between the two specified cities could be found...\n");
				return null;
			}
		}
		
		/**
		*Djikstra shortest path algorithm for price
		**/
		public int[] djikstraPrice(int source, int target){
			double[] tentative = new double[numberOfVertices+1];	//HOLDS OUR TENTATIVE DISTANCES
			int[] parent = new int[numberOfVertices+1];	//Where did we come from (CREATES THE FINAL PATH IN REVERSE)
			boolean marked[] = new boolean[numberOfVertices+1];	//have we visited this node before?
			Edge current;
			
			for(int i = 0; i <= numberOfVertices; i++){	//Initialize
				if(i == source){
					tentative[i] = 0;
				}else{
					tentative[i] = 999999;	//6 9's
				}
			}
			//UPDATE ALL EDGES AVAILABLE FROM SOURCE
			parent[source] = source;
			marked[source] = true;
			if(adjacencyList[source].getListSize() <= 0){	//We have a problem
				System.out.println("\nERROR: No route between the two specified cities exists...\n");
				return null;
			}else{
				current = adjacencyList[source].getHead();
				for(int i = 1; i <= adjacencyList[source].getListSize(); i++){	//update all edges
					if(tentative[current.getVertexA()] + current.getPrice() < tentative[current.getVertexB()] ){	//if we can update the edge
						tentative[current.getVertexB()] = tentative[current.getVertexA()] + current.getPrice();
						parent[current.getVertexB()] = current.getVertexA();
					}else{
						//do nothing
					}
					current = current.getNext();
				}
			}
			//Begin by choosing lowest edge, updating parent/marked + edge distances, 
			int lowestVertex = 0;
			int previousVertex = 0;
			double lowestVertexPrice = 9999999;
			for (int j = 1; j < numberOfVertices; j++){	//Should only have to do all this once per node
				lowestVertexPrice = 999999;
				//FIND SHORTEST EDGE
				for(int i = 1; i <= numberOfVertices; i++){
					if(tentative[i] == 999999 || marked[i] == true){
						//do nothing, unvisitable
					}else{
						if(tentative[i] <= lowestVertexPrice){
							lowestVertexPrice = tentative[i];
							lowestVertex = i;	//This is where we will begin 
 						}else{
							//do nothing
						}
					}
				}
				current = adjacencyList[lowestVertex].getHead();
				//UPDATE MARKED
				marked[lowestVertex] = true;
				//UPDATE DISTANCE
				for(int i = 1; i <= adjacencyList[lowestVertex].getListSize(); i++){	//update all edges
					if(marked[current.getVertexB()] == true ){	//If this edge takes us somewhere we've been before (BACKTRACKING MOST LIKE)
						//do nothing
					}else{
						if(tentative[current.getVertexA()] + current.getPrice() < tentative[current.getVertexB()] ){
							tentative[current.getVertexB()] = tentative[current.getVertexA()] + current.getPrice();
							parent[current.getVertexB()] = current.getVertexA();
						}
					}
					current = current.getNext();
				}
			}
			if (tentative[target] < 999999){	//If <, then we reached the targest successfully
				return parent;

			}else{
				System.out.println("\nALERT: No route between the two specified cities could be found...\n");
				return null;
			}
		}
		
		/**
		*Djikstra shortest path algorithm for #ofHops [A.K.A. edgeweights = 1]
		**/
		public int[] djikstraHops(int source, int target){
			double[] tentative = new double[numberOfVertices+1];	//HOLDS OUR TENTATIVE DISTANCES
			int[] parent = new int[numberOfVertices+1];	//Where did we come from (CREATES THE FINAL PATH IN REVERSE)
			boolean marked[] = new boolean[numberOfVertices+1];	//have we visited this node before?
			Edge current;
			
			for(int i = 0; i <= numberOfVertices; i++){	//Initialize
				if(i == source){
					tentative[i] = 0;
				}else{
					tentative[i] = 999999;	//6 9's
				}
			}
			//UPDATE ALL EDGES AVAILABLE FROM SOURCE
			parent[source] = source;
			marked[source] = true;
			if(adjacencyList[source].getListSize() <= 0){	//We have a problem
				System.out.println("\nERROR: No route between the two specified cities exists...\n");
				return null;
			}else{
				current = adjacencyList[source].getHead();
				for(int i = 1; i <= adjacencyList[source].getListSize(); i++){	//update all edges
					if(tentative[current.getVertexA()] + 1 < tentative[current.getVertexB()] ){	//if we can update the edge
						tentative[current.getVertexB()] = tentative[current.getVertexA()] + 1;
						parent[current.getVertexB()] = current.getVertexA();
					}else{
						//do nothing
					}
					current = current.getNext();
				}
			}
			//Begin by choosing lowest edge, updating parent/marked + edge distances, 
			int lowestVertex = 0;
			int previousVertex = 0;
			double lowestVertexHops = 9999999;
			for (int j = 1; j < numberOfVertices; j++){	//Should only have to do all this once per node
				lowestVertexHops = 999999;
				//FIND SHORTEST EDGE
				for(int i = 1; i <= numberOfVertices; i++){
					if(tentative[i] == 999999 || marked[i] == true){
						//do nothing, unvisitable
					}else{
						if(tentative[i] <= lowestVertexHops){
							lowestVertexHops = tentative[i];
							lowestVertex = i;	//This is where we will begin
 						}else{
							//do nothing
						}
					}
				}
				current = adjacencyList[lowestVertex].getHead();
				//UPDATE MARKED
				marked[lowestVertex] = true;
				//UPDATE DISTANCE
				for(int i = 1; i <= adjacencyList[lowestVertex].getListSize(); i++){	//update all edges
					if(marked[current.getVertexB()] == true ){	//If this edge takes us somewhere we've been before (BACKTRACKING MOST LIKE)
						//do nothing
					}else{
						if(tentative[current.getVertexA()] + 1 < tentative[current.getVertexB()] ){
							tentative[current.getVertexB()] = tentative[current.getVertexA()] + 1;
							parent[current.getVertexB()] = current.getVertexA();
						}
					}
					current = current.getNext();
				}
			}
			if (tentative[target] < 999999){	//If <, then we reached the targest successfully
				return parent;

			}else{
				System.out.println("\nERROR: No route between the two specified cities could be found...\n");
				return null;
			}
		}
		
		/**
		*With the aid of lessThan(); will help find all trips < a certain dollar amount
		**/
		public void findLessThan(double maximumCost){
			double amount = maximumCost;
			int count = 0;
			System.out.println("\n\n\nDisplaying All Trips <= $" + maximumCost);
			System.out.println("------------------------");
			
			for(int i = 1; i <= numberOfVertices; i++){ 	//go through edgelist[]
				if(adjacencyList[i].getListSize() > 0){
					Edge current = adjacencyList[i].getHead();
					for(int j = 1; j <= adjacencyList[i].getListSize(); j++){	//go through the specific edge list
						if(current.getPrice() <= maximumCost ){
							boolean[] marked = new boolean[numberOfVertices+1];	//have we visited it yet?
							count = lessThan(amount, marked, current, 0, count, null, 1);	//start the massive amount of recursive calls
						}
						current = current.getNext();
					}
					
				}else{
					//do nothing
				}
				
			}
			
		}
		
		/**
		*Utilized by findLessThat(), recursively prints out a path <= maximumCost
		* Pruning measures include (If current price is too high, if immediate future price will be too high, no more unvisited vertices within reach)
		**/
		public int lessThan(double maximumCost, boolean[] visited, Edge startingEdge, double runningTotal, int totalCount, int[] history, int historySize){
			runningTotal += startingEdge.getPrice();
			visited[startingEdge.getVertexA()] = true;
			visited[startingEdge.getVertexB()] = true;
			
			//ADD VISITED TO HISTORY
			if(historySize == 1){	//the trip is about to begin
				history = new int[numberOfVertices+1];
				history[historySize] = startingEdge.getVertexA();
				historySize += 1;
				history[historySize] = startingEdge.getVertexB();
				historySize += 1;
			}else{
				history[historySize] = startingEdge.getVertexB();
				historySize += 1;
			}
			
			//DISPLAY ALL VISITED IN HISTORY
			totalCount += 1;
			System.out.println("\nDisplaying Trip " + totalCount);
			System.out.println("--------------");
			for(int i = 1; i <= historySize; i++){
				if(adjacencyList[history[i]] != null){
					Edge current = adjacencyList[history[i]].getHead();
					for(int j=1; j <= adjacencyList[history[i]].getListSize(); j++){
						if(current.getVertexA() == history[i] && current.getVertexB() == history[i+1]   ){
							System.out.println("Flight from " + vertexNames[current.getVertexA()] + " to " +  vertexNames[current.getVertexB()] +  "			- Price: " + current.getPrice()  );
						}
						current = current.getNext();
					}
				}
			}
			System.out.println("Total Price: $" + runningTotal);
			
			//FIND NEW VERTICES TO VISIT
			if(adjacencyList[startingEdge.getVertexB()].getListSize() > 0 ){
				Edge current = adjacencyList[startingEdge.getVertexB()].getHead();
				for(int i = 1; i<=adjacencyList[startingEdge.getVertexB()].getListSize(); i++ ){ 	//iterate through startingedge's 
					if(runningTotal + current.getPrice() <= maximumCost && visited[current.getVertexB()] == false){
						totalCount = lessThan(maximumCost, visited, current, runningTotal, totalCount, history, historySize);
					}
					current = current.getNext();
				}
				return totalCount;
			}else{
				return totalCount;
			}
		}
		
		/**
		*Creates an MST (If whole graph is unconnected, will say so and return all component forests)
		* lazy prims.  Visit source, enqueue all, go to lowest weight, enqueue all, check if valid, visit lowest, go to all.
		* will continue until all nodes have been visited (Allows us to see isolated components)
		*
		*If a vertex has no edges, its edgelist() will be NULL [NOT just empty]
		**/
		public EdgeList[][] createMST(){
			EdgeList[][] mst = new EdgeList[numberOfVertices+1][numberOfVertices + 1];	//This will store our mst
			PQD pq = new PQD();
			boolean[] marked = new boolean[numberOfVertices+1];	//have we visited this node yet?
			int numberVisited = 0;	//How many unique nodes have we visited
			int numberOfNodes = 0;
			int numberOfEdges = 0;
			int startIndex = 1;	//The first node with an edge
			Edge current = null;
			
			if(numberOfVertices <= 0){
				System.out.println("ERROR: Cannot create an MST of an empty graph...\n");
				return null;
			}
			
			for(int i =1; i <= numberOfVertices; i++){	//Initialize our mst
				for(int j = 1; j <= numberOfVertices; j++){
					mst[i][j] = new EdgeList();
				}
			}
			
			int count = 0;			//How many components to the graph are there?
			while(numberVisited != numberOfVertices){	//While he havent checked every vertex
				count += 1;
			
				
				for(int i =1; i <= numberOfVertices; i++){	//Find out where we should start
					if(marked[i] == false){	//Go until we find the first one we have yet to visit
						startIndex = i;
						break;
					}
				}
				
				//Start at our source (so index 1); and enqueue all edges
				if(adjacencyList[startIndex].getListSize() <= 0){	//If the first unmarked node has no edges, just make the list = null
					numberOfNodes += 1;
					marked[startIndex] = true;
					numberVisited += 1;
					mst[count][startIndex] = null;	
				}else{
					numberOfNodes += 1;
					marked[startIndex] = true;	//yes we have visited node 1.
					numberVisited += 1;
					current = adjacencyList[startIndex].getHead();
					pq.insert(new Edge(current.getVertexA(), current.getVertexB(), current.getPrice(), current.getDistance(), null ));	//Add our first edge	//USED TO HAVE current.getNext()
					while(current.hasNext()){
						current = current.getNext();
						pq.insert(new Edge(current.getVertexA(), current.getVertexB(), current.getPrice(), current.getDistance(), null  ));	//Deep copy for days
					}

					//Now pick the lowest weight edge, check if valid, then visit and enqueue
					while(pq.isEmpty() != true){
						current = pq.remove();	//Take our lowest weight edge
						
						if(marked[current.getVertexA()] == true && marked[current.getVertexB()] == true    ){	//If we have visited both vertices, then skip it
							//DO NOTHING
						}else if(marked[current.getVertexA()] == true ){	//Then visit vertex b {A HAS BEEN VISITED}
							mst[count][current.getVertexA()].add(current); //Only worry about adding one for now, MST formatting and all	//POSSIBLE SHALLOW COPY BUT DOUBT IT
							marked[current.getVertexB()] = true;	//mark new node as visited
							numberVisited += 1;
							numberOfNodes += 1;
							numberOfEdges += 1;
							if(adjacencyList[current.getVertexB()].getListSize() > 0 ){
								current = adjacencyList[current.getVertexB()].getHead();
								pq.insert(new Edge(current.getVertexA(), current.getVertexB(), current.getPrice(), current.getDistance(), null ));	//add head to pq
								while(current.hasNext() ){	//iterate through the adjacency list
									current = current.getNext();
									if(marked[current.getVertexB()] != true ){	//have not visited
										pq.insert(new Edge(current.getVertexA(), current.getVertexB(), current.getPrice(), current.getDistance(), null) );	//add the edge into the queue
									}
								}
							}
						}else{	//then visit vertex a
							mst[count][current.getVertexB()].add(current);	//Only worry about adding one for now, MST formatting and all	//POSSIBLE SHALLOW COPY BUT DOUBT IT
							marked[current.getVertexA()] = true;
							numberVisited += 1;
							numberOfNodes += 1;
							numberOfEdges += 1;
							current = adjacencyList[current.getVertexA()].getHead();
							pq.insert(new Edge(current.getVertexA(), current.getVertexB(), current.getPrice(), current.getDistance(), null ));	//add head to pq
							while(current.hasNext() ){	//iterate through the adjacency list
								current = current.getNext();
								if(marked[current.getVertexB()] != true ){	//have not visited
									pq.insert(new Edge(current.getVertexA(), current.getVertexB(), current.getPrice(), current.getDistance(), null) );	//add the edge into the queue
								}
							}	
						}
					}
					
				}
			}					
			if(count > 1){
				System.out.println("\nALERT: Graph NOT FULLY connected, displaying UNIQUE forest components...");
			}else{
				System.out.println("\nALERT: Graph is FULLY connected, displaying COMPLETE MST...");
			}
			return mst;
		}
		
		/**
		*Saves the graph to the file we loaded from
		**/
		public boolean saveGraph(String filename2){
			System.out.println("\nSaving...");
			PrintWriter saveOut = null;
			try{
				File saveFile = new File(filename2);
				saveOut = new PrintWriter(saveFile);
			}catch(FileNotFoundException fnfe){
				System.out.println("ERROR: BOY SHOULD THIS NOT HAVE HAPPENED...\n");	//this should NEVER occur through user input, only through a program mistake
				return false;
			}
			saveOut.println(numberOfVertices);	//print out number
			for(int i = 1; i <= numberOfVertices; i ++){	//print out cities
				if(i == numberOfVertices){
					saveOut.print(vertexNames[i]);	//formatting
				}else{
					saveOut.println(vertexNames[i]);
				}
				
			}
			for(int i = 1; i <= numberOfVertices; i++){		//go through each edgelist and Print out edges
				//if the to portion (from:to) is < i, don't add because we've already covered it.
				for(int j = 1; j <= adjacencyList[i].getListSize(); j++){
					if(adjacencyList[i].getHead() != null){	//So if we still have something to work with
						if(adjacencyList[i].getHead().getVertexB() > i){	//Check for duplicates
							saveOut.println();
							saveOut.print(adjacencyList[i].getHead().getVertexA() + " " + adjacencyList[i].getHead().getVertexB() + " " + adjacencyList[i].getHead().getDistance() + " " + adjacencyList[i].getHead().getPrice());
							saveOut.flush();
							adjacencyList[i].removeHead();
						}else{ //Skip it
							adjacencyList[i].removeHead();
						}
					}
				}
			}
			saveOut.flush();
			saveOut.close();
			return true;
		}	
	}	
	//END OF GRAPH
	
	/**
	*Here's our interface + most of the fact/validity checking of user input
	**/
	public static void main(String[] args){
		Scanner userInput = new Scanner(System.in); //Read in from user
		Scanner readIn = null;			//Read in from user FILE
		String filename = "";		//where we will load/save from/to
		Graph graph = airline.new Graph(1);	//Create our graph representation
		
		System.out.println("Hello! Welcome to WestWind Airline's route information viewer!\n");
		
		//CREATE GRAPH FROM LOADFILE
		boolean flag = false;
		while(flag == false){
			System.out.print("Please enter the location of the file containing desired route information: ");
			filename = userInput.nextLine();
			try{
				File loadFile = new File(filename);
				readIn = new Scanner(loadFile);
				graph = graph.createGraph(readIn);	//Create it and return it.
				if(graph == null){
					flag = false;
				}else{
					flag = true;
				}
			}catch(FileNotFoundException fnfe){
				System.out.println("ERROR: No file could be found at that location...\n");
				flag = false;
			}
		}
	
		//MAIN MENU
		while(true){
			System.out.println("\n\n\n----------------------");
			System.out.println("Main Menu: ");
			System.out.println("----------------------");
			System.out.println("1. Show All Route Information\n2. Display MST Based on Distance\n3. Display Shortest Path Based On Total Miles\n4. Display Cheapest Path Based On Lowest Price\n5. Display Shortest Path On Number Of Hops\n6. Display All Trips Less Than Or Equal To...\n7. Add New Route\n8. Remove Route\n9. Save & Quit Program");
			int userChoice = 0;
			try{
				System.out.print("\nUser Choice: ");
				userChoice = Integer.parseInt(userInput.nextLine());
			}catch(NumberFormatException nfe){
				System.out.println("ERROR: Please enter a valid numberic choice [1-9]...\n");
			}
			if(userChoice == 1){	//Display graph information
				graph.displayGraph();				
				
			}else if(userChoice == 2){	//Display MST
				System.out.println("\n\n\nDisplaying Minimum Spanning Tree Based on Distance");
				System.out.println("------------------------------------");
				EdgeList[][] MST = graph.createMST();	//MST now holds our minimum spanning tree; like a boss
				for(int i = 1; i <= airline.numberOfVertices; i++){	//iterate through components
					for(int j = 1; j<= airline.numberOfVertices; j++){	//iterate through edgelists					
						Edge current;
						if(MST[i][j] == null){	//Vertex is alone
							System.out.println("\nDisplaying Component " + i);
							System.out.println("--------------------");
							System.out.println(graph.vertexNames[j] + ": No Flights" );
						}else if(MST[i][j].getListSize() > 0){	//vertex has at least one edge
							if(j == i){
								System.out.println("\nDisplaying Component " + i);
								System.out.println("--------------------");
							}
							current = MST[i][j].getHead();
							System.out.println("Flight: " + graph.vertexNames[current.getVertexA()] + " - " + graph.vertexNames[current.getVertexB()] + ":"	+ 	"    	 Distance = " + current.getDistance() );
							while(current.hasNext() ){
								current = current.getNext();
								System.out.println("Flight: " + graph.vertexNames[current.getVertexA()] + " - " + graph.vertexNames[current.getVertexB()] + ":"	+  "    	 Distance = " + current.getDistance() );
							}
						}else{	//that component does not exist, ignore it
							//DO NOTHING							
						}
					}	
				}
			}else if(userChoice == 3){	//Display short-path miles
				String city1 = "";
				String city2 = "";
				int vertexA = 0;
				int vertexB = 0;
				
				while(true){
					System.out.println("\n\n\nDisplaying Shortest Path Based On Distance");
					System.out.println("-------------------------------------------");
					System.out.println("To return to main menu enter 'BACK'");
					System.out.print("\nPlease enter city you will depart from: ");
					city1 = userInput.nextLine();
					if(city1.equalsIgnoreCase("back")){
						break;
					}
					System.out.print("\nPlease enter city destination: ");
					city2 = userInput.nextLine();
					if(city2.equalsIgnoreCase("back")){
						break;
					}
					for(int i = 1; i<=airline.numberOfVertices; i++ ){
						if(city1.equalsIgnoreCase(graph.vertexNames[i])){
							vertexA = i;
						}
						if(city2.equalsIgnoreCase(graph.vertexNames[i])){
							vertexB = i;
						}
					}
					if(vertexA > 0 && vertexB > 0){	//So we are good to try and find a path between these two cities
						int[] parents = graph.djikstraDistance(vertexA, vertexB);	//An array holding the parents of stuff
						
						if(parents == null){
							//error should be handled by djikstraDistance
						}else{
							int[] backwards = new int[airline.numberOfVertices+1];
							int[] forwards = new int[airline.numberOfVertices +1];
							int lengthOfPath = 0;
							int runningTotal = 0;
							
							//start from vertexb, work back to vertexA
							backwards[1] = vertexB;
							int index = 2;	//index to save into backwards
							int parentsIndex = parents[vertexB];	//Where we go from to get Target
							//System.out.println(parentsIndex);	//TEST
							while(true){ //create an array of ONLY the path we need
								if(parentsIndex == vertexA){
									backwards[index] = parentsIndex;
									index += 1;
									break;
								}else{
									backwards[index] = parentsIndex;
									index += 1;
									parentsIndex = parents[parentsIndex];
									//System.out.println(parentsIndex);	//TEST
								}
							}
							//Now go through backwards[] backwards and find/display each edge
							//System.out.println("Size of backwards = " + index-1);	//TEST
							System.out.println("\nShortest path from " + graph.vertexNames[vertexA] + " to " + graph.vertexNames[vertexB] + " based on distance follows:"   );	//FORMATTING REASONS
							System.out.println("--------------------------------------------------");
							for(int i = index-1; i > 0; i--){	//ITERATE THROUGH BACKWARDS[]
								int vertex1 = backwards[i];		
								int vertex2 = backwards[i-1];	
								
								Edge current = graph.adjacencyList[vertex1].getHead();
								for(int j=1; j <= graph.adjacencyList[vertex1].getListSize(); j++){	//Go through 1's list until you find a match with vertex2, thats our edge
									if(current.getVertexB() == vertex2 && current.getVertexA() == vertex1 ){	//We got a match
										System.out.println("Flight from " + graph.vertexNames[current.getVertexA()] + " to " + graph.vertexNames[current.getVertexB()] + "		- Distance: " + current.getDistance()  );
										runningTotal += current.getDistance();
										break;
									}else{
										current = current.getNext();
									}
								}
							}
							System.out.println("\nTOTAL DISTANCE: " + runningTotal + " MILES");
							break;
						}
					}else if(vertexA <= 0){
						System.out.println("\nERROR: Source City does not exist...\n");
					}else if(vertexB <= 0){
						System.out.println("\nERROR: Destination City does not exist...\n");
					}
				}
			}else if(userChoice == 4){	//Display short-path price
				String city1 = "";
				String city2 = "";
				int vertexA = 0;
				int vertexB = 0;
				
				while(true){
					System.out.println("\n\n\nDisplaying Shortest Path Based On Price");
					System.out.println("-------------------------------------------");
					System.out.println("To return to main menu enter 'BACK'");
					System.out.print("\nPlease enter city you will depart from: ");
					city1 = userInput.nextLine();
					if(city1.equalsIgnoreCase("back")){
						break;
					}
					System.out.print("\nPlease enter city destination: ");
					city2 = userInput.nextLine();
					if(city2.equalsIgnoreCase("back")){
						break;
					}
					for(int i = 1; i<=airline.numberOfVertices; i++ ){
						if(city1.equalsIgnoreCase(graph.vertexNames[i])){
							vertexA = i;
						}
						if(city2.equalsIgnoreCase(graph.vertexNames[i])){
							vertexB = i;
						}
					}
					if(vertexA > 0 && vertexB > 0){	//So we are good to try and find a path between these two cities
						int[] parents = graph.djikstraPrice(vertexA, vertexB);	//An array holding the parents of stuff
						
						if(parents == null){
							//error should be handled by djikstraDistance
						}else{
							int[] backwards = new int[airline.numberOfVertices+1];
							int[] forwards = new int[airline.numberOfVertices +1];
							int lengthOfPath = 0;
							int runningTotal = 0;
							
							//start from vertexb, work back to vertexA
							backwards[1] = vertexB;
							int index = 2;	//index to save into backwards
							int parentsIndex = parents[vertexB];	//Where we go from to get Target
							//System.out.println(parentsIndex);	//TEST
							while(true){ //create an array of ONLY the path we need
								if(parentsIndex == vertexA){
									backwards[index] = parentsIndex;
									index += 1;
									break;
								}else{
									backwards[index] = parentsIndex;
									index += 1;
									parentsIndex = parents[parentsIndex];
									//System.out.println(parentsIndex);	//TEST
								}
							}
							//Now go through backwards[] backwards and find/display each edge
							//System.out.println("Size of backwards = " + index-1);	//TEST
							System.out.println("\nCheapest path from " + graph.vertexNames[vertexA] + " to " + graph.vertexNames[vertexB] + " based on price follows:"   );	//FORMATTING REASONS
							System.out.println("-------------------------------------------------------");
							for(int i = index-1; i > 0; i--){	//ITERATE THROUGH BACKWARDS[]
								int vertex1 = backwards[i];		
								int vertex2 = backwards[i-1];	
								
								
								Edge current = graph.adjacencyList[vertex1].getHead();
								for(int j=1; j <= graph.adjacencyList[vertex1].getListSize(); j++){	//Go through 1's list until you find a match with vertex2, thats our edge
									if(current.getVertexB() == vertex2 && current.getVertexA() == vertex1 ){	//We got a match
										System.out.println("Flight from " + graph.vertexNames[current.getVertexA()] + " to " + graph.vertexNames[current.getVertexB()] + "		- Price: " + current.getPrice()  );
										runningTotal += current.getPrice();
										break;
									}else{
										current = current.getNext();
									}
								}
							}
							System.out.println("\nTOTAL PRICE: $" + runningTotal);
							break;
						}
					}else if(vertexA <= 0){
						System.out.println("\nERROR: Source City does not exist...\n");
					}else if(vertexB <= 0){
						System.out.println("\nERROR: Destination City does not exist...\n");
					}
				}
			}else if(userChoice == 5){	//display short-path hops
				String city1 = "";
				String city2 = "";
				int vertexA = 0;
				int vertexB = 0;
				
				while(true){
					System.out.println("\n\n\nDisplaying Shortest Path Based On Hops");
					System.out.println("-----------------------------------------------");
					System.out.println("To return to main menu enter 'BACK'");
					System.out.print("\nPlease enter city you will depart from: ");
					city1 = userInput.nextLine();
					if(city1.equalsIgnoreCase("back")){
						break;
					}
					System.out.print("\nPlease enter city destination: ");
					city2 = userInput.nextLine();
					if(city2.equalsIgnoreCase("back")){
						break;
					}
					for(int i = 1; i<=airline.numberOfVertices; i++ ){
						if(city1.equalsIgnoreCase(graph.vertexNames[i])){
							vertexA = i;
						}
						if(city2.equalsIgnoreCase(graph.vertexNames[i])){
							vertexB = i;
						}
					}
					if(vertexA > 0 && vertexB > 0){	//So we are good to try and find a path between these two cities
						int[] parents = graph.djikstraHops(vertexA, vertexB);	//An array holding the parents of stuff
						
						if(parents == null){
							//error should be handled by djikstraDistance
						}else{
							int[] backwards = new int[airline.numberOfVertices+1];
							int[] forwards = new int[airline.numberOfVertices +1];
							int lengthOfPath = 0;
							int runningTotal = 0;
							
							//start from vertexb, work back to vertexA
							backwards[1] = vertexB;
							int index = 2;	//index to save into backwards
							int parentsIndex = parents[vertexB];	//Where we go from to get Target
							//System.out.println(parentsIndex);	//TEST
							while(true){ //create an array of ONLY the path we need
								if(parentsIndex == vertexA){
									backwards[index] = parentsIndex;
									index += 1;
									break;
								}else{
									backwards[index] = parentsIndex;
									index += 1;
									parentsIndex = parents[parentsIndex];
									//System.out.println(parentsIndex);	//TEST
								}
							}
							//Now go through backwards[] backwards and find/display each edge
							//System.out.println("Size of backwards = " + index-1);	//TEST
							System.out.println("\nShortest trip from " + graph.vertexNames[vertexA] + " to " + graph.vertexNames[vertexB] + " based on total number of hops follows:"   );	//FORMATTING REASONS
							System.out.println("--------------------------------------------------");
							for(int i = index-1; i > 0; i--){	//ITERATE THROUGH BACKWARDS[]
								int vertex1 = backwards[i];		
								int vertex2 = backwards[i-1];	
								
								Edge current = graph.adjacencyList[vertex1].getHead();
								for(int j=1; j <= graph.adjacencyList[vertex1].getListSize(); j++){	//Go through 1's list until you find a match with vertex2, thats our edge
									if(current.getVertexB() == vertex2 && current.getVertexA() == vertex1 ){	//We got a match
										System.out.println("Flight from " + graph.vertexNames[current.getVertexA()] + " to " + graph.vertexNames[current.getVertexB()] + "		- Hops: 1");
										runningTotal += 1;
										break;
									}else{
										current = current.getNext();
									}
								}
							}
							System.out.println("\nTOTAL HOPS: " + runningTotal);
							break;
						}
						
					}else if(vertexA <= 0){
						System.out.println("\nERROR: Source City does not exist...\n");
					}else if(vertexB <= 0){
						System.out.println("\nERROR: Destination City does not exist...\n");
					}
				}
			}else if(userChoice == 6){	//Display all trips less than
				while(true){
					System.out.println("\n\n\nDisplay Trips Less Than [May contain Duplicate/Reversed paths]");
					System.out.println("---------------------------");
					System.out.print("Please enter a dollar amount: ");
					try{
						double userAmount = Double.parseDouble(userInput.nextLine());
						if(userAmount > 0){
							graph.findLessThan(userAmount);
							break;
						}else{
							System.out.println("\nERROR: Please enter a dollar amount > 0...\n");
						}
					}catch(NumberFormatException nfe){
						System.out.println("\nERROR: Please enter a valid dollar amount...\n");
					}
				}
			}else if(userChoice == 7){	//Add route
				int flag3 = 0;
				while(flag3 != 1){
					int vertex1 = 0;
					int vertex2 = 0;
					
					System.out.println("\n\n\nAdding New Route:");
					System.out.println("--------------------");
					try{
						System.out.print("Please enter name of City1 [or 'BACK' to return to main menu]: ");
						String vertexName1 = userInput.nextLine().toUpperCase();
						if(vertexName1.equals("BACK")){
							break;
						}
						System.out.print("\nPlease name enter City2: ");
						String vertexName2 = userInput.nextLine().toUpperCase();
						//Validate vertexName existence
						for(int i = 1; i <= airline.numberOfVertices; i++){
							if(vertexName1.equalsIgnoreCase(graph.vertexNames[i])){
								vertex1 = i;
							}
							if(vertexName2.equalsIgnoreCase(graph.vertexNames[i])){
								vertex2 = i;
							}
						}
						if(vertex1 > 0 && vertex2 > 0){	//good to go
							System.out.print("\nPlease enter DISTANCE of the route between the two previous cities: ");
							int miles2 = Integer.parseInt(userInput.nextLine());
							if(miles2 < 0){
								System.out.println("ERROR: Distance cannot be negative...\n ");
							}else{
								System.out.print("\nPlease enter PRICE of the route between the two previous cities: ");
								double cost2 = Double.parseDouble(userInput.nextLine());
								if(cost2 < 0 ){
									System.out.println("ERROR: Price cannot be negative...\n");
								}else{	//add the new edge
									if(graph.addEdge(airline.new Edge(vertex1, vertex2, cost2, miles2, null)) == true ){
										System.out.println("\nAdd New Route = SUCCESS!");
										flag3 = 1;
									}else{
										//System.out.println("ERROR: Cannot add repeat routes...\n");
									}
								}
							}
						}else if(vertex1 == 0){
							System.out.println("\nERROR: City1 name does not exist in file...\n");
						}else if(vertex2 == 0){
							System.out.println("\nERROR: City2 name does exist in file...\n");
						}
					}catch(NumberFormatException nfe){
						System.out.println("\nERROR: Please enter a valid int amount for distance or a valid double amount for price...\n");
					}
				}
			}else if(userChoice == 8){	//Remove Route
				int city1 = 0;
				int city2 = 0;
				System.out.println("\n\n\nRemoving Route:");
				System.out.println("--------------------");
				System.out.println("ALERT: You will be asked to enter the names of two cities, the route between will be remove. If you would like to exit, enter 'BACK'");
				while(true){
					System.out.print("\nPlease enter name of City1: ");
					String vertexName1 = userInput.nextLine();
					if(vertexName1.equalsIgnoreCase("BACK")){
						break;
					}
					System.out.print("\nPlease enter name of City2: ");
					String vertexName2 = userInput.nextLine();
					if(vertexName2.equalsIgnoreCase("BACK")){
						break;
					}
					for(int i = 1; i <= airline.numberOfVertices; i++){
						if(vertexName1.equalsIgnoreCase(graph.vertexNames[i])){
							city1 = i;
						}
						if(vertexName2.equalsIgnoreCase(graph.vertexNames[i])){
							city2 = i;
						}
					}
					
					if(city1 >0 && city2 >0){
						if(graph.removeEdge(city1,city2)== true ){
							System.out.println("\nRoute Sucessfuly Removed");
							break;
						}else{
							//Error should be thrown
						}
						
					}else if(city1 == 0){
						System.out.println("\nERROR: City1 name does not exist in file...\n");
					}else if(city2 == 0){
						System.out.println("\nERROR: City2 name does exist in file...\n");
					}

				}
			}else if(userChoice == 9){ 	//Save/Quit
				if(graph.saveGraph(filename) == false){
					System.out.println("ERROR: Graph cannot be saved...\n");	//NOT GOOD
				}else{
					System.out.println("Save Complete");
					System.exit(0);
				}
			}else{
				System.out.println("ERROR: Please enter a valid numeric choice [1-9]...\n");
			}
		}
	}
	//END OF MAIN
}
//END OF PROGRAM
