package bg.rumen.Bookstore.repository;

import bg.rumen.Bookstore.interfaces.CommentRepository;
import bg.rumen.Bookstore.models.Comment;

import java.util.*;

public class InMemCommentRepository implements CommentRepository {
    Map<Integer, List<Comment>> comments;
    private Integer commentId = 1;

    public InMemCommentRepository() {
        this.comments = new LinkedHashMap<>();
    }


    @Override
    public void addComment(Comment comment) {
        comment.setCommentId(commentId);
        commentId++;
        this.comments.putIfAbsent(comment.getBookId(), new ArrayList<>());
        this.comments.get(comment.getBookId()).add(comment);
    }


    @Override
    public List<Comment> getCommentById(Integer id) {
        if (!this.comments.containsKey(id)) {
            return new ArrayList<>();
        }
        return Collections.unmodifiableList(this.comments.get(id));
    }

    @Override
    public void deleteCommentById(Integer id) {
        Comment searchedComment = null;
        for (List<Comment> commentList : comments.values()) {
            for (Comment comment : commentList) {
                if (Objects.equals(comment.getCommentId(), id)) {
                    searchedComment = comment;
                }
            }
        }
        if (searchedComment != null) {
            this.comments.get(searchedComment.getBookId()).removeIf(c -> c.getCommentId().equals(id));
        }
    }

    @Override
    public void removeAllComments(Integer id) {
        this.comments.remove(id);
    }
}
