package moviesys;
import java.util.Objects;

public class User {
    private String username;
    private String password;

    //-----------------------------constructor-----------------------------
    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //-----------------------------getters-----------------------------
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    //-----------------------------setters-----------------------------
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //-----------------------------methods-----------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User u = (User) o;
        return Objects.equals(username, u.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return (" ");
    }
}
