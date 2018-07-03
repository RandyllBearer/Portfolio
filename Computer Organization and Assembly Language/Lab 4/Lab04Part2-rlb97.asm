.data
types:	.asciiz "bit", "nybble", "byte", "half", "word"
bits:	.asciiz "one", "four", "eight", "sixteen", "thirty-two"
pleaseType: .asciiz "Pease enter a datatype:\n"
userString: .space 7	## Our largest word to search for is nybble size 6
number: .asciiz "Number of bits: "
notFound: .asciiz "Not Found!"
.text
#ask for string
addi $v0, $zero, 4
la $a0, pleaseType
syscall
addi $v0, $zero, 8
la $a0, userString
addi $a1, $zero, 8
syscall
#REMOVE PESKY NEW LINE CHARACTER
la $t1, userString
add $t4, $zero, 0 #iterator
removeLoop:
	lb $t2, 0($t1)
	addi $t1, $t1, 1
	beq $t2, 0xa, removeNewLine
	j removeLoop
removeNewLine:
	add $t5, $zero, 0
	sb $t5, -1($t1)

la $a2, types
add $t7, $zero, $a2	#incrementor address in 'types', to look up next word in 'types', add to this
addi $t8, $zero, 0	# How many times have we gone through the loop
loop:	# main program loop
	add $a2, $zero, $t7
	la $a3, userString
	jal CheckType
	add $t1, $zero, $v1
	
	add $a2, $zero, $t8	# index for 'bits' (HOW MANY TIMES HAVE WE GONE THROUGH THIS MASTER LOOP)
	la $a1, bits		#address for 'bits'
	beq $t1, 1, LookUp	# if the 2 match, look up corresponding bit amount
	
	add $a1, $zero, $t7		
	beq $t1, 0, StrSize	# if the 2 don't match, iterate to next type
	add $t7, $t7, $v1
	addi $t8, $t8, 1
	beq $t8, 5, neverFound
	j loop
	
neverFound:
	addi $v0, $zero, 4
	la $a0, number
	syscall
	addi $v0, $zero, 4
	la $a0, notFound
	syscall
	addi $v0, $zero, 10
	syscall
	
exit:
	addi $v0, $zero, 4
	la $a0, number
	syscall
	addi $v0, $zero, 4
	la $a0, 0($v1)
	syscall
	addi $v0, $zero, 10
	syscall

CheckType:
	add $t6, $zero, $a2 #Increments through types
	add $t5, $zero, $a3 #Increments through userString
	loop2:
		lbu $t1, 0($t6)	#load first byte from userString
		lbu $t2, 0($t5) #load first byte from types
		bne $t1, $t2, exitNotEqual
		j equal
		
	equal:
		seq $t1, $t1, 0
		seq $t2, $t2, 0
		add $t9, $t1, $t2
		beq $t9, 2, exitEqual
		addi $t6, $t6, 1
		addi $t5, $t5, 1
		j loop2
		
	exitEqual:
		addi $v1, $zero, 1
		jr $ra
	exitNotEqual:
		addi $v1, $zero, 0
		jr $ra	

LookUp:	#Don't edit $t1 which is holding CheckType return
	add $t2, $a2, $zero	#Index in 'bits'
	add $t3, $a1, $zero	#address for 'bits'	WILL BE OUR RETURN VALUE
	addi $t5, $zero, 0	#incrementor through loop3
	loop3:
		beq $t2, $t5, exit3	#Have we gone through the same number of indexes as our desired index?
		lbu $t6, 0($t3)
		beq $t6, 0, nextItem
		addi $t3, $t3, 1
		j loop3
	
	nextItem:
		addi $t5, $t5, 1	#we have went through 1 index
		addi $t3, $t3, 1
		j loop3
	
	exit3:
		add $v1, $zero, $t3	#the address to load from to get what type matches
		j exit

StrSize:	#Edit size of $t7 / DONT EDIT $T1
	addi $t2, $zero, 0 #how many bytes are in our current word?
	add $t3, $zero, $a1 #incrementor through 'type' (ONCE THIS HITS 00, RETURN $T2)
	loop4:
		lbu $t4, 0($t3)
		beq $t4, 0, exit4
		addi $t2, $t2, 1
		addi $t3, $t3, 1
		j loop4
	
	exit4:
		addi $t2, $t2, 1
		add $v1, $zero, $t2 #What to add to $t7
		jr $ra
