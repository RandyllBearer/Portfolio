import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

public class Airline{
	static Airline airline = new Airline();
	int numberOfVertices = 0;
	
	/**
	* Will simulate an edge
	**/
	public class Edge{
		int vertexA = 0;
		int vertexB = 0;
		double price = 0;
		int distance = 0;
		Edge next = null;
		
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
		*Getters/Setters
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
		
		public void setNext(Edge toNext){
			next = toNext;
		}
		
		public Edge getNext(){
			return next;
		}
		
		//Is there more to the linked list?
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
	*Will act as a linked list for edges
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
			Edge temp = new Edge(head.getVertexA(), head.getVertexB(), head.getPrice(), head.getDistance(), head.getNext() );
			return temp;
		}
		
		/**
		*Add an edge to the list
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
			if(listSize == 0){
				System.out.println("ERROR: Cannot remove from an empty adjacency list...\n");
				return false;
			}else{	//Attempt to find the edge
				Edge previous = head;
				Edge current = head;
				for(int i = 0; i < listSize; i++){
					if(current.getVertexA() == source && current.getVertexB() == target){
						previous.setNext(current.getNext());
						listSize -= 1;
						return true;
					}else{
						previous = current;
						current = current.getNext();
					}
				}
				System.out.println("Sorry but an edge from " + source + " to " + target + " does not exist...");
				return false;
			}
		}
		
		/**
		*Remove Head
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
	*Minimum-priority queue based off distance
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
		*Inserts edge into the queue [UNORDERED]
		**/
		public void insert(Edge edgeToAdd){
			pq[size] = new Edge(edgeToAdd.getVertexA(), edgeToAdd.getVertexB(), edgeToAdd.getPrice(), edgeToAdd.getDistance(), edgeToAdd.getNext() );
			size += 1;
		}
		
		/**
		*Removes the item in the pq array with the lowest distance
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
	*The workhorse
	**/
	public class Graph{
		int numberOfEdges = 0;	//UNIQUE edges
		EdgeList[] adjacencyList;	//Will act as our main holder of graph information
		String[] vertexNames; 		//Will hold the names of vertexes		
		
		/**
		*Specific Constructor
		**/
		public Graph(int size){
			numberOfVertices = size;
			adjacencyList = new EdgeList[size + 1];	//We will ignore index 0
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
		*Adds an edge to the graph
		**/
		public boolean addEdge(Edge newEdge){
			final int vertex1 = newEdge.getVertexA();
			final int vertex2 = newEdge.getVertexB();
			
			//CHECK TO MAKE SURE NO REPEAT EDGES
			if(adjacencyList[vertex1].getListSize() != 0){	//if the adjacency list is not empty
				Edge current = adjacencyList[vertex1].getHead();
				while(true){	
					if(current.getVertexA() == vertex1 && current.getVertexB() == vertex2){
						System.out.println("ERROR: Cannot add repeat edges...\n");
						return false;
					}else{
						if(current.getNext() == null){
							break;
						}
						current = current.getNext();
					}
				}
			}
			
			adjacencyList[vertex1].add(newEdge);	//So if the edge is from 3, go to 3's adjacency list
			Edge newEdge2 = new Edge(vertex2, vertex1, newEdge.getPrice(), newEdge.getDistance(), newEdge.getNext());
			adjacencyList[vertex2].add(newEdge2);
			numberOfEdges += 1;
			return true;
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
		*Displays the graph
		**/
		public void displayGraph(){
			System.out.println("\n\n\nRoute Information");
			System.out.println("---------------------");
			System.out.println("Total Number of Cities: " + numberOfVertices + "        Total Number of Unique Trips: " + this.getNumberOfEdges());
			//Start Displaying Cities + Trips
			Edge current;
			for(int i=1; i <= numberOfVertices; i++){	//iterate through edgelist[]
				System.out.println("\nFlights from " + vertexNames[i] + ":  Total [" + adjacencyList[i].getListSize() + "]");
				System.out.println("----------------------------------");
				current = adjacencyList[i].getHead();
				System.out.println(1 + ". Flight from " + vertexNames[adjacencyList[i].getHead().getVertexA()] + " to " + vertexNames[adjacencyList[i].getHead().getVertexB()] + "- Distance: " + adjacencyList[i].getHead().getDistance() + " - Price: " + adjacencyList[i].getHead().getPrice());
				for(int j = 2; j <= adjacencyList[i].getListSize(); j++){	//iterate through specific edgelist
					current = current.getNext();
					System.out.println(j + ". Flight from " + vertexNames[current.getVertexA()] + " to " + vertexNames[current.getVertexB()] + "- Distance: " + current.getDistance() + " - Price: " + current.getPrice());
				}
			}
		}
		
		/**
		*Creates an MST (If whole graph is unconeccted, will say so and return all that can be connected from source)
		* lazy prims.  Visit source, enqueue all, go to lowest weight, enqueue all, check if valid, visit lowest, go to all, until.
		* returns what we have so far if unconnected
		**/
		public EdgeList[] createMST(){
			EdgeList[] mst = new EdgeList[numberOfVertices+1];	//This will store our mst
			PQD pq = new PQD();
			boolean[] marked = new boolean[numberOfVertices+1];	//have we visited this node yet?
			int numberOfNodes = 0;
			int numberOfEdges = 0;
			int startIndex = 1;	//The first node with an edge
			Edge current = null;
			
			if(numberOfVertices == 0){
				System.out.println("ERROR: Cannot create an MST of an empty graph...\n");
				return null;
			}
			
			for(int i =1; i <= numberOfVertices; i++){	//Initialize our mst
				mst[i] = new EdgeList();
			}
			for(int i =1; i <= numberOfVertices; i++){	//Find out where we should start
				if(adjacencyList[i].getListSize() > 0){
					startIndex = i;
					break;
				}
			}
			
			//Start at our source (so index 1); and enqueue all edges
			if(adjacencyList[startIndex].getListSize() <= 0){
				System.out.println("ERROR: Source is null...\n");
				return mst;
			}else{
				numberOfNodes += 1;
				marked[startIndex] = true;	//yes we have visited node 1.
				current = adjacencyList[startIndex].getHead();
				pq.insert(new Edge(current.getVertexA(), current.getVertexB(), current.getPrice(), current.getDistance(), current.getNext() ));	//Add our first edge
				while(current.hasNext()){
					current = current.getNext();
					pq.insert(new Edge(current.getVertexA(), current.getVertexB(), current.getPrice(), current.getDistance(), current.getNext()  ));	//Deep copy for days
				}	
			}
			
			//Now pick the lowest weight edge, check if valid, then visit and enqueue
			while(pq.isEmpty() != true){
				current = pq.remove();	//Take our lowest weight edge
				
				if(marked[current.getVertexA()] == true && marked[current.getVertexB()] == true    ){	//If we have visited both vertices, then skip it
					
				}else if(marked[current.getVertexA()] == true ){	//Then visit vertex b
					
					
				}else{	//then visit vertex a
					
					
				}
				
				
				if(marked[current.getVertexB()] != true){	//has yet to be visited
					mst[current.getVertexA()].add(current);	//Only worry about adding one for now, MST formatting and all	//POSSIBLE SHALLOW COPY BUT DOUBT IT
					marked[current.getVertexB()] = true;
					numberOfNodes += 1;
					numberOfEdges += 1;
					current = adjacencyList[current.getVertexB()].getHead();	//current equal first edge of visiting vertex
					if(marked[current.getVertexB()] != true ){
						pq.insert(new Edge(current.getVertexA(), current.getVertexB(), current.getPrice(), current.getDistance(), current.getNext() ));	//add head to pq
					}
					while(current.hasNext() ){	//iterate through the adjacency list
						current = current.getNext();
						if(marked[current.getVertexB()] != true ){	//have not visited
							pq.insert(new Edge(current.getVertexA(), current.getVertexB(), current.getPrice(), current.getDistance(), current.getNext()) );	//add the edge into the queue
						}
					}	
					
				}else{	//has already been visisted
					//DO NOTHING / SKIP IT PULL OUT ANOTHER ONE
					
				}
			}
			
			return mst;
			
			
			
			
		}
		
		/**
		*Saves the graph
		**/
		public boolean saveGraph(String filename2){
			System.out.println("\nSaving...");
			PrintWriter saveOut = null;
			try{
				File saveFile = new File(filename2);
				saveOut = new PrintWriter(saveFile);
			}catch(FileNotFoundException fnfe){
				System.out.println("ERROR: BOY SHOULD THIS NOT HAVE HAPPENED...\n");
				return false;
			}
			saveOut.println(numberOfVertices);	//print out number
			for(int i = 1; i <= numberOfVertices; i ++){	//print out cities
				if(i == numberOfVertices){
					saveOut.print(vertexNames[i]);	//TO PROPERLY FORMAT LINE 381
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
	*Here's our interface + most of the fact/validity checking
	**/
	public static void main(String[] args){
		Scanner userInput = new Scanner(System.in); //Read in form user
		Scanner readIn = null;
		String filename = "";
		Graph graph = airline.new Graph(100);
		
		System.out.println("Hello! Welcome to WestWind Airline's route information viewer!\n");
		
		//CREATE GRAPH
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
	
		//Here we go
		while(true){
			System.out.println("\n----------------------");
			System.out.println("Main Menu: ");
			System.out.println("----------------------");
			System.out.println("1. Show All Route Information\n2. Display MST Based on Distance\n3. Display Shortest Path Based On Total Miles\n4. Display Cheapest Path Based On Lowest Price\n5. Display Shortest Path On Number Of Hops\n6.Display All Trips Less Than...\n7. Add New Route\n8. Remove Route\n9. Save & Quit Program");
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
				EdgeList[] MST = graph.createMST();	//MST now holds our minimum spanning tree; like a boss
				
				for(int i = 1; i<= airline.numberOfVertices; i++){
					Edge current;
					if(MST[i].getListSize() > 0){
						current = MST[i].getHead();
						System.out.println("VERTEX 1: " + current.getVertexA() + "    VERTEX 2: " + current.getVertexB() + "     Distance = " + current.getDistance() );
						while(current.hasNext() ){
							current = current.getNext();
							System.out.println("VERTEX 1: " + current.getVertexA() + "    VERTEX 2: " + current.getVertexB() + "     Distance = " + current.getDistance() );
						}
						
					}
						
					
				}
				
			}else if(userChoice == 3){	//Display short=path miles
				
			}else if(userChoice == 4){	//Display short-path price
				
			}else if(userChoice == 5){	//display short-path hops
				
			}else if(userChoice == 6){	//Display all trips less than
				
			}else if(userChoice == 7){	//Add route
				
			}else if(userChoice == 8){	//Remove Route
				
			}else if(userChoice == 9){ 	//Save/Quit
				if(graph.saveGraph(filename) == false){
					System.out.println("ERROR: Graph cannot be saved...\n");	//NOT GOOD
				}else{
					System.exit(0);
				}
				
				
			}else{
				System.out.println("ERROR: Please enter a valid numeric choice [1-9]...\n");
			}
		}
		
		
	}
	//END OF MAIN
	
}
	
