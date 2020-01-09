package ittalentss11.traveller_online.controller.controller_exceptions;

public class AuthorizationException extends Exception {
    public AuthorizationException(String s) {
        super(s);
    }
    public AuthorizationException() {
        super("You must be logged in.");
    }

}
