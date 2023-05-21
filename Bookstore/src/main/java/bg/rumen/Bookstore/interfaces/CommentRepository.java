package bg.rumen.Bookstore.interfaces;

import bg.rumen.Bookstore.models.Comment;

import java.util.List;

public interface CommentRepository {

    void addComment(Comment comment);

    List<Comment> getCommentById(Integer id);

    void deleteCommentById(Integer id);

    void removeAllComments(Integer id);
}
