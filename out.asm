
.data

.text
move $fp $sp 
subi $sp $sp 8
li $a0 1
sw $a0 0($fp)
loop1:
lw $a0 0($fp)
subi $sp $sp 4
sw $a0 ($sp)
li $a0 10
lw $a1 ($sp)
addi $sp $sp 4
slt $a0 $a1 $a0
beqz $a0 end1
li $a0 1
sw $a0 4($fp)
loop2:
lw $a0 4($fp)
subi $sp $sp 4
sw $a0 ($sp)
li $a0 10
lw $a1 ($sp)
addi $sp $sp 4
slt $a0 $a1 $a0
beqz $a0 end2
lw $a0 4($fp)
subi $sp $sp 4
sw $a0 ($sp)
li $a0 1
lw $a1 ($sp)
addi $sp $sp 4
add $a0 $a0 $a1
sw $a0 4($fp)j loop2
end2:
lw $a0 0($fp)
subi $sp $sp 4
sw $a0 ($sp)
li $a0 1
lw $a1 ($sp)
addi $sp $sp 4
add $a0 $a0 $a1
sw $a0 0($fp)j loop1
end1:move $sp $fp 