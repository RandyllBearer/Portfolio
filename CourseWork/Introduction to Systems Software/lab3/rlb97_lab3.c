//Randyll Bearer       rlb97@pitt.edu

#include <stdio.h>
#include <stdlib.h>

struct Node {
	int grade;
	struct Node *next;
};

int main(){

	//TEST
	printf("size of unsigned char = %d", sizeof(unsigned char));
	//END TEST

	int userInput = 0;
	struct Node *head = NULL;
	struct Node *current = NULL;
	int i = 0;	
	
	//Read in grades
	while(1){
		printf("\nPlease enter grade integer: ");
		int flag = scanf("%d", &userInput);
		if(flag != 1){
			printf("\nERROR: User value was not an int type\n");
			return (0);
		}else{
			if(userInput == -1){
				break;
			}
			if(i ==0){
				head = (struct Node*) malloc(sizeof(struct Node));
				head->grade = userInput;
				head->next = NULL;
				current = head;
			}else{
				current->next = malloc(sizeof(struct Node));
				current = current->next;
				current->grade = userInput;
				current->next = NULL;
			}
			i++;
		}
	}

	//Traverse list to calculate average
	current = head;		//go back to the start of the linked list
	double total = 0;	//cumulative total
	int count = 0;
	while(current != NULL){
		total += current->grade;
		count += 1;
		current = current->next;

	}
	printf("\nAverage = %f\n", total/count);

	//Free up the linked list
	current = head;
	while(current != NULL){		//Why does the lab say we have to go backwards?
		struct Node *temp = current;
		current = current->next;
		free(temp);
	}
	










}



