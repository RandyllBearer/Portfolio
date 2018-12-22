#ifndef _LIBRARY_INCLUDED_H
#define _LIBRARY_INCLUDED_H

#include <stdlib.h>
#include <stdio.h>

#include <linux/fb.h>
#include <fcntl.h>
#include <sys/mman.h>

typedef unsigned short color_t;

//macros to extract RGB colors
#define BMASK(c) (c & 0x001f)	//BLUE mask
#define GMASK(c) (c & 0x07E0)	//GREEN mask
#define RMASK(c) (c & 0xF800)	//RED MASK

void init_graphics();
void exit_graphics();
void clear_screen();
char getkey();
void sleep_ms(long ms);
void draw_pixel(int x, int y, color_t color);
void draw_rect(int x1, int y1, int width, int height, color_t c);
void draw_text (int x, int y, const char *text, color_t c);
void draw_char (int x, int y, char character, color_t color);
color_t encodeColor(int red, int green, int blue);

#endif
