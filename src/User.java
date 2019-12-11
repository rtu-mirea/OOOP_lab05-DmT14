import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private String name;
    private String login;
    private String password;

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof User) {
            User user = (User) o;
            return this.login.equals(user.getLogin()) &&
                    this.password.equals(user.getPassword());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}