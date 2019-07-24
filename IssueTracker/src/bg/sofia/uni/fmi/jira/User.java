package bg.sofia.uni.fmi.jira;

public class User {
    private String username;
    public User(String userName)
    {
        this.setUsername(userName);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
