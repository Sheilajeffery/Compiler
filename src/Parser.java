import java.util.ArrayList;

public class Parser {
	public ArrayList<Token> tokenList = new ArrayList<Token>();
	public int idx = 0;

	public Parser(ArrayList<Token> tokenList) {
		this.tokenList = tokenList;
	}

	public boolean finished() {
		return idx == tokenList.size();
	}

	public boolean atLastToken() {
		return idx == tokenList.size() - 1;
	}

	public Token currentToken() {
		return tokenList.get(idx);
	}

	public Token nextToken() {
		return tokenList.get(idx+1);
	}

	public String parseName() {
		if (!finished() && currentToken() instanceof VarName) {
			String name = ((VarName) (currentToken())).name;
			idx++;
			return name;
		}
		else throw new RuntimeException("parseName: VarName token not found");
	}

	public ExprAST parseE() {
		if (currentToken() instanceof Number && atLastToken()) {
			NumberAst numast = new NumberAst(((Number) currentToken()).value);
			idx++;
			return numast;
		}
		else if (currentToken() instanceof VarName && atLastToken()) {
			return new VariableAst(parseName());
		}
		else if (currentToken() instanceof Number && nextToken() instanceof Plus) {
			NumberAst num = new NumberAst(((Number) (currentToken())).value);
			idx += 2;
			return new PlusAst(num , parseE());
		}
		else if (currentToken() instanceof Number && nextToken() instanceof Minus) {
			NumberAst num = new NumberAst(((Number) (currentToken())).value);
			idx += 2;
			return new MinusAst(num, parseE());
		}
		else if(currentToken() instanceof VarName && nextToken() instanceof Minus) {
			VariableAst var = new VariableAst(((VarName)(currentToken())).name);
			idx +=2;
			return new MinusAst(var, parseE());
		}
		else if(currentToken() instanceof VarName && nextToken() instanceof Plus) {
			VariableAst var = new VariableAst(((VarName)(currentToken())).name);
			idx +=2;
			return new PlusAst(var, parseE());
		}
		else if (currentToken() instanceof Number) {
			NumberAst numast = new NumberAst(((Number) (currentToken())).value);
			idx++;
			return numast;
		}
		else if (currentToken() instanceof VarName) {
			VariableAst var = new VariableAst(((VarName) (currentToken())).name);
			idx++;
			return var;
		}
		else {
			throw new RuntimeException("ParseE failed. Input wrong!");
		}
	}

	public CommandAST parse() {

		if (currentToken() instanceof VarName && nextToken() instanceof Assign) {
			String var = ((VarName) (currentToken())).name;
			idx += 2;
			AssignAst ast = new AssignAst(var, parseE());
			if (idx < tokenList.size() && currentToken() instanceof Semicolon) {
				if(currentToken() instanceof Semicolon && !atLastToken())
					{	idx++;
						return new SeqAst(ast, parse());
					}
					else
					throw new RuntimeException("Parse failed. Unexpected Semicolon at the end!");
			}
			else return ast;

		} else if (currentToken() instanceof While && nextToken() instanceof Lbr) {
			idx += 2;
			ExprAST condition = parseE();
			if (currentToken() instanceof Rbr && nextToken() instanceof Lcurl) {
				idx += 2;
				CommandAST body = parse();

				if (currentToken() instanceof Rcurl && idx == tokenList.size() - 1)
					return new WhileAst(condition, body);

				else if (currentToken() instanceof Rcurl && nextToken() instanceof Semicolon) {
					idx += 1;
					if(currentToken() instanceof Semicolon && !atLastToken())
					{	idx++;
						return new SeqAst(new WhileAst(condition, body), parse());
					}
					else{
					throw new RuntimeException("Unexpected semicolon at the end!");
					}
				}

				else if (currentToken() instanceof Rcurl)
					return new WhileAst(condition, body);
				else throw new RuntimeException("ParseC failed. Expeced a '{''");

			} else
				throw new RuntimeException("ParseC failed.While is incorect!");
		} else
			throw new RuntimeException("ParseC failed. While statement wrong");

	}

}
