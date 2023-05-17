package bg.rumen.Bookstore.repository;

import bg.rumen.Bookstore.models.Book;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
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


    public void deleteBookByTitle(String title) {
        this.books.removeIf(b -> b.getTitle().equals(title));
    }
}
