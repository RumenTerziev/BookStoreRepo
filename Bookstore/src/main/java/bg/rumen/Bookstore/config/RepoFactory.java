package bg.rumen.Bookstore.config;

import bg.rumen.Bookstore.models.Book;
import bg.rumen.Bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepoFactory {
    @Autowired
    @Qualifier("gotBook")
    private Book firstBook;

    @Autowired
    @Qualifier("harryBook")
    private Book secondBook;

    @Bean
    @Qualifier("getRepo")
    public BookRepository bookRepository() {
        BookRepository bookRepository = new BookRepository();
        bookRepository.addBook(firstBook);
        bookRepository.addBook(secondBook);
        return bookRepository;
    }

}
