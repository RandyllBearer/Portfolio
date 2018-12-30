class dfs:
	#Depth-First-Search/Traversal function
	#Accepts a single node from where the function will begin
	#At each stage prints out the current node and where cursor will be
	#travelling to.
	def depthFirstSearch(self, root):
		if root == None:
			return
		root.visited = True	#Mark this node as visited (Don't want to loop infinitely)
		for n in root.nodesCanReach:
			if n.visited == False:	#Proceed to next unvisited node
				print("Node ", root.value, " is travelling to node ", n.value)
				depthFirstSearch(n)	#Recursive call
			else:	#Print out that the node has been visited already
				print("Node ", root.value, " can reach node ", n.value, " but node has already been visited.")