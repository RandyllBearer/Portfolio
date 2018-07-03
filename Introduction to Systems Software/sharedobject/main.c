#include <stdio.h>
#include <dlfcn.h>

int main(){
	void *handle;
	void (*my_str_copy)(char*, char*);
	char* (*my_str_cat)(char*, char*);	//dest,src
	char* error;
	handle = dlopen("mystr.so", RTLD_LAZY);
	if(!handle){	//handle == NULL
		printf("%s\n", dlerror());
		exit(1);
	}
	dlerror();	//Clear any existing error
	my_str_copy = dlsym(handle, "my_strcpy");	//Lookup the function by name
	if ((error = dlerror()) != NULL){
		printf("%s\n", error);
		exit(1);
	}
	//Lets test it
	//strcopy
	char dest[100];
	char src[] = "Hello World!";
	
	my_str_copy(dest, src);

	printf("%s\n", dest);
	//strcat
	my_str_cat = dlsym(handle, "my_strcat");	//lookup my_strcat in library denoted by handle
	if((error = dlerror()) != NULL){
		printf("%s\n", error);
		exit(1);
	}
	char addition[] = "This has been added!";
	char* result;
	result = my_str_cat(dest, addition);	//add addition[] to end of dest

	printf("%s\n", result);

	dlclose(handle); //close the library
	return 0;









}
