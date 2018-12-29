# Driver.py by Randyll Bearer 2018
# Basic program to test a doubly-linked list structure using the
# corresponding Node.py implementation.

import Node

class Driver:

	#Main Driver
	def main():
		#Initialize three nodes
		first = Node.Node()
		first.value = 1
		second = Node.Node()
		second.value = 2
		third = Node.Node()
		third.value = 3
		
		#Create doubly-linked list
		first.next = second
		second.next = third
		second.previous = first
		third.previous = second
		
		#Print elements from first
		first.printFromHere()
	
	
	if __name__ == '__main__':
		main()

#End of File