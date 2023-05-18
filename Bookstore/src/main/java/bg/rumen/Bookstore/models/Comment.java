package bg.rumen.Bookstore.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment {
    private String bookTitle;
    private String comment;

    public Comment(String bookTitle, String comment) {
        this.bookTitle = bookTitle;
        this.comment = comment;
    }
}
