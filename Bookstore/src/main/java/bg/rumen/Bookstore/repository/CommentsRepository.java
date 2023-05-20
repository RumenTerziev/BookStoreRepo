package bg.rumen.Bookstore.repository;

import bg.rumen.Bookstore.models.Comment;

import java.util.*;

public class CommentsRepository {
    Map<Integer, List<String>> comments;

    public CommentsRepository() {
        this.comments = new LinkedHashMap<>();
    }


    public void addComment(Comment comment) {
        this.comments.putIfAbsent(comment.getBookId(), new ArrayList<>());
        this.comments.get(comment.getBookId()).add(comment.getComment());
    }


    public List<String> getCommentsById(Integer id) {
        if (!this.comments.containsKey(id)) {
            return new ArrayList<>();
        }
        return Collections.unmodifiableList(this.comments.get(id));
    }

}
