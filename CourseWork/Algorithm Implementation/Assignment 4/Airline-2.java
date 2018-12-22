import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

public class Airline{
	static Airline airline = new Airline();
	int numberOfVertices = 0;
	
	public class Graph{	// most important
		int numberOfEdges = 0;
		EdgeList[] adjacencyList;	//Will hold our adjacency list
		String[] vertexNames;	//Will hold the names + indexes for vertices for creation
		
		public Graph(int size){	//USE THIS ONE
			if(size > 0){
				numberOfVertices = size;
				adjacencyList = new EdgeList[size + 1];	//Don't worry about index 0 at all
				for(int i = 0; i < size+1; i ++){
					adjacencyList[i] = new EdgeList();
				}
				vertexNames = new String[size + 1];
			}else{
				System.out.println("ERROR: Cannot greate a graph with negative size...\n");
			}
		}
		
		/**
		*Add a new edge to the graph
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
			Edge newEdge2 = new Edge(vertex2, vertex1, newEdge.getPrice(), newEdge.getDistance());
			adjacencyList[vertex2].add(newEdge2);
			numberOfEdges += 1;
			return true;
		}
		
		public EdgeList[] copyEdgeList(EdgeList[] toCopy){
			EdgeList[] result = new EdgeList[numberOfVertices + 1];
			Edge current;
			
			for(int i = 1; i <= airline.numberOfVertices; i++){	//iterate through edgeList[]
				if(toCopy[i].getHead() != null){
					current = toCopy[i].getHead();
					result[i].add(new Edge(current.getVertexA(), current.getVertexB(), current.getPrice(), current.getDistance()));
					for(int j = 2; j <= toCopy[i].getListSize(); j++){	//Iterate through each edgelist
						current = current.getNext();
						result[i].add(new Edge(current.getVertexA(), current.getVertexB(), current.getPrice(), current.getDistance()));
					}
				}else{
					
				}
			}
			
			return result;
		}
		
		/**
		*returns a minimum spanning tree
		* WILL NEED TO FIND A WAY TO COPY ADJACENCY LIST BECAUSE BOY AM I FUCKING WITH IT
		**/
		public EdgeList[] createMSTDistance(){
			boolean[] marked = new boolean[numberOfVertices+1];	//+1 due to indexing starting at 1 not 0
			EdgeList[] MST = new EdgeList[numberOfVertices+1];	//Holds the edges we will be adding in order of when we add them
			int mstSize = 0;	//records number of edges added to MST
			PQDistance pqd = new PQDistance(this);
			
			//BEGIN AT VERTEX 1
			marked[1] = true;
			for(int i = 1; i <= adjacencyList[1].getListSize(); i++){	//Go through the first vertex and add edges
				if(marked[adjacencyList[1].getHead().getVertexB()] == true){	//So we've already visited the node this edge leads us to
					adjacencyList[1].removeHead();
				}else{	//visiting a node we have not yet visited, add edge to pqd
					pqd.push(adjacencyList[1].getHead());	//Push the edge onto the priority queue
					adjacencyList[1].removeHead();			//allow us to iterate through
				}
			}	
			//GO THROUGH THE PQD
			while(!pqd.isEmpty()){
				Edge current = pqd.pop();	//Lowest distance-weighted edge
				if(marked[current.getVertexA()] == true && marked[current.getVertexB()] == true){	//Determine which vertex we are at
					//this edge is ineligible, skip it
				}else if(marked[current.getVertexA()] == true){	//If we have already visited a, visit b
					int currentVertex = current.getVertexB();
					System.out.println("MADE IT"); //TEST
					MST[currentVertex].add(current);	//Add to the mst
					mstSize += 1;
					for(int i = 1; i <= adjacencyList[currentVertex].getListSize(); i ++){
						if(marked[adjacencyList[currentVertex].getHead().getVertexB()] == true){	//have we already visited where this edge will take us?
							adjacencyList[currentVertex].removeHead();
						}else{
							pqd.push(adjacencyList[currentVertex].getHead());
							adjacencyList[currentVertex].removeHead();
						}
					}
				}else if (marked[current.getVertexB()] == true){	//If we have already visited b, visit a
					int currentVertex = current.getVertexA();
					System.out.println("MADE IT 2");
					MST[currentVertex].add(current);	//ADD to the mst
					mstSize += 1;
					if(marked[adjacencyList[currentVertex].getHead().getVertexB()] == true){	//have we already visited where this edge will take us?
						adjacencyList[currentVertex].removeHead();
					}else{
						pqd.push(adjacencyList[currentVertex].getHead());
						adjacencyList[currentVertex].removeHead();
					}
				}
			}
			
			//COPY BACK ORIGINAL adjacencyList and we are good to go.
			if(numberOfVertices != (mstSize + 1)){
				System.out.println("ALERT: This MST could not be fully connected to every destination/vertex... Showing partial solution from " + vertexNames[MST[1].getHead().getVertexA()]);
			}
			
			return MST;
		}
		
		
	}
	
	/**
	*Class Priority Queue Distance
	**/
	public class PQDistance{
		Edge[] pq = new Edge[0];	//Will act as our array
		int index = 0;
		
		public PQDistance(Graph graph){
			pq = new Edge[graph.numberOfEdges];
		}
		
		public void push(Edge edgeToAdd){
			pq[index] = edgeToAdd;
			index += 1;
		}
		
		public Edge pop(){	//Return lowest edge based on distance, then fill in the gap
			if(index == 0){	//Cannot remove from an empty queue
				return null;
			}
		
			int currentLowest = 0;
			for(int i = 1; i < index; i++){	//Iterate through the priority queueue
				if(pq[i].getDistance() < pq[currentLowest].getDistance()){	//Find the lowest
					currentLowest = i;
				}
			}
			
			Edge result = new Edge(pq[currentLowest].getVertexA(), pq[currentLowest].getVertexB(), pq[currentLowest].getPrice(), pq[currentLowest].getDistance());
			pq[currentLowest] = new Edge(pq[index-1].getVertexA(), pq[index-1].getVertexB(), pq[index-1].getPrice(), pq[index-1].getDistance());	//SHOULD BE LAST ONE IN PQ
			pq[index-1] = null;					//Just to be safe
			
			return result;
		}
		
		public boolean isEmpty(){
			if(index == 0){
				return true;
			}
			return false;
		}
		
	}
	
	/**
	*class EDGELIST
	**/
	public class EdgeList{
		Edge head = null;
		int listSize = 0;
		
		public void add(Edge toAdd){	//Adds to back of list
			if(listSize == 0){	//If our list is empty, just send it to the front.
				head = toAdd;
			}else{
				Edge temp = head;
				head = toAdd;
				toAdd.setNext(temp);
			}
			listSize +=1;
		}
		
		//Rolls through EdgeList to look for a match
		public boolean remove(int source, int target){
			if(source < 0 || source > numberOfVertices){
				System.out.println("ERROR: Source " + source + " does not exist...\n");
				return false;
			}
			if(target < 0 || target > numberOfVertices){
				System.out.println("ERROR: Target " + target + " does not exist...\n");
				return false;
			}
			
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
		
		public int getListSize(){
			return listSize;
		}
		
		public Edge getHead(){
			return head;
		}
		
		public void removeHead(){	//Useful for my saving algorithm
			if(head == null){
				System.out.println("ERROR: Cannot remove the head from an empty list");
			}else{
				head = head.getNext();
			}
		}
		
	}
		
	/**
	*class EDGE
	**/
	public class Edge{	// in this case, emulates a route
		int vertexA = 0;	// Any positive # could be a vertex, avoid confusion.	[FROM]
		int vertexB = 0;															//[TO]
		double price = 0;		//How much does it cost to go to B from A
		int distance = 0;	//How far is B from A
		Edge next = null;
		
		/**
		*Default constructor
		**/
		public Edge(){
			
		}
		
		/**
		*Detailed Constructor (USE THIS ONE)
		**/
		public Edge(int source, int target, double cost, int miles){
			setVertexA(source);
			setVertexB(target);
			setPrice(cost);
			setDistance(miles);
		}
		
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
	

	public static void main(String[] args){	//create/save + interface
		Scanner userInput = new Scanner(System.in);	//use to handle user input
		Scanner loadFileInput = null;
		String filename = "";
		Graph graph = null;
		
		System.out.println("Hello! Welcome to WestWind Airline's route information viewer!\n");
		
		//CREATE THE INITIAL GRAPH
		boolean flag = false;
		boolean flag2 = false;
		int filesize = 0;
		while(flag == false || flag2 == false){	//Get a valid file location
			System.out.print("Please enter the location of the file containing desired route information: ");
			filename = userInput.nextLine();
			try{
				File loadFile = new File(filename);
				loadFileInput = new Scanner(loadFile);
				filesize = Integer.parseInt(loadFileInput.nextLine());	//How many vertices will we be dealing with?
				if(filesize > 0){
					flag = true;
					graph = airline.new Graph(filesize);	//create the graph now we know size
					for(int i = 1; i <= filesize; i++){	//Go through and store the location/vertex names.
						graph.vertexNames[i] = loadFileInput.nextLine();
					}
					while(loadFileInput.hasNext()){	//Go through and create edges for each of the connections.
						int vertex1 = loadFileInput.nextInt();
						if(vertex1 < 1 || vertex1 > filesize){	//Check to make sure if valid vertex
							System.out.println("ERROR: File (VertexA) not correctly formatted...\n");
							flag2 = false;
							break;
						}
						int vertex2 = loadFileInput.nextInt();
						if(vertex2 < 1 || vertex2 > filesize){	//Check to make sure if valid vertex
							System.out.println("ERROR: File (VertexB) not correctly formatted...\n");
							flag2 = false;
							break;
						}
						int miles = Integer.parseInt(loadFileInput.next());
						if(miles < 0){
							System.out.println("ERROR: Miles cannot be < 0...\n");
							flag2 = false;
							break;
						}
						double price = Double.parseDouble(loadFileInput.nextLine());	//Should consume the line and start anew
						if(price < 0){
							System.out.println("ERROR: Price cannot be < $0.00...\n");
							flag2 = false;
							break;
						}
						Edge edgeToAdd = airline.new Edge(vertex1, vertex2, price, miles);	//Create the edge
						System.out.println("VERTEX1 = " + vertex1 + " :VERTEX2 = " + vertex2);	//TEST
						if(graph.addEdge(edgeToAdd) == false){
							flag2 = false;
							break;
						}
						flag2 = true;
					}
				}else{
					System.out.println("ERROR: Cannot load in a graph from an empty file or negative sized file...\n");
					flag = false;
				}
			}catch(FileNotFoundException fnfe){
				System.out.println("ERROR: Could not load from designated file location, please enter the correct location...\n");
				flag = false;
			}catch(NumberFormatException nfe){
				System.out.println("ERROR: Sorry, but the designated file at '" + filename + "' could not be properly read from...\n");
				flag = false;
			}catch(NoSuchElementException nsee){
				System.out.println("ERROR: Cannot load from an empty file...\n");
				flag = false;
			}
		}
		System.out.println("SUCCESSFULLY CREATED");	//TEST
		
		//INTERFACE
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
				System.out.println("ERROR: Please enter a valid numeric choice [1-9]...\n");
			}
			if(userChoice == 1){	//Display all route information
				EdgeList[] temp = graph.copyEdgeList(graph.adjacencyList);
				System.out.println("\n\n\nRoute Information");
				System.out.println("--------------------");
				System.out.println("Total Number of Cities: " + filesize + "        Total Number of Unique Trips: " + graph.numberOfEdges);
				//START DISPLAYING CITIES + TRIPS
				for(int i = 1; i <= filesize; i++){	//Iterate through adjacency lists
					System.out.println("\nFlights from " + graph.vertexNames[i] + ":  Total [" + graph.adjacencyList[i].getListSize() + "]");	//Name of city
					System.out.println("---------------------------");
					for(int j = 1; j <= graph.adjacencyList[i].getListSize(); j ++){	//Iterate through specific adjacency list
						System.out.println(j + ". Flight from " + graph.vertexNames[graph.adjacencyList[i].getHead().getVertexA()] + " to " + graph.vertexNames[graph.adjacencyList[i].getHead().getVertexB()] + "- Distance: " + graph.adjacencyList[i].getHead().getDistance() + " - Price: " + graph.adjacencyList[i].getHead().getPrice());
						graph.adjacencyList[i].removeHead();
					}
				}
				graph.adjacencyList = temp;
			}else if(userChoice == 2){	//Display MST
				System.out.println("\n----------------------");
				System.out.println("Displaying MST Based on Distance:");
				System.out.println("----------------------");
				EdgeList[] mst = graph.createMSTDistance();	//Our created MST
				
				for(int i = 1; i <= filesize; i++){
					for(int j = 1; j <= mst[i].getListSize(); j ++){	//Iterate through specific mst
						System.out.println("edge (" + mst[i].getHead().getVertexA() + " : " + mst[i].getHead().getVertexB() + ")");
						mst[i].removeHead();
					}
				}
				
			}else if(userChoice == 3){	//Display Short-Path miles
				
			}else if(userChoice == 4){	//Display cheap-path price
				
			}else if(userChoice == 5){	//display short-path hops
				
			}else if(userChoice == 6){	//display all trips less than
				
			}else if(userChoice == 7){	//add new route
				
			}else if(userChoice == 8){	//remove route
				
			}else if(userChoice == 9){	//Save & Quit
				System.out.println("\nSaving...");
				PrintWriter saveOut = null;
				try{
					File saveFile = new File(filename);
					saveOut = new PrintWriter(saveFile);
				}catch(FileNotFoundException fnfe){
					System.out.println("ERROR: BOY SHOULD THIS NOT HAVE HAPPENED...\n");
				}
				saveOut.println(airline.numberOfVertices);	//print out number
				for(int i = 1; i <= airline.numberOfVertices; i ++){	//print out cities
					if(i == airline.numberOfVertices){
						saveOut.print(graph.vertexNames[i]);	//TO PROPERLY FORMAT LINE 381
					}else{
						saveOut.println(graph.vertexNames[i]);
					}
					
				}
				for(int i = 1; i <= airline.numberOfVertices; i++){		//go through each edgelist and Print out edges
					//if the to portion (from:to) is < i, don't add because we've already covered it.
					for(int j = 1; j <= graph.adjacencyList[i].getListSize(); j++){
						if(graph.adjacencyList[i].getHead() != null){	//So if we still have something to work with
							if(graph.adjacencyList[i].getHead().getVertexB() > i){	//Check for duplicates
								saveOut.println();
								saveOut.print(graph.adjacencyList[i].getHead().getVertexA() + " " + graph.adjacencyList[i].getHead().getVertexB() + " " + graph.adjacencyList[i].getHead().getDistance() + " " + graph.adjacencyList[i].getHead().getPrice());
								saveOut.flush();
								graph.adjacencyList[i].removeHead();
							}else{ //Skip it
								graph.adjacencyList[i].removeHead();
							}
						}
					}
				}
				saveOut.close();
				userInput.close();
				System.exit(0);
			}else{
				System.out.println("ERROR: Please enter a valid numeric choice [1-9]...\n");
			}
			
		}
		
	}
	
	/**
	public EdgeList[] copyEdgeList(EdgeList[] edgeListToCopy){
		int size = edgeListToCopy.getListSize();
		EdgeList[] result = new EdgeList[size];	//What we will be returning
		
		Edge current;
		for(int i = 1; i <= size; i ++){
			current = edgeListToCopy[i].getHead();
			for(int j = 2; j <= edgeListToCopy[i].getListSize(); j++){
				
			}
		}
		
		
		return result;
	}
	**/
	//What Have I got Done: Creation of initial graph, and that's about it

}

