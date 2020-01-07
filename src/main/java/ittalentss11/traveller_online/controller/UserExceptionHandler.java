package ittalentss11.traveller_online.controller;

import ittalentss11.traveller_online.controller.controller_exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//TODO ADD APPROPRIATE JSON RESPONSES FOR EXCEPTIONS
//TODO ADD GLOBAL HANDLERS SUCH AS SQL EXCEPTIONS AND OTHERS
//TODO STRUCTURE THE ERRORS LIKE KRASI DID FOR EASIER USAGE LATER ON

@ControllerAdvice
public class UserExceptionHandler {
    //===========REGISTRATION EXCEPTION HANDLERS===================//
    @ExceptionHandler(value = EmailTaken.class)
    public ResponseEntity<Object> emailTaken (){
        return new ResponseEntity<>("Email already exists, please pick another one.", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = UsernameTaken.class)
    public ResponseEntity<Object> usernameTaken (){
        return new ResponseEntity<>("This username already exists, please pick another one.", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = NoPassMatch.class)
    public ResponseEntity<Object> passMismatch(){
        return new ResponseEntity<>("Wrong password setup, please make sure to confirm your password.", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = EmailRegisterCheck.class)
    public ResponseEntity<Object> invalidMail(){
        return new ResponseEntity<>("You have entered an invalid email.", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = AuthorizationError.class)
    public ResponseEntity<Object> wrongUserPass(){
        return new ResponseEntity<>("Wrong credentials, please verify your username and password.", HttpStatus.BAD_REQUEST);
    }
}

