# Driver.py by Randyll Bearer 2018
# Practice for basic Python coding by testing a basic implementation of a Stack data structure
# To be used in conjuction with Stack.py

import Stack

class Driver:

	#Main driver method to test the Stack.py class/object
	def main():
	
		#Initializing the Stack
		print("Initializing the Stack...")
		stack = Stack.Stack()
		print("Stack Initialized.\n\n\n")
		
		#Adding contents to the Stack
		print("Adding contents to Stack...")
		print("Adding 1 to Stack...")
		stack.push(1)
		print("1 Added to Stack.")
		print("Adding 2 to Stack...")
		stack.push(2)
		print("2 Added to Stack.")
		print("Adding 3 to Stack...")
		stack.push(3)
		print("3 Added to Stack.\n\n\n")
		
		#Retrieve information from the Stack
		print("Getting numItems from Stack...")
		print(stack.getNumItems(), "items currently in Stack.\n\n\n")
		
		#Popping items from the Stack
		print("Popping items from Stack....")
		print("Popping top item from Stack...")
		print(stack.pop(), "popped from Stack.")
		print("Popping top item from Stack...")
		print(stack.pop(), "popped from Stack.")
		print("Popping top item from Stack...")
		print(stack.pop(), "popped from Stack.\n\n\n")
		
		#Retrieve information from the Stack
		print("Getting numItems from Stack...")
		print(stack.getNumItems(), "items currently in Stack.\n\n\n")
		
	if __name__ == '__main__':
		main()

#End of File