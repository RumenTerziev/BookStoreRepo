package bg.rumen.Bookstore.repository;

import bg.rumen.Bookstore.interfaces.CommentRepository;
import bg.rumen.Bookstore.models.Comment;

import java.util.*;

public class InMemCommentRepository implements CommentRepository {
    Map<String, List<Comment>> comments;
    private Integer idDigit = 1;
    private String commentId = String.format("19fthBokPR%d%d%d", idDigit, idDigit, idDigit);

    public InMemCommentRepository() {
        this.comments = new LinkedHashMap<>();
    }


    @Override
    public void addComment(Comment comment) {
        comment.setCommentId(commentId);
        idDigit++;
        changeCommentId();
        this.comments.putIfAbsent(comment.getBookId(), new ArrayList<>());
        this.comments.get(comment.getBookId()).add(comment);
    }


    @Override
    public List<Comment> getCommentsById(String id) {
        if (!this.comments.containsKey(id)) {
            return new ArrayList<>();
        }
        return Collections.unmodifiableList(this.comments.get(id));
    }

    @Override
    public void deleteCommentById(String bookId, String id) {
        this.comments.get(bookId).removeIf(c -> c.getCommentId().equals(id));
    }

    @Override
    public void removeAllComments(String id) {
        this.comments.remove(id);
    }

    private void changeCommentId() {
        this.commentId = String.format("19fthBokPR%d%d%d", idDigit, idDigit, idDigit);
    }

}
