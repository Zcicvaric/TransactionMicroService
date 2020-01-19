package oss.transaction.Bll.Auth.Model;

public class JwtUser {
    private String userName;
    private long id;
    private String password;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPassword(String role) {
        this.password = role;
    }

    public String getUserName() {
        return userName;
    }

    public long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
