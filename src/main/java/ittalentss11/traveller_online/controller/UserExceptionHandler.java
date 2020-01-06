package ittalentss11.traveller_online.controller;

import ittalentss11.traveller_online.controller.controller_exceptions.EmailTaken;
import ittalentss11.traveller_online.controller.controller_exceptions.NoPassMatch;
import ittalentss11.traveller_online.controller.controller_exceptions.UsernameTaken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
}

