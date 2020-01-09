package ittalentss11.traveller_online.controller.controller_exceptions;

public class MissingCategoryException extends Exception {
    public MissingCategoryException(String s){
        super(s);

    }
    public MissingCategoryException(){
        super("Invalid category");
    }

}
