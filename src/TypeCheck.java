import java.util.HashMap;
class TypeCheck{


  void typecheck(HashMap<String,Type> env, CommandAST c) throws TypeCheckException {
    if(c instanceof WhileAst)
      {
        if (! (typecheck(env,((WhileAst)c).condition) instanceof Bool))
              throw new TypeCheckException("The while condition must be a boolean!");
        typecheck(env,((WhileAst)c).body);
      }
    else if(c instanceof AssignAst)
      { if(env.containsKey(((AssignAst)c).name))
          { if ( env.get(((AssignAst)c).name) instanceof Int && typecheck(env,((AssignAst)c).expr) instanceof Int )
              return;
           else if (env.get(((AssignAst)c).name) instanceof Bool && typecheck(env,((AssignAst)c).expr) instanceof Bool )
           return;
           else throw new TypeCheckException("Type error!");
          }
        else
        { env.put(((AssignAst)c).name, typecheck(env, ((AssignAst)c).expr));
          return;
        }
      }
    else if( c instanceof SeqAst)
      { typecheck(env, ((SeqAst)c).first);
        typecheck(env, ((SeqAst)c).second);
        return;
      }
    }

//TYPECHECK FOR EXPRESSIONS

  Type typecheck(HashMap<String,Type> env, ExprAST e) throws TypeCheckException{
    if(e instanceof VariableAst)
      return env.get(((VariableAst)e).name);
    else if(e instanceof NumberAst)
      return  new Int();
    else if(e instanceof FalseAst || e instanceof TrueAst)
        return new Bool();
    else if(e instanceof PlusAst)
        {
          if(typecheck(env,((PlusAst)e).left) instanceof Int && typecheck(env,((PlusAst)e).right) instanceof Int)
          return new Int();
          else throw new TypeCheckException("Types don't match!");
        }
    else if(e instanceof UnaryMinusAst)
        { if(typecheck(env,((UnaryMinusAst)e).e) instanceof Int)
          return new Int();
          else
           throw new TypeCheckException("Types don't match!");
        }
    else if(e instanceof EqAst)
        {
          if((typecheck(env,((EqAst)e).left) instanceof Int && typecheck(env,((EqAst)e).right) instanceof Int)
          || ((typecheck(env,((EqAst)e).left) instanceof Bool && typecheck(env,((EqAst)e).right) instanceof Bool)))
          return new Bool();
          else throw new TypeCheckException("Types don't match!");
        }
        else if(e instanceof LessThanAst)
            {
              if(typecheck(env,((LessThanAst)e).left) instanceof Int && typecheck(env,((LessThanAst)e).right) instanceof Int)
                return new Bool();
              else throw new TypeCheckException("Types don't match!");
            }
    else if(e instanceof MinusAst)
        {
          if(typecheck(env,((MinusAst)e).left) instanceof Int && typecheck(env,((MinusAst)e).right) instanceof Int)
            return new Int();
          else throw new TypeCheckException("Types don't match!");
        }
    else if(e instanceof DivideAst)
        {
          if(typecheck(env,((DivideAst)e).left) instanceof Int && typecheck(env,((DivideAst)e).right) instanceof Int)
              return new Int();
              else throw new TypeCheckException("Types don't match!");
        }
    else if(e instanceof TimesAst)
        {
          if(typecheck(env,((TimesAst)e).left) instanceof Int && typecheck(env,((TimesAst)e).right) instanceof Int)
              return new Int();
              else throw new TypeCheckException("Types don't match!");
        }
    else if(e instanceof AndAst)
        {
          if(typecheck(env,((AndAst)e).left) instanceof Bool && typecheck(env,((AndAst)e).right) instanceof Bool)
            return new Bool();
          else throw new TypeCheckException("Types don't match!");
        }
    else if(e instanceof OrAst)
        {
          if(typecheck(env,((OrAst)e).left) instanceof Bool && typecheck(env,((OrAst)e).right) instanceof Bool)
            return new Bool();
          else throw new TypeCheckException("Types don't match!");
        }
    else throw new TypeCheckException("Type Error!");
  }
}
