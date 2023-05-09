package ui;


import bean.User;

public class Login {
    User[] users = {new User("admin", "123"),
            new User("liu", "456"),
            new User("ascent", "789")};

    //定义一个login()方法对用户登陆进行有效性检查：
    public void login(String username, String password) {
        int i = 0;
        //输入的密码为空
        if (password == "") {
            System.out.println("输入的密码不能为空！");
            return;
        }
        while (i < users.length) {
            String u = "";
            String p = "";
            u = users[i].getUsername();
            p = users[i].getPassword();

            if (u.equals(username) && p.equals(password)) {
                System.out.println("用户登录成功！");
                return;
            } else {
                //比较之后没有相同的用户名和密码
                if (i == users.length - 1) {
                    System.out.println("用户名或密码错误！");
                    return;
                }
            }
            i++;
        }
    }

    //定义main()方法，定义两个变量作为要登录的用户信息并进行测试：
    public static void main(String[] args) {
        String username = "liu";
        String password = "456";
        Login l = new Login();
        l.login(username, password);
    }
}
