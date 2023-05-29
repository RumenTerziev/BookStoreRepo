package bg.rumen.Bookstore.repository;

import bg.rumen.Bookstore.constants.Profiles;
import bg.rumen.Bookstore.exceptions.NoSuchBookWithIdException;
import bg.rumen.Bookstore.interfaces.BookRepository;
import bg.rumen.Bookstore.jdbc.ConnectionManager;
import bg.rumen.Bookstore.models.Book;
import bg.rumen.Bookstore.models.PageResult;
import bg.rumen.Bookstore.params.BookSearchParams;
import bg.rumen.Bookstore.params.PageParams;
import bg.rumen.Bookstore.util.PageManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
@Profile(Profiles.BASIC_JDBC)
public class BookRepoJDBC implements BookRepository {

    private Connection connection;

    public BookRepoJDBC() {
        this.connection = ConnectionManager.getConnection();
    }


    @Override
    public void addBook(Book book) {

    }

    @Override
    public void editBook(Book book) throws NoSuchBookWithIdException {

    }

    @Override
    public PageResult<Book> getBooks(BookSearchParams searchParams, PageParams pageParams) {

        List<Book> bookList = new ArrayList<>();
        try {
            PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM `books`;");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                Integer id = rs.getInt("id");
                Book book = new Book(title, author, id);
                bookList.add(book);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }


        return PageManager.getPages(bookList, pageParams);
     }

    @Override
    public Book getBookById(Integer id) throws NoSuchBookWithIdException {
        return null;
    }

    @Override
    public void deleteBookById(Integer id) {

    }
}
