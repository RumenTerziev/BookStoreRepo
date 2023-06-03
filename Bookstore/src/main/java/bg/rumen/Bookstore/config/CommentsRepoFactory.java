package bg.rumen.Bookstore.config;

import bg.rumen.Bookstore.constants.Profiles;
import bg.rumen.Bookstore.repository.InMemCommentRepository;
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
