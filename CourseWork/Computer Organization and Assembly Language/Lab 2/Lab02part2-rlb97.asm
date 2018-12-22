#Randyll Bearer, Friday Recitation 1:00pm
#Program to take the difference between two user-inputted values

.data
#Declaring labels for Strings
firstQuestion: .asciiz "What is the first value?\n"
secondQuestion: .asciiz	"What is the second value?\n"
thirdLinePartOne: .asciiz "The difference between "
thirdLinePartTwo: .asciiz " and "
thirdLinePartThree: .asciiz " is "

.text
#Print first question
addi $v0, $zero, 4
la $a0, firstQuestion
syscall
#read first user input and store it
addi $v0, $zero, 5
syscall
add $t1, $zero, $v0

#Print second question
addi $v0, $zero, 4
la $a0, secondQuestion
syscall
#read second user inputer and store it
addi $v0, $zero, 5
syscall
add $t2, $zero, $v0

#Perform the difference calculation
sub $t3, $t1, $t2

#Print final result
addi $v0, $zero, 4
la $a0, thirdLinePartOne
syscall
addi $v0, $zero, 1
add $a0, $zero, $t1
syscall
addi $v0, $zero, 4
la $a0, thirdLinePartTwo
syscall
addi $v0, $zero, 1
add $a0, $zero, $t2
syscall
addi $v0, $zero, 4
la $a0, thirdLinePartThree
syscall
addi $v0, $zero, 1
add $a0, $zero, $t3
syscall

#exit
add $v0, $zero, 10
syscall