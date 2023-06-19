package bg.rumen.bookstore.interfaces;

import bg.rumen.bookstore.exceptions.NoSuchBookWithIdException;
import bg.rumen.bookstore.models.Book;
import bg.rumen.bookstore.params.BookSearchParams;
import bg.rumen.bookstore.params.PageParams;
import bg.rumen.bookstore.models.PageResult;


public interface BookRepository {

    void addBook(Book book);

    void editBook(Book book) throws NoSuchBookWithIdException;

    PageResult<Book> getBooks(BookSearchParams searchParams, PageParams pageParams);

    Book getBookById(Integer id) throws NoSuchBookWithIdException;

    void deleteBookById(Integer id);

}
