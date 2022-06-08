package backend.model;

import backend.domain.User;

import java.sql.*;

public class DBManager {
    private Statement statement;

    public DBManager()
    {
        connect();
    }

    private void connect()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/zarisdb", "root", "");
            statement = connection.createStatement();
        }
        catch (Exception ex)
        {
            System.out.println("Connect error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public User authenticate(String username, String password)
    {
        ResultSet resultSet;
        User user = new User();

        try {
            String query = "SELECT * FROM users WHERE user = '" + username + "' AND password = '" + password + "'";
            resultSet = statement.executeQuery(query);

            if(resultSet.next())
            {
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("user"));
                user.setPassword(resultSet.getString("password"));
            }

            resultSet.close();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        System.out.println("User id: " + user.getId());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password: " + user.getPassword());

        return user;
    }
}
