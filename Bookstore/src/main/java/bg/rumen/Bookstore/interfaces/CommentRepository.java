package bg.rumen.Bookstore.interfaces;

import bg.rumen.Bookstore.models.Comment;
import bg.rumen.Bookstore.models.PageResult;
import bg.rumen.Bookstore.params.PageParams;


public interface CommentRepository {

    void addComment(Comment comment);

    PageResult<Comment> getComments(Integer id, PageParams pageParams);

    void deleteCommentById(Integer id);

    void removeAllComments(Integer id);
}
