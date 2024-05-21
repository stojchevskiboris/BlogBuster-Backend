package mk.ukim.finki.blogbusterbackend.model.exceptions;

public class ImageNotFoundException extends RuntimeException{
    public ImageNotFoundException() {
        super("Image cannot be found");
    }
}
