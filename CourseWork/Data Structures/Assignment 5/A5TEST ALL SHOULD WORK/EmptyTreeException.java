/**
 *  A class of runtime exceptions thrown by methods to indicate that a tree is empty
 */
public class EmptyTreeException extends RuntimeException {

    public EmptyTreeException() {
        this(null);
    }

    public EmptyTreeException(String message) {
        super(message);
    }

}


