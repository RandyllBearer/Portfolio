# Node.py by Randyll Bearer 2018
# A node and graph class to be used in an implementation of 
# Breadth-First-Search implemented by BFS.py.

#Graph contains an adjacency list of all nodes in the graph
class Graph:
	nodes = []

#Each node maintains a list of all nodes it can reach (i.e. Edges)
class Node:
	
	#Constructor to set value on initialization
	def __init__(self, value):
		self.value = value		#The value that this node contains
		self.nodesCanReach = []	#Contains all the nodes that this node can reach
		self.visited = False	#So we know if we've visited a node already in BFS, avoids potential infinite loops
	
	def addConnection(self, node):
		self.nodesCanReach.append(node)


#End of File