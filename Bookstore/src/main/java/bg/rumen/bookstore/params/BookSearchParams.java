package bg.rumen.bookstore.params;

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
