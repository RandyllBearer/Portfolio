# Stack.py by Randyll Bearer 2018
# Practice for basic Python coding implementing a basic Stack data structure
# To be used in conjuction with Driver.py
# Will throw an exception if pop() is called on an empty Stack
# Python lists automatically resize, so no need to manually do so.

class Stack:
	
	stack = []		#Create a List, Ordered, Sequential, Allows Duplicates (Order is important to preserve integrity of Stack)
	numItems = 0	#There should be no items currently in the Stack
	
	#Remove the top item from the Stack
	def pop(self):
		self.numItems = self.numItems - 1	#Decrement the number of items in the Stack
		return self.stack.pop()		#Python Lists have .pop() built in.
		
	#Add an item to the top of the Stack
	def push(self, valueToPush):
		self.stack.append(valueToPush)		#Increment items to the top of the Stack
		self.numItems = self.numItems + 1	#Python Lists have .pop() built in.
		
	#Return how many items we currently have in the Stack
	def getNumItems(self):
		return self.numItems
		
#End of File