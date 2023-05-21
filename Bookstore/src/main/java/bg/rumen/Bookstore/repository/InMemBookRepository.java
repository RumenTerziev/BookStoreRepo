package bg.rumen.Bookstore.repository;

import bg.rumen.Bookstore.exceptions.NoSuchBookWithIdException;
import bg.rumen.Bookstore.interfaces.BookRepository;
import bg.rumen.Bookstore.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Repository
public class InMemBookRepository implements BookRepository {

    @Autowired
    private List<Book> books;

    private Integer idDigit = 3;
    private String newId = String.format("rtghzza%dqwet%d%dqgjj", idDigit, idDigit, idDigit);

    public InMemBookRepository() {
        this.books = new ArrayList<>();
    }

    @Override
    public void addBook(Book book) {
        book.setId(newId);
        idDigit++;
        changeNewId();
        this.books.add(book);
    }


    @Override
    public Book getBookById(String id) throws NoSuchBookWithIdException {

      Book book = this.books.stream().filter(b -> b.getId().equals(id)).findFirst().orElse(null);
      if (book == null) {
          throw new NoSuchBookWithIdException(id);
      }
      return book;
    }

    @Override
    public List<Book> getAllBooksWithTitle(String title) {
       List<Book> matchingBooks  = this.books.stream().filter(b -> b.getTitle().equals(title)).toList();
        if (matchingBooks.size() == 0) {
            return new ArrayList<>();
        }
        return matchingBooks;
    }


    @Override
    public List<Book> getBooks() {
        return Collections.unmodifiableList(this.books);
    }

    @Override
    public void editBook(Book book, String id) throws NoSuchBookWithIdException {
        Book searchedBook = getBookById(id);
        searchedBook.setTitle(book.getTitle());
        searchedBook.setAuthor(book.getAuthor());
    }

    @Override
    public void deleteBookById(String id) {
        this.books.removeIf(b -> b.getId().equals(id));
    }


    private void changeNewId() {
        this.newId = String.format("19fthBokPR%d%d%d", idDigit, idDigit, idDigit);
    }
}
