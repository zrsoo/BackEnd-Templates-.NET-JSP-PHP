package backend.model;

import backend.domain.User;

import java.sql.*;

public class DBManager {
    Connection connection;

    public DBManager()
    {
        connect();
    }

    private void connect()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/zarisdb", "root", "");
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
            PreparedStatement statement = connection.prepareStatement(query);
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

    public int addDocument(String documentId, String documentName, String documentContents) {
        int nrRows = -1;

        try {
            String query = "INSERT INTO documents VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, documentId);
            statement.setString(2, documentName);
            statement.setString(3, documentContents);

            nrRows = statement.executeUpdate();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return nrRows;
    }
}
