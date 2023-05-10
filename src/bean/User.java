package bean;
/**
 * 实体类User，用来描述用户的信息类
 *
 * User类包含以下字段：
 *
 * username：用户名
 * password：密码
 * authority：用户权限
 * 此外，User类还包含以下方法：
 *
 * 默认构造方法
 * 带参数的构造方法
 * 带所有参数的构造方法
 * 获取/设置Username、Password、Authority字段的方法
 * toString方法用于返回用户名和密码
 *
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


