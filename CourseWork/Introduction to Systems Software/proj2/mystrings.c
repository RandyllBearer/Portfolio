//Randyll Bearer: Proj2_MyStrings: rlb97@pitt.edu

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

//Follows a linked list structure, each character loaded from binary is a node, if the length of the list is > 4 then print it out. A linked list avoids problems with a static buffer.

struct node{
	char character;
	struct node* next;

};

int main(int argc, char* argv[]){
	//variable declaration
	struct node* head;	//Just preparation for our linked list
	struct node* current;
	FILE* file = NULL;	
	char readChar;		//The character we just read in from file	
	int count = 0;		//how many valid characters are in a string

	//open file
	file = fopen(argv[1], "rb");	//read binary option
	if(file == NULL){
		printf("ERROR: The file %s either could not be opened or does not exist.\n",argv[1] );
		return 1;	//Return by error
	}

	//iterate through file, one byte at a time
	while(feof(file) == 0){	//while we have more bytes left
		fread(&readChar, sizeof(readChar), 1, file);	//Read in 1 element size 1 char long into readChar from file
		if(readChar == 9 || (readChar >= 32 && readChar <= 126)){ //check if the read char is in bounds
			if(count == 0){	//create the head
				head = malloc(sizeof(struct node));	//Must make sure to free these nodes later
				head->character = readChar;
				head->next = NULL;
				current = head;
				count += 1;
			}else{	//keep building on
				current->next = malloc(sizeof(struct node));	//MUST FREE THIS
				current = current->next;
				current->character = readChar;
				current->next = NULL;
				count += 1;
			}
		}else{	//readChar not a valid ASCII character, end of string
			if(count >= 4){	//Print valid strings of size > 4
				current = head;
				while(current != NULL){	//Loop through linked list
					printf("%c", current->character);
					fflush(stdout);	//we don't have a newline character, so have to force it
					head = current;
					current = current->next;
					free(head);
				}
				printf("\n");	//formatting purposes
			}else{	//free the linked list each time we print it
				if(count > 0){
					current = head;
					while(current != NULL){
						head = current;
						current = current->next;
						free(head);	
					}
				}
			}
			count = 0;	//reset for next string
		}
	}
	fclose(file);	
	return(0);
}
