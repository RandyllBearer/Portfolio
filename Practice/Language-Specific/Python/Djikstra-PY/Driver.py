# Driver.py by Randyll Bearer 2018
# Basic program to test my implementation of Djikstra's Algorithm
# on a cyclic directed graph composed of nodes implemented in Graph.py. 
# Note: Djikstra's Algorithm returns the shortest path between root node and all others.
# Diagram of graph used can be found in graphDiagram.png

from Graph import Graph
from Graph import Node

class Driver:

	#This implementation of Djikstra's prints a Minimum Spanning Tree
	def djikstra(graph, root):
		#Initialize unvisited and visited dictionaries (Dicts so that we can store which node we came from/distance)
		unvisited = {node: None for node in graph.nodes}	#Pyhton has no MAXINT or INF, so we'll just use None
		visited = {}
		
		#Initialize with starting node
		current = root
		currentDistance = 0	#This is our cumulative distance we have travelled so far
		unvisited[current] = currentDistance	#Since we starting at root, distance to root is 0
		
		#Begin iteration through the algorithm
		while True:
			for node, distance in current.adjacentNodes.items(): #Loop through current's neighbors
				if node not in unvisited:
					continue	#if we've been to a node already, skip items
				else:
					newDistance = currentDistance + distance #Get new distance to adjacent node
				
				if unvisited[node] is None or unvisited[node] > newDistance: #Check for none because no MAXINT Line 13
					unvisited[node] = newDistance	#If we've found a shorter way to get there, update it
			
			visited[current] = currentDistance	#Update where we've been and how far it was to get there
			del unvisited[current]	#Since we just added it to visited, we can get rid of it from unvisited
			
			if not unvisited:	#Empty dicts evaluate to false, so if empty we visited everywhere, we're done
				break
				
			#Proceed to next node which is closest to current [smallest distance]
			candidates = [node for node in unvisited.items() if node[1]]
			current, currentDistance = sorted(candidates, key = lambda x: x[1])[0]
			
		print(visited) #Print the results
		
	#This implementation of Djikstra's prints shortest path between two nodes
	def djikstraPath(graph, root, toVisit):
		if root == toVisit:	#If we are already at our toVisit, just quit
			print("Shortest path to node is itself")
			return
	
		#Initialize unvisited, visited, lastReached dictionaries (Dicts so that we can store which node we came from/distance)
		unvisited = {node: None for node in graph.nodes}	#Pyhton has no MAXINT or INF, so we'll just use None
		visited = {}
		lastReached = {node: None for node in graph.nodes}
		
		#Initialize with starting node
		current = root
		lastReached[current] = root
		currentDistance = 0	#This is our cumulative distance we have travelled so far
		unvisited[current] = currentDistance	#Since we starting at root, distance to root is 0
		
		#Begin iteration through the algorithm
		while True:
			#Shortest path successfully found, print results
			if current.value == toVisit.value:	#If we reached our toVisit, finish
				visited[current] = {lastReached[current]: currentDistance}
				#Determine all nodes in shortest path from root to current
				nodesInPath = []	#Where we will store all nodes in path
				while current != root: #work backwards
					nodesInPath.insert(0, current)  #Insert current into front of list (to preserve ordering)
					current = lastReached[current]
				nodesInPath.insert(0, root)
				#Print all nodes in shortest path
				print("Now printing nodes in shortest path from root to toVisit")
				i = 0
				while i < len(nodesInPath)-1:
					print("Node ", nodesInPath[i], ", path to ", nodesInPath[i+1], " distance ", nodesInPath[i].adjacentNodes[nodesInPath[i+1]])
					i = i + 1;
				break
		
			#Update shortest adjacent distances
			for node, distance in current.adjacentNodes.items(): #Loop through current's neighbors
				if node in unvisited: #Only concerned with nodes we haven't visited yet
					newDistance = currentDistance + distance #Get new distance to adjacent node
					
					if unvisited[node] is None or newDistance < unvisited[node]: #Check for none because no MAXINT Line 13
						unvisited[node] = newDistance	#If we've found a shorter way to get there, update it
						lastReached[node] = current		#Update which node has best way to reach this node
			
			#Update visited{} with current node
			visited[current] = {lastReached[current]: currentDistance}	#Update where we are, how we got there
			del unvisited[current]	#Since we just added it to visited, we can get rid of it from unvisited
			
			#No shortest path could be found
			if not unvisited:	#Empty dicts evaluate to false, if we traversed whole graph without finding toVisit, finish
				print("toVisit could not be reached from Root")
				break
				
			#Proceed to next node which is closest to current [smallest distance]
			candidates = [node for node in unvisited.items() if node[1]]
			current, currentDistance = sorted(candidates, key = lambda x: x[1])[0]
		
		
	#Initialize Nodes for Graph
	one = Node(1)
	two = Node(2)
	three = Node(3)
	four = Node(4)
	five = Node(5)
	six = Node(6)
	
	#Initialize Edges between Nodes
	one.addConnection(two, 3)
	one.addConnection(three, 5)
	one.addConnection(four, 12)
	two.addConnection(four, 4)
	three.addConnection(four, 6)
	four.addConnection(one, 12)
	four.addConnection(five, 5)
	four.addConnection(six, 8)
	five.addConnection(six, 2)
	six.addConnection(five, 2)

	#Add Nodes to Graph
	myGraph = Graph()
	myGraph.addNode(one)
	myGraph.addNode(two)
	myGraph.addNode(three)
	myGraph.addNode(four)
	myGraph.addNode(five)
	myGraph.addNode(six)
	
	#Run Djikstra's Algorithm (mst)
	djikstra(myGraph, one)
	
	#Run Djikstra's Algorithm (path)
	djikstraPath(myGraph, one, six)
	
#End of File