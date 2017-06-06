import java.util.HashMap;

public class Interpreter {

/*  public BoolValue and(boolean left, boolean right){
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
*/
  public HashMap<String, Value> interpretCommand (CommandAST command, HashMap<String, Value> initialStore )
  {
    if(command instanceof SeqAst ) {
      HashMap<String, Value> newStore =
        interpretCommand (((SeqAst)command).first, initialStore);
      return interpretCommand (((SeqAst)command).second, newStore);
    }
    else if(command instanceof AssignAst) {
     initialStore.put( ((AssignAst)command).name, interpretExpr(((AssignAst) command).expr, initialStore) );
      return initialStore;
    }
    else if(command instanceof WhileAst){
      BoolValue n = (BoolValue)(interpretExpr( ((WhileAst)command).condition, initialStore));
      if(n.value == false) {
        return initialStore;
      }
      else {
        HashMap<String, Value> newStore = interpretCommand(((WhileAst)command).body, initialStore);
        return interpretCommand(command, newStore);
      }

    }
    else throw new RuntimeException("CommandAST subtype not recognised");

  }

  public Value interpretExpr(ExprAST expr, HashMap<String, Value> store){
    if(expr instanceof NumberAst) {
      return new IntValue(((NumberAst)expr).value);
    }
    else if(expr instanceof TrueAst){
      return new BoolValue(true);
    }
    else if(expr instanceof FalseAst)
      return new BoolValue(false);

    else if(expr instanceof PlusAst ) {
      return new IntValue(((IntValue)interpretExpr(((PlusAst)expr).left, store)).value + ((IntValue)interpretExpr(((PlusAst)expr).right, store)).value ) ;
    }
    else if(expr instanceof EqAst ) {
      if(interpretExpr(((EqAst)expr).left, store) instanceof IntValue)
        return new BoolValue(((IntValue)interpretExpr(((EqAst)expr).left, store)).value == ((IntValue)interpretExpr(((EqAst)expr).right, store)).value ) ;
      else
        return new BoolValue(((BoolValue)interpretExpr(((EqAst)expr).left, store)).value == ((BoolValue)interpretExpr(((EqAst)expr).right, store)).value ) ;
    }
    else if(expr instanceof LessThanAst ) {
      return new BoolValue(((IntValue)interpretExpr(((LessThanAst)expr).left, store)).value < ((IntValue)interpretExpr(((LessThanAst)expr).right, store)).value ) ;
    }
    else if(expr instanceof MinusAst ) {
      return new IntValue(((IntValue)interpretExpr(((MinusAst)expr).left, store)).value - ((IntValue)interpretExpr(((MinusAst)expr).right, store)).value ) ;
    }
    else if(expr instanceof TimesAst ) {
      return new IntValue(((IntValue)interpretExpr(((TimesAst)expr).left, store)).value * ((IntValue)interpretExpr(((TimesAst)expr).right, store)).value ) ;
    }
    else if(expr instanceof DivideAst ) {
      if(  ((IntValue)interpretExpr(((DivideAst)expr).right, store)).value !=0     )
        return new IntValue(((IntValue)interpretExpr(((DivideAst)expr).left, store)).value / ((IntValue)interpretExpr(((DivideAst)expr).right, store)).value ) ;
     else throw new RuntimeException("Cannot divide by zero!");
    }
    else if(expr instanceof AndAst ) {
      return new BoolValue( ((BoolValue)interpretExpr(((AndAst)expr).left, store)).value && ((BoolValue)interpretExpr(((AndAst)expr).right, store)).value );
    }
    else if(expr instanceof OrAst ) {
      return new BoolValue( ((BoolValue)interpretExpr(((OrAst)expr).left, store)).value ||  ((BoolValue)interpretExpr(((OrAst)expr).right, store)).value );
    }
    else if(expr instanceof VariableAst) {

     return store.get(((VariableAst)expr).name);

    }
    else throw new RuntimeException("Expression not recognised");

  }

}
