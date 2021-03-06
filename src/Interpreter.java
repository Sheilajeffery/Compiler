import java.util.HashMap;
/**
    The Interpreter translates the input program into Java
*/
public class Interpreter {

    /**
        This method interprets commands. Loops through the command AST and calls
        the mehtod for interpreting expressions which changes the values of the
        variables in the store.
        Input: Command and the initial store of variables and values
        Output: new store of variables and values resulting from
                interpreting the input program

    */
    public HashMap<String, Value> interpretCommand (CommandAST command, HashMap<String, Value> initialStore ) {
        if(command instanceof SeqAst ) {
            HashMap<String, Value> newStore =
            interpretCommand (((SeqAst)command).first, initialStore);
            return interpretCommand (((SeqAst)command).second, newStore);
        }
        else if(command instanceof AssignAst) {
            initialStore.put( ((AssignAst)command).name, interpretExpr(((AssignAst) command).expr, initialStore) );
            return initialStore;
        }
        else if(command instanceof WhileAst) {
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

    /**
        This method interprets expressions and changes the values of the
        variables in the store.
        Input: an expression and the initial store of variables and values
        Output: new store of variables and values resulting from
                interpreting the expression

    */
    public Value interpretExpr(ExprAST expr, HashMap<String, Value> store) {
        if(expr instanceof NumberAst) {
            return new IntValue(((NumberAst)expr).value);
        }
        else if(expr instanceof TrueAst) {
            return new BoolValue(true);
        }
        else if(expr instanceof FalseAst) {
            return new BoolValue(false);
        }
        if(expr instanceof UnaryMinusAst) {
            return new IntValue( -((IntValue)interpretExpr(((UnaryMinusAst)expr).e, store)).value);
        }
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
