//Randyll Bearer: Lab2b
#include <stdio.h>
#include <stdlib.h>

int* search(int* begin, int* end, int needle);

int main(int argc, char **argv){
	int num;
	int nums[10], i;
	int *found = NULL;

	if(argc != 2){
		printf("Usage: search <number>\n");
		return 1;
	}
	num = atoi(argv[1]);   //Create the array, shift 2^N left each binary
	for(i = 0; i < 10; i++){
		nums[i] = 1 << i;
	}
	found = search(nums, &nums[9], num);  //FUNCTION CALL (nums+&nums are both poinrters)
	if(found) {
		printf("Number %d found in index %d.\n", num, found - nums);
	}else{
		printf("Number %d was not found.\n", num);
	}
	return 0;
}

int* search(int* begin, int* end, int needle){
	// * begin is just the array pointer to [0], *end is the last [9], *needle is what we are looking for
	//STEP1: Find the middle of the array
	int bisectionAmount = (end - begin)/2;		//This will return in sizeof()
	int* bisection = begin + bisectionAmount;	//Begin + offset
		
	//STEP2: COMPARE TO NEEDLE
	if(*bisection == needle){
		return bisection;
	}else if(*bisection < needle){
		if(*begin == *end){
			return 0;
		}
		return search(bisection+1, end, needle);
	}else{
		if(*begin == *end){
			return 0;
		}
		return search(begin, bisection-1, needle);
	}

}
