abstract class ExprAST {
}

class PlusAst extends ExprAST {
	public ExprAST left;
	public ExprAST right;

	public PlusAst(ExprAST left, ExprAST right) {
		this.right = right;
		this.left = left;
	}
	public String toString() {
		return left + " + " + right;
	}
}

class MinusAst extends ExprAST {
	public ExprAST left;
	public ExprAST right;

	public MinusAst(ExprAST left, ExprAST right) {
		this.right = right;
		this.left = left;
	}
	public String toString() {
		return left + " - " + right;
	}
}

class NumberAst extends ExprAST {
	public int value;

	public NumberAst(int value) {
		this.value = value;
	}
	public String toString() {
		return value + "";
	}
}

class VariableAst extends ExprAST {
	public String name;

	public VariableAst(String name) {
		this.name = name;
	}
	public String toString() {
		return name;
	}
}

abstract class CommandAST{}

class WhileAst extends CommandAST{
	public ExprAST condition;
	public CommandAST body;
	
	public WhileAst(ExprAST condition, CommandAST body){
		this.condition = condition;
		this.body = body;
	}
	public String toString() {
		return "while (" + condition + ") {" + body + "}";
	}
	
}
class AssignAst extends CommandAST{
	public String  name;
	public ExprAST expr;
	
	public AssignAst(String name, ExprAST expr) {
		this.name = name;
		this.expr = expr;
	}
	public String toString() {
		return name + " := " + expr;
	}
	
} 

class SeqAst extends CommandAST{
	public CommandAST command1;
	public CommandAST command2;

	public SeqAst(CommandAST command1, CommandAST command2) {
		this.command1 = command1;
		this.command2 = command2;
	}
	public String toString() {
		return command1 + " ; " + command2;
	}
	
}
	

