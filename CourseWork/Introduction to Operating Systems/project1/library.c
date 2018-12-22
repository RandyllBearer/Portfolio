//Randyll Bearer: CS1550: Project 1: Linux Calls
/* This program compiles when in same directory as iso_font.h and library.h
 * Error Handling done using write() to std error
 * Contains implementations for the listed required functions.
 * 	Also contains draw_char() and encodeColor() [which were suggested]
 */
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/ioctl.h>
#include <linux/fb.h>
#include <sys/mman.h>
#include <termios.h>
#include <fcntl.h>
#include "iso_font.h" //local directory
#include "library.h"

//Type def
typedef unsigned short color_t;

//Globals
int fileDescriptor;
int frameBufferSize;	//size we must dedicate for the mapping
short *frameBuffer;	//result of mmap()
int rowLength;		//needed for draw_pixel

/*
 * Opens the filedescriptor for the frame buffer
 * Learns information about the screen reslution and color depth
 * Disables the display of keypresses by flipping ECHO and ICANON bits
 */
void init_graphics(){
	int totalSize;	//vinfo.yres_virtual * finfo.line_length, size of mem allocation for mmap()
	struct fb_var_screeninfo vinfo;		//virtual resolution
	struct fb_fix_screeninfo finfo;		//color depth
	struct termios terminal_settings;	//For keypress display

	//OPEN FRAME BUFFER FILE
	fileDescriptor = open("/dev/fb0", O_RDWR);
	if(fileDescriptor == -1){
		//error catching
	}	

	//GET FRAME BUFFER INFO
	int errorFlag = 1;
	errorFlag = ioctl(fileDescriptor, FBIOGET_VSCREENINFO, &vinfo);
	if(errorFlag != 0){
		//error catching
	}
	errorFlag = ioctl(fileDescriptor, FBIOGET_FSCREENINFO, &finfo);
	if(errorFlag != 0){
		//error catching
	}
	
	rowLength = finfo.line_length;	//save this for draw_pixel
	frameBufferSize = vinfo.yres_virtual * rowLength;
	frameBuffer = mmap(NULL, frameBufferSize, PROT_READ|PROT_WRITE, MAP_SHARED, fileDescriptor, 0); //MAP_SHARED required in project description

	//DISABLE AUTOMATIC DISPLAY OF KEYPRESSES
	//tcgets, tcsets
	errorFlag = ioctl(1, TCGETS, &terminal_settings);	//Get info about terminal
	if(errorFlag != 0){
		//error catching
	}
	terminal_settings.c_lflag &= ~ECHO;	//if flag = 10011, and echo is bit 4, then and 11110, positive mask
	terminal_settings.c_lflag &= ~ICANON;
	errorFlag = ioctl(1, TCSETS, &terminal_settings);	//push our termios struct to kernel
	if(errorFlag != 0){
		//error catching
	}	

}

/*
 * munmap()s the framebuffer
 * closes framebuffer filedescriptor
 * Resets the changes made to termios by init_graphics()
 * Flips the ICANON and ECHO bits back to normal.
 */
void exit_graphics(){
	struct termios terminal_settings;	//required for changing ECHO and ICANON
	int errorFlag = 0;		

	//UNMAP
	errorFlag = munmap(frameBuffer, frameBufferSize);
	if(errorFlag == -1){
		//error catching
	}

	//CLOSE FRAMEBUFFER FD
	errorFlag = close(fileDescriptor);
	if(errorFlag == -1){
		//error catching
	}

	//RESET TERMINAL
	errorFlag = ioctl(1, TCGETS, &terminal_settings);	//load settings into struct
	if(errorFlag != 0){
		//error catching
	}
	terminal_settings.c_lflag |= ECHO;
	terminal_settings.c_lflag |= ICANON;
	errorFlag = ioctl(1, TCSETS, &terminal_settings);	//restore terminal settings
	if(errorFlag != 0){
		//error catching
	}
}

/*
 * Writes the ANSI escape code to clear screen
 */
void clear_screen(){
	int amountWritten = 0;	//should be 4, \033 is one octal
	amountWritten = write(1, "\033[2J", 4);
	if(amountWritten != 4){
		//error catching
	}
}

/*
 * Uses the select() linux call to detect any waiting input
 * read()s in any found input one character at a time and returns it
 */
char getkey(){
	fd_set read_set;	//required select() field
	struct timeval maxTimer;	//timeout timer for select()
	int selectResult;	//return value of select()
	int readResult;
	char readCharacter;	//what will be returned

	//PREPARE SELECT()
	maxTimer.tv_usec = 0;	//this level of granularity not necessary
	maxTimer.tv_sec = 15;	//Not specified but 15 seems fair, subject to change

	//PREPARE READ()
	FD_ZERO(&read_set);
	FD_SET(0, &read_set);	//0 = stdin

	//RUN SELECT()
	selectResult = select(1, &read_set, NULL, NULL, &maxTimer);	//only care about stdin field
	if(selectResult == 1){	//this is good
		readResult = read(0, &readCharacter, 1);
		if(readResult == -1){
			//error catching
		}
	}else if(selectResult == 0){	//select() timed out
		//error catching
	}else if(selectResult == -1){
		//error catching
	}
	
	return readCharacter;	//end
}

/*
 * Specify the time sleep_ms() will sleep
 */
void sleep_ms(long ms){
	int errorFlag;
	struct timespec sleepTimer;	//required for nanosleep()
	sleepTimer.tv_sec = 0;	//we will deal with nanoseconds not seconds
	sleepTimer.tv_nsec = ms * 1000000;	//as specified in proect description

	errorFlag = nanosleep(&sleepTimer, NULL);	//NULL because we don't care about reamining time
	if(errorFlag == -1){
		//error catching
	}
}

/*
 * file operation on the framebuffer at offset determined by (x,y)	
 * Changes the color of the pixel (i.e. draws)
 */
void draw_pixel(int x, int y, color_t color){
	short *tempPointer;	//we dont want to lose the location of our existing framebuffer pointer
	//our framebuffer is stored as a short, yet we are reciving integers. 32 vs 16 bit 
	//multiplication pointer arithmetic will skip by 32 bit not 16, so we want to cut this in half
	int targetOffset = x + (y*(rowLength/2));
	tempPointer = frameBuffer + targetOffset;
	*tempPointer = color;	//set the desired pixel color

}

/*
 * Iterative loops of draw_pixel() to draw a rectangle
 * Corners of the rectangle begin with (x1,y1), then others found
 * using the width and height.
 */
void draw_rect(int x1, int y1, int width, int height, color_t c){
	//we know our 4 corners, we can for loop to each one
	int i;
	// this will draw horizontal paralell lines, the upper and lower side of the rectangle.
	for(i=0;i<width;i++){
		draw_pixel(x1+i, y1, c);	//lower side
		draw_pixel(x1+i, y1+height, c);	//upper side
	}
	
	//this will draw the two vertical sides of the rectangle.
	for(i=0;i<height;i++){
		draw_pixel(x1, y1+i, c);	//left side
		draw_pixel(x1+width, y1+i, c);	//right side
	}
}

/*
 * Takes three fields for corrsesponding red, green, and blue values.
 * Bit Shifts and masks these together to form a single color_t
 */
color_t encodeColor(int red, int green, int blue){
	//red = upper 5
	//green = middle 6
	//blue = bottom 5
	color_t newColor = 0;
	
	//add red and shift
	red = red << 11;
	newColor = newColor + red;

	//add green and shift
	green = green & 0x003f;	//003f = 0000 0000 0011 1111
	green = green << 5;
	newColor = newColor + green;

	//ad blue and shift
	blue = blue & 0x001f;	//001f = 0000 0000 0001 1111
	newColor = newColor + blue;

	return newColor;
}

/*
 * Helper function for draw_text
 * draw_char recieves one character from draw_text then 
 * iteratively draw_pixel()s corresponding to the format
 * existing in iso_font.h
 */
void draw_char(int x, int y, char character, color_t color){
	
	char bitMask = 0x01;	//to isolate the single rightmost bit

	int i;
	for(i=0; i<16; i++){
		char row = iso_font[character * 16 + i];	//gets a single row of the letter format

		int j;
		for(j=0; j<8; j++){
			char result = (row & (bitMask << j)) >> j;
			
			if((int)result == 1){
				draw_pixel((x+j), (y+i), color);
			}
		}
	}
}

/*
 * Requires draw_char
 * Iterates through each character in *text
 * Passes each character to draw_char()
 */
void draw_text(int x, int y, const char *text, color_t c){
	unsigned char character;
	
	int i=0;
	do{
		character = *(text+i);
		draw_char(x,y,character,c);
		x = x+8;	//Don't want to overlap our letters
		i = i+1;
	}while(character != '\0');

}
