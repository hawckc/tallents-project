package ittalentss11.traveller_online.controller.controller_exceptions;

import org.springframework.stereotype.Component;

public class RegisterCheck extends Exception {
    public RegisterCheck(String s) {
        super(s);
    }
    public RegisterCheck() {
        super("Something went wrong. Please verify your registration fields.");
    }
}
