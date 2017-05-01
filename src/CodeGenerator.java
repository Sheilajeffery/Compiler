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
  return "li $a0 " + n + "\n";}

  else if (e instanceof PlusAst){
    String left = codeGen(((PlusAst)e).left);
    String right = codeGen(((PlusAst)e).right);
    return left
          + "subi $sp $sp 4"
          + "sw $a0 ($sp)"
          + right
          + "lw $a1 ($sp)"
          + "addi $sp $sp 4"
          + "add $a0 $s0 $a1";
  }
  else if (e instanceof VariableAst)
  return "lw $a0 " + (((vars.size()-1) - vars.indexOf(((VariableAst)e).name))*4) + " ($sp)";
}

public String CodeGen(CommandAST c){
if(c instanceof SeqAst){
  return codeGen(((SeqAst)c).first) + "\n" + codeGen((SeqAst)c).second;}

  else if(c instanceof WhileAst)
  { return "loop: " +
          codeGen(((WhileAst)c).condition)+
          "beqz $a0 end" +
          "body:" +
          codeGen(((SeqAst)c).body)+
          "j loop" +
          "end:";
  }
  else if (c instanceof AssignAst){
    return codeGen(((AssignAst)c).expr) +
    "sw $a0 " + (((vars.size()-1) - vars.indexOf(((VariableAst)e).name))*4) + " ($sp)";
  }
}

}
