package mk.ukim.finki.blogbusterbackend.model.exceptions;

public class InvalidCommentIdException extends RuntimeException{
    public InvalidCommentIdException() {
        super("Post doesn't exist");
    }

}
