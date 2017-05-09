import java.util.*;

public class CodeGenerator{
CommandAST progAST;
ArrayList<String> vars;

public CodeGenerator(CommandAST progAST, ArrayList<String> vars)
{ this.progAST = progAST;
  this.vars = vars;
}

public String codeGen(ExprAST e){
  if(e instanceof NumberAst){
  return "li $a0 " + ((NumberAst)e).value + "\n";
  }
  if(e instanceof TrueAst){
  return "li $a0 " + 1 + "\n";
  }
  if(e instanceof FalseAst){
  return "li $a0 " + 0 + "\n";
  }
  else if (e instanceof PlusAst){
    String left = codeGen(((PlusAst)e).left);
    String right = codeGen(((PlusAst)e).right);
    return left
          + "\nsubi $sp $sp 4\n"
          + "sw $a0 ($sp)\n"
          + right
          + "lw $a1 ($sp)\n"
          + "addi $sp $sp 4\n"
          + "add $a0 $a0 $a1\n";
  }
  else if (e instanceof TimesAst){
    String left = codeGen(((TimesAst)e).left);
    String right = codeGen(((TimesAst)e).right);
    return left
          + "\nsubi $sp $sp 4\n"
          + "sw $a0 ($sp)\n"
          + right
          + "\nlw $a1 ($sp)\n"
          + "addi $sp $sp 4\n"
          + "mult $a0 $a1\n"
          + "mflo $a0\n";
  }
  else if (e instanceof DivideAst){
    String left = codeGen(((DivideAst)e).left);
    String right = codeGen(((DivideAst)e).right);
    return left
          + "\nsubi $sp $sp 4\n"
          + "sw $a0 ($sp)\n"
          + right
          + "lw $a1 ($sp)\n"
          + "addi $sp $sp 4\n"
          + "divu $a1 $a0\n"
          + "mflo $a0\n";
  }
  else if (e instanceof MinusAst){
    String left = codeGen(((MinusAst)e).left);
    String right = codeGen(((MinusAst)e).right);
    return left
          + "\nsubi $sp $sp 4\n"
          + "sw $a0 ($sp)\n"
          + right
          + "\nlw $a1 ($sp)\n"
          + "addi $sp $sp 4\n"
          + "sub $a0 $a1 $a0\n";
  }
  else if (e instanceof AndAst){
    String left = codeGen(((AndAst)e).left);
    String right = codeGen(((AndAst)e).right);
    return left
          + "\nsubi $sp $sp 4\n"
          + "sw $a0 ($sp)\n"
          + right
          + "\nlw $a1 ($sp)\n"
          + "addi $sp $sp 4\n"
          + "and $a0 $a1 $a0\n";
  }
  else if (e instanceof OrAst){
    String left = codeGen(((OrAst)e).left);
    String right = codeGen(((OrAst)e).right);
    return left
          + "\nsubi $sp $sp 4\n"
          + "sw $a0 ($sp)\n"
          + right
          + "\nlw $a1 ($sp)\n"
          + "addi $sp $sp 4\n"
          + "or $a0 $a1 $a0\n";
  }
  else if (e instanceof VariableAst)
  return "lw $a0 " +  vars.indexOf(((VariableAst)e).name)*4 + "($fp)\n";
  else return "ERROR";
}

public String codeGen(CommandAST c){
if(c instanceof SeqAst){
  return codeGen(((SeqAst)c).first) + "\n" + codeGen(((SeqAst)c).second);
}
  else if(c instanceof WhileAst)
  { return "loop:\n" +
          codeGen(((WhileAst)c).condition)+
          "beqz $a0 end\n" +
          "body:\n" +
          codeGen(((WhileAst)c).body)+
          "j loop\n" +
          "end:";
  }
  else if (c instanceof AssignAst){
    return codeGen(((AssignAst)c).expr)
    +"sw $a0 "
    + vars.indexOf(((AssignAst)c).name)*4
    + "($fp)\n";

  }
    else return "ERROR";

}

}
