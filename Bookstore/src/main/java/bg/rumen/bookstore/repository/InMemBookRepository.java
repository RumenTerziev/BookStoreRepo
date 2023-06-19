package bg.rumen.bookstore.repository;

import bg.rumen.bookstore.constants.Profiles;
import bg.rumen.bookstore.exceptions.NoSuchBookWithIdException;
import bg.rumen.bookstore.interfaces.BookRepository;
import bg.rumen.bookstore.models.Book;
import bg.rumen.bookstore.util.PageManager;
import bg.rumen.bookstore.params.BookSearchParams;
import bg.rumen.bookstore.params.PageParams;
import bg.rumen.bookstore.models.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@Profile(Profiles.IN_MEM)
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
    public PageResult<Book> getBooks(BookSearchParams searchParams, PageParams pageParams) {

        List<Book> bookList = new ArrayList<>(this.books);

        if (searchParams.getBookTitle() != null) {
            bookList = bookList.stream().filter(b -> b.getTitle().equals(searchParams.getBookTitle())).collect(Collectors.toList());
        }

        if (searchParams.getAuthor() != null) {
            bookList = bookList.stream().filter(b -> b.getAuthor().equals(searchParams.getAuthor())).collect(Collectors.toList());
        }

        return PageManager.getPages(bookList, pageParams);
    }

    @Override
    public void deleteBookById(Integer id) {
        this.books.removeIf(b -> Objects.equals(b.getId(), id));
    }

}
