package ittalentss11.traveller_online.controller.controller_exceptions;

public class PostPicturePerPostException extends Exception {
    public PostPicturePerPostException(){
        super("You cannot upload more than three pcitures per post");
    }
}
