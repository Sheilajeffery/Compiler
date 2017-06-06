import java_cup.runtime.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) throws Exception {

		Lexer lexer = new Lexer(new FileReader(args[0]));
		Parser parser = new Parser(lexer);
		CommandAST prog = (CommandAST) parser.parse().value;
		System.out.println(prog);

		HashMap<String, Value> store = new HashMap<String, Value>();


		Interpreter interpreter = new Interpreter();
	 	store = interpreter.interpretCommand(prog, store);


		System.out.println(store);


		ArrayList<String> variables = new ArrayList (prog.vars());
		System.out.println(variables);

		HashMap<String,Type> env = new HashMap<String, Type>();
		TypeCheck check = new TypeCheck();
		check.typecheck(env, prog);


	CodeGenerator gen = new CodeGenerator(prog, variables);
		String mipsCode = "move $fp $sp \nsubi $sp $sp " + variables.size()*4 + "\n"
								+  gen.codeGen(prog)
								+ "move $sp $fp ";
								System.out.println(mipsCode);

	}

}
