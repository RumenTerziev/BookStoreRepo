package bg.rumen.Bookstore.repository;

import bg.rumen.Bookstore.interfaces.CommentRepository;
import bg.rumen.Bookstore.models.Book;
import bg.rumen.Bookstore.models.Comment;
import bg.rumen.Bookstore.models.PageManager;
import bg.rumen.Bookstore.models.PageResult;
import bg.rumen.Bookstore.models.params.PageParams;

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

        PageResult<Comment> result = new PageResult<>();
        if (!this.comments.containsKey(id)) {
            result.setTList(new ArrayList<>());
            result.setTotalRecords(0);
        } else {
            List<Comment> commentList = new ArrayList<>(this.comments.get(id));
            commentList = PageManager.getPages(commentList, pageParams.getLimit(), pageParams.getPage());
            result.setTList(commentList);
            result.setTotalRecords(commentList.size());
        }
        return result;
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
