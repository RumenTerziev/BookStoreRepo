package bg.rumen.Bookstore.repository;

import bg.rumen.Bookstore.interfaces.BookRepository;
import bg.rumen.Bookstore.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class InMemBookRepository implements BookRepository {

    @Autowired
    private List<Book> books;

    private Integer newId = 3;

    public InMemBookRepository() {
        this.books = new ArrayList<>();
    }

    @Override
    public void addBook(Book book) {
        book.setId(newId);
        newId++;
        this.books.add(book);
    }


    @Override
    public Book getBookById(Integer id) {
        return this.books.stream().filter(b -> Objects.equals(b.getId(), id)).findFirst().orElse(null);
    }

    @Override
    public Book getBookByTitle(String title) {
        return this.books.stream().filter(b -> b.getTitle().equals(title)).findFirst().orElse(null);
    }


    @Override
    public List<Book> getBooks() {
        return this.books;
    }

    @Override
    public void editBook(Book book, Integer id) {
        Book searchedBook = getBookById(id);
        searchedBook.setTitle(book.getTitle());
        searchedBook.setAuthor(book.getAuthor());
    }

    @Override
    public void deleteBookById(Integer id) {
        this.books.removeIf(b -> b.getId().equals(id));
    }
}
