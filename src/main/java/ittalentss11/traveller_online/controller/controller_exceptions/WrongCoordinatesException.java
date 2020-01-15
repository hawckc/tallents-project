package ittalentss11.traveller_online.controller.controller_exceptions;
public class WrongCoordinatesException extends Exception {
    public WrongCoordinatesException(String s){
        super(s);
    }

    public WrongCoordinatesException(){
        super("Wrong coordinates format. Simply type the coordinates without dots or commas." +
                "The format should be two up-to-9-digit numbers separated by space.");
    }
}
