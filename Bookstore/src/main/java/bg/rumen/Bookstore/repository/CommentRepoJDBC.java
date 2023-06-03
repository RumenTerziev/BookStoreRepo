package bg.rumen.Bookstore.repository;

import bg.rumen.Bookstore.constants.Profiles;
import bg.rumen.Bookstore.interfaces.CommentRepository;
import bg.rumen.Bookstore.jdbc.ConnectionManager;
import bg.rumen.Bookstore.models.Comment;
import bg.rumen.Bookstore.models.PageResult;
import bg.rumen.Bookstore.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CommentRepoJDBC implements CommentRepository {

    @Autowired
    ConnectionManager connectionManager;

    @Override
    public void addComment(Comment comment) {
        Connection connection = this.connectionManager.getConnection();

        int bookId = comment.getBookId();
        String commentText = comment.getComment();

        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO `comments`" +
                    "SET `comment` = ?, `book_id` = ?;");
            stmt.setString(1, commentText);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }

        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public PageResult<Comment> getComments(Integer searchedId, PageParams pageParams) {
        Connection connection = this.connectionManager.getConnection();

        List<Comment> commentList = new ArrayList<>();

        int offset = pageParams.getPage() * 5 - 5;
        if (offset < 0) {
            offset = 0;
        }
        int totalRecords = 0;


        try {

            PreparedStatement stmtNew = connection.prepareStatement("SELECT COUNT(*) AS `count` FROM `comments`" +
                    "WHERE `book_id` = ?;");
            stmtNew.setInt(1, searchedId);

            ResultSet newSet = stmtNew.executeQuery();

            if (newSet.next()) {
                totalRecords = newSet.getInt("count");
            }

            newSet.close();
            stmtNew.close();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `comments` " +
                    "WHERE `book_id` = ? " +
                    "ORDER BY `id` " +
                    "LIMIT ?, 5;");

            preparedStatement.setInt(1, searchedId);
            preparedStatement.setInt(2, offset);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Integer commentID = rs.getInt("id");
                String comment = rs.getString("comment");
                Integer bookId = rs.getInt("book_id");

                Comment commentObj = new Comment(bookId, comment, commentID);
                commentList.add(commentObj);
            }

            rs.close();
            preparedStatement.close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }


        PageResult<Comment> result = new PageResult<>();
        result.setItems(commentList);
        result.setTotalRecords(totalRecords);
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    @Override
    public void deleteCommentById(Integer id) {
        Connection connection = this.connectionManager.getConnection();

        try {

            PreparedStatement stmt = connection.prepareStatement("DELETE FROM `comments`" +
                    "WHERE `id` = ?;");
            stmt.setInt(1, id);

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void removeAllComments(Integer bookId) {
        Connection connection = this.connectionManager.getConnection();

        try {

            PreparedStatement stmt = connection.prepareStatement("DELETE FROM `comments`" +
                    "WHERE `book_id` = ?");
            stmt.setInt(1, bookId);

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
