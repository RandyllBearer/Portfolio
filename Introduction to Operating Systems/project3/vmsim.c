//Randyll Bearer: RLB97: CS 1550: Project 3
//Adapated from the Project3 skeleton code posted by Mohammad @ https://github.com/hmofrad/CS1550/tree/master/project3
//
//NRU, RAND, and CLOCK work but OPT is not completed.
//Accepts command line arguments as specified by project description	
	
/**
* DEFINES
**/
#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>
#include <string.h>

#define PAGE_SIZE_4KB	4096
#define PT_SIZE_1MB		1048576	//Page Table size
#define PAGE_SIZE_BYTES	4
#define PTE_SIZE_BYTES	4

//First 20 bits tossed into pageTable
#define PTE32_INDEX(x)  (((x) >> 12) & 0xfffff)
#define FRAME_INDEX(x)  ( (x)        &   0xfff)

/**
* GLOBALS
**/
int numFrames;						//User defined, how many frames we have to allocate
int refreshRate;					//User defined, the amount of memory accesses before reference bit set back to 0
unsigned int *physicalFrames;
unsigned int *pageTable = NULL;		//Page Table
struct pte32 *pte = NULL;			//PTE
struct frameStruct *head = NULL;
int clockFrame = 0;					//used in getClock(). The current location of the "hand"

/**
*STRUCTS & FUNCTIONS
**/
//32-Bit memory frame data structure, linked-list
struct frameStruct{
	unsigned int frameNumber;		//# in the linked-list
	unsigned int *physicalAddress;	
	unsigned int virtualAddress;
	struct pte32 *ptePointer;
	struct frameStruct *next;		//Next frame in the linked-list
};

//32-Bit Root Level Page Table Entry, stored together as an array
struct pte32{
	unsigned int present;	//AKA valid: BIT 20
	unsigned int dirty;		//Has this entry been modified since being loaded into RAM? 1 if yes, 0 if no
	unsigned int referenced;	//Has this pte been referenced lately?
	unsigned int *physicalAddress;
};

//Page Fault Handler function, returns a PTE
struct frameStruct* handlePageFault(unsigned int faultAddress){
		pte = (struct pte32*) pageTable[PTE32_INDEX(faultAddress)];	//Grab the PTE from pageTable accessed by trace log
		
		if(!pte){	//if the PTE hasn't been accessed yet create it
			pte = malloc(sizeof(struct pte32));
			memset(pte, 0, sizeof(struct pte32));
			pte->present = 0;
			pte->physicalAddress = NULL;	//this pte has yet to be mapped to a physical address yet
			pageTable[PTE32_INDEX(faultAddress)] = (unsigned int) pte;			
		}
		
		return ((struct frameStruct *) pte);
}

//Not completed
int getOpt(){
	printf("getOPT() not completed\n");
	return 1;
}

//Clock page replacement algorithm. returns a frame number.
int getClock(){
	int frameToReplace = -1;
	struct frameStruct *hand = head;
	
	//Get the frame where clock hand is currently pointing
	while(1){
		if(hand->frameNumber == clockFrame){
			break;
		}else{
			if(hand->next){
				hand = hand->next;
			}else{
				hand = head;
			}
		}
	}
	
	//Move the clock hand resetting r=1 bits but stop to evict an r=0
	while(1){
		if(hand->ptePointer->referenced == 0){	//EVICT
			frameToReplace = hand->frameNumber;	//return the page to be evicted
			clockFrame = hand->frameNumber;	//update where our clock hand is pointing to so we can pick up from here
			break;
		}else if(hand->ptePointer->referenced == 1){ //CLEAR R AND ADVANCE HAND
			hand->ptePointer->referenced = 0;
			if(hand->frameNumber == (numFrames-1)){	//we can just use next
				hand = head;
			}else{	//if we have to move back to start of linked list
				hand = hand->next;
			}
			
		}
		
	}
	return frameToReplace;
}

//NRU page replacement algorithm. Returns a frame number
int getNRU(){
	int frameToReplace = -1;
	struct frameStruct *current = head;
	
	int class0 = -1;
	int class1 = -1;
	int class2 = -1;
	int class3 = -1;
	
	//Traverse the frames linked-list and separate by class [0-3] by looking at referenced and dirty bits.
	while(current->next){
		if(current->ptePointer->referenced == 0 && current->ptePointer->dirty == 0){
			class0 = current->frameNumber;
		}else if(current->ptePointer->referenced == 0 && current->ptePointer->dirty == 1){
			class1 = current->frameNumber;
		}else if(current->ptePointer->referenced == 1 && current->ptePointer->dirty == 0){
			class2 = current->frameNumber;
		}else{
			class3 = current->frameNumber;
		}	
		current = current->next;
	}
	
	//determine which frame to replace
	if(class0 != -1){
		frameToReplace = class0;
	}else if(class1 != -1){
		frameToReplace = class1;
	}else if(class2 != -1){
		frameToReplace = class2;
	}else{
		frameToReplace = class3;
	}
	
	return frameToReplace;
}

//Random page replacement algorithm. Returns a frame number
int getRandom(){
	int randomFrameNumber = rand() % numFrames;
	return randomFrameNumber;
}
	
/**
*MAIN
**/
int main(int argc, char *argv[]){
	//need to check command line arguments
	FILE *file;
	srand((unsigned)time(NULL));
	
	if( (argc==8) && (!strcmp(argv[1],"-n")) && (!strcmp(argv[3],"-a")) && (!strcmp(argv[5],"-r")) ) {	//NRU requires extra arguments
		numFrames = atoi(argv[2]);
		refreshRate = atoi(argv[6]);
		file = fopen(argv[7],"rb");
		if(!file){
			fprintf(stderr, "USAGE: %s -n <numframes> -a <fifo> <tracefile>\n", argv[0]);
			fprintf(stderr, "Error on opening the trace file\n");
			exit(1); 
		}
	}else if( (argc==6) && (!strcmp(argv[1],"-n")) && (!strcmp(argv[3],"-a")) ){ 	//OPT, CLOCK, RAND
		numFrames = atoi(argv[2]);
		file = fopen(argv[5],"rb");
		if(!file){
			fprintf(stderr, "USAGE: %s -n <numframes> -a <fifo> <tracefile>\n", argv[0]);
			fprintf(stderr, "Error on opening the trace file\n");
			exit(1); 
		}
	}else{
		fprintf(stderr, "USAGE: %s -n <numframes> -a <fifo> <tracefile>\n", argv[0]);
		exit(1); 	
	}
	
	//Calculate trace file length + read it in and write it into arrays
	unsigned int numAccesses = 0;	//# of Lines in trace file
	unsigned int addr = 0;
	unsigned char mode = NULL;
	
	//Calculate number of lines in the trace file
	while(fscanf(file, "%x %c", &addr, &mode) == 2){
		numAccesses++;
	}
	rewind(file);
	
	//Store the memory accesses
	unsigned int addressArray[numAccesses];	//List of addresses in hex
	unsigned char modeArray[numAccesses];	//R or W
	unsigned int i = 0;
	
	while(fscanf(file, "%x %c", &addressArray[i], &modeArray[i]) == 2){
		i++;
	}
	
	if(!fclose(file)){	//error catching on file handling
		;	//no-op
	}else{
		fprintf(stderr, "Error on closing the trace file\n");
		exit(1);
	}
	
	//Initialize the physical memory address space
	physicalFrames = malloc(PAGE_SIZE_4KB * numFrames);
	if(!physicalFrames){
		fprintf(stderr, "Error on mallocing physical frames\n");
		exit(1);
	}
	memset(physicalFrames, 0, PAGE_SIZE_4KB * numFrames);	//0 out the allocation
	
	//Create the first frame of frames linked list
	struct frameStruct *frame = malloc(sizeof(struct frameStruct));	//frame = the first frame linked node
	if(!frame){
		fprintf(stderr, "Error on mallocing frame struct\n");
		exit(1);
	}
	memset(frame, 0, sizeof(struct frameStruct));	//0 out the allocation of frames
	
	//struct frameStruct *head = frame;
	head = frame;
	struct frameStruct *curr;			
	
	//Initialize the page table
	pageTable = malloc(PT_SIZE_1MB * PTE_SIZE_BYTES);
	if(!pageTable){
		fprintf(stderr, "Error on mallocing page table\n");
	}
	memset(pageTable, 0, PT_SIZE_1MB * PTE_SIZE_BYTES);
	
	struct pte32 *newPTE = NULL;
	
	//Initialize the frames linked list
	for(i = 0; i<numFrames; i++){
		frame->frameNumber = i;
		frame->physicalAddress = physicalFrames + (i * PAGE_SIZE_4KB) / PAGE_SIZE_BYTES; //physicalFrames + offset
		frame->virtualAddress = 0;
		frame->ptePointer = NULL;
		frame->next = malloc(sizeof(struct frameStruct));
		frame = frame->next;
		memset(frame, 0, sizeof(struct frameStruct));
	}

	unsigned int addressToAccess = 0;			
	unsigned int previousFaultAddress = 0;
	unsigned char modeType = NULL;
	int hit = 0;
	int pageToEvict = 0;
	int numFaults = 0;
	int numWrites = 0;
	struct frameStruct *available;
	
	//Loop through all accessess in trace log
	for(i=0; i < numAccesses; i++){
		addressToAccess = addressArray[i];
		modeType = modeArray[i];
		hit = 0;
		available = NULL;	//points to the last available/empty frame in linked-list
		
		//perform the page walk for the address (check to see if it exists in page table)
		//returns either a new PTE or an already existing one
		newPTE = (struct pte32*) handlePageFault(addressToAccess);	//newPTE is the physical address
		
		
		//reset referenced bits to 0 if we've hit refresh points
		curr = head;
		if(strcmp(argv[4],"nru") == 0 && i%refreshRate == 0 ){
			while(curr->next){
				if(curr->ptePointer){
					curr->ptePointer->referenced = 0;
				}
				curr = curr->next;
			}
		}
			
		//Traverse the frames linked list to see if the requested page is present in the frames linked list
		curr = head;
		while(curr->next){	//Go through the entire Frame linked list
			//determine if a physical frame is available to be loaded into
			if(!(curr->ptePointer) ){
				available = curr;
			}
		
			if(curr->physicalAddress == newPTE->physicalAddress){	//a new PTE returned by handlePageFault will not have a physicalAddress
				if(newPTE->present){	//If we are trying to access a virtual address which is mapped to a valid physical address, clean hit
					curr->virtualAddress = addressToAccess;
					newPTE->referenced = 1;
					
					if(modeType == 'W'){
						newPTE->dirty = 1;
					}
					
					hit = 1;
					printf("%5d: PAGE HIT: address 0x%08x mode %c\n",i,addressToAccess,modeType);
				}
				break;
			}else{
				curr = curr->next;
			}
		}
		
		//If the requested page is not present in frames linked list but we have an available frame so no eviction
		if(hit == 0 && available){	
			//map the PTE to the Frame
			available->ptePointer = (struct pte32 *) newPTE;
			newPTE->physicalAddress = available->physicalAddress;
			newPTE->present = 1;
			available->virtualAddress = addressToAccess;
			newPTE->referenced = 1;
			
			if(modeType == 'W'){
				newPTE->dirty = 1;
			}
			hit = 1;
			numFaults++;
			printf("%5d: PAGE FAULT - NO EVICTION: address 0x%08x mode %c\n",i,addressToAccess,modeType);
		}
		
		//If the requested page is not present in the fully loaded frames linked list we need to evict and load it in
		if(hit == 0){
			//Determine which algorithm we are using and let that algorithm determine the frame pageToEvict
			if(strcmp(argv[4],"opt") == 0){
				pageToEvict = getOpt();
			}else if(strcmp(argv[4],"clock") == 0){
				pageToEvict = getClock();
			}else if(strcmp(argv[4],"nru") == 0 ){
				pageToEvict = getNRU();
			}else if(strcmp(argv[4],"rand") == 0){
				pageToEvict = getRandom();
			}
			
			//Traverse the frames linked list to find the victim frame and swap it out, set the present, referenced, and 
			//dirty bits and collect statistics
			curr = head;
			while(curr->next){
				if(curr->frameNumber == pageToEvict){
					previousFaultAddress = curr->virtualAddress;
					numFaults++;
					if(curr->ptePointer){
						curr->ptePointer->present = 0;
						if(curr->ptePointer->dirty){	//if we are doing dirty-eviction, write to disk
							curr->ptePointer->dirty = 0;
							numWrites++;
							printf("%5d: PAGE FAULT - DIRTY EVICTION: address 0x%08x mode %c\n",i,addressToAccess,modeType);
						}else{
							printf("%5d: PAGE FAULT - CLEAN EVICTION: address 0x%08x mode %c\n",i,addressToAccess,modeType);
						}
						
					}
					curr->ptePointer = (struct pte32 *) newPTE;
					newPTE->physicalAddress = curr->physicalAddress;
					newPTE->present = 1;
					curr->virtualAddress = addressToAccess;
					newPTE->referenced = 1;
					
					if(modeType == 'W'){
						newPTE->dirty = 1;
					}
					
					//break; COME BACK TO THIS
					
				}
				curr = curr->next;
			}
		}
	}
	
	//Output statistics
	printf("Algorithm:				%s\n", argv[4]);
	printf("Number of frames:		%d\n", numFrames);
	printf("Total memory accesses:	%d\n", i);
	printf("Total page faults:		%d\n", numFaults);
	printf("Total writes to disk:	%d\n", numWrites);
	
	//Free up mallocs
	free(pte);
	free(physicalFrames);
	free(frame);
	free(pageTable);
	
	return (0);
}