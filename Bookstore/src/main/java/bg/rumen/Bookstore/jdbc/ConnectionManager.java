package bg.rumen.Bookstore.jdbc;

import java.sql.*;
import java.util.Properties;

public class ConnectionManager {

    public static Connection getConnection() {

        Connection connection = null;
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
