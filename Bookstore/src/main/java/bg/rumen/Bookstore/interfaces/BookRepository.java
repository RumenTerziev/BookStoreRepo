package bg.rumen.Bookstore.interfaces;

import bg.rumen.Bookstore.models.Book;

import java.util.List;

public interface BookRepository {
    void addBook(Book book);
    void editBook(Book book, Integer id);
    Book getBookById(Integer id);
    Book getBookByTitle(String title);
    List<Book> getBooks();
    void deleteBookById(Integer id);
}
