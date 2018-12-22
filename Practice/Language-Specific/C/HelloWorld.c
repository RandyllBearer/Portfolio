/*
	HelloWorld.java by Randyll Bearer
	Simple Program to test development environment and C/GCC install
*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

char* getPhrase(){
	
	char *message = malloc(sizeof(char) * 14);
	strcpy(message, "Hello, World!");
	return message;
	
}

int printPhrase(char *phrase){
	
	printf(phrase);
	
	return 1;
	
}

int main(){
	
	char *phrase = getPhrase();
	
	if(printPhrase(phrase) == 1 ){
		free(phrase);	//always free after malloc
		return 0;
	}else{
		printf("error");
		return 1;
	}
	
}
//End of File