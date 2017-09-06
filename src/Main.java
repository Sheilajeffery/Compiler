import java_cup.runtime.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/*
This is the class containing the main method that creates instances of the Lexer,
Parser,TypeCheck, Interpreter and CodeGenerator. It reads and prints the input
program from a file, compiles it and prints out the results of interpreting the
source code. It also writes the generates assembly code to out.asm
*/

public class Main {

	public static void main(String[] args) throws Exception {

		Lexer lexer = new Lexer(new FileReader(args[0]));
		Parser parser = new Parser(lexer);
		CommandAST prog = (CommandAST) parser.parse().value;
		System.out.println(prog);

		HashMap<String,Type> environment = new HashMap<String, Type>();
		TypeCheck check = new TypeCheck();
		check.typecheck(environment, prog);

		HashMap<String, Value> store = new HashMap<String, Value>();
		Interpreter interpreter = new Interpreter();
	 	store = interpreter.interpretCommand(prog, store);

		System.out.println(store);


		ArrayList<String> variables = new ArrayList (prog.vars());
		System.out.println(variables);

		CodeGenerator gen = new CodeGenerator(variables);
		String mipsCode = gen.codeGenWithPreamble(prog);


		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("out.asm"), "utf-8"));
			writer.write(mipsCode);
			writer.close();
		}
		catch(IOException e) {}

	}

}
