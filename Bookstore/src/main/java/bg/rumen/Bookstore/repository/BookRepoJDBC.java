package bg.rumen.Bookstore.repository;

import bg.rumen.Bookstore.constants.Profiles;
import bg.rumen.Bookstore.exceptions.NoSuchBookWithIdException;
import bg.rumen.Bookstore.interfaces.BookRepository;
import bg.rumen.Bookstore.jdbc.ConnectionManager;
import bg.rumen.Bookstore.models.Book;
import bg.rumen.Bookstore.models.PageResult;
import bg.rumen.Bookstore.params.BookSearchParams;
import bg.rumen.Bookstore.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
@Profile(Profiles.BASIC_JDBC)
public class BookRepoJDBC implements BookRepository {

    @Autowired
    private ConnectionManager connectionManager;

    @Override
    public void addBook(Book book) {
        Connection connection = this.connectionManager.getConnection();

        String title = book.getTitle();
        String author = book.getAuthor();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO `books`(`title`, `author`) VALUES(?, ?);");
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }

        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void editBook(Book book) throws NoSuchBookWithIdException {
        Connection connection = this.connectionManager.getConnection();

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
            stmt.close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }

        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PageResult<Book> getBooks(BookSearchParams searchParams, PageParams pageParams) {

        Connection connection = this.connectionManager.getConnection();

        int offset = pageParams.getPage() * 5 - 5;
        if (offset < 0) {
            offset = 0;
        }
        int totalRecords = 0;

        List<Book> bookList = new ArrayList<>();
        try {


            String searchedTitle = searchParams.getBookTitle();
            String searchedAuthor = searchParams.getAuthor();
            StringBuilder query = new StringBuilder(String.format("SELECT * FROM `books` WHERE 1 = 1%n"));

            StringBuilder countQuery = new StringBuilder(String.format("SELECT COUNT(*) AS `count` FROM `books`%nWHERE 1 = 1%n"));

            StringBuilder queryQueue = new StringBuilder();

            if (searchedTitle != null) {
                String toAppend = String.format(" AND `title` = '%s'%n ", searchedTitle);
                queryQueue.append(toAppend);
            }

            if (searchedAuthor != null) {
                String nextAppend = String.format(" AND `author` = '%s'%n ", searchedAuthor);
                queryQueue.append(nextAppend);
            }

            query.append(queryQueue);
            countQuery.append(queryQueue);

            query.append(String.format(" ORDER BY `id`%nLimit %d, 5; ", offset));
            countQuery.append(";");


            PreparedStatement stmt = connection.prepareStatement(query.toString());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String title = rs.getString("title");
                String author = rs.getString("author");
                Integer id = rs.getInt("id");
                Book book = new Book(title, author, id);
                bookList.add(book);
            }

            rs.close();
            stmt.close();

            PreparedStatement newStmt = connection.prepareStatement(countQuery.toString());

            ResultSet newSet = newStmt.executeQuery();

            if (newSet.next()) {

                totalRecords = newSet.getInt("count");
            }

            newSet.close();
            newStmt.close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }

        PageResult<Book> result = new PageResult<>();
        result.setItems(bookList);
        result.setTotalRecords(totalRecords);


        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Book getBookById(Integer id) throws NoSuchBookWithIdException {
        Connection connection = this.connectionManager.getConnection();

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM `books`" +
                    "WHERE `id` = ?;");

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
            rs.close();
            stmt.close();
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return book;


        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }


    }

    @Override
    public void deleteBookById(Integer id) {
        Connection connection = this.connectionManager.getConnection();

        try {

            PreparedStatement statement = connection.prepareStatement("DELETE FROM `comments`" +
                    "WHERE `book_id`= ?");
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();

            PreparedStatement stmt = connection.prepareStatement("DELETE FROM `books`" +
                    "WHERE `id` = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }

        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
