package bg.rumen.Bookstore.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookSearchParams {
    private String bookTitle;
    private String author;

    public BookSearchParams() {
    }

    public BookSearchParams(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public BookSearchParams(String bookTitle, String author) {
      this(bookTitle);
      this.author = author;
    }
}
