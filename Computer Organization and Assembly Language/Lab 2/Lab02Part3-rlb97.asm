#Randyll Bearer Lab 02 Part 3

#Declaring Variables
.data
y: .byte 37
z: .byte 0
x: .byte 13

.text
#Gather the values into registers and add them
la $t1, y
lb $t1, 0($t1)
la $t2, x
lb $t2, 0($t2)
add $t3, $t1, $t2 

#Store the result into z
la $t1, z
sb $t3, 0($t1)

#Overwrite the adresses of x & y with z
sb $t3, -1($t1)
sb $t3, 1($t1)