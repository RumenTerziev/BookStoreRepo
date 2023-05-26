package bg.rumen.Bookstore.repository;

import bg.rumen.Bookstore.exceptions.NoSuchBookWithIdException;
import bg.rumen.Bookstore.interfaces.BookRepository;
import bg.rumen.Bookstore.models.Book;
import bg.rumen.Bookstore.util.PageManager;
import bg.rumen.Bookstore.models.params.BookSearchParams;
import bg.rumen.Bookstore.models.params.PageParams;
import bg.rumen.Bookstore.models.PageResult;
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
    public PageResult<Book> getBooks(BookSearchParams searchParams, PageParams pageParams) {

        PageResult<Book> result = new PageResult<>();
        List<Book> bookList = new ArrayList<>(this.books);

        if (searchParams.getBookTitle() != null) {
            bookList = bookList.stream().filter(b -> b.getTitle().equals(searchParams.getBookTitle())).collect(Collectors.toList());
        }

        if (searchParams.getAuthor() != null) {
            bookList = bookList.stream().filter(b -> b.getAuthor().equals(searchParams.getAuthor())).collect(Collectors.toList());
        }

        bookList = PageManager.getPages(bookList, pageParams.getLimit(), pageParams.getPage());

        result.setTList(bookList);
        result.setTotalRecords(this.books.size());

        return result;

    }

    @Override
    public void deleteBookById(Integer id) {
        this.books.removeIf(b -> Objects.equals(b.getId(), id));
    }

}
