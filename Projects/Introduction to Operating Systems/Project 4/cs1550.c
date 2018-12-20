/**
*	Randyll Bearer: rlb97@pitt.edu
*	Adapted from the skeleton code provided in the cs1550.c example which came with our FUSE installation.
*	Implements the required functions and leaves the others alone as specified in the project description
* 	Uses the FUSE file system.
**/

#define	FUSE_USE_VERSION 26

#include <fuse.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <fcntl.h>

//INCLUDES
#include<stdlib.h>

//size of a disk block
#define	BLOCK_SIZE 512

//Use 8.3 filenames
#define	MAX_FILENAME 8
#define	MAX_EXTENSION 3

//How many files can there be in one directory?
#define MAX_FILES_IN_DIR (BLOCK_SIZE - sizeof(int)) / ((MAX_FILENAME + 1) + (MAX_EXTENSION + 1) + sizeof(size_t) + sizeof(long))

//The attribute packed means to not align these things
struct cs1550_directory_entry{
	int nFiles;	//How many files are in this directory.
				//Needs to be less than MAX_FILES_IN_DIR

	struct cs1550_file_directory{
		char fname[MAX_FILENAME + 1];	//filename (plus space for nul)
		char fext[MAX_EXTENSION + 1];	//extension (plus space for nul)
		size_t fsize;					//file size
		long nStartBlock;				//where the first block is on disk
	} __attribute__((packed)) files[MAX_FILES_IN_DIR];	//There is an array of these

	//This is some space to get this to be exactly the size of the disk block.
	//Don't use it for anything.  
	char padding[BLOCK_SIZE - MAX_FILES_IN_DIR * sizeof(struct cs1550_file_directory) - sizeof(int)];
} ;

typedef struct cs1550_root_directory cs1550_root_directory;

#define MAX_DIRS_IN_ROOT (BLOCK_SIZE - sizeof(int)) / ((MAX_FILENAME + 1) + sizeof(long))

struct cs1550_root_directory{
	int nDirectories;	//How many subdirectories are in the root
						//Needs to be less than MAX_DIRS_IN_ROOT
	struct cs1550_directory{
		char dname[MAX_FILENAME + 1];	//directory name (plus space for nul)
		long nStartBlock;				//where the directory block is on disk
	} __attribute__((packed)) directories[MAX_DIRS_IN_ROOT];	//There is an array of these

	//This is some space to get this to be exactly the size of the disk block.
	//Don't use it for anything.  
	char padding[BLOCK_SIZE - MAX_DIRS_IN_ROOT * sizeof(struct cs1550_directory) - sizeof(int)];
} ;

typedef struct cs1550_directory_entry cs1550_directory_entry;

//How much data can one block hold?
#define	MAX_DATA_IN_BLOCK (BLOCK_SIZE)

struct cs1550_disk_block{
	//All of the space in the block can be used for actual data
	//storage.
	char data[MAX_DATA_IN_BLOCK];
};

typedef struct cs1550_disk_block cs1550_disk_block;

#define MAX_FAT_ENTRIES (BLOCK_SIZE/sizeof(short))

struct cs1550_file_alloc_table_block {
	short table[MAX_FAT_ENTRIES];
};

typedef struct cs1550_file_alloc_table_block cs1550_fat_block;

/*****************
*Helper Functions
*****************/

//Read in the specified block
static int readDisk(int block, void *dest){
	int result = 0;
	FILE *disk = fopen(".disk", "r+b");	//binary mode
	fseek(disk, (BLOCK_SIZE * block), SEEK_SET);
	result = fread(dest, BLOCK_SIZE, 1, disk);
	fclose(disk);
	return result;
}

//write to the specified block
static int writeDisk(int block, void *dest){
	int result = 0;
	FILE *disk = fopen(".disk", "r+b");	//binary mode
	fseek(disk, (BLOCK_SIZE * block), SEEK_SET);
	result = fwrite(dest, BLOCK_SIZE, 1, disk);
	fclose(disk);
	return result;
}

//writes to the File Allocation Table
static int writeFAT(cs1550_fat_block *FAT){
	int result = 0;
	FILE *disk = fopen(".disk", "r+b");	//binary mode
	fseek(disk, -BLOCK_SIZE, SEEK_END);	//FAT is stored at end of disk
	result = fwrite(FAT, BLOCK_SIZE, 1, disk);
	fclose(disk);
	return result;
}

//Initializes the File Allocation Table, set values to -1 to specify 'free'
static void initializeFAT(){
	FILE *disk = fopen(".disk", "r+b");	//binary mode
	
	cs1550_fat_block *FAT = malloc(sizeof(cs1550_fat_block));
	fseek(disk, -BLOCK_SIZE, SEEK_END);	//FAT initialized at end of disk as opposed to root which is initialized at start of disk
	fread(FAT, BLOCK_SIZE, 1, disk);

	int i;
	for(i = 0; i < MAX_FAT_ENTRIES; i++){
		FAT->table[i] = -1;
	}

	writeFAT(FAT);
	fclose(disk);
}

//fills the root with data from the first block on disk
static int getRoot(cs1550_root_directory *root){
	int result = 0;
	FILE *disk = fopen(".disk", "r+b");	//binary mode
	result = fread(root, BLOCK_SIZE, 1, disk);

	if(root -> nDirectories == 0){
		initializeFAT();
		writeDisk(0, root);
	}
	
	fclose(disk);
	return result;
}

//Updates the File Allocation Table with the value argument
static void updateFAT(int free_block, short value){
	FILE *disk = fopen(".disk", "r+b");
	
	cs1550_fat_block *FAT = malloc(sizeof(cs1550_fat_block));
	fseek(disk, -BLOCK_SIZE, SEEK_END);
	fread(FAT, BLOCK_SIZE, 1, disk);

	FAT->table[free_block] = value;

	writeFAT(FAT);
	fclose(disk);
}

//returns a block which is currently free, found using the File Allocation Table
static int getFreeBlock(){
	FILE *disk = fopen(".disk", "r+b");
	
	cs1550_fat_block *FAT = malloc(sizeof(cs1550_fat_block));
	fseek(disk, -BLOCK_SIZE, SEEK_END);
	fread(FAT, BLOCK_SIZE, 1, disk);

	int i;
	FAT->table[0] = -2; //-2: Used by root or subdirectory
	for(i = 1; i < MAX_FAT_ENTRIES; i++){
		if(FAT->table[i] == -1){	//-1: Free block
			return i;
		}
	}

	fclose(disk);
	return -1;
}

/*******************
*Required Functions
*******************/

/*
 * Called whenever the system wants to know the file attributes, including
 * simply whether the file exists or not. 
 *
 * man -s 2 stat will show the fields of a stat structure
 */
static int cs1550_getattr(const char *path, struct stat *stbuf){
	memset(stbuf, 0, sizeof(struct stat));	//clean out the stbuf
	cs1550_root_directory *root = malloc(sizeof(cs1550_root_directory));
	cs1550_directory_entry *subDirectory = malloc(sizeof(cs1550_directory_entry));
	getRoot(root);	//get the root data on disk
   
    int result = 0;
   
	//is the provided path the root directory?
	if (strcmp(path, "/") == 0){ 			//yes
		stbuf->st_mode = S_IFDIR | 0755;
		stbuf->st_nlink = 2;
	}else{									//no
		char *dir = malloc(MAX_FILENAME + 1);
		char *filename = malloc(MAX_FILENAME + 1);
		char *extension = malloc(MAX_EXTENSION + 1);
		dir = "";
		filename = "";
		extension = "";
		sscanf(path, "/s[^/]/&[^.].%s", dir, filename, extension);	//parse our path input

		int i;
		int foundDirectory = 0;
		int foundFile = 0;
		for(i = 0; i < root->nDirectories; i++){	//traverse all directories
			if(strcmp(dir, ((root->directories[i]).dname)) == 0){	//our directory matches an existing directory
				readDisk((root->directories[i]).nStartBlock, subDirectory);
				foundDirectory = 1;
				if(strcmp(extension, "") == 0){	//if we don't have an extension
					stbuf->st_mode = S_IFDIR | 0755;
					stbuf->st_nlink = 2;
				}else{
					int j;
					for(j = 0; j < subDirectory->nFiles; j++){	//traverse subdirectories for file
						if(strcmp(filename,((subDirectory->files[i]).fname)) == 0){	//found the file
							stbuf->st_mode = S_IFREG | 0666; 
							stbuf->st_nlink = 1; //file links
							stbuf->st_size = (subDirectory->files[j]).fsize; 
							foundFile = 1;
						}
					}
				}
			}
		}

		if(foundDirectory == 0){
			result = -ENOENT;
		}else if(foundDirectory == 1 && foundFile == 0){
			result = -ENOENT;
		}
		
		//Free everything
		free(dir);
		free(filename);
		free(extension);
		free(root);
		free(subDirectory);
	}
	return result;
}

/* 
 * Called whenever the contents of a directory are desired. Could be from an 'ls'
 * or could even be when a user hits TAB to do autocompletion
 */
static int cs1550_readdir(const char *path, void *buf, fuse_fill_dir_t filler,
			 off_t offset, struct fuse_file_info *fi){
	//Since we're building with -Wall (all warnings reported) we need
	//to "use" every parameter, so let's just cast them to void to
	//satisfy the compiler
	(void) offset;
	(void) fi;

	//changed as instructed in example code
	if (strcmp(path, "/") == 0)
	return -ENOENT;

	char *dir = malloc(MAX_FILENAME + 1);
	char *filename = malloc(MAX_FILENAME + 1);
	char *extension = malloc(MAX_EXTENSION + 1);
	dir = "";
	filename = "";
	extension = "";
	sscanf(path, "/s[^/]/&[^.].%s", dir, filename, extension);	//parse our provided path

	if(strcmp(extension, "") != 0){	//if our path doesn't have a file extension, might be a problem
		return -ENOENT;
	}

	cs1550_root_directory *root = malloc(sizeof(cs1550_root_directory));
	cs1550_directory_entry *subDirectory = malloc(sizeof(cs1550_directory_entry));
	getRoot(root);
	
	int i;
	int found = 0;
	for(i = 0; i < root->nDirectories; i++){
		if(strcmp(dir, ((root->directories[i]).dname)) == 0){
			readDisk((root->directories[i]).nStartBlock, subDirectory);
			found = 1;
		}
	}

	if(found == 0){	//if we couldn't find a match in our directories, that's a problem
		return -ENOENT;
	}

	//the filler function allows us to add entries to the listing
	//read the fuse.h file for a description (in the ../include dir)
	filler(buf, ".", NULL, 0);
	filler(buf, "..", NULL, 0);
	for(i=0; i<subDirectory->nFiles; i++){
		filler(buf, subDirectory->files[i].fname, NULL, 0);
	}

	//Free everything
	free(dir);
	free(filename);
	free(extension);
	free(root);
	free(subDirectory);
	return 0;
}


/* 
 * Creates a directory. We can ignore mode since we're not dealing with
 * permissions, as long as getattr returns appropriate ones for us.
 */
static int cs1550_mkdir(const char *path, mode_t mode){
	//"use" these like told to in example
	(void) path;
	(void) mode;

	char *dir = malloc(MAX_FILENAME + 1);
	char *filename = malloc(MAX_FILENAME + 1);
	char *extension = malloc(MAX_EXTENSION + 1);
	dir = "";
	filename = "";
	extension = "";
	sscanf(path, "/s[^/]/&[^.].%s", dir, filename, extension);	//parse our provided path
	
	cs1550_root_directory *root = malloc(sizeof(cs1550_root_directory));
	getRoot(root);

	//Error Handling
	if(strlen(dir) > MAX_FILENAME){	//dir can't be longer than the maximum allowed filename
		return -ENAMETOOLONG;
	}else if(strcmp(extension, "") != 0){ //if we don't have a file extension, that's bad
		return -EPERM;
	}

	int i;
	for(i = 0; i < root->nDirectories; i++){	//traverse the root level directories
		if(strcmp(dir, ((root->directories[i]).dname)) == 0){	//found a match
			return -EEXIST;	//can't have two directories existing with the same name
		}
	}

	if(root->nDirectories = MAX_DIRS_IN_ROOT){	//have a limited amount of directories
		return -EPERM;
	}

	int freeBlock = getFreeBlock();
	if(freeBlock == -1){
		return -EPERM;
	}

	//Traverse directories
	for(i = 0; i < strlen(dir); i++){
		root->directories[root->nDirectories].dname[i] = dir[i];
	}
	root->directories[root->nDirectories].dname[i] = '\0';
	root->directories[root->nDirectories].nStartBlock = freeBlock;
	updateFAT(freeBlock, -2);
	root->nDirectories++;
	writeDisk(0, root);

	//Free everything
	free(dir);
	free(filename);
	free(extension);
	free(root);

	return 0;
}

/* 
 * Does the actual creation of a file. Mode and dev can be ignored.
 * 
 */
static int cs1550_mknod(const char *path, mode_t mode, dev_t dev){
	//"Use" these like instructed
	(void) mode;
	(void) dev;

	char *dir = malloc(MAX_FILENAME + 1);
	char *filename = malloc(MAX_FILENAME + 1);
	char *extension = malloc(MAX_EXTENSION + 1);
	dir = "";
	filename = "";
	extension = "";
	sscanf(path, "/s[^/]/&[^.].%s", dir, filename, extension);	//parse our provided string

	if(strlen(filename) > MAX_FILENAME || strlen(extension) > MAX_EXTENSION){	//filenames/file extensions are limted
		return -ENAMETOOLONG;
	}
	
	if (strcmp(path, "/") == 0){	//Cannot create files in our root directory, not allowed
		return -EPERM;
	}

	cs1550_root_directory *root = malloc(sizeof(cs1550_root_directory));
	cs1550_directory_entry *subDirectory = malloc(sizeof(cs1550_directory_entry));
	getRoot(root);

	//Traverse the directories to find a match
	int i;
	int j;
	for(i = 0; i < root->nDirectories; i++){
		if(strcmp(dir, ((root->directories[i]).dname)) == 0){	//match found
			readDisk((root->directories[i]).nStartBlock, subDirectory);
			for(j = 0; j < subDirectory->nFiles; j++){	//traverse files
				if(strcmp(filename,((subDirectory->files[i]).fname)) == 0){
					return -EEXIST;
				}
			}
		}
	}

	//Update the director and file allocation table
	int freeBlock = getFreeBlock();
	if(freeBlock == -1){
		return -EPERM;
	}
	
	for(i=0; i<strlen(filename); i++){
		subDirectory->files[subDirectory->nFiles].fname[i] = filename[i];
	}
	subDirectory->files[subDirectory->nFiles].fname[i] = '\0';	//add terminator to string
	
	for(i = 0; i < strlen(extension); i++){
		subDirectory->files[subDirectory->nFiles].fext[i] = extension[i];
	}
	subDirectory->files[subDirectory->nFiles].fext[i] = '\0';	//add terminator to string

	subDirectory->files[subDirectory->nFiles].nStartBlock = freeBlock;
	updateFAT(freeBlock, -2);
	subDirectory->files[subDirectory->nFiles].fsize = 0;
	subDirectory->nFiles++;
	writeDisk(freeBlock, subDirectory);
	writeDisk(0, root);

	//Free everything
	free(dir);
	free(filename);
	free(extension);
	free(root);
	free(subDirectory);

	return 0;
}

/* 
 * Read size bytes from file into buf starting from offset
 *
 */
static int cs1550_read(const char *path, char *buf, size_t size, off_t offset,
			  struct fuse_file_info *fi){
	//"Use" these to avoid compiling errors as instructed
	(void) path;
	(void) buf;
	(void) offset;
	(void) fi;

	int dirBlock;
	int fileNum;
	int fileBlock;
	int fileSize;
	
	char *dir = malloc(MAX_FILENAME + 1);
	char *filename = malloc(MAX_FILENAME + 1);
	char *extension = malloc(MAX_EXTENSION + 1);
	dir = "";
	filename = "";
	extension = "";
	sscanf(path, "/s[^/]/&[^.].%s", dir, filename, extension);	//Parse our provided strings
	
	struct stat *stbuf = malloc(sizeof(struct stat));
	
	//Error Handle
	if(strcmp(extension, "") == 0){	//we need a file extension
		return -EISDIR;
	}
	
	if(size <= 0){	//can't read a negative amount
		return -EPERM;
	}
	
	if(cs1550_getattr(path, stbuf) !=0){	//cs1550_getattr will check for both dir and file
		return -ENOENT;
	}

	cs1550_root_directory *root = malloc(sizeof(cs1550_root_directory));
	cs1550_directory_entry *subDirectory = malloc(sizeof(cs1550_directory_entry));
	getRoot(root);
	
	//traverse directories to find correct file
	int found = 0;
	int i;
	for(i = 0; i < root->nDirectories; i++){
		if(strcmp(dir, ((root->directories[i]).dname)) == 0){
			dirBlock = root->directories[i].nStartBlock;
			readDisk((root->directories[i]).nStartBlock, subDirectory);
			int j;
			for(j = 0; j < subDirectory->nFiles; j++){
				if(strcmp(filename,((subDirectory->files[i]).fname)) == 0){
					fileNum = j;
					found = 1;
				}
			}
		}
	}

	if(found == 0){	//We need to find the file in order to read it, error
		return -EISDIR;
	}

	fileBlock = subDirectory->files[fileNum].nStartBlock;
	fileSize = subDirectory->files[fileNum].fsize;

	FILE *disk = fopen(".disk", "r+b");
	cs1550_fat_block *FAT = malloc(sizeof(cs1550_fat_block));

	fseek(disk, -BLOCK_SIZE, SEEK_END);
	fread(FAT, BLOCK_SIZE, 1, disk);
	fclose(disk);

	int offsetUsed;
	if(offset >= MAX_DATA_IN_BLOCK){	//If we need to cover multiple blocks
		int blocks = offset / MAX_DATA_IN_BLOCK;
		offsetUsed = offset;

		for(i = 0; i < blocks; i++){
			fileBlock = FAT->table[fileBlock];
			offsetUsed -= MAX_DATA_IN_BLOCK;
		}
	}

	cs1550_disk_block *temp = malloc(sizeof(cs1550_disk_block));
	readDisk(fileBlock, temp);

	int j;
	for(i = offsetUsed, j = 0; j < size; i++, j++){
		buf[j] = temp->data[i];

		if(offsetUsed == MAX_DATA_IN_BLOCK && FAT->table[fileBlock] != -3){
			fileBlock = FAT->table[fileBlock];
			readDisk(fileBlock, temp);
			offsetUsed = 0;
		}
	}
	
	//Free everything
	free(dir);
	free(filename);
	free(extension);
	free(root);
	free(subDirectory);
	free(temp);

	return size;
}

/* 
 * Write size bytes from buf into file starting from offset
 */
static int cs1550_write(const char *path, const char *buf, size_t size, 
			  off_t offset, struct fuse_file_info *fi){
	//"Use" these somehow like instructed
	(void) path;
	(void) buf;
	(void) offset;
	(void) fi;

	int dirBlock;
	int fileNum;
	int fileBlock;
	int fileSize;
	int offsetUsed;
	int freeBlock;
	
	char *dir = malloc(MAX_FILENAME + 1);
	char *filename = malloc(MAX_FILENAME + 1);
	char *extension = malloc(MAX_EXTENSION + 1);
	dir = "";
	filename = "";
	extension = "";
	sscanf(path, "/s[^/]/&[^.].%s", dir, filename, extension);	//Parse our input path
	
	//Error Handling
	struct stat *stbuf = malloc(sizeof(struct stat));
	if(cs1550_getattr(path, stbuf) !=0){	//cs1550_getattr will check for both dir and file
		return -ENOENT;
	}
	
	if(size <= 0){	//can't write a negative amount
		return -EPERM;
	}

	cs1550_root_directory *root = malloc(sizeof(cs1550_root_directory));
	cs1550_directory_entry *subDirectory = malloc(sizeof(cs1550_directory_entry));
	getRoot(root);

	
	int i;
	for(i = 0; i < root->nDirectories; i++){
		if(strcmp(dir, ((root->directories[i]).dname)) == 0){
			dirBlock = root->directories[i].nStartBlock;
			readDisk((root->directories[i]).nStartBlock, subDirectory);
			int j;
			for(j = 0; j < subDirectory->nFiles; j++){
				if(strcmp(filename,((subDirectory->files[i]).fname)) == 0){
					fileNum = j;
				}
			}
		}
	}

	fileBlock = subDirectory->files[fileNum].nStartBlock;
	fileSize = subDirectory->files[fileNum].fsize;

	if(offset > fileSize){
		return -EFBIG;
	}

	FILE *disk = fopen(".disk", "r+b");
	cs1550_fat_block *FAT = malloc(sizeof(cs1550_fat_block));

	fseek(disk, -BLOCK_SIZE, SEEK_END);
	fread(FAT, BLOCK_SIZE, 1, disk);
	fclose(disk);
	
	if(offset >= MAX_DATA_IN_BLOCK){
		int blocks = offset / MAX_DATA_IN_BLOCK;
		offsetUsed = offset;
		
		for(i = 0; i < blocks; i++){
			fileBlock = FAT->table[fileBlock];
			offsetUsed -= MAX_DATA_IN_BLOCK;
		}
	}

	cs1550_disk_block *temp = malloc(sizeof(cs1550_disk_block));
	readDisk(fileBlock, temp);

	int j;
	for(i = offsetUsed, j = 0; j < size; i++, j++){
		temp->data[i] = buf[i];

		if(offsetUsed == MAX_DATA_IN_BLOCK && FAT->table[fileBlock] != -3){
			writeDisk(fileBlock, temp);
			fileBlock = FAT->table[fileBlock];
			readDisk(fileBlock, temp);
			offsetUsed = 0;
		}
		else if(offsetUsed == MAX_DATA_IN_BLOCK && FAT->table[fileBlock] == -3){
			freeBlock = getFreeBlock();
			FAT->table[fileBlock] = freeBlock;
			FAT->table[freeBlock] = -3;
			writeDisk(fileBlock, temp);
			readDisk(freeBlock, temp);
			fileBlock = freeBlock;
			offsetUsed = 0;
		}
	}

	writeDisk(fileBlock, temp);
	subDirectory->files[fileNum].fsize += size;
	writeDisk(dirBlock, subDirectory);
	writeDisk(0, root);

	//Free everything
	free(dir);
	free(filename);
	free(extension);
	free(root);
	free(subDirectory);
	free(temp);

	return size;
}

/******************************************************************************
 *
 *  DO NOT MODIFY ANYTHING BELOW THIS LINE
 *
 *****************************************************************************/

/* 
 * Removes a directory. Specified to leave unmodified by the project description
 */
static int cs1550_rmdir(const char *path){
	(void) path;
    return 0;
}
 
/*
* Deletes a file, Specified to leave unmodified by the project description
*/
static int cs1550_unlink(const char *path){
    (void) path;

    return 0;
}
 
/*
 * truncate is called when a new file is created (with a 0 size) or when an
 * existing file is made shorter. We're not handling deleting files or 
 * truncating existing ones, so all we need to do here is to initialize
 * the appropriate directory entry.
 *
 */
static int cs1550_truncate(const char *path, off_t size){
	(void) path;
	(void) size;

    return 0;
}


/* 
 * Called when we open a file
 *
 */
static int cs1550_open(const char *path, struct fuse_file_info *fi){
	(void) path;
	(void) fi;
    /*
        //if we can't find the desired file, return an error
        return -ENOENT;
    */

    //It's not really necessary for this project to anything in open

    /* We're not going to worry about permissions for this project, but 
	   if we were and we don't have them to the file we should return an error

        return -EACCES;
    */

    return 0; //success!
}

/*
 * Called when close is called on a file descriptor, but because it might
 * have been dup'ed, this isn't a guarantee we won't ever need the file 
 * again. For us, return success simply to avoid the unimplemented error
 * in the debug log.
 */
static int cs1550_flush (const char *path , struct fuse_file_info *fi){
	(void) path;
	(void) fi;

	return 0; //success!
}


//Remember to register our functions as syscalls for FUSE
static struct fuse_operations hello_oper = {
    .getattr	= cs1550_getattr,
    .readdir	= cs1550_readdir,
    .mkdir	= cs1550_mkdir,
	.rmdir = cs1550_rmdir,
    .read	= cs1550_read,
    .write	= cs1550_write,
	.mknod	= cs1550_mknod,
	.unlink = cs1550_unlink,
	.truncate = cs1550_truncate,
	.flush = cs1550_flush,
	.open	= cs1550_open,
};

//Don't change this.
int main(int argc, char *argv[]){
	return (argc, argv, &hello_oper, NULL);
}
