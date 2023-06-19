package bg.rumen.bookstore.config;

import bg.rumen.bookstore.constants.Profiles;
import bg.rumen.bookstore.repository.InMemCommentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(Profiles.IN_MEM)
public class CommentsRepoFactory {

    @Bean
    public InMemCommentRepository commentsRepository() {
        return new InMemCommentRepository();
    }
}
