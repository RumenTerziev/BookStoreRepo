package bg.rumen.Bookstore.repository;

import bg.rumen.Bookstore.interfaces.CommentRepository;
import bg.rumen.Bookstore.models.Comment;
import bg.rumen.Bookstore.util.PageManager;
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

        List<Comment> commentList = this.comments.get(id);

        if (commentList == null || commentList.size() == 0) {
            result.setTList(new ArrayList<>());
            result.setTotalRecords(0);
        } else {
            if (pageParams.getPage() == null) {
                result.setTList(this.comments.get(id));
            } else {

                commentList = PageManager.getPages(commentList, pageParams.getLimit(), pageParams.getPage());
                result.setTList(commentList);
            }
            result.setTotalRecords(this.comments.get(id).size());
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
