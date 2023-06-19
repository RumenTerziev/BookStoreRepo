package bg.rumen.bookstore.jdbc;

import bg.rumen.bookstore.constants.Profiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Properties;

@Service
@Profile(Profiles.BASIC_JDBC)
public class ConnectionManager {
    @Autowired
    private JdbcProps user;

    public Connection getConnection() {

        Connection connection;
        try {
            connection = doGetConnection();
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return connection;
    }


    private Connection doGetConnection() throws SQLException {

        Properties connectionProps = new Properties();
        connectionProps.put("user", this.user.getUsername());
        connectionProps.put("password", this.user.getPassword());

        return DriverManager.getConnection(this.user.getUrl(), connectionProps);
    }
}