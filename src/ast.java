abstract class ExprAST {
}

class PlusAst extends ExprAST {
	public ExprAST left;
	public ExprAST right;

	public PlusAst(ExprAST left, ExprAST right) {
		this.right = right;
		this.left = left;
	}
}

class MinusAst extends ExprAST {
	public ExprAST left;
	public ExprAST right;

	public MinusAst(ExprAST left, ExprAST right) {
		this.right = right;
		this.left = left;
	}
}

class NumberAst extends ExprAST {
	public int value;

	public NumberAst(int value) {
		this.value = value;
	}
}

class VariableAst extends ExprAST {
	public String name;

	public VariableAst(String name) {
		this.name = name;
	}
}

abstract class CommandAST{}

class VariableNameAst extends CommandAST{
	public String name;

	public VariableNameAst(String name) {
		this.name = name;
	}
	
	
}

class WhileAst extends CommandAST{
	public ExprAST condition;
	public CommandAST body;
	
	public WhileAst(ExprAST condition, CommandAST body){
		this.condition = condition;
		this.body = body;
	}
}
class AssignAst extends CommandAST{
	public ExprAST expr1;
	public ExprAST expr2;
	
	public AssignAst(ExprAST expr1, ExprAST expr2) {
		this.expr1 = expr1;
		this.expr2 = expr2;
	}
} 

class SeqAst extends CommandAST{
	public CommandAST command1;
	public CommandAST command2;

	public SeqAst(CommandAST command1, CommandAST command2) {
		this.command1 = command1;
		this.command2 = command2;
	}
	
}
	

