#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>

int x;

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;	//Mutex Lock

//Increment X in a loop
void *foo(void *p){
	int i;
	for(i=0;i<100000;i++){
		pthread_mutex_lock(&mutex);
		x++;
		pthread_mutex_unlock(&mutex);
	}
	return NULL;
}

//Create two threads
int main(){
	pthread_t threadOne;
	int arg1 = 1;
	pthread_t threadTwo;
	int arg2 = 2;
	pthread_create(&threadOne, NULL, foo, (void *)&arg1);
	pthread_create(&threadTwo, NULL, foo, (void *)&arg2);
	pthread_join(threadOne, NULL);	//thread 2 with thread 1
	pthread_join(threadTwo, NULL);	//Thread 2/1 with main parent thread
	
	printf("x=%d\n",x);

	return 0;





}
