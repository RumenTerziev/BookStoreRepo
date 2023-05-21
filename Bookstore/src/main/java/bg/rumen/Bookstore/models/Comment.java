package bg.rumen.Bookstore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Comment {
    private String bookId;
    private String comment;
    private String commentId;
}
