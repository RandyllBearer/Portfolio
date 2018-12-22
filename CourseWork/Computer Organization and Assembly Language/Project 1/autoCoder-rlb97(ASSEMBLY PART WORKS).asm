.data
welcome: .asciiz "Welcome to Auto Coder!\n"
opcodes: .asciiz "The opcode (1-9 : 1=add, 2=addi, 3=or, 4=ori, 5=lw, 6=sw, 7=j, 8=beq, 9=bne)\n"
please1: .asciiz "Please enter the 1st opcode: "
please2: .asciiz "Please enter the 2nd opcode: "
please3: .asciiz "Please enter the 3rd opcode: "
please4: .asciiz "Please enter the 4th opcode: "
please5: .asciiz "Please enter the 5th opcode: "
newLine: .asciiz "\n"
noLabelSpace: .asciiz "       " ### 7 spaces
comma: .asciiz ", "
leftP: .asciiz "("
rightP: .asciiz ")"
line100: .asciiz "L100:  "
jumpLine100: .asciiz "L100"	### Is used when need to attach to end of J/BEQ/BNE
line101: .asciiz "L101:  "
jumpLine101: .asciiz "L101"
line102: .asciiz "L102:  "
jumpLine102: .asciiz "L102"
line103: .asciiz "L103:  "
jumpLine103: .asciiz "L103"
line104: .asciiz "L104:  "
jumpLine104: .asciiz "L104"
completed: .asciiz "\n\nThe completed code is\n"
opAdd: .asciiz "add "
opAddi: .asciiz "addi "
opOr: .asciiz "or "
opOri: .asciiz "ori "
opLw: .asciiz "lw "
opSw: .asciiz "sw "
opJ: .asciiz "j "
opBeq: .asciiz "beq "
opBne: .asciiz "bne "
opT0: .asciiz "$t0"
opT1: .asciiz "$t1"
opT2: .asciiz "$t2"
opT3: .asciiz "$t3"
opT4: .asciiz "$t4"
opT5: .asciiz "$t5"
opT6: .asciiz "$t6"
opT7: .asciiz "$t7"
opT8: .asciiz "$t8"
opT9: .asciiz "$t9"
machineCode: .asciiz "The machine code is\n"
instruction1: .space 32 #Will encode as we go
instruction2: .space 32
instruction3: .space 32
instruction4: .space 32
instruction5: .space 32

.text
### Step 1: Print out and recieve opcodes [save opcodes to stack]
addi $v0, $zero, 4
la $a0, welcome
syscall
addi $v0, $zero, 4
la $a0, opcodes
syscall
addi $v0, $zero, 4
la $a0, please1
syscall
addi $v0, $zero, 5	### Read in Opcode 1
syscall
addi $sp, $sp, -4
sb $v0, 0($sp)		### Transfer opcode 1 to 0(sp)
addi $v0, $zero, 4
la $a0, please2
syscall
addi $v0, $zero, 5	### Read in Opcode 2
syscall
addi $sp, $sp, -1
sb $v0, 0($sp)		### Opcode 2 = -1(sp)
addi $v0, $zero, 4
la $a0, please3
syscall
addi $v0, $zero, 5	### Read in Opcode 3	
syscall
addi $sp, $sp, -1
sb $v0, 0($sp)		### Opcode 3 = -2(sp)
addi $v0, $zero, 4
la $a0, please4
syscall
addi $v0, $zero, 5	### Read in Opcode 4
syscall
addi $sp, $sp, -1
sb $v0, 0($sp)		### Opcode 4 = -3(sp)
addi $v0, $zero, 4
la $a0, please5
syscall
addi $v0, $zero, 5	### Read in Opcode 5
syscall
addi $sp, $sp, -1	
sb $v0, 0($sp)		### Opcode 5 = -4(sp)
addi $sp, $sp, 4	### Reset stack pointer to the first opcode (sub 1 per iteration)

### Print Display
addi $v0, $zero, 4
la $a0, completed
syscall

mainLoop:
	addi $t9, $t9, 1    	### T9 keeps track of our total iteration / which opcode we're on
	beq $t9, 6, displayMachine
	lb $t5, 0($sp)	    	### T5 holds our opcode instruction
	addi $sp, $sp, -1
	lb $t0, 0($sp)
	checkLabel:		### Is our next instruction going to require us to branch/jump to our current instruction?
		jal checkLabel2	
	beq $t5, 1, bAdd		### 1=add, 2=addi, 3=or, 4=ori, 5=lw, 6=sw, 7=j, 8=beq, 9=bne
	beq $t5, 2, bAddi
	beq $t5, 3, bOr
	beq $t5, 4, bOri
	beq $t5, 5, bLw
	beq $t5, 6, bSw
	beq $t5, 7, bJ
	beq $t5, 8, bBeq
	beq $t5, 9, bBne
	### End of valid inputs	
	
checkLabel2:	### Do we need to add a label to this instruction?
	beq $t0, 7, addLabel
	beq $t0, 8, addLabel
	beq $t0, 9, addLabel	
	j noLabel
	
addLabel:	### Add a label to this instruction
	beq $t9, 1, addLabel100
	beq $t9, 2, addLabel101
	beq $t9, 3, addLabel102
	beq $t9, 4, addLabel103
	beq $t9, 5, addLabel104
	
addLabel100:	### Since our next instruction will be jumping/branching to our current instruction, we need to give it a label
	addi $v0, $zero, 4
	la $a0, line100
	syscall
	jr $ra
addLabel101:
	addi $v0, $zero, 4
	la $a0, line101
	syscall
	jr $ra
addLabel102:
	addi $v0, $zero, 4
	la $a0, line102
	syscall
	jr $ra
addLabel103:
	addi $v0, $zero, 4
	la $a0, line103
	syscall
	jr $ra
addLabel104:
	addi $v0, $zero, 4
	la $a0, line104
	syscall
	jr $ra
noLabel:
	addi $v0, $zero, 4
	la $a0, noLabelSpace
	syscall
	jr $ra
	
getMachineInstruction:		### which instruction are we encoding?
	beq $t9, 1, getMachineInstruction1
	beq $t9, 2, getMachineInstruction2
	beq $t9, 3, getMachineInstruction3
	beq $t9, 4, getMachineInstruction4
	beq $t9, 5, getMachineInstruction5
getMachineInstruction1:
	la $t0, instruction1	### We will save our binary-to-encode to instruction1
	jr $ra
getMachineInstruction2:
	la $t0, instruction2
	jr $ra
getMachineInstruction3:
	la $t0, instruction3
	jr $ra
getMachineInstruction4:
	la $t0, instruction4
	jr $ra
getMachineInstruction5:
	la $t0, instruction5
	jr $ra
	
bAdd:
	bne $t8, 0, bAddLink	### T8 holds whether we have to link the previous destination register as a source register [1=yes, 2=no]
	### Print add
	addi $v0, $zero, 4
	la $a0, opAdd
	syscall
	### Print destination register
	jal addRegister
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	addi $t4, $t7, 0	### save our destination register in case we need to link it to a source register
	addi $t7, $t7, 1	### We are now on 1 register higher
	### Print first source register
	jal addRegister
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	addi $t7, $t7, 1	### Increment register number
	### Print second source register
	jal addRegister
	addi $v0, $zero, 4
	la $a0, newLine
	syscall
	addi $t7, $t7, 1
	### We will link registers after this
	addi $t8, $zero, 1
	j mainLoop
bAddLink:
	### Print add
	addi $v0, $zero, 4
	la $a0, opAdd
	syscall
	### Print destination register
	jal addRegister
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	addi $t3, $t7, 0	### save our destination register for future link (MUST move this to $t4 before ending addLink)
	addi $t7, $t7, 1	### Increment to next register
	### Print source register (Linked)
	jal addRegisterLinked
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	addi $t4, $t3, 0	### Register we used as destination will now be linked to source
	### Print second source register
	jal addRegister
	addi $v0, $zero, 4
	la $a0, newLine
	syscall
	addi $t7, $t7, 1
	### We will link registers after this
	addi $t8, $zero, 1
	j mainLoop

bAddi:
	bne $t8, 0, bAddiLink
	### Print addi
	addi $v0, $zero, 4
	la $a0, opAddi
	syscall
	### Print destination register
	jal addRegister
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	addi $t4, $t7, 0 ### Save our destination register in case link for source
	addi $t7, $t7, 1 ### Increment register +1
	### Print second register
	jal addRegister
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	addi $t7, $t7, 1 ### Increment register
	### Print constant [#t6 holds constant value X]
	addi $v0, $zero, 1
	addi $a0, $t6, 100
	syscall
	addi $t6, $t6, 1 ### increment our X value
	addi $v0, $zero, 4
	la $a0, newLine
	syscall
	### We will link registers after this
	addi $t8, $zero, 1
	j mainLoop
bAddiLink:
	### Print addi
	addi $v0, $zero, 4
	la $a0, opAddi
	syscall
	### Print destination register
	jal addRegister
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	addi $t3, $t7, 0 	### Save our destination in case of link (MUST move to $t4)
	addi $t7, $t7, 1
	### Print source register (LINKED)
	jal addRegisterLinked
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	addi $t4, $t3, 0 	### Update the register to be linked
	### Print constant
	addi $v0, $zero, 1
	addi $a0, $t6, 100
	syscall
	addi $t6, $t6, 1	### Increment our X value
	addi $v0, $zero, 4
	la $a0, newLine
	syscall
	### We will link registers after this
	addi $t8, $zero, 1
	j mainLoop
	
bOr:
	bne $t8, 0, bOrLink
	### Print or
	addi $v0, $zero, 4
	la $a0, opOr
	syscall
	### Print destination register
	jal addRegister
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	addi $t4, $t7, 0 ### Save our destination register to be linked as source
	addi $t7, $t7, 1 ### Increment next register to use
	### Print source register
	jal addRegister
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	addi $t7, $t7, 1	### Increment future register to use
	### Print second source register
	jal addRegister
	addi $v0, $zero, 4
	la $a0, newLine
	syscall
	addi $t7, $t7, 1
	### We will link registers after this
	addi $t8, $zero, 1
	j mainLoop
bOrLink:
	### Print or
	addi $v0, $zero, 4
	la $a0, opOr
	syscall
	### Print destination register
	jal addRegister
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	addi $t3, $t7, 0 ### Save destination register for future link (MUST BE MOVED TO $T4)
	addi $t7, $t7, 1
	### Print source register (LINKED)
	jal addRegisterLinked
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	addi $t4, $t3, 0 ### Update our destination register for Link
	### Print second ource register
	jal addRegister
	addi $v0, $zero, 4
	la $a0, newLine
	syscall
	addi $t7, $t7, 1	### increment register to use
	### We will link registers
	addi $t8, $zero, 1
	j mainLoop
	
bOri:
	bne $t8, 0, bOriLink
	### Print ori
	addi $v0, $zero, 4
	la $a0, opOri
	syscall
	### Print destination register
	jal addRegister
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	addi $t4, $t7, 0	### Update our destination register for future link
	addi $t7, $t7, 1	### Increment future to use register
	### Print source register
	jal addRegister
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	addi $t7, $t7, 1	### Increment to ue register
	### Print immediate ($t6 holds X)
	addi $v0, $zero, 1
	addi $a0, $t6, 100
	syscall
	addi $t6, $t6, 1	### Increment X constant
	addi $v0, $zero, 4
	la $a0, newLine
	syscall
	### We will Linked after this
	addi $t8, $zero, 1
	j mainLoop
bOriLink:
	### Print ori
	addi $v0, $zero, 4
	la $a0, opOri
	syscall
	### Print destinaion register
	jal addRegister
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	addi $t3, $t7, 0 	### Save destination register for future link (MUST be moved to $t4)
	addi $t7, $t7, 1
	### Print source register (LINKED)
	jal addRegisterLinked
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	addi $t4, $t3, 0	### move linked register to $t4
	### Print immediate/constant
	addi $v0, $zero, 1
	addi $a0, $t6, 100
	syscall
	addi $t6, $t6, 1	### Increment X register
	addi $v0, $zero, 4
	la $a0, newLine
	syscall
	### We will link after this
	addi $t8, $zero, 1
	j mainLoop
	
bLw:
	bne $t8, 0, bLwLink
	###Print lw
	addi $v0, $zero, 4
	la $a0, opLw
	syscall
	### Print destination register
	jal addRegister
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	addi $t4, $t7, 0	### save destination register for future use
	addi $t7, $t7, 1	### increment to use register
	### print immediate offset
	addi $v0, $zero, 1
	addi $a0, $t6, 100
	syscall
	addi $t6, $t6, 1	### increment X register
	### print source register *($Tx)
	addi $v0, $zero, 4
	la $a0, leftP
	syscall
	jal addRegister
	addi $v0, $zero, 4
	la $a0, rightP
	syscall
	addi $t7, $t7, 1	### Increment to use register
	addi $v0, $zero, 4
	la $a0, newLine
	syscall
	### We will link this destination in the future
	addi $t8, $zero, 1
	j mainLoop
bLwLink:
	### print lw
	addi $v0, $zero, 4
	la $a0, opLw
	syscall
	### Print destination register
	jal addRegister
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	addi $t3, $t7, 0	### Saving destination register for future link (MUST move to #t4)
	addi $t7, $t7, 1	### Incrementing register
	### Print immediate / offset
	addi $v0, $zero, 1
	addi $a0, $t6, 100
	syscall
	addi $t6, $t6, 1	### Increment X register
	### Print source register (LINKED)
	addi $v0, $zero, 4
	la $a0, leftP
	syscall
	jal addRegisterLinked
	addi $t4, $t3, 0 	### Move destination register to $t4
	addi $v0, $zero, 4
	la $a0, rightP
	syscall
	addi $v0, $zero, 4
	la $a0, newLine
	syscall
	### We will link destination in future
	addi $t8, $zero, 1
	j mainLoop
	
bSw:
	bne $t8, 0, bSwLink
	### Print Sw
	addi $v0, $zero, 4
	la $a0, opSw
	syscall
	### Print source register
	jal addRegister
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	addi $t7, $t7, 1 	### Increment future register
	###  Print Immediate / Offset
	addi $v0, $zero, 1
	addi $a0, $t6, 100
	syscall
	addi $t6, $t6, 1	### Increment X register
	### Print destination register
	addi $v0, $zero, 4
	la $a0, leftP
	syscall
	jal addRegister
	addi $t4, $t7, 0	### Save destination register for future link
	addi $t7, $t7, 1	### Increment to use register
	addi $v0, $zero, 4
	la $a0, rightP
	syscall
	addi $v0, $zero, 4
	la $a0, newLine
	syscall
	### We will be linking destination
	addi $t8, $zero, 1
	j mainLoop
bSwLink:
	### Print sw
	addi $v0, $zero, 4
	la $a0, opSw
	syscall
	### Print source register
	jal addRegisterLinked
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	### Print immediate / offset
	addi $v0, $zero, 1
	addi $a0, $t6, 100
	syscall
	addi $t6, $t6, 1	### Increment X register
	### print destination Register
	addi $v0, $zero, 4
	la $a0, leftP
	syscall
	jal addRegister
	addi $t4, $t7, 0	### Must save destination register for future link
	addi $t7, $t7, 1	### must increment future to use register
	addi $v0, $zero, 4
	la $a0, rightP
	syscall
	addi $v0, $zero, 4
	la $a0, newLine
	syscall
	### We will use this destination for future link
	addi $t8, $zero, 1
	j mainLoop

bJ:
	### Print j
	addi $v0, $zero, 4
	la $a0, opJ
	syscall
	### Print label
	addi $t0, $t9, 0	### Save the $t9 register state
	addi $t9, $t9, -1	### decrement $t9 by 1 so we can use already made procedure addLabel
	jal addJumpLabel
	add $t9, $zero, $t0	### restore $t9
	addi $v0, $zero, 4
	la $a0, newLine
	syscall
	### We will not be linking anything here (BREAKS CHAIN)
	addi $t8, $zero, 0
	j mainLoop
bBeq:
	bne $t8, 0, bBeqLink
	### Print beq
	addi $v0, $zero, 4
	la $a0, opBeq
	syscall
	### Print first source
	jal addRegister
	addi $t7, $t7, 1	### Increment future to use register
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	### Print second source
	jal addRegister
	addi $t7, $t7, 1	### Increment future to use register
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	### Print label
	addi $t0, $t9, 0	### Save the $t9 register state
	addi $t9, $t9, -1	### decrement $t9 by 1 so we can use already made procedure addlabel
	jal addJumpLabel
	add $t9, $zero, $t0	### restore $t9
	addi $v0, $zero, 4
	la $a0, newLine
	syscall
	### We have no destination register to link
	addi $t8, $zero, 0
	j mainLoop
bBeqLink:
	### Print beq
	addi $v0, $zero, 4
	la $a0, opBeq
	syscall
	### Print first source register (LINKED)
	jal addRegisterLinked
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	### Print second source register
	jal addRegister
	addi $t7, $t7, 1	### Increment future to use register
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	### Print label
	addi $t0, $t9, 0	### Save the $t9 register state
	addi $t9, $t9, -1	### decrement $t9 by 1 so we can use already made procedure addlabel
	jal addJumpLabel
	add $t9, $zero, $t0	### restore $t9
	addi $v0, $zero, 4
	la $a0, newLine
	syscall
	### We have no destination register to link
	addi $t8, $zero, 0
	j mainLoop
	
bBne:
	bne $t8, 0, bBneLink
	### Print BNE
	addi $v0, $zero, 4
	la $a0, opBne
	syscall
	### print source register
	jal addRegister
	addi $t7, $t7, 1	### Increment future to use register
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	### Print second source register
	jal addRegister
	addi $t7, $t7, 1	### increment future to use register
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	### Print Label
	addi $t0, $t9, 0	### Save the $t9 register state
	addi $t9, $t9, -1	### decrement $t9 by 1 so we can use already made procedure addlabel
	jal addJumpLabel
	add $t9, $zero, $t0	### restore $t9
	addi $v0, $zero, 4
	la $a0, newLine
	syscall
	### We have no destination register to link
	addi $t8, $zero, 0
	j mainLoop
bBneLink:
	### Print BNE
	addi $v0, $zero, 4
	la $a0, opBne
	syscall
	### Print source Register (LINKED)
	jal addRegisterLinked
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	### Print second source
	jal addRegister
	addi $t7, $t7, 1	### Increment future to use register
	addi $v0, $zero, 4
	la $a0, comma
	syscall
	### Print Label
	addi $t0, $t9, 0	### Save the $t9 register state
	addi $t9, $t9, -1	### decrement $t9 by 1 so we can use already made procedure addlabel
	jal addJumpLabel
	add $t9, $zero, $t0	### restore $t9
	addi $v0, $zero, 4
	la $a0, newLine
	syscall
	### We have no destination register to link
	addi $t8, $zero, 0
	j mainLoop
	
addJumpLabel:
	beq $t9, 1, addJumpLabel100
	beq $t9, 2, addJumpLabel101
	beq $t9, 3, addJumpLabel102
	beq $t9, 4, addJumpLabel103
	beq $t9, 5, addJumpLabel104
addJumpLabel100:
	addi $v0, $zero, 4
	la $a0, jumpLine100
	syscall
	jr $ra
addJumpLabel101:
	addi $v0, $zero, 4
	la $a0, jumpLine101
	syscall
	jr $ra
addJumpLabel102:
	addi $v0, $zero, 4
	la $a0, jumpLine102
	syscall
	jr $ra
addJumpLabel103:
	addi $v0, $zero, 4
	la $a0, jumpLine103
	syscall
	jr $ra
addJumpLabel104:
	addi $v0, $zero, 4
	la $a0, jumpLine104
	syscall
	jr $ra
	
addRegister:
	beq $t7, 0, addT0 ### T7 holds which register we are currently on
	beq $t7, 1, addT1
	beq $t7, 2, addT2
	beq $t7, 3, addT3
	beq $t7, 4, addT4
	beq $t7, 5, addT5
	beq $t7, 6, addT6
	beq $t7, 7, addT7
	beq $t7, 8, addT8
	beq $t7, 9, addT9
addRegisterLinked:	  ## T4 holds which register we linked
	beq $t4, 0, addT0
	beq $t4, 1, addT1
	beq $t4, 2, addT2
	beq $t4, 3, addT3
	beq $t4, 4, addT4
	beq $t4, 5, addT5
	beq $t4, 6, addT6
	beq $t4, 7, addT7
	beq $t4, 8, addT8
	beq $t4, 9, addT9
addT0:
	addi $v0, $zero, 4
	la $a0, opT0
	syscall
	jr $ra
addT1:
	addi $v0, $zero, 4
	la $a0, opT1
	syscall
	jr $ra
addT2:
	addi $v0, $zero, 4
	la $a0, opT2
	syscall
	jr $ra
addT3:
	addi $v0, $zero, 4
	la $a0, opT3
	syscall
	jr $ra
addT4:
	addi $v0, $zero, 4
	la $a0, opT4
	syscall
	jr $ra
addT5:
	addi $v0, $zero, 4
	la $a0, opT5
	syscall
	jr $ra
addT6:
	addi $v0, $zero, 4
	la $a0, opT6
	syscall
	jr $ra
addT7:
	addi $v0, $zero, 4
	la $a0, opT7
	syscall
	jr $ra
addT8:
	addi $v0, $zero, 4
	la $a0, opT8
	syscall
	jr $ra
addT9:
	addi $v0, $zero, 4
	la $a0, opT9
	syscall
	jr $ra

displayMachine:
	j exit
	
exit:
	addi $v0, $zero, 10
	syscall
