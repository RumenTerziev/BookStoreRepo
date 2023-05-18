package bg.rumen.Bookstore.config;

import bg.rumen.Bookstore.models.Book;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static bg.rumen.Bookstore.qualifiers.BeanQualifiers.*;

@Configuration
public class BookFactory {

    @Bean
    @Qualifier(GAME_OF_THRONES_BOOK)
    public Book gotBook() {
        Book book = new Book();
        book.setTitle("Game Of Thrones");
        book.setAuthor("George R.R. Martin");
        return book;
    }


    @Bean
    @Qualifier(HARRY_POTTER_BOOK)
    public Book harryBook() {
        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor("J. K. Rowling");
        return book;
    }
}
