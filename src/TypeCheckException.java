import java.lang.Exception;
/**
    Custom TypeCheckException thrown by both typecheck methods
    in the TypeCheck class
*/
public class TypeCheckException extends Exception {

    public TypeCheckException(String message) {
        super(message);
    }
}
