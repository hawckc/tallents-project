package ittalentss11.traveller_online.controller.controller_exceptions;

import org.springframework.stereotype.Component;

public class RegisterCheckException extends Exception {
    public RegisterCheckException(String s) {
        super(s);
    }
    public RegisterCheckException() {
        super("Something went wrong. Please verify your registration fields.");
    }
}
