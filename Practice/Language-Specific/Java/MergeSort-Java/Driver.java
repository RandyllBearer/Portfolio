/*
* Driver.java by Randyll Bearer 2019
* This driver class implements a merge-sort function I programmed as practice
* and review. This implementation has a run-time of O(n log(n)).
*/

class Driver{
	
	/*
	* Iterates over both left and right subarrays, merging them into new order
	*/
	static void merge(int[] array, int[]leftArray, int[] rightArray, int left, int right){
		
		int i = 0, j = 0, k = 0; 
		while(i < left && j < right ){
			if(leftArray[i] <= rightArray[j] ){
				array[k++] = leftArray[i++];
			}else{
				array[k++] = rightArray[j++];
			}
		}
		
		while(i < left){
			array[k++] = leftArray[i++];
		}
		while(j < right){
			array[k++] = rightArray[j++];
		}
	}
	
	/*
	* Splits arrays/subarrays into two halves
	*/
	static void sort(int[] array, int length){
		if(length < 2){	//1 element already sorted
			return;
		}
		
		//Find middle point of array
		int middle = length/2;
		
		//Create temp arrays of left and right halves
		int[] leftArray = new int[middle];
		int[] rightArray = new int[length - middle];
		
		for(int i = 0; i < middle; i++){
			leftArray[i] = array[i];
		}
		for(int i = middle; i<length; i++){
			rightArray[i - middle] = array[i];
		}
		
		//Sort first and second halves
		sort(leftArray, middle);
		sort(rightArray, length-middle);
		
		//Merge the sorted halves
		merge(array, leftArray, rightArray, middle, length-middle);
		
	}
	
	/*
	* Prints the array
	*/
	static void printArray(int array[]){
		int n = array.length;
		for (int i = 0; i < n; i++){
			System.out.print(array[i] + " ");
		}
		System.out.println();	//To start a new line
	}
	
	//Main driver method for the Merge-Sort
	public static void main(String[] args){
		int myArray[] = {12, 21, 15, 5, 4, 7};
		
		printArray(myArray);
		
		sort(myArray, myArray.length);
		
		printArray(myArray);
		
		
	}
	
}
//End of File