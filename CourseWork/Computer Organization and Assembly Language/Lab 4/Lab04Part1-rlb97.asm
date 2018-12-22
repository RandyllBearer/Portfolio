.data
userString: .space 64
pleaseString: .asciiz "Please enter your string: \n"
pleaseCharacter: .asciiz "Please enter the character to replace: \n"
hereOutput: .asciiz "\nHere is the output: "
userCharacter: .space 1
.text
#Ask for userString
addi $v0, $zero, 4
la $a0, pleaseString
syscall
addi $v0, $zero, 8
la $a0, userString
addi $a1, $zero, 64
syscall

#Ask for userCharacter
addi $v0, $zero, 4
la $a0, pleaseCharacter
syscall
addi $v0, $zero, 8
la $a0, userCharacter
addi $a1, $zero, 2
syscall

#Load arguments and jump to procedures
la $a2 userString
la $a3 userCharacter
jal ReplaceWithTilde

la $a2 userString
jal InvertCase

#Print New String and exit
addi $v0, $zero, 4
la $a0, hereOutput
syscall
addi $v0, $zero, 4
la $a0, userString
syscall
addi $v0, $zero, 10
syscall


ReplaceWithTilde:
addi $t5, $zero, 0 	#$t5 = incrementor
addi $t4, $zero, 0	#$t4 = current index of string
lbu $t2 0($a3)		#$t2 = character to match
addi $t3, $zero, 0x7E	#$t3 = ascii tilde code
loop:
	add $t4, $t5, $a2
	lbu $t1, 0($t4)
	beq $t1, 0, exit
	beq $t1, $t2, replace
	addi $t5, $t5, 1	#If doesn't match, increment by 1
	j loop
	
exit:
	jr $ra

replace:
	sb $t3, 0($t4)
	addi $t5, $t5, 1	#If match, increment by 1
	j loop
	
InvertCase: # Check if within lowerCase or upperCase ascii bounds, then add 0x20 to Uppercase, subtract 0x20 from Lowercase
addi $t5, $zero, -1	#$t5 = incrementor
addi $t4, $zero, 0	#$t4 = current byte index in string
addi $t3, $zero, 0	#If it is within range, $t3 will == 1, if outside range, #t3 will == 0
loop2:
	addi $t5, $t5, 1
	add $t4, $t5, $a2	# Set incrementor
	lbu $t1, 0($t4)
	
	##Check if end of string
	beq $t1, 0, exit2
	
	##Check if Uppercase
	addi $t2, $zero, 0x41	## First uppercase hex value
	sltu $t3, $t1, $t2
	beq $t3, 1, loop2	## If its lower than uppercase its none of our concern, iterate again
	addi $t2, $zero, 0x5A	## Last uppercase hex value
	sgtu $t3, $t1, $t2
	beq $t3, 0, convertToLower	# If its not greater then its in range, invert it
	
	##Check if lowercase
	addi $t2, $zero, 0x61 	## First lowercase hex value
	sltu $t3, $t1, $t2
	beq $t3, 1, loop2	## If 1, lower then lowercase and not in uppercase, iterate again
	addi $t2, $zero, 0x7A
	sgtu $t3, $t1, $t2
	beq $t3, 0, convertToUpper
	j loop2
	
convertToLower:
	addi $t1, $t1, 0x20
	sb $t1, ($t4)
	j loop2

convertToUpper:
	subi $t1, $t1, 0x20
	sb $t1, ($t4)
	j loop2

exit2:
	jr $ra
