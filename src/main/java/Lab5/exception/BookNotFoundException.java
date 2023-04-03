package Lab5.exception;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(String msg) {
        super(msg);
    }
}
