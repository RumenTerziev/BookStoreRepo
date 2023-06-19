package bg.rumen.bookstore.exceptions;

public class NoSuchBookWithIdException extends RuntimeException {

    public NoSuchBookWithIdException(Integer id) {
        super(String.format("Book with Id %d not found", id));
    }
}
