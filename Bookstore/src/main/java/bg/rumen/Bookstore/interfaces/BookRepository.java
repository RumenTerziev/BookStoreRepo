package bg.rumen.Bookstore.interfaces;

import bg.rumen.Bookstore.exceptions.NoSuchBookWithIdException;
import bg.rumen.Bookstore.models.Book;

import java.util.List;

public interface BookRepository {

    void addBook(Book book);

    void editBook(Book book, String id) throws NoSuchBookWithIdException;

    Book getBookById(String id) throws NoSuchBookWithIdException;

    List<Book> getAllBooksWithTitle(String title);

    List<Book> getBooks();

    void deleteBookById(String id);

}
