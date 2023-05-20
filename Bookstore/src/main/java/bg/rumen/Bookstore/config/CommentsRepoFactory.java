package bg.rumen.Bookstore.config;

import bg.rumen.Bookstore.repository.InMemCommentRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static bg.rumen.Bookstore.qualifiers.BeanQualifiers.*;

@Configuration
public class CommentsRepoFactory {

    @Bean
    @Qualifier(COMMENTS_REPO)
    public InMemCommentRepository commentsRepository() {
        return new InMemCommentRepository();
    }
}
