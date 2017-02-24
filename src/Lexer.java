import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

public class Lexer {

	public static void main(String[] args) throws Exception {

		String fileName = "C:\\Users\\SheilaS\\workspace\\Compiler\\src\\input.txt";

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
	
	
	Parser p = new Parser(tokens);
	
	System.out.println(p.parse());
	
	
	}

	
}
