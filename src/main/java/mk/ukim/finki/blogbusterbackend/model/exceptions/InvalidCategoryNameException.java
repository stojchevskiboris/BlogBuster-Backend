package mk.ukim.finki.blogbusterbackend.model.exceptions;

public class InvalidCategoryNameException extends RuntimeException{
    public InvalidCategoryNameException() {
        super("Category name doesn't exist");
    }
}
