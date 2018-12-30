# Driver.py by Randyll Bearer 2018
# Basic program to test my implementation of Depth-First-Search/Traversal
# on a cyclic graph composed of nodes found in Node.py. The DFS algorithm
# implemented here is recursive in nature.

from Node import Graph
from Node import Node
import dfs

class Driver:		
	
	#Depth-First-Search/Traversal function
	#Accepts a single node from where the function will begin
	#At each stage prints out the current node and where cursor will be
	#travelling to.
	def depthFirstSearch(root, visited):
		if root is None:
			return
		visited.append(root.value)	#Mark this node as visited (Don't want to loop infinitely)
		
		for n in root.nodesCanReach:
			if n.value in visited:	#Print out that the node has been visited already
				print("Node ", root.value, " can reach node ", n.value, " but node has already been visited.")
			else:	#Proceed to next unvisited node
				print("Node ", root.value, " is travelling to node ", n.value)
				depthFirstSearch(n, visited)	#Recursive call

	#Driver Code
	
	#Initialize some nodes to be used in the graph
	one = Node(1)
	two = Node(2)
	three = Node(3)
	four = Node(4)
	five = Node(5)
	six = Node(6)
	seven = Node(7)

	#Add Edges (Connect the nodes to each other)
	#Visual Image of graph contained in graphDiagram.png
	one.addConnection(two)
	two.addConnection(three)
	two.addConnection(four)
	three.addConnection(two)
	three.addConnection(five)
	four.addConnection(two)
	four.addConnection(three)
	four.addConnection(five)
	five.addConnection(three)
	five.addConnection(four)
	five.addConnection(six)
	six.addConnection(seven)
	#seven can reach no nodes
	
	#Initialize graph and add nodes to list
	myGraph = Graph()
	myGraph.nodes.append(one)
	myGraph.nodes.append(two)
	myGraph.nodes.append(three)
	myGraph.nodes.append(four)
	myGraph.nodes.append(five)
	myGraph.nodes.append(6)
	myGraph.nodes.append(7)

	#Conduct the Breadth-First-Search
	myRoot = myGraph.nodes[0]
	visitedNodes = []
	depthFirstSearch(myRoot, visitedNodes)	#root = node 1
	
#End of File