//Randyll Bearer - rlb97@pitt.edu - EXIF Viewer, Proj1.B

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct EXIF { //Size 20
	unsigned char startFile[2];	//offset[0] - length 2 	//Start of file [is this even jpg]
	unsigned char app1[2];		//offset[2] - length 2	//APP1 Marker [Verify its the correct constant]
	unsigned short app1Length;	//offset[4] - length 2
	unsigned char exifString[4];	//offset[6] - length 4 		
	unsigned char terminator[2];	//offset[10] - length 2	//Just a part of the format, 00 00
	unsigned char endianness[2];	//offset[12] - length 2 //IF MM, WE CAN JUST REPORT ERROR FOCUS ONLY ON INTEL
	unsigned short versionNumber;	//offset[14] - length 2	//Constant, should be 42
	unsigned int offset;		//offset[16] - length 4	//Offset of first IFD [12 + offset] //If ends in /0 no offset 
};

struct TIFF { //Size 12 
	unsigned char  tagIdentifier[2];	//offset[0] - length 2 bytes	//Type
	unsigned short  dataType;		//offset[2] - length 2	//String or Int
	unsigned int numberBytes;		//offset[4] - length 4	//How long is the date we're looking for
	unsigned int offsetData;		//offset[8] - length 4	//Where is that data at?

};

struct OUTPUT {
	char manufacturer[50];		//Manufacturer string
	char model[50];			//Camera model string
	unsigned int isoSpeed;		//Directly encoded int
	unsigned int width;		//Directly encoded int
	unsigned int height;		//Directly encoded int
	unsigned int exposureSpeed[2];	//two unsigned ints, need to display the ratio
	unsigned int fStop[2];		//same as above
	unsigned int focalLength[2];	//Same as above
	char date[50];			//ASCII String of Date Taken

};

//Step 1: Open File [binary mode] and Read in first 20 bytes, Load in EXIF Header Struct
//Step 2: Jump to EXIF Header's offset, and read in Count
//Step 3: Loop through Count IFF Tags until complete or hit 0x8769 tag identifier
//Step 4: Jump to second IFD block, read in another count
//Step 5: Loop through again
//Step 6: Print out output struct
main(int argc, char *argv[]){
	struct OUTPUT output;	//Where we will be storing the info we need to display
	
	//Step 1. Open the file and load in EXIF struct
	FILE *imageFile = NULL;
	imageFile = fopen(argv[1],"rb");
	if(imageFile == NULL){ //If it == null, then the file cannot be read
		printf("\nERROR: File '%s' could not be read/opened\n", argv[1]);
		return 1;	//Abnormal exit
	}
	unsigned char dumpArray[20];	//Loading in the first 20 here
	int amountRead = 0;
	amountRead = fread(dumpArray, 1 , 20, imageFile);	//Read in the EXIF Header
	/*	//TEST: Print out the individual bytes of the EXIF Header
	int i = 0;
	for(i =0; i<amountRead; i++){	
		printf("byte%d: %02X\n", i, dumpArray[i]);
	}
	*/
	if(amountRead != 20){	//If we didn't read in 20 bytes, there is a problem
		if(feof(imageFile)){
			printf("\nERROR: End of File\n");
			return 1;
		}
		else if(ferror(imageFile)) perror("\nERROR:");
		else {printf("\nERROR: PROBLEM READING IN 20 BYTES\n"); return 1; }
	}
	struct EXIF exif;	// Create the EXIF Header struct
	exif.startFile[0] = dumpArray[0]; exif.startFile[1] = dumpArray[1];	//JPEG Start of File marker
	if(exif.startFile[0] != 0xFF || exif.startFile[1] != 0xD8  ){	//If so, then we aren't dealing with a .JPG
		printf("\nERROR: File '%s' is not a .jpg file\n", argv[1]);
		return 1;
	}
	exif.app1[0] = dumpArray[2]; exif.app1[1] = dumpArray[3];	//APP1 marker
	if(exif.app1[0] != 0xFF || exif.app1[1] != 0xE1){	
		printf("\nERROR: File '%s' app1 Marker not properly formatted\n", argv[1]);
		return 1;
	}
	unsigned short temp = dumpArray[4];	//This part is always stored in big endian
	temp = temp << 8;		//Need to make room for [1]
	temp = temp | dumpArray[5];	//Should complete it
	exif.app1Length = temp;
	exif.exifString[0] = dumpArray[6]; exif.exifString[1] = dumpArray[7];	//EXIF header string
	exif.exifString[2] = dumpArray[8]; exif.exifString[3] = dumpArray[9]; 
	if(exif.exifString[0]!='E'&&exif.exifString[1]!='x'&&exif.exifString[2]!='i'&&exif.exifString[3]!='f'){
		printf("\nERROR: ExifString of '%s' not in correct format\n", argv[1]);
		return 1;
	}
	exif.terminator[0] = dumpArray[10]; exif.terminator[1] = dumpArray[11];	//NUL and Terimator string
	if(exif.terminator[0] != 0x00 || exif.terminator[1] != 0x00){	
		printf("\nERROR: NUL Terminator and zero byte improperly formatted in '%s'", argv[1]);
		return 1;
	}
	exif.endianness[0] = dumpArray[12]; exif.endianness[1] = dumpArray[13];	//Should be II, disregard anything else
	if(strcmp(exif.endianness, "II") != 0){
		printf("\nERROR: Endianness of '%s' is not II\n", argv[1]);
		return 1;
	}
	temp = dumpArray[15];	//Load in constant version number of EXIF header [should be 42]
	temp = temp << 8;
	temp = temp | dumpArray[14];
	exif.versionNumber = temp;
	if(exif.versionNumber != 42){	//Must verify proper formatting of EXIF Header
		printf("\nERROR: versionNumber != 42 in '%s'\n", argv[1]);
		return 1;
	}
	unsigned int temp2 = 0;	//Load in the offset held by EXIF header
	temp2 = dumpArray[19];
	temp2 = temp2 << 8;
	temp2 = temp2 | dumpArray[18];
	temp2 = temp2 << 8;
	temp2 = temp2 | dumpArray[17];
	temp2 = temp2 << 8;
	temp2 = temp2 | dumpArray[16];
	exif.offset = temp2;
	//Step 3. Jump to exifheader offset and load first iff tag, paying attention to its little endianness
	unsigned short count = 0;
	unsigned char buffer[12]; 	//used to hold IFF tag

	if(fseek(imageFile, 12 + exif.offset, SEEK_SET)!=0){	//Seek to the first offset pointed to by tiffHeader offset
		printf("\nERROR: Occurred when jumping to first IFF Offset\n");
		return 1;
	}
	amountRead = fread(&count, 2, 1, imageFile);	//Read in the count [how many tiff tags we will run into in this section]
	if(amountRead != 1){
		if(feof(imageFile)){
			printf("\nERROR: End of file\n");
		}else if(ferror(imageFile)) perror("\nERROR:");
		else{
			printf("\nERROR: Problem occured when reading TIFF count\n");
		}
		return 1;
	}
	int i = 0;
	int reachedSecondBlock = 0;		//If we hit a tag with 0x8769
	unsigned int currentFileOffset = 20;	//Where we jump back to
	struct TIFF tiff;		//Where we will be storing the 12 bytes of data
	for(i = 0; i < count; i++){	//Loop through the file count times
		amountRead = fread(buffer, 1, 12, imageFile);	//Buffer[] now holds one tag data 	
		if(amountRead != 12){	
			if(feof(imageFile)){
				printf("\nERROR: Reached end of file while reading IFF tag\n");
			}else if(ferror(imageFile)) perror("\nERROR:");
			else{
				printf("\nERROR: Not correctly reading in 12 byte IFF TAG\n");
			}			
			return 1;
		}
		/* //TEST: Prints out each individiual byte of each tag
		int j = 0;
		for(j = 0; j < amountRead; j++){
			printf("Byte%d: %02X\n", j, buffer[j]);
		}
		*/
		//Load in the TIFF Struct	
		tiff.tagIdentifier[0] = buffer[1]; tiff.tagIdentifier[1] = buffer[0];	//LittleEndianness
		temp = buffer[3];	//Load in tiff datatype
		temp = temp << 8;
		temp = temp | buffer[2];
		tiff.dataType = temp;
		temp2 = buffer[7];	//load in numberBytes [Little endian]
		temp2 = temp2 << 8;
		temp2 = temp2 | buffer[6];
		temp2 = temp2 << 8;
		temp2 = temp2 | buffer[5];
		temp2 = temp2 << 8;
		temp2 = temp2 | buffer[4];
		tiff.numberBytes = temp2;
		temp2 = buffer[11];	//load in offsetData [Little endian]
		temp2 = temp2 << 8;
		temp2 = temp2 | buffer[10];
		temp2 = temp2 << 8;
		temp2 = temp2 | buffer[9];
		temp2 = temp2 << 8;
		temp2 = temp2 | buffer[8];
		tiff.offsetData = temp2;	//Keep in mind this isnt a guaranteed offset [if data can be held in 4 bytes]
		//Start parsing TIFF info
		currentFileOffset = ftell(imageFile);	//Will need to return to this offset for type1 and type2
		if(tiff.tagIdentifier[0] == 0x01 && tiff.tagIdentifier[1] == 0x0F && tiff.dataType == 2){ //Type 1
			//ascii string MANUFACTURER	;
			if(fseek(imageFile,12+tiff.offsetData,SEEK_SET) != 0){	//seek to where offset points
				printf("\nERROR: Occurred when attempting to reach IFF manufacturer Offset\n");
				return 1;
			}
			amountRead = fread(output.manufacturer, 1, tiff.numberBytes, imageFile); //read into output
			if(amountRead != tiff.numberBytes){	//if we couldn't read in as much as we should've
				if(feof(imageFile)){
					printf("\nERROR: Cannot read in manufacturer string due to EoF\n");
				}else if(ferror(imageFile)) perror("\nERROR:");
				else{
					printf("\nERROR: Cannot read in manufacturer string\n");
				}
				return 1;
			}
			if(fseek(imageFile, currentFileOffset,SEEK_SET) !=0){	//Seek back to original IFF position
				printf("\nERROR: Occurred when attempting return from IFF Offset\n");
				return 1;
			}						
		}else if(tiff.tagIdentifier[0] == 0x01 && tiff.tagIdentifier[1] == 0x10 && tiff.dataType == 2){	//Type 2
			//ascii string CAMERA MODEL
			if(fseek(imageFile, 12+tiff.offsetData, SEEK_SET) != 0){	//Seek to data at offset
				printf("\nERROR: Occurred when attempting to reach IFF camera model Offset\n");
				return 1;
			}			
			amountRead = fread(output.model, 1, tiff.numberBytes, imageFile);	//read into output.model
			if(amountRead != tiff.numberBytes){
				if(feof(imageFile)){
					printf("\nERROR: Cannot read in model string due to EoF\n");
				}else if(ferror(imageFile)) perror("\nERROR:");
				else{
					printf("\nERROR: Cannot read in model string \n");
				}
				return 1;
			}
			if(fseek(imageFile, currentFileOffset,SEEK_SET) != 0){	//Jump back to previous file position
				printf("\nERROR: Occurred when attempting to return from model data\n");
				return 1;
			}
		}else if(tiff.tagIdentifier[0] == 0x87 && tiff.tagIdentifier[1] == 0x69 && tiff.dataType == 4){	//Type 3
			//32-bit integer sub block address, we can leave this section and start a new loop
			reachedSecondBlock = 1;	//Necessary due to iterative nature of this program
			break;
		}
	}
	//Loop through second block
	if(reachedSecondBlock == 1){
		if(fseek(imageFile, 12+tiff.offsetData, SEEK_SET) != 0){
			printf("\nERROR: Cannot seek to second IFD block\n");
			return 1;
		}
		amountRead = fread(&count, 2, 1, imageFile);	//Read in how many IFF tags there will be
		if(amountRead != 1){
			if(feof(imageFile)){
				printf("\nERROR: Cannot read in second block count\n");
			}else if(ferror(imageFile)) perror("\nERROR:");
			else{
				printf("\nERROR: Cannot read in second block count\n");
			}
			return 1;
		}	//now have the amount of tags in second block, loop through them
		for(i=0; i<count; i++){
			amountRead = fread(buffer, 1, 12, imageFile);	//Read in a tag
			if(amountRead != 12){	//If we couldnt read in all 12 bytes, there is a problem
				if(feof(imageFile)){
					printf("\nERROR: Cannot read in tag of second block due to EoF\n");
				}else if(ferror(imageFile)) perror("\nERROR:");
				else{
					printf("\nERROR: Cannot read in second block tag\n");
				}
				return 1;
			}
			/*  TEST: Prints out the individual bytes of each tag in the second IFD Block
   			int j = 0;
			for(j=0;j<amountRead;j++){
				printf("B2:Byte %d: %02X\n", j, buffer[j]);	
			}
			*/
			//Start parsing the four sections of an IFF tag
			tiff.tagIdentifier[0] = buffer[1]; tiff.tagIdentifier[1] = buffer[0];	//Little Endian
			temp = buffer[3];	//data type
			temp = temp << 8;
			temp = temp | buffer[2];
			tiff.dataType = temp;	
			temp2 = buffer[7];	//numberBytes [how many elements will be found]
			temp2 = temp2 << 8;
			temp2 = temp2 | buffer[6];
			temp2 = temp2 << 8;
			temp2 = temp2 | buffer[5];
			temp2 = temp2 << 8;
			temp2 = temp2 | buffer[4];
			tiff.numberBytes = temp2;
			temp2 = buffer[11];	//Stored data in last 4 bits (sometimes offset, sometimes data itself)
			temp2 = temp2 << 8;
			temp2 = temp2 | buffer[10];
			temp2 = temp2 << 8;
			temp2 = temp2 | buffer[9];
			temp2 = temp2 << 8;
			temp2 = temp2 | buffer[8];			
			tiff.offsetData = temp2;
			//Start parsing TIFF info
			currentFileOffset = ftell(imageFile);	//Save our spot to jump back to
			if(tiff.tagIdentifier[0] == 0xA0 && tiff.tagIdentifier[1] == 0x02 && tiff.dataType == 4){ //Width in pixels
				//Directly encoded in last 4 bits
				output.width = tiff.offsetData;				
			}else if(tiff.tagIdentifier[0] == 0xA0 && tiff.tagIdentifier[1] == 0x03 && tiff.dataType == 4){ //Height pixels
				//Directly encoded in last 4 bits
				output.height = tiff.offsetData;
			}else if(tiff.tagIdentifier[0] == 0x88 && tiff.tagIdentifier[1] == 0x27 && tiff.dataType == 3){ //ISO
				//Directly encoded in last 4 bits
				output.isoSpeed = tiff.offsetData;
			}else if(tiff.tagIdentifier[0] == 0x82 && tiff.tagIdentifier[1] == 0x9A && tiff.dataType == 5){ //Exposure
				//Must Seek at offset
 				if(fseek(imageFile, 12+tiff.offsetData, SEEK_SET) != 0){	//Jump to data location
					printf("\nERROR: Could not seek to Exposure data\n");
					return 1;
				}
				amountRead = fread(output.exposureSpeed, 4, 2, imageFile);	//Read in exposure speed
				if(amountRead != 2){
					if(feof(imageFile)){
						printf("\nERROR: EoF when reading exposureSpeed\n");
					}else if(ferror(imageFile)) perror("\nERROR:");
					else{
						printf("\nERROR: Could not read exposure speed\n");
					}
					return 1;
				}
				if(fseek(imageFile, currentFileOffset, SEEK_SET) != 0){ 	//Jump back to previous file location
					printf("\nERROR: Could not seek back from Exposure data\n");
					return 1;
				}
			}else if(tiff.tagIdentifier[0] == 0x82 && tiff.tagIdentifier[1] == 0x9D && tiff.dataType == 5){ //F-Stop
				//Must Seek at offset
				if(fseek(imageFile, 12+tiff.offsetData, SEEK_SET) != 0){	//Jump to data offset
					printf("\nERROR: Could not seek to fStop data\n");
					return 1;
				}
				amountRead = fread(output.fStop, 4, 2, imageFile);		//Read in F-Stop
				if(amountRead != 2){
					if(feof(imageFile)){
						printf("\nERROR: EoF when reading fStop\n");
					}else if(ferror(imageFile)) perror("\nERROR:");
					else{
						printf("\nERROR: Could not read fStop data\n");
					}
					return 1;
				}
				if(fseek(imageFile, currentFileOffset, SEEK_SET) != 0){		//Jump back to array of IFF tags
					printf("\nERROR: Could not seek back from fStop data\n");
					return 1;
				}
			}else if(tiff.tagIdentifier[0] == 0x92 && tiff.tagIdentifier[1] == 0x0A && tiff.dataType == 5){ //Focal Length
				//Must Seek at offset
				if(fseek(imageFile, 12 + tiff.offsetData, SEEK_SET) != 0){	//Jump to data offset
					printf("\nERROR: Could not seek to Focal Length data\n");
					return 1;
				}
				amountRead = fread(output.focalLength, 4, 2, imageFile);	//Read in that data
				if(amountRead != 2){
					if(feof(imageFile)){
						printf("\nERROR: EoF when reading focalLength\n");
					}else if(ferror(imageFile)) perror("\nERROR:");
					else{
						printf("\nERROR: Could not read focalLength data\n");
					}
					return 1;
				}
				if(fseek(imageFile, currentFileOffset, SEEK_SET) != 0){		//Jump back to previous file location
					printf("\nERROR: Could not seek back from focalLength data\n");
					return 1;
				}
			}else if(tiff.tagIdentifier[0] == 0x90 && tiff.tagIdentifier[1] == 0x03 && tiff.dataType == 2){ //Date Taken
				//Must Seek at offset
				if(fseek(imageFile, 12 + tiff.offsetData, SEEK_SET) != 0){	//Jump to held data
					printf("\nERROR: Could not seek to Date Taken data\n");
					return 1;
				}
				amountRead = fread(output.date, 1, tiff.numberBytes, imageFile); //Read in held data
				if(amountRead != tiff.numberBytes){
					if(feof(imageFile)){
						printf("\nERROR: EoF whenr eading Date Taken\n");
					}else if(ferror(imageFile)) perror("\nERROR:");
					else{
						printf("\nERROR: Could not read Date Taken data\n");
					}
					return 1;
				}
				if(fseek(imageFile, currentFileOffset, SEEK_SET) != 0){		//Jump back to previous file location
					printf("\nERROR: Could not seek back from dateTaken data\n");
					return 1;
				}
			}
		}
	}else{
		printf("\nERROR: Never reached a 0x8769 Tag Identifier\n");
		return 1;
	}
	//Print out the output struct and close the file
	fclose(imageFile);	//No longer need to read anything
	printf("Manufacturer:\t%s\n", output.manufacturer);
	printf("Model:\t\t%s\n", output.model);
	printf("Exposure Time:\t%d/%d second\n", output.exposureSpeed[0], output.exposureSpeed[1]);
	printf("F-Stop:\t\tf/%g\n",(float) output.fStop[0] / output.fStop[1]);	//RATIO
	printf("ISO:\t\tISO %d\n", output.isoSpeed);
	printf("Date Taken:\t%s\n", output.date);
	printf("Focal Length:\t%g mm\n", (float)output.focalLength[0] /output.focalLength[1]);
	printf("Width:\t\t%d pixels\n", output.width);
	printf("Height:\t\t%d pixels\n", output.height);

}
