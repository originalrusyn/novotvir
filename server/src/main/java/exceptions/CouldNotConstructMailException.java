package exceptions;

// @author Titov Mykhaylo (titov) on 16.05.2014.
public class CouldNotConstructMailException extends RuntimeException{
    public CouldNotConstructMailException(String message, Throwable cause) {
        super(message, cause);
    }
}
