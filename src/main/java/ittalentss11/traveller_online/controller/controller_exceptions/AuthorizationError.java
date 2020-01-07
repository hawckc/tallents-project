package ittalentss11.traveller_online.controller.controller_exceptions;

public class AuthorizationError extends Exception {
    public AuthorizationError(String s) {
        super(s);
    }
    public AuthorizationError() {
        super("You must be logged in.");
    }

}
