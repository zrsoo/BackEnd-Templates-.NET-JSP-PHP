package backend.model;

import backend.domain.Document;
import backend.domain.Movie;
import backend.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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

    public ArrayList<Document> getAllDocuments()
    {
        ResultSet resultSet;
        ArrayList<Document> documentList = new ArrayList<>();

        try {
            String query = "SELECT * FROM documents";
            PreparedStatement statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();

            while(resultSet.next())
            {
                documentList.add(new Document(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("contents")
                ));
            }

            resultSet.close();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return documentList;
    }

    public ArrayList<User> getAllUsers()
    {
        ResultSet resultSet;
        ArrayList<User> userList = new ArrayList<>();

        try {
            String query = "SELECT * FROM users";
            PreparedStatement statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();

            while(resultSet.next())
            {
                userList.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("user"),
                        resultSet.getString("password"),
                        resultSet.getString("documentList"),
                        resultSet.getString("movieList")
                ));
            }

            resultSet.close();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        return userList;
    }

    public ArrayList<Document> getDocumentsByUser(int userId)
    {
        ArrayList<Integer> documentIdList = new ArrayList<>();
        ArrayList<Document> documentList = new ArrayList<>();
        ResultSet resultSet;

        try {
            String query = "SELECT documentList FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, String.valueOf(userId));

            resultSet = statement.executeQuery();

            while(resultSet.next())
            {
                StringTokenizer documentIds = new StringTokenizer(resultSet.getString("documentList"));
                while(documentIds.hasMoreTokens())
                    documentIdList.add(Integer.valueOf(documentIds.nextToken()));
            }

            documentIdList.forEach(documentId -> {
                try
                {
                    String documentQuery = "SELECT * FROM documents WHERE id = ?";
                    PreparedStatement documentStatement = connection.prepareStatement(documentQuery);
                    ResultSet resultSetDocument;

                    documentStatement.setString(1, String.valueOf(documentId));
                    resultSetDocument = documentStatement.executeQuery();

                    while(resultSetDocument.next())
                    {
                        documentList.add(new Document(
                                resultSetDocument.getInt("id"),
                                resultSetDocument.getString("name"),
                                resultSetDocument.getString("contents")
                        ));
                    }

                    resultSetDocument.close();
                }
                catch (SQLException exception)
                {
                    exception.printStackTrace();
                }
            });

            resultSet.close();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
        return documentList;
    }

    public ArrayList<Movie> getMoviesByUser(int userId)
    {
        ArrayList<Integer> movieIdList = new ArrayList<>();
        ArrayList<Movie> movieList = new ArrayList<>();
        ResultSet resultSet;

        try {
            String query = "SELECT movieList FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, String.valueOf(userId));

            resultSet = statement.executeQuery();

            while(resultSet.next())
            {
                StringTokenizer movieIds = new StringTokenizer(resultSet.getString("movieList"));
                while(movieIds.hasMoreTokens())
                    movieIdList.add(Integer.valueOf(movieIds.nextToken()));
            }

            movieIdList.forEach(movieId -> {
                try
                {
                    String movieQuery = "SELECT * FROM movies WHERE id = ?";
                    PreparedStatement movieStatement = connection.prepareStatement(movieQuery);
                    ResultSet resultSetMovie;

                    movieStatement.setString(1, String.valueOf(movieId));
                    resultSetMovie = movieStatement.executeQuery();

                    while(resultSetMovie.next())
                    {
                        movieList.add(new Movie(
                                resultSetMovie.getInt("id"),
                                resultSetMovie.getString("title"),
                                resultSetMovie.getString("duration")
                        ));
                    }

                    resultSetMovie.close();
                }
                catch (SQLException exception)
                {
                    exception.printStackTrace();
                }
            });

            resultSet.close();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
        return movieList;
    }
}

