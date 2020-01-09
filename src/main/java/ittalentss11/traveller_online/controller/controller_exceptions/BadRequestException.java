package ittalentss11.traveller_online.controller.controller_exceptions;

public class BadRequestException extends Exception {

    public BadRequestException(String s){

        super(s);
    }
    public BadRequestException(){
        super("Wrong request");
    }
}
