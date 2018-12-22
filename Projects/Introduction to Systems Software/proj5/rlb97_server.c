//Randyll Bearer   || rlb97@pitt.ed  ||  Project 5: Web Server
/**
 * As per requirements, this web server follows the TCP transmission protocol and I have tested its paralellism to work with 3 instances of telnet, WGET, and Links.
 * Main thread establishes a listener and after accept()'ing a connectin passes the communication off to the worker thread whose subroutine is communicate().
 * Main thread will act in an infinite while() loop, the FAQ for proj5 didn't specify the end condition so I left it to last for ever. the CTRL^C command will terminate it.
 *
 * Notes:
 * 1. I understand that multiple ports were allocated to us for this project, I implemented this server to use the first port allocated for me 51010.
 * 2. When using telnet, it will be mandatory to press "enter" twice in order for the web server to recieve two consecutive CRLF's, which will lead it to end recv()'ing.
 * 3. I'm confident that I've correctly synchronized the write to stats.txt and that no other data races have occurred while testing.
 * 4. I wasn't sure if a GET request for a file that doesn't exist should still be logged or not after the 404. I chose to log it.
 * */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <pthread.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <errno.h>
#include <time.h>

#define MYPORT 51010	//The first assigned Port Number to be used on Thoth machine

//MUTEXES for locking
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

//Holds the connected client's IP and Port # for use in logging purposes. Will be passed to worker thread.
struct clientInfo{
	int connectionFileDescriptor;
	char* ipAddress;		//Client IP Address, to be logged
	unsigned short clientPort;	//Client Port, to be logged
};

/**
 * Start Routine for the worker thread spawned from main and accept().
 * Handles the communication between client and server.
 * */
void *communicate(void* p){	//Worker thread will spawn here and handle communications
	struct clientInfo *connectionInfo = (struct clientInfo*)p;	//cast new pointer tp

	//Recv() Loop: Need to parse for two CRLFs, 
	int ret = 0;		//Length of recieved message
	char buffer[4096];	//Recieved Message
	int exists = 1;		//If 0, then the requested file does not exist in curr.Directory
	char tempBuffer[4096];	//To append to buffer
	int tempRet = 1;	//Length of tempBuffer
	int reps = 0;		//# times while() has incremented
	while(1){	//Keep recieving until the full message has been recieved
		if(reps == 0){	//If this is the first message we're recieving
			ret = recv(connectionInfo->connectionFileDescriptor,buffer,4096,0);
		}else{	//We need to append to the previous message we've recieved
			tempRet = recv(connectionInfo->connectionFileDescriptor,tempBuffer,4096,0);
			if(ret+tempRet < 4096){	//If this can be appended
				strcpy(&buffer[ret], tempBuffer);
				memset(tempBuffer,0,tempRet);	//Clean out tempBuffer for next possible message
				ret += tempRet;
			}else{	//we've recieved too much message
				printf("ERROR: Recieved a message larger than 4096 Bytes\n");
				close(connectionInfo->connectionFileDescriptor);
				free(connectionInfo);
				return;
			} 
		}
		if(ret == -1 || tempRet == -1){	//Failed to recieve anything
			perror("ERROR: ");
			close(connectionInfo->connectionFileDescriptor);
			free(connectionInfo);
			return;
		}
		else if(ret == 0 || tempRet == -0){	//Peer closed connection
			printf("ERROR: Peer closed connection\n");
			close(connectionInfo->connectionFileDescriptor);
			free(connectionInfo);
			return;
		}else if(strncmp(buffer, "GET ",4) == 0){	//Recieved an HTTP request
			//Need to parse for a double new line so we know that we recieved the full message
			//Check if the last 4 characters are /r/n/r/n
			if((int)buffer[ret-4] == 13 && (int)buffer[ret-3] == 10 && (int)buffer[ret-2] == 13 && (int)buffer[ret-1] == 10){
				break;
			}
			reps += 1;
		}else{	//Got something other than an HTTP GET request
			printf("ERROR: Recieved something other than an HTTP GET request\n");
			close(connectionInfo->connectionFileDescriptor);
			free(connectionInfo);
			return;
		}
	}
	//Parsing recieved HTTP Request
	char requestedFile[1000];	//can't know exactly how large these are, should be safe enough
	char hostAddress[1000];
	char httpCode[1000];
	int i;
	int j=0;
	for(i=5;i<4096;i++){	//parse buffer for requested filename
		if(buffer[i] == ' '){
			requestedFile[j] = '\0';	//terminate the string
			break;
		}else if(j == 1000){
			printf("ERROR: requested filename too large\n");
			close(connectionInfo->connectionFileDescriptor);
			free(connectionInfo);
			return;
		}else{
			requestedFile[j] = buffer[i];
		}
		j=j+1;
	}
	for(i=0;i<(4096);i++){	//parse buffer for the HTTPCode, then copy it
		if(i+4 >= 4095){
			printf("ERROR: Recieved GET request is not in proper format -1\n");
			close(connectionInfo->connectionFileDescriptor);
			free(connectionInfo);
			return;
		}
		if(buffer[i] == 'H' && buffer[i+1] == 'T' && buffer[i+2] == 'T' && buffer[i+3] == 'P' && buffer[i+4] == '/'){
			int l;
			for(l=0;l<1000;l++){
				if(buffer[i] == ' '||buffer[i]=='\n'||buffer[i]==10||buffer[i]==13){	//HTTP Code should be followed by 1 of these
					httpCode[l] = '\0';
					break;
				}else if(i >= 4095){
					printf("ERROR: httpCode was too large\n");
					close(connectionInfo->connectionFileDescriptor);
					free(connectionInfo);
				}else{
					httpCode[l] = buffer[i];
				}
				i++;
			}
			break;
		}
	}
	j=j+5;	//We know the first 4 characters are "GET " so skip them.
	for(i=j;i<(4096-j);i++){	//Parse the buffer for the Host: line
		if(i+4 >= 4095){	//if we are about to overrun our buffer
			printf("ERROR: Recieved GET request is is not in proper format 0\n");
			close(connectionInfo->connectionFileDescriptor);
			free(connectionInfo);
			return;
		}else{
			if(buffer[i] == 'H' && buffer[i+1] == 'o' && buffer[i+2] == 's' && buffer[i+3] == 't' && buffer[i+4] == ':'){//Found Host: line
				j = i+6;
				int k;
				for(k=0;k<1000;k++){	//Start reading host:line
					if(buffer[j] == ' '|| buffer[j] == '\n' || buffer[j] == 10 || buffer[j] == 13){
						hostAddress[k] = '\0';	//terminate the string
						break;
					}else if(k+j >= 4095){
						printf("ERROR: host address of requester is too larger\n");
						close(connectionInfo->connectionFileDescriptor);
						free(connectionInfo);
						return;
					}else{
						hostAddress[k] = buffer[j];
					}
					j=j+1;
				}
				break;
			}
		}
	}
	//Check to see if requested file exists in local directory. If yes: discover itslength. If no: Send a 404 not found message
	FILE *fp;
	int fileLength = 0;	//Content-Length of the file
	fp = fopen(requestedFile,"r");
	if(fp == NULL){	//file does not exist
		//Send back the 404 message, failure
		int bytesSentTotal = 0;
		int bytesSent = 0;
		char notFoundMessage[23] = "HTTP/1.1 404 Not Found\n";
		while(bytesSentTotal < strlen(notFoundMessage)){	//Ensure that the whole thing gets sent.
			bytesSent = send(connectionInfo->connectionFileDescriptor,(notFoundMessage+bytesSentTotal),(strlen(notFoundMessage)-bytesSentTotal),0);
			bytesSentTotal = bytesSentTotal + bytesSent;
		}
		//Log the GET request anyways into stats.txt
		FILE *statsFile;
		
		pthread_mutex_lock(&mutex);	//Synchronize access to file during Write operation
			statsFile = fopen("stats.txt", "a");
			fprintf(statsFile, "\nGET %s %s\nHost: %s\nClient: %s/%u\n",requestedFile,httpCode,hostAddress,connectionInfo->ipAddress,connectionInfo->clientPort);	//Appends to statsFile
			fclose(statsFile);
		pthread_mutex_unlock(&mutex);
		
		//Close
		close(connectionInfo->connectionFileDescriptor);
		close(fp);
		//Free
		free(connectionInfo);	

		return;
	}else{	//determine the content length of the file
		fseek(fp,0,SEEK_END);
		fileLength = ftell(fp);
		fseek(fp,0,SEEK_SET);
	}

	//Determine the date/time/etc.
	time_t currentTime;
	struct tm *localTime;
	currentTime = time(NULL);
	localTime = localtime(&currentTime);
	char stringTime[50];	//Holds the formatted time 
	strftime(stringTime, 50, "Date: %A, %d %B %Y %X %Z\n",localTime);

	//Compose the appropriate responseMessage to send back to client
	char responseMessage[4096+fileLength]; //Worst case: sizeof(recieved) + requested(filesize)
	strcpy(responseMessage, "HTTP/1.1 200 OK\n");	//Project5 document says we need this, standard protocol
	strcat(responseMessage, stringTime);
	char stringLength[fileLength];	//holds the filesize in characters
	sprintf(stringLength, "Content-Length: %d\n", fileLength);
	strcat(responseMessage, stringLength);
	strcat(responseMessage, "Connection: close\nContent-Type: text/html\n\n");

	//Read in from requested file to copy it to the composed message
	char fileBuffer[fileLength];	//Will hold what we read from the file
	fread(fileBuffer, 1, fileLength, fp);
	strcat(responseMessage, fileBuffer); //Response message complete

	//Send the composed message back to client
	int bytesSentTotal = 0;	//We need this to be == sizeof(responseMessage)
	int bytesSent = 0;	//this will be == to the amount of bytes sent for 1 send()
	while(bytesSentTotal < strlen(responseMessage)){
		bytesSent = send(connectionInfo->connectionFileDescriptor,(responseMessage + bytesSentTotal),(strlen(responseMessage) - bytesSentTotal),0);
		bytesSentTotal = bytesSentTotal + bytesSent; 
	}

	//Log the communication exchange in stats.txt
	FILE *statsFile;

	pthread_mutex_lock(&mutex);	//Synchronize access to the file
		statsFile = fopen("stats.txt", "a");	//appending
		fprintf(statsFile, "\nGET %s %s\nHost: %s\nClient: %s:%u\n",requestedFile,httpCode,hostAddress,connectionInfo->ipAddress,connectionInfo->clientPort);	//Should update our stats.txt
		fclose(statsFile);
	pthread_mutex_unlock(&mutex);	//Unlock

	//Close sockets and files
	close(connectionInfo->connectionFileDescriptor);	//SOCKET
	close(fp);	//FILE
	//Free clientInfo
	free(connectionInfo);	//the client info passed to thread

	return;
}

int main(){
  //Establish variables
  pthread_t thread;		//Worker thread, will be spawned whenever a connection is accepted
  int sfd;			//Socket File Descriptor
  struct sockaddr_in addr;	//Established in Header File
  int min = 51010;		//Assigned Port Values
  int max = 51019;
  int ret = 0;			//General return value
  int connfd = 0;		//Will hold the newly created socket file descriptor return by accept()
  int len = 0;  		//Will be size of addr
  unsigned short port;		//Client Port, to be logged
  char *ipstring;		//client IP, to be logged

  //Create/Bind/Listen Socket
  sfd = socket(PF_INET, SOCK_STREAM, 0);	//IPV4
  if(sfd == -1){
	perror("ERROR: ");
	return -1;
  }
  memset(&addr, 0, sizeof(addr));
  addr.sin_family = AF_INET;
  addr.sin_port = htons(MYPORT);	//Swap Endians
  addr.sin_addr.s_addr = INADDR_ANY;	//Automatically find IP/make every port available
  ret = bind(sfd,(struct sockaddr *)&addr, sizeof(addr));	//Bind()
  if(ret == -1){
	perror("ERROR: ");
	return -1;
  }
  ret = listen(sfd,128);	//Listen(), maximum backlog for thoth machine is 128
  if(ret == -1){
	perror("ERROR: ");
	return -1;
  }

  //Accept()
  struct clientInfo *toPass;
  while(1){
	//accept connection and record the client IP / Port
	len = sizeof(addr);
	connfd = accept(sfd, (struct sockaddr *)&addr, &len);
	if(connfd == -1){
		perror("ERROR: ");
		return -1;
	}
	port = ntohs(addr.sin_port);		//ntohs converts from network endian order to host endian order
	ipstring = inet_ntoa(addr.sin_addr);	//inet_ntoa returns dotted string notation of 32-bit IP address

	//Fill in the struct which will be passed to worker thread
	toPass = malloc(sizeof(struct clientInfo));
	if(toPass == NULL){
		printf("ERROR: Could not allocate memory for the struct to be passed to worker thread.\n");
		return 0;
	}
	toPass->connectionFileDescriptor = connfd;
	toPass->ipAddress = ipstring;
	toPass->clientPort = port;

	//Create Worker Thread
	pthread_create(&thread, NULL, communicate,(void *)toPass);	//Pass void* to allow for struct

  }

  close(sfd);	//Infinite Loop: won't reach but here as a reminder to close listener as well as connection socket.
  return 0;
}
