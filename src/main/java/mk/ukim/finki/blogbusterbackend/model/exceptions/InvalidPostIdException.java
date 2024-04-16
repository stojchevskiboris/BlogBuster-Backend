package mk.ukim.finki.blogbusterbackend.model.exceptions;

public class InvalidPostIdException extends RuntimeException{
    public InvalidPostIdException() {
        super("Post doesn't exist");
    }

}
