package bg.rumen.Bookstore.repository;

import bg.rumen.Bookstore.exceptions.NoSuchBookWithIdException;
import bg.rumen.Bookstore.interfaces.BookRepository;
import bg.rumen.Bookstore.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public Book getBookById(Integer id) throws NoSuchBookWithIdException {

      Book book = this.books.stream().filter(b -> Objects.equals(b.getId(), id)).findFirst().orElse(null);
      if (book == null) {
          throw new NoSuchBookWithIdException(id);
      }
      return book;
    }


    @Override
    public void editBook(Book book) throws NoSuchBookWithIdException {
        Book searchedBook = getBookById(book.getId());
        searchedBook.setTitle(book.getTitle());
        searchedBook.setAuthor(book.getAuthor());
    }

    @Override
    public List<Book> getBooks(String title) {
        if (title == null) {
            return this.books;
        } else {
            List<Book> bookList = this.books.stream().filter(book -> book.getTitle().equals(title)).collect(Collectors.toList());
           if (bookList.size() > 0) {
               return bookList;
           } else {
              return new ArrayList<>();
           }
        }

    }

    @Override
    public void deleteBookById(Integer id) {
        this.books.removeIf(b -> Objects.equals(b.getId(), id));
    }

}
