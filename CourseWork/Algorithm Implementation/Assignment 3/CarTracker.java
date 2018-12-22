import java.util.Scanner;
import java.util.HashMap;
import java.text.NumberFormat;

/**
* Purpose of this program is to act as a basic application to help a user select a car to buy.
* Requires a more advanced implementation of a Minimum Heap-Array Priority Queue which can sort
* based off of either lowest price or lowest mileage and uses indirection for fast indexing.
* 
* Author: Randyll Bearer rlb97@pitt.edu
**/
public class CarTracker{
	static Scanner systemIn = new Scanner(System.in);
	static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	boolean justOrderedPrice = false;
	boolean justOrderedMileage = false;
	int mode = 0;		//0 = sort by lowest price	/ 	1 = sort by lowest mileage
	
	/**
	* Inner class which emulates a car/vehicle.
	* Contains fields for Vehicle ID Number, make, model, price,
	* mileage, and color.  Car objects will be added into the priority queue
	**/
	public class Car{
		private String VIN = null;		//17 Characters Long, No O(o), I(i), or Q(q)
		private String make = null; 	//E.G. Ford, Honda, etc... 
		private String model = null;	//Fiesta, Camry, Civic, etc...
		private double price = 0;		//U.S. Dollars
		private double mileage = 0;		//Imperial miles
		private String color = null;	//Red,Blue, etc..
		
		/**
		* Default Constructor
		* (Mainly used for testing the update method in main.)
		**/
		public Car(){	//Default Constructor	(0 or null)
			
		}
		
		/**
		* Specific-Detailed Constructor (Primary Constructor)
		*
		* @param IDNumber: What the car's VIN will be set to.
		* @param company: What the car's make will be set to.
		* @param type: What the car's model will be set to.
		* @param cost: What the car's price will be set to.
		* @param miles: What the car's mileage will be set to.
		* @param appearance: What the car's color will be set to.
		**/
		public Car(String IDNumber, String company, String type, double cost, double miles, String appearance){		//Specific Constructor
			this.setVIN(IDNumber);
			this.setMake(company);
			this.setModel(type);
			this.setPrice(cost);
			this.setMileage(miles);
			this.setColor(appearance);
		}
		
		//GETTERS AND SETTERS
		public void setVIN(String IDNumber){
			VIN = IDNumber;
		}
		
		public String getVIN(){
			return VIN;
		}
		
		public void setMake(String company){
			make = company;
		}
		
		public String getMake(){
			return make;
		}
		
		public void setModel(String type){
			model = type;
		}
		
		public String getModel(){
			return model;
		}
		
		public void setPrice(double cost){
			price = cost;
		}
		
		public double getPrice(){
			return price;
		}
		
		public void setMileage(double miles){
			mileage = miles;
		}
		
		public double getMileage(){
			return mileage;
		}
		
		public void setColor(String appearance){
			color = appearance;
		}
		
		public String getColor(){
			return color;
		}
		
	}
	
	/**
	* Implementation of a MIN indirect heap-array priority queue
	* Car Array Heap and Index HashMap indirection reference for constant time put/get
	**/
	public class PriorityQueue{
		private Car[] pq = null; 			//This is our array heap, empty at first
		private int capacity = 100;			//How much we CAN hold
		private int size = 0;				//How much we are HOLDING [Where we will add to not where last one is stored]	[LAST HEAP ELEMENT IS IN POSITION SIZE-1]
		private HashMap<String,Integer> indirection = new HashMap<String,Integer>();	//Our indirection hashmap VIN[KEY] : Index[VALUE]
		
		/**
		* Default Constructor
		* 
		* Creates a new PriorityQueue with capacity 100 and an indirection hashmap with default load factor / size
		**/
		public PriorityQueue(){
			pq = new Car[100];
		}
		
		/**
		* Determines whether the PriorityQueue is empty
		* Does not get used atm by the driver method, but is fitting for a data structure to contain in case of expansion.
		*
		* @return boolean: If return true: The PriorityQueue is empty	/ If return false: The PriorityQueue is NOT empty
		**/
		public boolean isEmpty(){
			if(size == 0){
				return true;
			}else{
				return false;
			}
		}
		
		/**
		* Returns the size of the Priority Queue
		*
		* @return size: int amount of car objects currently in the priority queue.
		**/
		public int size(){
			return size;
		}
		
		/**
		* Swap the positions of two Car objects in the Priority Queue
		* and update indirection references
		*
		* @param moveToNew: Car object contained within the Priority Queue
		* @param moveToSpot: Car object contained within the Priority Queue
		**/
		public void swap(Car moveToNew, Car moveToSpot){	
			int spotIndex = indirection.get(moveToNew.getVIN());	//moveToNew's location
			int newIndex = indirection.get(moveToSpot.getVIN());	//moveToSpot's location
			//What we are replacing with
			pq[spotIndex] = moveToSpot;
			indirection.put(moveToSpot.getVIN(),spotIndex);
			//What we are moving
			pq[newIndex] = moveToNew;
			indirection.put(moveToNew.getVIN(),newIndex);
			justOrderedPrice = false;		//Cannot guarantee correct order after a swap
			justOrderedMileage = false;
		}
		
		/**
		* Add a Car object into the Priority Queue and set its indirection reference
		*
		* @param carToAdd: Car Object to be added into PriorityQueue and given indirection reference.
		**/
		public void add(Car carToAdd){
			if(size == capacity){	//If we need to resize before adding, do so
				capacity = 2*capacity;
				Car[] temp = new Car[capacity];	//Make a new heap of old*2 = size
				for(int i = 0; i < size; i++){	//Transfer old heap to new heap
					temp[i] = pq[i];
				}
				pq = temp;
			}
			pq[size] = carToAdd;	// Add to heap-array
			indirection.put(carToAdd.getVIN(),size);	//Add to indirection reference
			size += 1;	// Increment size
			swim(size-1);	//Since we added to leaf, see if we can climb up the heap
			justOrderedPrice = false;	//Cannot guarantee order after addition
			justOrderedMileage = false;
		}
		
		/**
		* Removes the Car object corresponding to the given VIN
		* 
		* @param VIN: String Vehicle ID Number of desired Car object to delte
		* @return boolean: If True: Desired Car object was deleted	/ False: Desired Car object could not be found within Priority Queue and therefore could not be deleted
		**/
		public boolean remove(String VIN){
			Integer index = indirection.get(VIN);
			if(index == null){
				System.out.println("Sorry but that VIN does not exist in the system...");
				return false;
			}
			swap(pq[index],pq[size-1]);	//Replaces the desired removal with the last in the queue 
			pq[size-1] = null;			//Remove the desired car
			size = size - 1;			//Decrement pq size
			swim(index);	//Since we could be removing from ANY position in the Priority Queue, we must both swim and sink to be safe
			sink(index);
			justOrderedPrice = false;	//Cannot guarantee correct order after removal
			justOrderedMileage = false;
			System.out.println("\nRemoval Successful");
			return true;
		}
		
		/**
		* Does this VIN exist in the indirection hashmap?
		*
		* @param VIN: Vehicle ID Number to be checked as existing key
		* @return boolean: If True: The VIN exists in the HashMap therefore its corresponding Car Object exists in Priority Queue	/	False: VIN could not be found, the Car object does not exist within Priority Queue
		**/
		public boolean contains(String VIN){
			if(indirection.containsKey(VIN)){
				return true;
			}else{
				return false;
			}
		}
		
		/**
		* Updates an existing Car object's price and accordingly sinks or swims
		* 
		* @param VIN: VIN which should correspond to a Car object within Priority Queue
		* @return boolean: True: Update was successful	/ False: Update was NOT successful [VIN not contained within indirection HashMap] 
		**/
		public boolean updatePrice(String VIN){
			mode = 0; //We are concerned only with price atm
			Integer index = indirection.get(VIN);
			if(index == null){	//Does this VIN even exist in the indirection HashMap?
				System.out.println("Sorry but that VIN does not exist in the system...");
				return false;
			}
			double oldPrice = pq[index].getPrice();
			System.out.println("Please enter the new price [XXXXX.XX]: ");
			double newPrice = 0;
			try{
				newPrice = Double.parseDouble(systemIn.nextLine());	//Convert what you get to a double
			}catch(NumberFormatException nfe){
				System.out.println("\nSorry but that is not an acceptable price...");
			}
			pq[index].setPrice(newPrice);
			if(newPrice < oldPrice){	//If the new price is smaller than the old one, swim
				swim(index);
			}else if(newPrice > oldPrice){	//If the new price is larger than the old one, sink
				sink(index);
			}		
			justOrderedPrice = false;	//Cannot guarantee sorted order after an update
			justOrderedMileage = false;
			return true;			
		}
		
		/**
		* Updates an existing Car object's mileage and accordingly sinks or swims
		*
		* @param VIN: VIN which should correspond to a Car object within Priority Queue
		* @return boolean: True: Update was successful	/ False: Update was NOT successful [VIN not contained within indirection HashMap]
		**/
		public boolean updateMileage(String VIN){
			mode = 1;	//We are concerned only with Mileage atm.
			Integer index = indirection.get(VIN);
			if(index == null){
				System.out.println("Sorry but that VIN does not exist in the system...");
				return false;
			}
			double oldMileage = pq[index].getMileage();
			System.out.println("Please enter the new mileage [XXXXX.XX]");
			double newMileage = 0;
			try{
				newMileage = Double.parseDouble(systemIn.nextLine());	//Convert user input to double
			}catch(NumberFormatException nfe){
				System.out.println("\nSorry but that is not an acceptable mileage...");
			}
			pq[index].setMileage(newMileage);
			if(newMileage > oldMileage){
				swim(index);
			}else if(newMileage < oldMileage){
				sink(index);
			}
			justOrderedPrice = false;
			justOrderedMileage = false;
			return true;
		}
		
		
		/**
		* Updates an existing Car object's color.
		*
		* @param VIN: Vehicle ID Number which should correspond to a Car object within Priority Queue
		* @return boolean: True: Update successful	/ False: Update failed [VIN does not exist within indirection HashMap]
		**/
		public boolean updateColor(String VIN){
			Integer index = indirection.get(VIN);
			if (index == null){
				System.out.println("Sorry but that VIN does not exist in the system...");
				return false;
			}
			System.out.println("Please enter the new color: ");	//No restrictions on car color, so let user make it whatever he/she wants
			String newColor = systemIn.nextLine();
			pq[index].setColor(newColor);
			return true;
			
		}
		
		/**
		* Retrieve the vehicle with the lowest price in the Priority Queue
		*
		* @return Car: Car with lowest price in the Priority Queue
		**/
		public Car retrieveLowestPrice(){
			mode = 0;	//We are concerned with Price
			if(justOrderedPrice == true){	//If we just sorted it, don't bother doing it again
				return pq[0];
			}else{
				heapsortPrice();	//Need to guarantee it is sorted by price priority, run a heapsort
				return pq[0];
			}
		}
		
		/**
		* Accepts a make and model of Car object and returns the one with the lowest price if one exists
		* 
		* @param make: make of the desired Car to retrieve
		* @param model: model of the desired Car to retrieve
		* @return Car: Car which has same fields as desired make/model and is of the lowest price	/ If null: no car of desired make/model could be found.
		**/
		public Car retrieveLowestPriceSpecific(String make, String model){
			mode = 0;
			if(justOrderedPrice == false){	//If sorted order by price can't be guaranteed
				heapsortPrice(); //Heapsort by lowestPrice
			}
			int index = 0;
			Car toReturn = pq[index];
			for(int i = 0; i < size; i++){	//Since we can guarantee it is in sorted heap order, just go from left to right [WILL be ascending order] until you find the desired make/model
				if(toReturn.getMake().equalsIgnoreCase(make) && toReturn.getModel().equalsIgnoreCase(model)){
					return toReturn;
				}
				toReturn = pq[i];
			}
			return null;
			
		}
		
		/**
		* Retrieve the vehicle with the lowest mileage in the Priority Queue
		*
		* @return Car: Var with lowest mileage in the Priority Queue
		**/
		public Car retrieveLowestMileage(){
			mode = 1;
			if(justOrderedMileage == true){	//If the Priority Queue was just heapsorted, don't bother doing it again
				return pq[0];
			}else{
				heapsortMileage();//heapsort by lowest mileage
				return pq[0];
			}
		}
		
		/**
		* Accepts a make and model of Car object and returns the one with the lowest mileage if one exists
		*
		* @param make: make of the desired Car to retrieve
		* @param model: model of the desired Car to retrieve
		* @return Car: Car which has same fields as desired make/model and is of the lowest mileage	/ If null: no car of desired make/model could be found.
		**/
		public Car retrieveLowestMileageSpecific(String make, String model){
			mode = 1;
			heapsortMileage();
			if(justOrderedMileage == false){	//If we can't guarantee sorted order by Mileage, then sort it
				heapsortMileage();
				justOrderedMileage = true;
				justOrderedPrice = false;
			}
			int index = 0;
			Car toReturn = pq[index];
			for(int i = 0; i < size; i++){	//Due to sorting we are able to assume ascending order, just return the first one to have matching make/model
				if(toReturn.getMake().equalsIgnoreCase(make) && toReturn.getModel().equalsIgnoreCase(model)){
					return toReturn;
				}
				toReturn = pq[i];
			}
			return null;
		}
		
		/**
		* Potentialy "swims" a car up the Heap-array
		*
		* @param indexToSwim: Index location of the desired Car object to "swim"
		**/
		public void swim(int indexToSwim){
			if(mode == 0){	//Swimming by price
				while(indexToSwim > 0 && pq[(indexToSwim-1)/2].getPrice() > pq[indexToSwim].getPrice()){	//As long as we dont try to swim when we are root and as long as current node is < parent node
					swap(pq[indexToSwim], pq[(indexToSwim-1)/2]);
					indexToSwim = (indexToSwim-1)/2;
				}
			}else{			//Swimming by mileage
				while(indexToSwim > 0 && pq[(indexToSwim-1)/2].getMileage() > pq[indexToSwim].getMileage()){
					swap(pq[indexToSwim], pq[(indexToSwim-1)/2]);
					indexToSwim = (indexToSwim-1)/2;
				}
			}
		}
		
		/**
		* Potentially "sinks" a car down the Heap-array
		*
		* @param indexToSink: Index location of the desired Car object to "sink"
		**/
		public void sink(int indexToSink){	
			if(mode == 0){	// Sink by price	
				while(((2*indexToSink) + 1) < size){	// < size since size-1 is the last location in heap				
					int j = (2*indexToSink) + 1;	//Left binary child
					if(j+1 >= size){				//IF RIGHT CHILD OUT OF BOUNDS, JUST TRY TO SWAP WITH LEFT CHILD
						if(pq[indexToSink].getPrice() < pq[j].getPrice()){	//If Our Sinker is > left Child, Sink
							swap(pq[indexToSink],pq[j]);
							indexToSink = j;
						}else{
							break;
						}
					}else{	//IF RIGHT CHILD NOT OUT OF BOUNDS
						if(pq[j].getPrice() < pq[j+1].getPrice()){	//Find the smaller child TEST WAS >
							j = j + 1;		//Swap with the smaller number; don't move larger numbers up
						}
						if(pq[indexToSink].getPrice() < pq[j].getPrice()){	//If our child is greater than the parent, don't move up the tree
							swap(pq[indexToSink],pq[j]);
							indexToSink = j;
						}else{
							break;
						}
					}
				}
			}else{		// Sink by mileage
				while(((2*indexToSink) + 1) < size){
					int j = (2*indexToSink) + 1;
					if(j+1 >= size){
						if(pq[indexToSink].getMileage() < pq[j].getMileage()){
							swap(pq[indexToSink],pq[j]);
							indexToSink = j;
						}else{
							break;
						}
					}else{
						if(pq[j].getMileage() < pq[j+1].getMileage()){	
							j = j + 1;
						}
						if(pq[indexToSink].getMileage() < pq[j].getMileage()){
							swap(pq[indexToSink],pq[j]);
							indexToSink = j;
						}else{
							break;
						}
					}
				}
			}
		}
		
		/**
		* Sorts the priority queue heap into ascending order based on PRICE
		**/
		public void heapsortPrice(){
			mode = 0;
			//Construction phase
			for(int i = ((size-1)/2); i >= 0; i--){	//Go through and sink all parent nodes
				sink(i);
			}
			//Sortdown phase
			int oldSize = size;	//Just to keep it on track
			while(size > 0){
				swap(pq[0], pq[size-1]);
				size = size - 1;
				sink(0);
			}
			size = oldSize;	//Repair size
			justOrderedPrice = true;	//We CAN guarantee sorted order by price now
			justOrderedMileage = false;
		}
		
		/**
		* Sorts the priority queue heap into ascending order based on MILEAGE
		**/
		public void heapsortMileage(){
			mode = 1;
			//Construction phase
			for(int i = ((size-1)/2); i >= 0; i--){
				sink(i);
			}
			//Sortdown phase
			int oldSize = size;
			while(size > 0){	
				swap(pq[0], pq[size-1]);
				size = size - 1;
				sink(0);
			}
			size = oldSize;	//Repair size
			justOrderedMileage = true;	//We CAN guarantee sorted order by mileage now
			justOrderedPrice = false;
		}
	}
	
	/**
	* Driver Method whih handles the Terminal Interface
	**/
	public static void main(String[] args){
		CarTracker carTracker = new CarTracker();
		PriorityQueue priorityQueue = carTracker.new PriorityQueue();
		//test(carTracker,priorityQueue);  //This will add 19 test cars to the Priority Queue to be able to test with. Just delete the comment marks
		System.out.println("Hello! Welcome to CarTracker.java! Please follow the directions on screen.");	//FORMATTING
		while(true){
			System.out.println("\nWhat Would You Like To Do? [Enter corresponding number]: ");	//FORMATTING
			System.out.println("1. Add A Car");		//FORMATTING
			System.out.println("2. Update A Car");	//FORMATTING
			System.out.println("3. Remove A Specific Car From Consideration");	//FORMATTING
			System.out.println("4. Retrieve The Lowest Price Car");				//FORMATTING
			System.out.println("5. Retrieve The Lowest Mileage Car");			//FORMATTING
			System.out.println("6. Retrieve The Lowest Price Car By Make And Model");	//FORMATTING
			System.out.println("7. Retrieve The Lowest Mileage Car By Make And Model");	//FORMATTING
			System.out.println("8. Terminate CarTracker.java");	//FORMATTING
			System.out.print("\nDesired Option: ");
			
			int selection = 9;	//Must be initalized to SOMETHING
			try{
				selection = Integer.parseInt(systemIn.nextLine());
			}catch(NumberFormatException nfe){
				System.out.println("\nERROR: Please enter a valid NUMERIC choice [1-8]...");
			}
			
			if(selection == 1){		//ADD
				String desiredVIN = "";
				String desiredMake = "";
				String desiredModel = "";
				double desiredPrice = 0;
				double desiredMileage = 0;
				String desiredColor = "";
				while(true){
					System.out.println("\nADDING CAR:\n");
					System.out.print("Please enter the VIN: ");
					desiredVIN = systemIn.nextLine();
					desiredVIN = desiredVIN.toUpperCase();
					//Check to see if it exists already
					if(priorityQueue.contains(desiredVIN)){
						System.out.println("ERROR: That VIN already exists within the system, please try updating or removing it instead...");
						break;
					}
					boolean result = checkVIN(desiredVIN);
					if(result == true){
						System.out.print("\nPlease enter the car's make [Ford, Honda, etc.]: ");
						desiredMake = systemIn.nextLine();
						desiredMake = desiredMake.toUpperCase();
						System.out.print("\nPlease enter the car's model [Fiesta, Camry, etc.]: ");
						desiredModel = systemIn.nextLine();
						desiredModel = desiredModel.toUpperCase();
						while(true){
							try{
								System.out.print("\nPlease enter the car's price: ");
								desiredPrice = Double.parseDouble(systemIn.nextLine());
								System.out.print("\nPlease enter the car's mileage: ");
								desiredMileage = Double.parseDouble(systemIn.nextLine());
								break;
							}catch(NumberFormatException nfe){
								System.out.println("\nPlease enter a valid price format [4500.67] or mileage format [56000]\n");
							}
						}
						System.out.print("\nPlease enter the car's color: ");
						desiredColor = systemIn.nextLine();
						Car carToAdd = carTracker.new Car(desiredVIN, desiredMake, desiredModel, desiredPrice, desiredMileage, desiredColor);
						priorityQueue.add(carToAdd);	//FINAL
						break;
					}
					break;
				}
			}else if(selection == 2){	//UPDATE
				System.out.println("\nUPDATING CAR:\n");
				System.out.print("Please enter VIN of car to update: ");
				String updateVIN = systemIn.nextLine();
				boolean result = priorityQueue.contains(updateVIN);
				if(result == true){
					while(true){
						System.out.println("\nUPDATING CAR:\n");
						System.out.println("1. Update Price");
						System.out.println("2. Update Mileage");
						System.out.println("3. Update Color");
						System.out.println("4. Back to Main Menu");
						System.out.print("\nDesired Option: ");
						
						int desiredSelection = 5;
						try{
							desiredSelection = Integer.parseInt(systemIn.nextLine());
						}catch(NumberFormatException NFE){
							System.out.println("\nERROR: Please enter a valid NUMERIC choice [1-4]...");
						}
						
						if(desiredSelection == 1){
							priorityQueue.updatePrice(updateVIN);
						}else if(desiredSelection == 2){
							priorityQueue.updateMileage(updateVIN);
						}else if(desiredSelection == 3){
							priorityQueue.updateColor(updateVIN);							
						}else if(desiredSelection == 4){
							break;
						}else{
							System.out.println("ERROR: Please enter a valid choice [1-4]...");
						}
					}
				}else{
					System.out.println("\nERROR: VIN could not be found in system...");
				}
			}else if(selection == 3){	//REMOVE
				while(true){
					System.out.println("\nREMOVING CAR:\n");
					System.out.print("Please enter VIN of car to remove or 'BACK' to go back to main menu: ");
					String removeVIN = systemIn.nextLine();
					if(removeVIN.equalsIgnoreCase("back")){
						break;
					}
					boolean result = priorityQueue.contains(removeVIN);
					if(result == true){
						priorityQueue.remove(removeVIN);
						break;
					}else{
						System.out.println("ERROR: VIN not found in system...");
					}
				}
			}else if(selection == 4){	//Retrieve low price
				carTracker.mode = 0;
				Car retrievedCar = priorityQueue.retrieveLowestPrice();
				System.out.println("\nVIEWING LOWEST PRICE CAR:\n");
				System.out.println("VIN: " + retrievedCar.getVIN());
				System.out.println("MAKE: " + retrievedCar.getMake());
				System.out.println("MODEL: " + retrievedCar.getModel());
				System.out.println("PRICE: " + currencyFormatter.format(retrievedCar.getPrice()));
				System.out.println("MILEAGE: " + retrievedCar.getMileage());
				System.out.println("COLOR: " + retrievedCar.getColor());
				
			}else if(selection == 5){	//Retrieve low mileage
				carTracker.mode = 1;
				Car retrievedCar = priorityQueue.retrieveLowestMileage();
				System.out.println("\nVIEWING LOWEST MILEAGE CAR:\n");
				System.out.println("VIN: " + retrievedCar.getVIN());
				System.out.println("MAKE: " + retrievedCar.getMake());
				System.out.println("MODEL: " + retrievedCar.getModel());
				System.out.println("PRICE: " + currencyFormatter.format(retrievedCar.getPrice()));
				System.out.println("MILEAGE: " + retrievedCar.getMileage());
				System.out.println("COLOR: " + retrievedCar.getColor());
				
			}else if(selection == 6){	//Rretrieve low price specific
				carTracker.mode = 0;
				System.out.println("\nRETRIEVING LOWEST PRICE SPECIFIC\n");
				System.out.print("Please enter make: ");
				String userMake = systemIn.nextLine();
				userMake = userMake.toUpperCase();
				System.out.print("\nPlease enter model: ");
				String userModel = systemIn.nextLine();
				userModel = userModel.toUpperCase();
				Car retrievedCar = priorityQueue.retrieveLowestPriceSpecific(userMake, userModel);
				if(retrievedCar == null){
					System.out.println("ERROR: No car of specified make and model could be found in the system...");
				}else{
					System.out.println("\nVIEWING LOWEST PRICE CAR SPECIFIC:\n");
					System.out.println("VIN: " + retrievedCar.getVIN());
					System.out.println("MAKE: " + retrievedCar.getMake());
					System.out.println("MODEL: " + retrievedCar.getModel());
					System.out.println("PRICE: " + currencyFormatter.format(retrievedCar.getPrice()));
					System.out.println("MILEAGE: " + retrievedCar.getMileage());
					System.out.println("COLOR: " + retrievedCar.getColor());
				}
				
			}else if(selection == 7){	//Retrieve low mileage specific
				carTracker.mode = 1;
				System.out.println("\nRETRIEVING LOWEST MILEAGE SPECIFIC\n");
				System.out.print("Please enter make: ");
				String userMake = systemIn.nextLine();
				userMake = userMake.toUpperCase();
				System.out.print("\nPlease enter model: ");
				String userModel = systemIn.nextLine();
				userModel = userModel.toUpperCase();
				Car retrievedCar = priorityQueue.retrieveLowestMileageSpecific(userMake, userModel);
				if(retrievedCar == null){
					System.out.println("ERROR: No car of specified make and model could be found in the system...");
				}else{
					System.out.println("\nVIEWING LOWEST MILEAGE CAR SPECIFIC:\n");
					System.out.println("VIN: " + retrievedCar.getVIN());
					System.out.println("MAKE: " + retrievedCar.getMake());
					System.out.println("MODEL: " + retrievedCar.getModel());
					System.out.println("PRICE: " + currencyFormatter.format(retrievedCar.getPrice()));
					System.out.println("MILEAGE: " + retrievedCar.getMileage());
					System.out.println("COLOR: " + retrievedCar.getColor());
				}	
			}else if(selection == 8){	//Terminate CarTracker.java
				System.exit(0);
			}else{
				System.out.println("\nERROR: Please enter a valid choice [1-8]...\n");
			}
			
			
		}
	}
	
	/**Assumes VINToCheck is already uppercase and verifies that the desired VIN does not contain restricted characters
	* 
	* @param VINToCheck: Desired Vehicle ID Number
	* @return boolean: True: desired VIN is available	/ False: Desired VIN could not be used
	**/
	public static boolean checkVIN(String VINToCheck){
		if(VINToCheck.length() == 17){
			if(VINToCheck.indexOf("I") > -1){
				System.out.println("ERROR: VIN String cannot include a 'I'...\n");
				return false;
			}else if(VINToCheck.indexOf("O") > -1){
				System.out.println("ERROR: VIN String cannot include a 'O'...\n");
				return false;
			}else if(VINToCheck.indexOf("Q") > -1){
				System.out.println("ERROR: VIN String cannot include a 'Q'...\n");
				return false;
			}else if(VINToCheck.indexOf(" ") > -1){
				System.out.println("ERROR: VIN String cannot contain a space...\n");
				return false;
			}
			return true;	//Desired Outcome
		}
		System.out.println("ERROR: VIN Must be 17 characters/numbers long...\n");
		return false;
	}
	
	/**
	* Just some simple declarations to test functionality with.
	*
	* @param ct: static CarTracker object
	* @param pq: static PriorityQueue object
	**/
	public static void test(CarTracker ct, PriorityQueue pq){
		Car car1 = ct.new Car("1111432X098765432",	"Ford",	"Fiesta",	12500,	55000,	"Red");
		pq.add(car1);
		Car car2 = ct.new Car("2222432X098765432",	"Ford",	"Escape",	30000,	54000,	"Black");
		pq.add(car2);
		Car car3 = ct.new Car("3333432X098765432",	"Toyota",	"Camry",	15000,	54500,	"Blue");
		pq.add(car3);
		Car car4 = ct.new Car("4444432X098765432",	"Toyota",	"Camry",	14000,	6000,	"Orange");
		pq.add(car4);
		Car car5 = ct.new Car("5555432X098765432",	"Subaru",	"Impreza",	27500,	53000,	"Yellow");
		pq.add(car5);
		Car car6 = ct.new Car("6666432X098765432",	"Honda",	"Civic",	14500,	25000,	"Green");
		pq.add(car6);
		Car car7 = ct.new Car("7777432X098765432", "Chevrolet",	"Equinox",	32000,	42789,	"Purple");
		pq.add(car7);
		Car car8 = ct.new Car("8888432X098765432",	"BMW",	"X5", 47500,	21987,	"Silver");
		pq.add(car8);
		Car car9 = ct.new Car("9999432X098765432",	"BMW",	"X5",	47000,	18768,	"White");
		pq.add(car9);
		Car car10 = ct.new Car("1111432X098761111",	"Ford", "Fiesta",	20000,	10000,	"Bronze");
		pq.add(car10);
		Car car11 = ct.new Car("2222432X098762222",	"Nissan",	"Rogue",	19000,	45000,	"Teal");
		pq.add(car11);
		Car car12 = ct.new Car("3333432X098763333",	"Nissan",	"Juke",	18000,	42500,	"Sapphire");
		pq.add(car12);
		Car car13 = ct.new Car("4444432X098764444",	"Nissan",	"Juke",	15500,	33500,	"Sable");
		pq.add(car13);
		Car car14 = ct.new Car("5555432X098765555",	"Dodge",	"Charger",	12000,	60000,	"Burgundy");
		pq.add(car14);
		Car car15 = ct.new Car("6666432X098766666",	"Dodge",	"Charger",	14000,	61000,	"Aqua");
		pq.add(car15);
		Car car16 = ct.new Car("7777432X098767777",	"Ford",	"F-150",	20000,	62000,	"Forest");
		pq.add(car16);
		Car car17 = ct.new Car("8888432X098768888",	"Ford",	"F-150",	25000,	95000,	"Cherry");
		pq.add(car17);
		Car car18 = ct.new Car("9999432X098769999",	"Aston Martin",	"DB-9",	75000,	40000,	"Pearl");
		pq.add(car18);
		Car car19 = ct.new Car("8888432X098762222",	"Dodge",	"Charger",	22500,	10000,	"Lavender");
		pq.add(car19);
		
		//To test my assumption of ascending order after heapsorting used in find lowest specifics
		pq.heapsortPrice();
		System.out.println("Position of Car 14 " + pq.indirection.get("5555432X098765555"));	//Car14
		System.out.println("Position of Car 15 " + pq.indirection.get("6666432X098766666"));	//Car15
		System.out.println("Position of Car 19 " + pq.indirection.get("8888432X098762222"));	//Car19
		//ORDER SHOULD BE FOR PRICE: 14/15/19
		
		pq.heapsortMileage();
		System.out.println("\nPosition of Car 14 " + pq.indirection.get("5555432X098765555"));	//Car14
		System.out.println("Position of Car 15 " + pq.indirection.get("6666432X098766666"));	//Car15
		System.out.println("Position of Car 19 " + pq.indirection.get("8888432X098762222"));	//Car19
		//ORDER SHOULD BE FOR MILEAGE: 19/14/15
	}
}