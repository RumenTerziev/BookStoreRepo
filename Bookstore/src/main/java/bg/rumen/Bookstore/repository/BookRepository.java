package bg.rumen.Bookstore.repository;

import bg.rumen.Bookstore.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {

    @Autowired
    private List<Book> books;

    public BookRepository() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public Book getBookByTitle(String title) {
        return this.books.stream().filter(b -> b.getTitle().equals(title)).findFirst().orElse(null);
    }

    public List<Book> getBooks() {
        return this.books;
    }


    public void deleteBookById(Integer id) {
        this.books.removeIf(b -> b.getId().equals(id));
    }
}
