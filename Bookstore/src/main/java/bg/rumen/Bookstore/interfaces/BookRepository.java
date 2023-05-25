package bg.rumen.Bookstore.interfaces;

import bg.rumen.Bookstore.exceptions.NoSuchBookWithIdException;
import bg.rumen.Bookstore.models.Book;
import bg.rumen.Bookstore.models.params.BookSearchParams;
import bg.rumen.Bookstore.models.params.PageParams;
import bg.rumen.Bookstore.models.PageResult;


public interface BookRepository {

    void addBook(Book book);

    void editBook(Book book) throws NoSuchBookWithIdException;

    PageResult<Book> getBooks(BookSearchParams searchParams, PageParams pageParams);

    Book getBookById(Integer id) throws NoSuchBookWithIdException;

    void deleteBookById(Integer id);

}
