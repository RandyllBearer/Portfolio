//Randyll Bearer: rlb97@pitt.edu
//Driver program for CS1550 Project 1.
/**
 * The purpose of this program is to test my implementations of the required functions
 * for library.c. To do so, this program asks the user to make one of two choices, to
 * either draw a rectangle and make it move with the WASD keys or to have some text
 * displayed to the screen using the iso_font.h format.
 */

#include "library.h"

typedef unsigned short color_t;

int main(){
	//PREPARE COLORS
	color_t black = encodeColor(0,0,0);
	color_t green = encodeColor(1,50,1);
	color_t red = encodeColor(50,1,1);
	color_t blue = encodeColor(1,1,50);

	//INITIALIZE GRAPHICS
	init_graphics();

	char key;

	while(1){
		clear_screen();	//Initial clear
		int x = (640-200)/2;
		int y = (480 -100)/2;	//just the same dimensions as in square.c

		draw_text(x,y, "Hello, welcome to my driver program!", green);
		draw_text(x,y+16, "Press '1' to move the rectangle using WASD", green);
		draw_text(x,y+32, "Press '2' to see some text", green);
		draw_text(x,y+48, "Press 'q' at anytime to quit!", green);
		
		key = getkey();
		
		if(key == '1'){		//draw + move rectangle
			clear_screen();
			while(key != 'q'){
				key = getkey();
				if(key == 'w'){
					y = y-10;
				}else if(key == 's'){
					y = y+10;
				}else if(key == 'a'){
					x = x-10;
				}else if(key == 'd'){
					x = x+10;
				}	
				clear_screen();
				draw_rect(x, y, 20, 20, blue);
				sleep_ms(20);
			}
		}else if(key == '2'){	//display some text
			clear_screen();
			draw_text(x,y,"Here is some text! Press 'q' to return to main menu", red);
			do{
				key = getkey();
			}while(key != 'q');
		}else if(key == 'q'){	//exit_graphics() and quit
			clear_screen();
			exit_graphics();
			return 0;
		}
		
	}
}
