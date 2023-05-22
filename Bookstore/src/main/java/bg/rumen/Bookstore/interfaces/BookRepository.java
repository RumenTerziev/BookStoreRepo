package bg.rumen.Bookstore.interfaces;

import bg.rumen.Bookstore.exceptions.NoSuchBookWithIdException;
import bg.rumen.Bookstore.models.Book;
import bg.rumen.Bookstore.models.BookSearchParams;

import java.util.List;


public interface BookRepository {

    void addBook(Book book);

    void editBook(Book book) throws NoSuchBookWithIdException;

    List<Book> getBooks(BookSearchParams searchParams);

    Book getBookById(Integer id) throws NoSuchBookWithIdException;

    void deleteBookById(Integer id);

}
