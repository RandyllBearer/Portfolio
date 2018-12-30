# Driver.py by Randyll Bearer 2018
# Basic program to test my implementation of Breadth-First-Search/Traversal
# on a cyclic graph composed of nodes found in Node.py. The BFS algorithm
# implemented here uses a Queue structure to keep track of remaining nodes to
# visit. This queue structure can be found in Queue.py

from Node import Graph	#Because we have multiple class definitions in same file
from Node import Node
import Queue

class Driver:
	
	#Breadth-First-Search function
	#Accepts a single node argument (Where one wants to begin the search)
	#Prints initial starting point value
	#Then prints each node and its value as it reaches it
	#Prints "Node can reach no other nodes"
	def breadthFirstSearch(root):
		myQueue = Queue.Queue()	#Initialize the imported Queue class
		myQueue.enqueue(root)
		root.visited = True	#Mark that this node has been visited to avoid infinite looping
		print("Beginning breadthFirstSearch at node ", root.value)
		
		#Try to branch out from each node we have visited
		while(myQueue.isEmpty() == False):
			current = myQueue.dequeue()
			current.visited = True
			print("\nNow visiting node ", current.value)
			
			#Add all nodes yet to be visited to the Queue
			for n in current.nodesCanReach:
				if n.visited == False:
					n.visited = True;
					myQueue.enqueue(n)
					print("Node, ", current.value, " can reach new node ", n.value)
				else:
					print("Node, ", current.value, " can reach node ", node.value, " but has already been visited.")

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
	breadthFirstSearch(myRoot)	#root = node 1
	
#End of File