package ittalentss11.traveller_online.controller.controller_exceptions;

public class WrongRequest extends Exception {

    public WrongRequest(String s){

        super(s);
    }
    public WrongRequest(){
        super("Wrong request");
    }
}
