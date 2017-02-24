import java.util.ArrayList;

public class Parser {
	public ArrayList<Token> tokenList = new ArrayList<Token>();
	public int idx = 0 ;

	public Parser(ArrayList<Token> tokenList){
		this.tokenList = tokenList;
	}
	
	public ExprAST parseE(){
	
		if(tokenList.get(idx) instanceof Number)
			return new NumberAst( ((Number)(tokenList.get(idx))).value );
		else
			if(tokenList.get(idx) instanceof VarName)
				return new VariableAst( ((VarName)(tokenList.get(idx))).name);
			else
				if(tokenList.get(idx) instanceof Number && tokenList.get(idx + 1)instanceof Plus){
					idx+=2;
					return new PlusAst(new NumberAst( ((Number)(tokenList.get(idx))).value ),(NumberAst)( parseE()) );
				}
				else
					if(tokenList.get(idx) instanceof Number && tokenList.get(idx + 1)instanceof Minus){					
						idx+=2;
						return new MinusAst(new NumberAst( ((Number)(tokenList.get(idx))).value ),(NumberAst)( parseE()) );
					}
					else
						throw new RuntimeException("ParseE failed. Input wrong!");
	}
	
	public CommandAST parse(){
		
		if (tokenList.get(idx) instanceof VarName && tokenList.get(idx+1) instanceof Assign)
			{	VariableAst var =  new VariableAst( ((VarName)(tokenList.get(idx))).name );
			 	idx+=2;
			 	AssignAst ast =  new AssignAst( var, parseE());
			 	if(idx == tokenList.size()-1 )
				 return ast;
			 else
				 if(tokenList.get(idx+1) instanceof Semicolon)
					return new SeqAst(ast,parse());
				 else 
					 throw new RuntimeException();
			}
		else
			if(tokenList.get(idx) instanceof While && tokenList.get(idx+1) instanceof Lbr ){
				idx += 2;
				ExprAST condition = parseE();
				if (tokenList.get(idx+1) instanceof Rbr && tokenList.get(idx+2) instanceof Lcurl)
				{	idx+=3;
					CommandAST body = parse();
					
					if(tokenList.get(idx+1) instanceof Rcurl && idx+1 == tokenList.size()-1)
						return new WhileAst(condition, body);
					else
						if (tokenList.get(idx+1) instanceof Semicolon){
							idx+=3;
							return new SeqAst(new WhileAst(condition, body), parse());
							
						}
			
						else
							throw new RuntimeException();
					
				} 
			}	


}

}