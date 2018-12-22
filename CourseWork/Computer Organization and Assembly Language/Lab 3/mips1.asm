.data
x: .word 13141516
.text
la $t2, x
lb $t1 3($t2)
addi $v0, $zero, 1
add $a0, $zero, $t1
syscall