import java.util.HashMap;

public class Interpreter {

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
    else if(expr instanceof PlusAst ) {
      return interpretExpr(((PlusAst)expr).left, store) + interpretExpr(((PlusAst)expr).right, store);
    }
    else if(expr instanceof MinusAst ) {
      return interpretExpr(((MinusAst)expr).left, store) - interpretExpr(((MinusAst)expr).right, store);
    }
    else if(expr instanceof VariableAst) {
      return store.get(((VariableAst)expr).name);
    }
    else throw new RuntimeException("Expression not recognised");

  }

}
