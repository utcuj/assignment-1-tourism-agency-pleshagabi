package dbConnection;

/**
 * Created by plesha on 27-Mar-18.
 */
import java.sql.*;

public class DatabaseConnection {

    private Connection connection = null;

    private Connection getConnectionToMySqlDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourismagencydb?useSSL=false","root","plesha");
            return connection;
        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }

    public void openConnectionToDatabase() {
        try {
            getConnectionToMySqlDatabase();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ResultSet executeQuery(String query, String type) {
        Statement statement = null;
        ResultSet resultSet = null;
        if (connection != null) {
            try {
                statement = connection.createStatement();
                if (type.equals("select")) {
                    resultSet = statement.executeQuery(query);
                } else {
                    statement.executeUpdate(query);
                }
                return resultSet;
            } catch (Exception e) {
                System.out.println(e);
            } /*finally {
                try {
					if (resultSet != null) {
						resultSet.close();
					}
					if (statement != null) {
						statement.close();
					}
                } catch (Exception e) {
                    System.out.println(e);
                }

            } */
        }
        return null;
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String age = resultSet.getString("age");
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
        }
    }

    public void closeConnectionToDatabase() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}