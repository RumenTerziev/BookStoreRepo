package bg.rumen.Bookstore.config;

import bg.rumen.Bookstore.models.Book;
import bg.rumen.Bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static bg.rumen.Bookstore.qualifiers.BeanQualifiers.*;

@Configuration
public class BookRepoFactory {
    @Autowired
    @Qualifier(GAME_OF_THRONES_BOOK)
    private Book firstBook;

    @Autowired
    @Qualifier(HARRY_POTTER_BOOK)
    private Book secondBook;

    @Bean
    @Qualifier(BOOK_REPO)
    public BookRepository bookRepository() {
        BookRepository bookRepository = new BookRepository();
        bookRepository.addBook(firstBook);
        bookRepository.addBook(secondBook);
        return bookRepository;
    }

}
