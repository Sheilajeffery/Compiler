import java_cup.runtime.*;
//import java.io.FileReader;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

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
