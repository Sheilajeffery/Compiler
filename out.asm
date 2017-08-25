
.data

.text
move $fp $sp 
subi $sp $sp 16
li $a0 10
sw $a0 8($fp)

li $a0 0
sw $a0 12($fp)

li $a0 1
sw $a0 0($fp)

loop1:
li $a0 1
subi $sp $sp 4
sw $a0 ($sp)
lw $a0 8($fp)
lw $a1 ($sp)
addi $sp $sp 4
slt $a0 $a1 $a0
beqz $a0 end1
lw $a0 12($fp)
subi $sp $sp 4
sw $a0 ($sp)
lw $a0 0($fp)
lw $a1 ($sp)
addi $sp $sp 4
add $a0 $a0 $a1
sw $a0 4($fp)

lw $a0 0($fp)
sw $a0 12($fp)

lw $a0 4($fp)
sw $a0 0($fp)

lw $a0 8($fp)
subi $sp $sp 4
sw $a0 ($sp)
li $a0 1

lw $a1 ($sp)
addi $sp $sp 4
sub $a0 $a1 $a0
sw $a0 8($fp)
j loop1
end1:
move $sp $fp 