package excursions.utils;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(){
        super("A user with that username already exists");
    }
}
