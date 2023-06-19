package bg.rumen.bookstore.repository;

import bg.rumen.bookstore.interfaces.CommentRepository;
import bg.rumen.bookstore.models.Comment;
import bg.rumen.bookstore.util.PageManager;
import bg.rumen.bookstore.models.PageResult;
import bg.rumen.bookstore.params.PageParams;

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
    public PageResult<Comment> getComments(Integer id, PageParams pageParams) {

        List<Comment> commentList;
        if (!this.comments.containsKey(id)) {
            commentList = new ArrayList<>();
        } else {
            commentList = this.comments.get(id);
        }

        return PageManager.getPages(commentList, pageParams);
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
