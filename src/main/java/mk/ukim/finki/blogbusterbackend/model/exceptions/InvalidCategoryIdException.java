package mk.ukim.finki.blogbusterbackend.model.exceptions;

public class InvalidCategoryIdException extends RuntimeException{

    public InvalidCategoryIdException() {
        super("Category doesn't exist");
    }
}
