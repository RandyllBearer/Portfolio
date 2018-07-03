.data
instruction1: .space 32

.text
addi $t0, $t0, 0x12345678
la $t1, instruction1
sw $t0, ($t1)

lb $a0, 3($t1)
addi $v0, $zero, 34
syscall