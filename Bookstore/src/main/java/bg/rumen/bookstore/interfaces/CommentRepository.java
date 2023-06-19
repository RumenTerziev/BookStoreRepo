package bg.rumen.bookstore.interfaces;

import bg.rumen.bookstore.models.Comment;
import bg.rumen.bookstore.models.PageResult;
import bg.rumen.bookstore.params.PageParams;


public interface CommentRepository {

    void addComment(Comment comment);

    PageResult<Comment> getComments(Integer id, PageParams pageParams);

    void deleteCommentById(Integer id);

    void removeAllComments(Integer id);
}
