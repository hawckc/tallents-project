package ittalentss11.traveller_online.controller;

import ittalentss11.traveller_online.controller.controller_exceptions.*;
import ittalentss11.traveller_online.model.dto.ExceptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

//TODO ADD GLOBAL HANDLERS SUCH AS SQL EXCEPTIONS AND OTHERS

@ControllerAdvice
public class UserExceptionHandler {
    //===========REGISTRATION EXCEPTION HANDLERS===================//
    @ExceptionHandler(value = EmailTaken.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionDTO> emailTaken(Exception e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                e.getClass().getName());
        return new ResponseEntity<ExceptionDTO>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = UsernameTaken.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionDTO> usernameTaken(Exception e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                e.getClass().getName());
        return new ResponseEntity<ExceptionDTO>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = NoPassMatch.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionDTO> passMismatch(Exception e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                e.getClass().getName());
        return new ResponseEntity<ExceptionDTO>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RegisterCheck.class)
    public ResponseEntity<ExceptionDTO> registrationExceptions(Exception e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                e.getClass().getName());
        return new ResponseEntity<ExceptionDTO>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }


    //LOGIN EXCEPTION HANDLER
    @ExceptionHandler(value = AuthorizationError.class)
    public ResponseEntity<ExceptionDTO> loginException(Exception e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now(),
                e.getClass().getName());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.UNAUTHORIZED);
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

