package bg.rumen.Bookstore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Comment {
    private Integer bookId;
    private String comment;
}
