# Node.py by Randyll Bearer 2018
# A node class to be used in an implementation of a doubly-linked
# list.

class Node:

	next = None
	previous = None
	value = 0
	
	def printFromHere(self):
		current = self
		print("Printing from node containing value ", current.value)
		
		i = 0
		while current is not None:
			print("Node ", i, " contains value ", current.value)
			current = current.next
			i = i + 1
			
		
	
#End of File