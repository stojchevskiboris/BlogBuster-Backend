package mk.ukim.finki.blogbusterbackend.model.exceptions;

public class InvalidUserIdException extends RuntimeException{
    public InvalidUserIdException() {
        super("User doesn't exist");
    }
}
