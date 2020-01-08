package ittalentss11.traveller_online.controller;

import ittalentss11.traveller_online.controller.controller_exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO ADD APPROPRIATE JSON RESPONSES FOR EXCEPTIONS
//TODO ADD GLOBAL HANDLERS SUCH AS SQL EXCEPTIONS AND OTHERS

@ControllerAdvice
public class UserExceptionHandler {
    //===========REGISTRATION EXCEPTION HANDLERS===================//
    @ExceptionHandler(value = EmailTaken.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> emailTaken (){
        return new ResponseEntity<>("Email already exists, please pick another one.", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = UsernameTaken.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> usernameTaken (){
        return new ResponseEntity<>("This username already exists, please pick another one.", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = NoPassMatch.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> passMismatch(){
        return new ResponseEntity<>("Wrong password setup, please make sure to confirm your password.", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = EmailRegisterCheck.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> invalidMail(){
        return new ResponseEntity<>("You have entered an invalid email.", HttpStatus.BAD_REQUEST);
    }
    //LOGIN EXCEPTION HANDLER
    @ExceptionHandler(value = AuthorizationError.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> wrongUserPass(){
        return new ResponseEntity<>("Wrong credentials, please verify your username and password.", HttpStatus.BAD_REQUEST);
    }

    //THIS IS WHAT THE NEXT EXCEPTION SHOULD LOOK LIKE:

//    @ExceptionHandler(SQLException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorDTO handleSQLExceptions(Exception e){
//        ErrorDTO errorDTO = new ErrorDTO(
//                e.getMessage(),
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                LocalDateTime.now(),
//                e.getClass().getName());
//        return errorDTO;
//    }
}

