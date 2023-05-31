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

import java.sql.*;
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

        String title = book.getTitle();
        String author = book.getAuthor();
        try {
            PreparedStatement stmt = this.connection.prepareStatement(
                    "INSERT INTO `books`(`title`, `author`) VALUES(?, ?);");
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.executeUpdate();


        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }

    }

    @Override
    public void editBook(Book book) throws NoSuchBookWithIdException {

        Integer searchedId = book.getId();
        String newTitle = book.getTitle();
        String newAuthor = book.getAuthor();

        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE `books`" +
                    "SET `title` = ?, `author` = ?" +
                    "WHERE `id` = ?");
            stmt.setString(1, newTitle);
            stmt.setString(2, newAuthor);
            stmt.setInt(3, searchedId);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public PageResult<Book> getBooks(BookSearchParams searchParams, PageParams pageParams) {
        int offset = pageParams.getPage() * 5 - 5;
        int totalRecords = 0;

        List<Book> bookList = new ArrayList<>();
        try {

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM `books`" +
                    "ORDER BY `id`" +
                    "LIMIT ?, 5;");
            stmt.setInt(1, offset);
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {

                String title = rs.getString("title");
                String author = rs.getString("author");
                Integer id = rs.getInt("id");
                Book book = new Book(title, author, id);
                bookList.add(book);
            }

            rs = stmt.executeQuery("SELECT COUNT(*) AS `count` FROM `books`;");

            if (rs.next()) {

                totalRecords = rs.getInt("count");
            }


        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }

        PageResult<Book> result = new PageResult<>();
        result.setItems(bookList);
        result.setTotalRecords(totalRecords);
        return result;
    }

    @Override
    public Book getBookById(Integer id) throws NoSuchBookWithIdException {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM `books`" +
                    "WHERE `id` = ?" +
                    "LIMIT ?, 5;");

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            Book book = null;
            if (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                Integer bookId = rs.getInt("id");
                book = new Book(title, author, bookId);
            }

            if (book == null) {
                throw new NoSuchBookWithIdException(id);
            }
            return book;

        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }

    }

    @Override
    public void deleteBookById(Integer id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM `books`" +
                    "WHERE `id` = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }

    }
}
