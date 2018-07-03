import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter

public class Airline{
	static Airline airline = new Airline();
	int numberOfVertices = 0;
	
	/**
	* This will do hopefully all the work
	**/
	public class Graph{
		private int numberOfEdges = 0;	//UNIQUE edges
		private EdgeList[] adjacencyList;	//hold our adjacency list
		private String[] vertexNames;	//HOLD OUR STRING NAMES
		
		/**
		*Initializer Constructor
		**/
		public Graph(int size){
			if(size > 0){
				numberOfVertices = size;
				adjacencyList = new EdgeList[size + 1]; // allows for better indexig starting from 1
				for(int i = 0; i < size + 1; i++){
					adjacencyList[i] = new EdgeList();
				}
				vertexNames = new String[size+1];
			}else{
				System.out.println("ERROR: Cannot create a graph with negative size...\n");
			}
		}
		
		/**
		*Returns number of UNIQUE edges held in the Graph
		**/
		public int getNumberOfEdges(){
			return numberOfEdges;
		}
		
		public boolean addEdge(Edge newEdge){
			final int vertex1 = newEdge.getVertexA();
			final int vertex2 = newEdge.getVertexB();
			
			//Check to make sure there are no repeat edges
			if(adjacencyList[vertex1].getListSize() != 0){
				Edge current = ad
			}
		}
		
		
		
		
	}
	
	
	
	
	
}