#Graphy.py & Node.py by Randyll Bearer 2018
# A node and graph class to be used in an implementation of 
# Djikstra's Algorithm by Driver.py.

#Graph contains an adjacency list of all nodes in the graph
class Graph:
	nodes = []

	def addNode(self, nodeToAdd):
		self.nodes.append(nodeToAdd)
	
#Each node maintains a list of all nodes it can reach (i.e. Edges)
class Node:
	
	#Constructor to set value on initialization
	def __init__(self, value):
		self.value = value		#The value that this node contains
		self.adjacentNodes = {}	#Contains all the nodes that this node can reach and the distances (edge weights) between
		self.visited = False	#So we know if we've visited a node already in BFS, avoids potential infinite loops
		
	def __str__(self):
		return str(self.value)

	def __repr__(self):
		return str(self.value)
		
	#Adds node:distance to self.nodesCanReach
	def addConnection(self, node, distance):
		self.adjacentNodes[node] = distance	#Might need to make key immutable, so a string


#End of File