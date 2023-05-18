package bg.rumen.Bookstore.repository;

import bg.rumen.Bookstore.models.Comment;

import java.util.*;

public class CommentsRepository {
    Map<String, List<String>> comments;

    public CommentsRepository() {
        this.comments = new LinkedHashMap<>();
    }


    public void addComment(Comment comment) {
        this.comments.putIfAbsent(comment.getBookTitle(), new ArrayList<>());
        this.comments.get(comment.getBookTitle()).add(comment.getComment());
    }


    public List<String> getCommentsByTitle(String title) {
        if (!this.comments.containsKey(title)) {
            return new ArrayList<>();
        }
        return Collections.unmodifiableList(this.comments.get(title));
    }

}
