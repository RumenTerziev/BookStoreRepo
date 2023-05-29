package bg.rumen.Bookstore.config;

import bg.rumen.Bookstore.constants.Profiles;
import bg.rumen.Bookstore.repository.InMemCommentRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static bg.rumen.Bookstore.constants.BeanQualifiers.*;

@Configuration
@Profile(Profiles.IN_MEM)
public class CommentsRepoFactory {

    @Bean
    @Qualifier(COMMENTS_REPO)
    public InMemCommentRepository commentsRepository() {
        return new InMemCommentRepository();
    }
}
