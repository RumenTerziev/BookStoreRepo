package bg.rumen.Bookstore.jdbc;

import bg.rumen.Bookstore.constants.Profiles;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Getter
@Configuration
@Profile(Profiles.BASIC_JDBC)
public class UserJDBC {
    @Value("${user}")
    private String username;
    @Value("${pass}")
    private String password;
    @Value("${url}")
    private String url;

}
