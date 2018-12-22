.data
#Idea of this is to go in a loop which increments every byte until it hits the .asciiz  null (This will be our index for our Least Significant Digit), then just use binary or's and's slt's
userString1: .space 64
userString2: .space 64
zero: .byte 0x30
one: .byte 0x31
newString: .space 64
pleaseFirst: .asciiz "Please enter the first string:\n"
pleaseSecond: .asciiz "Please enter the second string:\n"
theSumOf: .asciiz "The sum of "
and1:	  .asciiz " and "
is:	  .asciiz " is "

.text
#Ask the user for strings
addi $v0, $zero, 4
la $a0, pleaseFirst
syscall
addi $v0, $zero, 8
la $a0, userString1
addi $a1, $zero, 65
syscall
addi $v0, $zero, 4
la $a0, pleaseSecond
syscall
addi $v0, $zero, 8
la $a0, userString2
addi $a1, $zero, 65
syscall
#Use find string length to find what index to start at
la $a0, userString1
addi $s0, $zero, 0	#This tells us how many bytes are in our strings, to find index of least significant digit use $s0 - 1
jal findLength
la $a0, userString2
addi $s0, $zero, 0
addi $t2, $zero, 0
jal findLength
#Add them together
add $a0, $zero, $v0 #Max Size of String
la $a1, userString1
la $a2, userString2
la $a3, newString #String to update and return
jal binarySum

add $a0, $zero, $v0
addu $v0, $zero, 1
syscall

findLength: # ALSO REMOVES NEW LINE CHARACTER
	add $t1, $a0, $t2
	lbu $t0, 0($t1)
	beq $t0, 0x00, exitFindLength
	addi $t2, $t2, 1
	j findLength
	exitFindLength:
		addi $t0, $zero, 0x00
		sb $t0, -1($t1)
		subi $t2, $t2, 1
		add $v0, $t2, $zero
		jr $ra	
binarySum:
	addi $t8, $t8, 1
	sub $t0, $a0, $t8 #Offset of the last byte = offset of least signigicant digit (OUR STARTING POINT)
	beq $t0, -1, exit2
	add $t1, $a1, $t0 #L.S.D. of string1 [ $t1, $t3, $t5 go together]
	add $t2, $a2, $t0 #L.S.D. of string2 [ $t2, $t4, %t6 go together]
	add $t6, $a3, $t0 #Where to store our sum byte
	lbu $t3, 0($t1)
	lbu $t4, 0($t2)
	## Check if both are equal
	beq $t3, $t4, checkEqual
	#Now do what whappens when not equal [can assume that our sum = 1 + carry bit]
	beq $t9, 1, checkCarry011 # Sum = 1 + 1, sum value = 0, carry value = 1
	#can now assume that sum value = 1, carry value = 0
	addi $t7, $zero, 0x31
	sb $t7, 0($t6)
	j binarySum
	checkEqual:
		#check if both equal to 0
		seq $t5, $t3, 0x30
		beq $t5, 1, checkCarry00
		#check if both equal to 1
		seq $t5, $t3, 0x31
		beq $t5, 1, checkCarry11
	checkCarry00:
		beq $t9, 1, checkCarry001
		addi $t7, $zero, 0x30
		addi $t9, $zero, 0	#Just added a carry bit so reset it
		sb $t7, 0($t6)		#Add in the new sum byte
		j binarySum	
	checkCarry001:
		addi $t7, $zero, 0x31
		addi $t9, $zero, 0
		sb $t7, 0($t6)
		j binarySum	
	checkCarry11:
		beq $t9, 1, checkCarry111
		# So for this we need to set our sum byte = 0 and add carry bit
		addi $t7, $zero, 0x30
		sb $t7, 0($t6)
		addi $t9, $zero, 1
		j binarySum		
	checkCarry111:
		#We need to store sum byte = 1 and add carry bit
		addi $t7, $zero, 0x31
		sb $t7, 0($t6)
		addi $t9, $zero, 1
		j binarySum
	checkCarry011:
		addi $t7, $zero, 0x30
		sb $t7, 0($t6)
		addi $t9, $zero, 1
		j binarySum
	exit2: 
		la $v0, newString
		j exit
exit:
add $t0, $v0, $zero #$t0 now holds address of new string
addi $v0, $zero, 4
la $a0, theSumOf
syscall
addi $v0, $zero, 4
la $a0, userString1
syscall
addi $v0, $zero, 4
la $a0, and1
syscall
addi $v0, $zero, 4
la $a0, userString2
syscall
addi $v0, $zero, 4
la $a0, is
syscall
addi $v0, $zero, 4
add $a0, $zero, $t0
syscall

addi $v0, $zero, 10
syscall
