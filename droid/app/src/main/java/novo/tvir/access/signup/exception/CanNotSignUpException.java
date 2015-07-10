package novo.tvir.access.signup.exception;

// @author Titov Mykhaylo on 10.07.2015.
public class CanNotSignUpException extends RuntimeException {

    public CanNotSignUpException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
