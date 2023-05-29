package bg.rumen.Bookstore.repository;


import bg.rumen.Bookstore.constants.Profiles;
import bg.rumen.Bookstore.interfaces.CommentRepository;
import bg.rumen.Bookstore.jdbc.ConnectionManager;
import bg.rumen.Bookstore.models.Book;
import bg.rumen.Bookstore.models.Comment;
import bg.rumen.Bookstore.models.PageResult;
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
import java.util.Objects;


@Repository
@Profile(Profiles.BASIC_JDBC)
public class CommentRepoJDBC implements CommentRepository {

    private Connection connection;

    public CommentRepoJDBC() {
        this.connection = ConnectionManager.getConnection();
    }

    @Override
    public void addComment(Comment comment) {

    }

    @Override
    public PageResult<Comment> getComments(Integer id, PageParams pageParams) {

        List<Comment> bookList = new ArrayList<>();
        try {
            PreparedStatement stmt = this.connection.
                    prepareStatement("SELECT * FROM `comments`AS `c`" +
                            "JOIN `books` AS `b`" +
                            "ON `c`.`book_id` = `b`.`id`;");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Integer commentID = rs.getInt("id");
                String comment = rs.getString("comment");
                Integer bookId = rs.getInt("book_id");
                if (Objects.equals(id, bookId)) {
                    Comment commentObj = new Comment(bookId, comment, commentID);
                    bookList.add(commentObj);
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return PageManager.getPages(bookList, pageParams);
    }

    @Override
    public void deleteCommentById(Integer id) {

    }

    @Override
    public void removeAllComments(Integer id) {

    }
}
