package ittalentss11.traveller_online.controller.controller_exceptions;
public class WrongCoordinatesException extends Exception {
    public WrongCoordinatesException(String s){
        super(s);
    }

    public WrongCoordinatesException(){
        super("Wrong coordinates format. The format should be two up-to-7-digit numbers separated by space.");
    }
}
