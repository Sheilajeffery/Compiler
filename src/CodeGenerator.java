/*
This is the class used for generating mips assembly code.
It uses a list of variable names, and an int counter for the number of whiles
in the input program, so as to generate the appropiate start and end lablels
for each while loops.

*/

import java.util.*;
public class CodeGenerator {
    ArrayList<String> vars;
    int numberOfWhiles;

    public CodeGenerator(ArrayList<String> vars) {
        this.vars = vars;
        this.numberOfWhiles = 0;
    }
    public String generateLoopLabel() {
        return "loop" + numberOfWhiles;
    }
    public String generateEndLabel() {
        return "end"+numberOfWhiles;
    }

/*
The code generation method for Expressions.
Input:  an expression in the form of an ExprAST
Output: a String, representing the generated mips code
Throws RuntimeException for expressions for which no code generation
has been implemented yet.
*/

    public String codeGen(ExprAST e) {
        if(e instanceof NumberAst) {
            return "li $a0 " + ((NumberAst)e).value + "\n";
        }
        if(e instanceof TrueAst) {
            return "li $a0 " + 1 + "\n";
        }
        if(e instanceof FalseAst) {
            return "li $a0 " + 0 + "\n";
        }
        else if (e instanceof PlusAst) {
            String left = codeGen(((PlusAst)e).left);
            String right = codeGen(((PlusAst)e).right);
            return left
                + "subi $sp $sp 4\n"
                + "sw $a0 ($sp)\n"
                + right
                + "lw $a1 ($sp)\n"
                + "addi $sp $sp 4\n"
                + "add $a0 $a0 $a1\n";
        }
        else if (e instanceof EqAst) {
            String left = codeGen(((EqAst)e).left);
            String right = codeGen(((EqAst)e).right);
            return left
                + "subi $sp $sp 4\n"
                + "sw $a0 ($sp)\n"
                + right
                + "lw $a1 ($sp)\n"
                + "addi $sp $sp 4\n"
                + "subu $a0 $a0 $a1\n"
                + "sltu $a0 $zero $a0\n"
                + "xori $a0 $a0 1 \n";
        }
        else if (e instanceof LessThanAst) {
            String left = codeGen(((LessThanAst)e).left);
            String right = codeGen(((LessThanAst)e).right);
            return left
                + "subi $sp $sp 4\n"
                + "sw $a0 ($sp)\n"
                + right
                + "lw $a1 ($sp)\n"
                + "addi $sp $sp 4\n"
                + "slt $a0 $a1 $a0\n";
        }
        else if (e instanceof TimesAst) {
            String left = codeGen(((TimesAst)e).left);
            String right = codeGen(((TimesAst)e).right);
            return left
                + "subi $sp $sp 4\n"
                + "sw $a0 ($sp)\n"
                + right
                + "\nlw $a1 ($sp)\n"
                + "addi $sp $sp 4\n"
                + "mult $a0 $a1\n"
                + "mflo $a0\n";
        }
        else if (e instanceof DivideAst) {
            String left = codeGen(((DivideAst)e).left);
            String right = codeGen(((DivideAst)e).right);
            return left
                + "subi $sp $sp 4\n"
                + "sw $a0 ($sp)\n"
                + right
                + "lw $a1 ($sp)\n"
                + "addi $sp $sp 4\n"
                + "divu $a1 $a0\n"
                + "mflo $a0\n";
        }
        else if (e instanceof MinusAst) {
            String left = codeGen(((MinusAst)e).left);
            String right = codeGen(((MinusAst)e).right);
            return left
                + "subi $sp $sp 4\n"
                + "sw $a0 ($sp)\n"
                + right
                + "\nlw $a1 ($sp)\n"
                + "addi $sp $sp 4\n"
                + "sub $a0 $a1 $a0\n";
            }
        else if (e instanceof UnaryMinusAst) {
            return codeGen( new MinusAst(new NumberAst(0), ((UnaryMinusAst)e).e));
        }

        else if (e instanceof AndAst) {
            String left = codeGen(((AndAst)e).left);
            String right = codeGen(((AndAst)e).right);
            return left
                + "subi $sp $sp 4\n"
                + "sw $a0 ($sp)\n"
                + right
                + "\nlw $a1 ($sp)\n"
                + "addi $sp $sp 4\n"
                + "and $a0 $a1 $a0\n";
            }
        else if (e instanceof OrAst) {
            String left = codeGen(((OrAst)e).left);
            String right = codeGen(((OrAst)e).right);
            return left
                + "subi $sp $sp 4\n"
                + "sw $a0 ($sp)\n"
                + right
                + "\nlw $a1 ($sp)\n"
                + "addi $sp $sp 4\n"
                + "or $a0 $a1 $a0\n";
            }
        else if (e instanceof VariableAst)
            return "lw $a0 " +  vars.indexOf(((VariableAst)e).name)*4 + "($fp)\n";
        else throw new RuntimeException("codeGen not implemented for " + e.getClass().getName());
        }

/*
The code generation method for Commands.
Input:  a command in the form of a CommandAST
Output: a String, representing the generated mips code
Throws RuntimeException for commands for which no code generation
has been implemented yet.
*/

    public String codeGen(CommandAST c) {
        if(c instanceof SeqAst) {
            return codeGen(((SeqAst)c).first) + "\n" + codeGen(((SeqAst)c).second);
        }
        else if(c instanceof WhileAst) {
            this.numberOfWhiles++;
            String loopLabel =  generateLoopLabel();
            String endLabel = generateEndLabel();
            return loopLabel + ":\n"
                + codeGen(((WhileAst)c).condition)
                + "beqz $a0 "+ endLabel + "\n"
                + codeGen(((WhileAst)c).body)
                + "j "+ loopLabel + "\n"
                + endLabel + ":\n";
        }
        else if (c instanceof AssignAst) {
            return codeGen(((AssignAst)c).expr)
                + "sw $a0 "
                + vars.indexOf(((AssignAst)c).name)*4
                + "($fp)\n";
        }
        else throw new RuntimeException("codeGen not implemented for " + c.getClass().getName());
        }
/*
This method generates the preamble mips code.
The preamble code is similar fo any commands, the only thing that changes is
the memory space that needs to be allocated, which is the number of variables*4.
Input: CommandAST
*/
    public String codeGenWithPreamble(CommandAST c) {
        return  "\n.data\n\n.text\n"
            + "move $fp $sp \n"
            + "subi $sp $sp " + vars.size()*4 + "\n"
            +  codeGen(c)
            + "move $sp $fp ";
    }
    }
