package ittalentss11.traveller_online.controller;
import ittalentss11.traveller_online.controller.controller_exceptions.*;
import ittalentss11.traveller_online.model.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.sql.SQLException;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.io.IOException;
import java.time.LocalDateTime;

//TODO ADD GLOBAL HANDLERS SUCH AS SQL EXCEPTIONS AND OTHERS

@ControllerAdvice
public class GlobalExceptionHandler {
    //===========REGISTRATION EXCEPTION HANDLERS===================//
    @ExceptionHandler(value = EmailTakenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionDTO> emailTaken(Exception e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                e.getClass().getName());
        return new ResponseEntity<ExceptionDTO>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = UsernameTakenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionDTO> usernameTaken(Exception e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                e.getClass().getName());
        return new ResponseEntity<ExceptionDTO>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = NoPassMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionDTO> passMismatch(Exception e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                e.getClass().getName());
        return new ResponseEntity<ExceptionDTO>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RegisterCheckException.class)
    public ResponseEntity<ExceptionDTO> registrationExceptions(Exception e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                e.getClass().getName());
        return new ResponseEntity<ExceptionDTO>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDTO> badRequestException(Exception e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                e.getClass().getName());
        return new ResponseEntity<ExceptionDTO>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
    //=================LOGIN EXCEPTION HANDLER=============
    @ExceptionHandler(value = AuthorizationException.class)
    public ResponseEntity<ExceptionDTO> loginException(Exception e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now(),
                e.getClass().getName());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.UNAUTHORIZED);
    }
    //POST CATEGORY handle
    @ExceptionHandler(value = MissingCategoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionDTO> categoryException(Exception e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                e.getClass().getName());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
    //POST COORDINATES handle
    @ExceptionHandler(value = WrongCoordinatesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionDTO> coordinatesException(Exception e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                e.getClass().getName());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
    //POST PICTURE handle
    @ExceptionHandler(value = PostPicturePerPostException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionDTO> postPicturePerPost(Exception e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                e.getClass().getName());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionDTO> postPictureIO(Exception e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                e.getClass().getName());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }

    //==========GENERAL EXCEPTIONS=================//

    //SQL EXCEPTION
    //TODO IS THIS OK??? SHOULD I CHANGE THE CAPTION?
    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity<ExceptionDTO> sqlException(Exception e){
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                "Oops, something went wrong, try again later!",
                HttpStatus.I_AM_A_TEAPOT.value(),
                LocalDateTime.now(),
                e.getClass().getName());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.I_AM_A_TEAPOT);
    }
}

