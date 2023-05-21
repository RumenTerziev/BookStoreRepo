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
        return new Book("Game Of Thrones", "George R.R. Martin", 1);
    }


    @Bean
    @Qualifier(HARRY_POTTER_BOOK)
    public Book harryBook() {
        return new Book("Harry Potter", "J. K. Rowling", 2);
    }

}
