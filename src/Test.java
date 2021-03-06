import java_cup.runtime.*;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

/**
    Testing class keeps track of how many tests run and passed.
*/

public class Test {

    private int testsRun = 0;
    private int testsPassed = 0;

    public void printState() {
        System.out.println("Passed " + testsPassed + "/" + testsRun + " tests.");
    }

    public boolean ok() {
        return testsPassed == testsRun;
    }

    /**
        This method runs tests on programms written in the implemented language.
        It throws exception if the expeced and returned output of interpreting
        the input program do not match.

        Input:  - filename, a String containing the name of the file containing the tested programm
                - expectedOutput, a hashmap of variable names and values that the input program should return
        Output: void

    */
    public void testExample(String filename, HashMap<String, Value> expectedOutput) throws Exception {
        Lexer lexer = new Lexer(new FileReader(filename));
    	Parser parser = new Parser(lexer);
    	CommandAST prog = (CommandAST) parser.parse().value;

        new TypeCheck().typecheck(new HashMap<String, Type>(), prog);

        Interpreter interpreter = new Interpreter();
    	HashMap<String, Value> store = interpreter.interpretCommand(prog, new HashMap<String, Value>());
        if (!store.equals(expectedOutput)) {
            System.out.println("Test for file: " + filename + " failed!");
        }
        else {
          testsPassed++;
        }
        testsRun++;
    }

    /**
        This is the main method, testing various programs in different files.
        Prints a record of how many tests were run and how many have passed.
    */

    public static void main(String[] args) throws Exception {
        Test test = new Test();

        HashMap<String, Value> reallysimpleExpected = new HashMap<>();
        reallysimpleExpected.put("x", new IntValue(1));
        test.testExample("examples/reallysimple", reallysimpleExpected);

        HashMap<String, Value> arithmeticExpected = new HashMap<>();
        arithmeticExpected.put("x", new IntValue(1));
        arithmeticExpected.put("y", new IntValue(10));
        arithmeticExpected.put("z", new IntValue(9));
        arithmeticExpected.put("w", new IntValue(27));
        arithmeticExpected.put("v", new BoolValue(false));
        test.testExample("examples/arithmetic", arithmeticExpected);

        HashMap<String, Value> simpleLoopExpected = new HashMap<>();
        simpleLoopExpected.put("x", new IntValue(10));
        test.testExample("examples/simpleloop", simpleLoopExpected);

        HashMap<String, Value> loopsExpected = new HashMap<>();
        loopsExpected.put("x", new IntValue(100));
        loopsExpected.put("y", new IntValue(50));
        test.testExample("examples/loops", loopsExpected);

        HashMap<String, Value> moreLoopsExpected = new HashMap<>();
        moreLoopsExpected.put("y", new IntValue(10));
        moreLoopsExpected.put("z", new IntValue(10));
        test.testExample("examples/moreloops", moreLoopsExpected);


        test.printState();
        System.exit(test.ok() ? 0 : 1);
    }

}
