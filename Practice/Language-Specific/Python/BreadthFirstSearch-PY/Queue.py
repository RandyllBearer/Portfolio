# Node.py by Randyll Bearer 2018
# A basic Queue structure to be used in an implementation
# of breadth-first-search conducted by BFS.py

class Queue:
	
	elements = []	#The list we will be storing nodes in
	numElements = 0	#How many elements are currently in the queue
	
	#Return the first/oldest element in the Queue
	def peek(self):
		return self.elements[0]	#Return the oldest/first added element
		
	#Removes and returns the first/oldest element from the Queue
	def dequeue(self):
		toReturn = self.elements[0]
		del self.elements[0]
		self.numElements = self.numElements - 1
		
		return toReturn
	
	#Adds an element to the end of the Queue
	def enqueue(self, toAdd):
		try:
			self.elements.append(toAdd)
			self.numElements = self.numElements + 1
		
		except TypeError:
			print("Hey bud, check which types you're trying to enqueue")
	
	#Returns True if numElements == 0, False if otherwise
	def isEmpty(self):
		if self.numElements == 0:
			return True
			
		else:
			return False





#End of File