package bg.rumen.Bookstore.jdbc;

import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Properties;

@Service
public class ConnectionManager {

    public Connection getConnection() {

        Connection connection;
        try {
            connection = doGetConnection();
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage() );
        }
        return connection;
    }


    private static Connection doGetConnection() throws SQLException {

        Properties connectionProps = new Properties();
        connectionProps.put("user", UserJDBC.getUser());
        connectionProps.put("password", UserJDBC.getPassword());

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", connectionProps);
    }
}