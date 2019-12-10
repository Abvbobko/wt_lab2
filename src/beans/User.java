package beans;

import java.io.Serializable;

public class User implements Serializable, Comparable {

    private boolean admin = false;
    private String login;

    public boolean isAdmin(){
        return admin;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    private String passwordHash;

    public User(){}

    public User(String login, String password){
        this.login = login;
        this.passwordHash = String.valueOf(password.hashCode());
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public int compareTo(Object o) {
        return login.compareTo(((User) o).getLogin());
    }
}
