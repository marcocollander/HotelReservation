package exceptions;

public class WrongOptionException extends Exception {

    public WrongOptionException() {
        super();
    }

    public WrongOptionException(String message) {
        super(message);
    }

    public int getCode() {
        return 101;
    }
}
