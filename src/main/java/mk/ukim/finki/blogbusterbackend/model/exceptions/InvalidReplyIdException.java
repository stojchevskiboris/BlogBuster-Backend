package mk.ukim.finki.blogbusterbackend.model.exceptions;

public class InvalidReplyIdException extends RuntimeException{
    public InvalidReplyIdException() {
        super("Reply doesn't exist");
    }
}
