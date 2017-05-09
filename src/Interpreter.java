import java.util.HashMap;

public class Interpreter {

  public Integer and(Integer left, Integer right){
    if(left == 0 && right == 0)
    return new Integer(0);
    else if((left == 0 && right == 1) || (left == 1 && right == 0))
    return new Integer(0);
    else if(left == 1 && right == 1)
    return new Integer(1);
    return null;
  }

  public Integer or(Integer left, Integer right){
    if(left == 0 && right == 0)
    return new Integer(0);
    if((left == 0 && right == 1) || (left == 1 && right == 0))
    return new Integer(1);
    if(left == 1 && right == 1)
    return new Integer(1);
    return null;
  }

  public HashMap<String, Integer> interpretCommand (CommandAST command, HashMap<String, Integer> initialStore )
  {
    if(command instanceof SeqAst ) {
      HashMap<String, Integer> newStore =
        interpretCommand (((SeqAst)command).first, initialStore);
      return interpretCommand (((SeqAst)command).second, newStore);
    }
    else if(command instanceof AssignAst) {
     initialStore.put( ((AssignAst)command).name, interpretExpr(((AssignAst) command).expr, initialStore) );
      return initialStore;
    }
    else if(command instanceof WhileAst){
      Integer n = interpretExpr( ((WhileAst)command).condition, initialStore);
      if(n == 0) {
        return initialStore;
      }
      else {
        HashMap<String, Integer> newStore = interpretCommand(((WhileAst)command).body, initialStore);
        return interpretCommand(command, newStore);
      }

    }
    else throw new RuntimeException("CommandAST subtype not recognised");

  }

  public Integer interpretExpr(ExprAST expr, HashMap<String, Integer> store){
    if(expr instanceof NumberAst) {
      return new Integer(((NumberAst)expr).value);
    }
    else if(expr instanceof TrueAst){
      return new Integer(1);
    }
    else if(expr instanceof FalseAst)
      return new Integer(0);
    else if(expr instanceof PlusAst ) {
      return interpretExpr(((PlusAst)expr).left, store) + interpretExpr(((PlusAst)expr).right, store);
    }
    else if(expr instanceof MinusAst ) {
      return interpretExpr(((MinusAst)expr).left, store) - interpretExpr(((MinusAst)expr).right, store);
    }
    else if(expr instanceof TimesAst ) {
      return interpretExpr(((TimesAst)expr).left, store) * interpretExpr(((TimesAst)expr).right, store);
    }
    else if(expr instanceof DivideAst ) {
      if(interpretExpr(((DivideAst)expr).right, store) !=0 )
        return interpretExpr(((DivideAst)expr).left, store) / interpretExpr(((DivideAst)expr).right, store);
     else throw new RuntimeException("Cannot divide by zero!");
    }
    else if(expr instanceof AndAst ) {
      return and(interpretExpr(((AndAst)expr).left, store), interpretExpr(((AndAst)expr).right, store));
    }
    else if(expr instanceof OrAst ) {
      return or(interpretExpr(((OrAst)expr).left, store), interpretExpr(((OrAst)expr).right, store));
    }
    else if(expr instanceof VariableAst) {
      return store.get(((VariableAst)expr).name);
    }
    else throw new RuntimeException("Expression not recognised");

  }

}
