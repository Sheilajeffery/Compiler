import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) throws Exception {

		String fileName = "src/input.txt";

		// FileReader reads text files in the default encoding.
		FileReader fileReader = new FileReader(fileName);

		// Always wrap FileReader in BufferedReader.
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		Yylex scanner = new Yylex(fileReader);

		ArrayList<Token> tokens = new ArrayList<Token>();
		Token t = scanner.yylex();


		while (t != null) {
			tokens.add(t);
			t = scanner.yylex();
		}

		// Always close files.
		bufferedReader.close();

	for(Token to: tokens)
		System.out.println(to);


	Parser parser = new Parser(tokens);

	HashMap<String, Integer> store = new HashMap<String, Integer>();

	CommandAST prog = parser.parse();
	System.out.println(prog);


	Interpreter interpreter = new Interpreter();
	 store = interpreter.interpretCommand(prog, store);


	System.out.println(store);


	ArrayList<String> variables = new ArrayList (prog.vars());
	System.out.println(variables);

System.out.println(variables.indexOf("y"));




	}


}
