import java.util.*;
abstract class ExprAST {
	public abstract HashSet<String> vars();
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
	public HashSet<String> vars(){
		HashSet vs = new HashSet<String>();
		vs.addAll(this.left.vars());
		vs.addAll(this.right.vars());
		return  vs;
	}
}

class TrueAst extends ExprAST{
	public String toString() {
		return "true";
		}
	public HashSet<String> vars(){
		return new HashSet<String>();
		}
	}

	class FalseAst extends ExprAST{
		public String toString() {
			return "false";
			}
		public HashSet<String> vars(){
			return new HashSet<String>();
			}
		}

	class AndAst extends ExprAST{
		public ExprAST left;
		public ExprAST right;

		public AndAst(ExprAST left, ExprAST right) {
			this.right = right;
			this.left = left;
		}
		public String toString() {
			return left + " && " + right;
		}
		public HashSet<String> vars(){
			HashSet vs = new HashSet<String>();
			vs.addAll(this.left.vars());
			vs.addAll(this.right.vars());
			return  vs;
		}
	}

	class OrAst extends ExprAST{
		public ExprAST left;
		public ExprAST right;

		public OrAst(ExprAST left, ExprAST right) {
			this.right = right;
			this.left = left;
		}
		public String toString() {
			return left + " || " + right;
		}
		public HashSet<String> vars(){
			HashSet vs = new HashSet<String>();
			vs.addAll(this.left.vars());
			vs.addAll(this.right.vars());
			return  vs;
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
	public HashSet<String> vars(){
		HashSet vs = new HashSet<String>();
		vs.addAll(this.left.vars());
		vs.addAll(this.right.vars());
		return  vs;
	}
}

class TimesAst extends ExprAST{
	public ExprAST left;
	public ExprAST right;

	public TimesAst(ExprAST left, ExprAST right) {
		this.right = right;
		this.left = left;
	}
	public String toString() {
		return left + " * " + right;
	}
	public HashSet<String> vars(){
		HashSet vs = new HashSet<String>();
		vs.addAll(this.left.vars());
		vs.addAll(this.right.vars());
		return  vs;
	}
}

class DivideAst extends ExprAST{
	public ExprAST left;
	public ExprAST right;

	public DivideAst(ExprAST left, ExprAST right) {
		this.right = right;
		this.left = left;
	}
	public String toString() {
		return left + " / " + right;
	}
	public HashSet<String> vars(){
		HashSet vs = new HashSet<String>();
		vs.addAll(this.left.vars());
		vs.addAll(this.right.vars());
		return  vs;
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
	public HashSet<String> vars(){
		return  new HashSet<String>();
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
	public HashSet<String> vars(){
		HashSet vs = new HashSet<String>();
		vs.add(this.name);
		return vs;
	}
}

// _____________ COMMANDS _________________

abstract class CommandAST{
	public abstract HashSet<String> vars();
}

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

	public HashSet<String> vars(){
		HashSet vs = new HashSet<String>();
		vs.addAll(this.condition.vars());
		vs.addAll(this.body.vars());
		return vs;
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

	public HashSet<String> vars(){ //aici
		HashSet vs = new HashSet<String>();
		vs.addAll(this.expr.vars());
		vs.add(this.name);
		return vs;
	}

}

class SeqAst extends CommandAST{
	public CommandAST first;
	public CommandAST second;

	public SeqAst(CommandAST first, CommandAST second) {
		this.first = first;
		this.second = second;
	}
	public String toString() {
		return first + " ; " + second;
	}

	public HashSet<String> vars(){ //aici
		HashSet vs = new HashSet<String>();
		vs.addAll(this.first.vars());
		vs.addAll(this.second.vars());
		return vs;
	}

}
