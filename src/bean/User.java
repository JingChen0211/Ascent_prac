package bean;
/**
 * User类
 * @author cjc
 * @version 1.0
 */
public class User {
    private String username;
    private String password;
    private int authority;

    public User() {
    }

    @Override
    public String toString() {
        return "用户名：" + username+
                " 密码：" + password
                ;
    }

    /**
     * 有参构造
     * @param username
     * @param password
     * @param authority
     */
    public User(String username, String password, int authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}


