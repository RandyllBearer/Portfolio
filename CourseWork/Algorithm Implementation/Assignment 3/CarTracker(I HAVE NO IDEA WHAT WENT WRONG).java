import java.util.Scanner;
import java.util.HashMap;
import java.text.NumberFormat;

/**
* Purpose of this program is to simulate a program
* which tracks various information about numerous cars
* and stores it in a MIN indirect heap-array priority queue.
*
*
**/
public class CarTracker{
	static Scanner systemIn = new Scanner(System.in);
	static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	boolean justOrderedPrice = false;
	boolean justOrderedMileage = false;
	int mode = 0;		//0= sort by lowest price 		/ 	1 = sort by lowest mileage
	
	/**
	* Inner class which emulates a vehicle
	*
	*
	**/
	public class Car{
		private String VIN = null;		//17 Characters Long, No O(o), I(i), or Q(q)
		private String make = null; 	//E.G. Ford, Honda, etc... 
		private String model = null;	//Fiesta, Camry, Civic, etc...
		private double price = 0;		//U.S. Dollars
		private double mileage = 0;		//Imperial miles
		private String color = null;	//Red,Blue, etc..
		
		public Car(){	//Default Constructor	(0 or null)
			
		}
		
		public Car(String IDNumber, String company, String type, double cost, double miles, String appearance){		//Specific Constructor
			this.setVIN(IDNumber);
			this.setMake(company);
			this.setModel(type);
			this.setPrice(cost);
			this.setMileage(miles);
			this.setColor(appearance);
		}
		
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
	* Array Heap and HashMap indirection reference
	*
	**/
	public class PriorityQueue{
		private Car[] pq = null; 	//This is our array heap, size 100
		private int capacity = 100;			//How much we CAN hold
		private int size = 0;				//How much we are HOLDING [Where we will add to not where last one is stored]	[LAST HEAP ELEMENT IS IN POSITION SIZE-1]
		private HashMap<String,Integer> indirection = new HashMap<String,Integer>();	//Our indirection hashmap VIN[KEY] : Index[VALUE]
		
		//Default Constructor
		public PriorityQueue(){
			pq = new Car[100];
		}
		
		//Returns true if empty, false if NOT empty
		public boolean isEmpty(){
			if(size == 0){
				return true;
			}else{
				return false;
			}
		}
		
		//Returns the size of the Priority Queue
		public int size(){
			return size;
		}
		
		/**
		* Swap the positions of two cars in the priority queue
		* and updates indirection reference
		*
		**/
		public void swap(Car moveToNew, Car moveToSpot){	//FIRST ONE MOVES TO SECOND's
			int spotIndex = indirection.get(moveToNew.getVIN());	//moveToNew's location
			int newIndex = indirection.get(moveToSpot.getVIN());	//moveToSpot's location
			//What we are replacing with
			pq[spotIndex] = moveToSpot;
			indirection.put(moveToSpot.getVIN(),spotIndex);
			//What we are moving
			pq[newIndex] = moveToNew;
			indirection.put(moveToNew.getVIN(),newIndex);
			justOrderedPrice = false;
			justOrderedMileage = false;
		}
		
		//Add a car into the priority queue
		public void add(Car carToAdd){
			if(size == capacity){	//RESIZE NECESSARY
				capacity = 2*capacity;
				Car[] temp = new Car[capacity];	//Make a new heap of old*2 = size
				for(int i = 0; i < size; i++){	//Transfer old heap to new heap
					temp[i] = pq[i];
				}
				pq = temp;
			}
			pq[size] = carToAdd;	// Add to heap-array
			indirection.put(carToAdd.getVIN(),size);	//add to indirection reference
			size += 1;
			swim(size-1);
			justOrderedPrice = false;
			justOrderedMileage = false;
		}
		
		/**
		* Removes a car corresponding to the VIN
		* Will return true if the removal was successful, false otherwise
		**/
		public boolean remove(String VIN){
			Integer index = indirection.get(VIN);
			if(index == null){
				System.out.println("Sorry but that VIN does not exist in the system...");
				return false;
			}
			swap(pq[index],pq[size-1]);	//Replaces the desired removal with the last in the queue 
			pq[size-1] = null;	//Remove the desired car
			size -= 1;			//Decrement pq size
			swim(index);	//NOT GUARANTEED TO BE IN CORRECT POSITION, DO BOTH TO BE SAFE
			sink(index);
			justOrderedPrice = false;
			justOrderedMileage = false;
			return true;
		}
		
		/**
		* Does this VIN exist in the hashmap?
		* TEST TO SEE IF IT RETURNS NULL IF NOTHING EXISTS, THIS MAY TAKE UNNECCESSARY TIME
		**/
		public boolean contains(String VIN){
			if(indirection.containsKey(VIN)){
				return true;
			}else{
				return false;
			}
		}
		
		/**
		* Updates an existing car's price and accordingly sinks or swims
		* Returns true if the update was completed, false if not
		**/
		public boolean updatePrice(String VIN){
			Integer index = indirection.get(VIN);
			if(index == null){
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
			if(newPrice > oldPrice){
				swim(index);
			}else if(newPrice < oldPrice){
				sink(index);
			}		
			justOrderedPrice = false;
			justOrderedMileage = false;
			return true;			
		}
		
		/**
		* Updates an existing car's mileage and accordingly sinks or swims
		* Returns true if the update was completed, false if not
		**/
		public boolean updateMileage(String VIN){
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
		* Updates an existing car's color
		* Returns true if the update was completed, false if not
		**/
		public boolean updateColor(String VIN){
			Integer index = indirection.get(VIN);
			if (index == null){
				System.out.println("Sorry but that VIN does not exist in the system...");
				return false;
			}
			System.out.println("Please enter the new color: ");
			String newColor = systemIn.nextLine();
			pq[index].setColor(newColor);
			return true;
			
		}
		
		/**
		* Retrieve the vehicle with the lowest price
		**/
		public Car retrieveLowestPrice(){
			mode = 0;
			if(justOrderedPrice == true){
				return pq[0];
			}else{
				heapsortPrice();//heapsort by lowest price
				return pq[0];
			}
		}
		
		/**
		* Accepts a make and model of car and returns the one with the lowest price
		* If no vehicle of specified make and model exists, returns null
		**/
		public Car retrieveLowestPriceSpecific(String make, String model){
			mode = 0;
			if(justOrderedPrice == false){
				heapsortPrice(); //Heapsort by lowestPrice [to guarantee ascending order in priority queue array: So that a left-right index search's first result will be the lowest]
				justOrderedPrice = true;
				justOrderedMileage = false;
			}
			int index = 0;
			Car toReturn = pq[index];
			for(int i = 0; i < size; i++){
				if(toReturn.getMake().equalsIgnoreCase(make) && toReturn.getModel().equalsIgnoreCase(model)){
					return toReturn;
				}
				toReturn = pq[i];
			}
			return null;
			
		}
		
		/**
		* Retrieve the vehicle with the lowest mileage
		**/
		public Car retrieveLowestMileage(){
			mode = 1;
			if(justOrderedMileage == true){
				return pq[0];
			}else{
				heapsortMileage();//heapsort by lowest mileage
				return pq[0];
			}
		}
		
		/**
		* Accepts a make and model of car and returns the one with the lowest mileage
		* If no vehicle of specified make and model exists, returns null
		**/
		public Car retrieveLowestMileageSpecific(String make, String model){
			mode = 1;
			if(justOrderedMileage == false){
				heapsortMileage();//Heapsort by lowestMileage [to guarantee ascending order in priority queue array]
				justOrderedMileage = true;
				justOrderedPrice = false;
			}
			int index = 0;
			Car toReturn = pq[index];
			for(int i = 0; i < size; i++){
				if(toReturn.getMake().equalsIgnoreCase(make) && toReturn.getModel().equalsIgnoreCase(model)){
					return toReturn;
				}
				toReturn = pq[i];
			}
			return null;
		}
		
		/**
		* Potentiall "swims" a car up the priority queue
		*
		**/
		public void swim(int indexToSwim){
			if(mode == 0){	//Swimming by price
				while(indexToSwim > 0 && pq[(indexToSwim-1)/2].getPrice() > pq[indexToSwim].getPrice()){	//As long as we dont try to swim when we are root and as long as current node is > parent node
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
		* Potentially "sinks" a car down the priority queue
		*
		**/
		public void sink(int indexToSink){	//TEST REPLACE ALL < with >
			if(mode == 0){	// Sink by price	
				while(((2*indexToSink) + 1) < size){	// < size since size-1 is the last location in heap				
					int j = (2*indexToSink) + 1;	//Left binary child
					if(j+1 >= size){	//IF RIGHT CHILD OUT OF BOUNDS, JUST TRY TO SWAP WITH LEFT CHILD
						if(pq[indexToSink].getPrice() > pq[j].getPrice()){	//If Our Sinker is > left Child, Sink
							swap(pq[indexToSink],pq[j]);
							indexToSink = j;
						}else{
							break;
						}
					}else{	//IF NOT OUT OF BOUNDS
						if(pq[j].getPrice() > pq[j+1].getPrice()){	//Find the smaller child
							j = j + 1;		//Swap with the smaller number; don't move larger numbers up
						}
						if(pq[indexToSink].getPrice() > pq[j].getPrice()){	//If our child is greater than the parent, don't move up the tree
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
						if(pq[indexToSink].getMileage() > pq[j].getMileage()){
							swap(pq[indexToSink],pq[j]);
							indexToSink = j;
						}else{
							break;
						}
					}else{
						if(pq[j].getMileage() > pq[j+1].getMileage()){
							j = j + 1;
						}
						if(pq[indexToSink].getMileage() > pq[j].getMileage()){
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
		* Sorts the priority queue heap into ascending order based on price
		* MIGHT BE ABLE TO COMBINE THE TWO [CURRENTLY ALL THAT IS DIFFERENT IS 'mode']
		**/
		public void heapsortPrice(){
			mode = 0;
			//Construction phase
			for(int i = (size/2); i >= 0; i--){	//Go through and sink all parent nodes
				sink(i);
			}
			//Sortdown phase
			int oldSize = size;	//Just to keep it on track
			while(size > 1){
				swap(pq[0], pq[size-1]);
				size = size - 1;
				sink(0);
			}
			size = oldSize;	//Repair size
			justOrderedPrice = true;
			justOrderedMileage = false;
		}
		/**
		* Sorts the priority queue heap into ascending order based on mileage
		*
		**/
		public void heapsortMileage(){
			mode = 1;
			//Construction phase
			for(int i = (size/2); i >= 0; i--){
				sink(i);
			}
			//Sortdown phase
			int oldSize = size;
			while(size > 1){
				swap(pq[0], pq[size-1]);
				size = size - 1;
				sink(0);
			}
			size = oldSize;	//Repair size
			justOrderedMileage = true;
			justOrderedPrice = false;
		}
		
		//LEFT TO DO [DRIVER METHOD]
		
	}
	
	/**
	* Driver Method
	*
	* Will handle terminal interface and user I/O
	* Handles most verifications and errors
	**/
	public static void main(String[] args){
		CarTracker carTracker = new CarTracker();
		PriorityQueue priorityQueue = carTracker.new PriorityQueue();
		System.out.println("Hello! Welcome to CarTracker.java! Please follow the directions on screen.\n\n");	//FORMATTING
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
			
			int selection = 9;
			try{
				selection = Integer.parseInt(systemIn.nextLine());
			}catch(NumberFormatException nfe){
				System.out.println("\nERROR: Please enter a valid choice [1-8]...");
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
							System.out.println("\nERROR: Please enter a valid choice [1-4]...");
						}
						
						if(desiredSelection == 1){
							priorityQueue.updatePrice(updateVIN);
						}else if(desiredSelection == 2){
							priorityQueue.updateMileage(updateVIN);
						}else if(desiredSelection == 3){
							priorityQueue.updateColor(updateVIN);							
						}else if(desiredSelection == 4){
							break;
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
			}else if(selection == 4){	//REtrieve low price
				carTracker.mode = 0;
				Car retrievedCar = priorityQueue.retrieveLowestPrice();
				System.out.println("\nVIEWING LOWEST PRICE CAR:\n");
				System.out.println("VIN: " + retrievedCar.getVIN());
				System.out.println("MAKE: " + retrievedCar.getMake());
				System.out.println("MODEL: " + retrievedCar.getModel());
				System.out.println("PRICE: " + currencyFormatter.format(retrievedCar.getPrice()));
				System.out.println("MILEAGE: " + retrievedCar.getMileage());
				System.out.println("COLOR: " + retrievedCar.getColor());
				
			}else if(selection == 5){	//retrieve low mileage
				carTracker.mode = 1;
				Car retrievedCar = priorityQueue.retrieveLowestMileage();
				System.out.println("\nVIEWING LOWEST MILEAGE CAR:\n");
				System.out.println("VIN: " + retrievedCar.getVIN());
				System.out.println("MAKE: " + retrievedCar.getMake());
				System.out.println("MODEL: " + retrievedCar.getModel());
				System.out.println("PRICE: " + currencyFormatter.format(retrievedCar.getPrice()));
				System.out.println("MILEAGE: " + retrievedCar.getMileage());
				System.out.println("COLOR: " + retrievedCar.getColor());
				
			}else if(selection == 6){	//retrieve low price specific
				carTracker.mode = 0;
				System.out.println("\nRETRIEVING LOWEST PRICE SPECIFIC\n");
				System.out.print("\nPlease enter make: ");
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
				
			}else if(selection == 7){	//retrieve low mileage specific
				carTracker.mode = 1;
				System.out.println("\nRETRIEVING LOWEST MILEAGE SPECIFIC\n");
				System.out.print("\nPlease enter make: ");
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
			}else if(selection == 8){
				System.exit(0);
			}else{
				System.out.println("\nERROR: Please enter a valid choice [1-8]...\n");
			}
			
			
		}
	}
	
	//Assumes VINToCheck is already uppercase
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
}


//TO DO [BUG FIX]