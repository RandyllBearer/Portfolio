Randyll Bearer
Assignment 1
rlb97@pitt.edu

1. I have included a (Rough) flow chart with my program to break it down somewhat more, but the basic gist of it is this:
	A. My program prints out Assembly Code and encodes Machine Code as it goes.  I don't create a string and edit it at the end but rather print out each aspect of the Assembly
	Code individually and save the encoding to allocated space.
	B. To handle the issue of "Linking" previous destination registers to current source registers, I dedicate a specific register to being a boolean value of whether the previous
	source register should be linked or not.  All instructions except BEQ/BNE/J have destination registers, so they can be linked to the next instruction.  BEQ/BNE/J break the chain.
	C. So 
	- Start main loop
	- Print out first Assembly Instruction + Encode first Machine Code
	- Print out second Assembly Instruction + Encode second Machine Code
	- Print out third Assembly Instruction + Encode third Machine Code
	- Print out fourth Assembly Instruction + Encode fourth Machine Code
	- Print out fifth Assembly Instruction + Encode fifth Machine Code
	- Start displayMachine loop
	- Load first encoding from memory and print it out
	- Load second encoding from memory and print it out
	- Load third encoding from memory and print it out
	- Load fouth encoding from memory and print it out
	- Load fifth enocoding from memory and print it out
	- Exit

2. Here is an outline of the registers I use and what they're used for:
	$t9: Holds current iteration (This program will end at $t9 = 6)
	$t8: Holds boolean value of whether we are linking a register from the previous instruction (BNE/BEQ/J = 0, everything else = 1)
	$t7: Holds which numbered register we will add next.  If an instruction uses two new registers, add two to this value
	$t6: Holds the constant immediate value (Starts at 0, increments by 1 each time used)
	$t5: Holds the user-input loaded from stack (Determines which instruction is used)
	$t4: Holds a value saying which register must be linked (Register # of previous destination)
	$t3: A temp value holding the current destination # (Will be moved to $t4)
	$t2: Holds the label we must print in opcode J [Temp]
	$t1: Holds the hexadecimal 0xXXXXXXXX encoding to be stored in $t0
	$t0: The address of the allocated location in memory for the encoded machine code

3. As to my knowledge my program *should* be bug-free, but if anything is incorrect it will be the machine encoding of my branch instructions.  I believe that I encode it correctly,
   but I may be incorrect. 
	0 steps back = you have to decrement PC by -4 = -4/4 = -1 = ffff = sign extended
	1 step back = pc -8 = -8/4 = -2 = fffe
	2 step back = pc -12 = -12/4 = -3 = fffd
	3 step back = pc -16 = -16/4 = -4 = fffc
	4 step back = pc -20 = -20/4 = -5 = fffb

4. Link to flowchart 
	http://imgur.com/t3Nxkdh

---------------------------------------------Post Revision (1 out of 2 Extra-Credit)--------------------------------

5. I *DO NOT* use syscall 34

6. I *DO* Print out a label for every line