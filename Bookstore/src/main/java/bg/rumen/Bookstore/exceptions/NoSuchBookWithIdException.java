package bg.rumen.Bookstore.exceptions;

public class NoSuchBookWithIdException extends RuntimeException {

    public NoSuchBookWithIdException(String id) {
        super(String.format("Book with Id %s not found", id));
    }
}
