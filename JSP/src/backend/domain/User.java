package backend.domain;

public class User {
    private int id;
    private String username;
    private String password;
    private String documentList;
    private String movieList;

    public User() {
    }

    public User(int id, String username, String password, String documentList, String movieList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.documentList = documentList;
        this.movieList = movieList;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDocumentList() {
        return documentList;
    }

    public void setDocumentList(String documentList) {
        this.documentList = documentList;
    }

    public String getMovieList() {
        return movieList;
    }

    public void setMovieList(String movieList) {
        this.movieList = movieList;
    }
}
