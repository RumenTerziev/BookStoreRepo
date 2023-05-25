package bg.rumen.Bookstore.models.params;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookSearchParams {
    private String bookTitle;
    private String author;
}
