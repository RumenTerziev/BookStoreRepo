package bg.rumen.Bookstore.repository;

import bg.rumen.Bookstore.interfaces.CommentRepository;
import bg.rumen.Bookstore.models.Comment;

import java.util.*;

public class InMemCommentRepository implements CommentRepository {
    Map<Integer, List<Comment>> comments;

    public InMemCommentRepository() {
        this.comments = new LinkedHashMap<>();
    }


    @Override
    public void addComment(Comment comment) {
        this.comments.putIfAbsent(comment.getBookId(), new ArrayList<>());
        this.comments.get(comment.getBookId()).add(comment);
    }


    @Override
    public List<Comment> getCommentsById(Integer id) {
        if (!this.comments.containsKey(id)) {
            return new ArrayList<>();
        }
        return Collections.unmodifiableList(this.comments.get(id));
    }

}
