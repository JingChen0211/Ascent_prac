package ui;

import bean.User;
import util.UserDataClient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

/**
 * 用户登陆窗体
 * 这是一个Java程序的源代码，主要实现了用户登陆窗口的功能。
 * 程序中定义了一个LoginFrame类，继承了JFrame类，实现了用户登陆界面的UI。
 * 在该类中，定义了一个构造方法，创建了用户登陆界面的各个组件，如用户名输入框、密码输入框、登陆按钮、注册按钮、退出按钮等。
 * 同时，定义了处理“退出”按钮、处理“登陆”按钮、处理“注册”按钮、处理“关闭窗口”事件的内部类，通过这些内部类实现了登陆、退出、注册等功能。
 * <p>
 * 在实现用户登陆的功能时，程序通过调用UserDataClient类中的getUsers()方法，获取服务器上保存的用户数据，
 * 并进行比对，判断输入的用户名和密码是否正确，
 * 如果正确则关闭当前窗口，打开主窗口；否则在界面上显示相应的错误信息。
 * <p>
 * 程序中还定义了一个WindowCloser类，用于在用户关闭窗口时，关闭与服务器的连接。
 *
 * @author cjc
 * @version 1.0
 */

public class LoginFrame extends JFrame {

    private JPanel contentPane;
    private JTextField userField;
    private JPasswordField keyField;//密码用JPasswordField

    protected UserDataClient userDataClient;//端口

    /**
     * Launch the application.与Ascentsys类的功能相同
     * 可以解开注释，尝试运行
     */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    LoginFrame frame = new LoginFrame();
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    /**
     * Create the frame.
     */
    public LoginFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        //账号
        userField = new JTextField();
        userField.setBounds(137, 65, 183, 28);
        contentPane.add(userField);
        userField.setColumns(10);
        //密码
        keyField = new JPasswordField();
        keyField.setColumns(10);
        keyField.setBounds(137, 134, 183, 28);
        contentPane.add(keyField);

        JLabel userLabel = new JLabel("账号：");
        userLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        userLabel.setBounds(61, 67, 54, 21);
        contentPane.add(userLabel);

        JLabel passwordLabel = new JLabel("密码：");
        passwordLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        passwordLabel.setBounds(61, 141, 54, 21);
        contentPane.add(passwordLabel);

        JLabel tip = new JLabel();
        tip.setBounds(138, 170, 150, 15);
        contentPane.add(tip);

        JButton loginButton = new JButton("登录");
        //登录功能的事件监听
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean flag = false;
                HashMap<String, User> users = userDataClient.getUsers();
                if(users != null){
                    if(users.containsKey(userField.getText())){
                        User user = users.get(userField.getText());//get(Object o) 返回与指定 key 所关联的 value。
                        char[] password = keyField.getPassword();
                        String pwd = new String(password);
                        if(user.getPassword().equals(pwd)){
                            flag = true;
                        }
                    }
                    if(flag){
                        //密码正确
                        //关闭用户数据客户端
                        userDataClient.closeSocket();
                        //将登录界面设置为不可见
                        setVisible(false);
                        dispose();
                        //进入主界面
                        MainFrame mainFrame = new MainFrame();
                        mainFrame.setVisible(true);
                    }else{
                        tip.setText("账号不存在，或密码错误。");
                    }
                }else{
                    tip.setText("服务器连接失败...");
                }
            }
        });
        loginButton.setBounds(29, 195, 93, 34);
        contentPane.add(loginButton);

        JLabel headingLabel = new JLabel("登录界面");
        headingLabel.setFont(new Font("宋体", Font.BOLD, 20));
        headingLabel.setBounds(175, 22, 93, 28);
        contentPane.add(headingLabel);

        JButton registerButton = new JButton("注册");
        //注册功能
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegistFrame registFrame = null;
                try {
                    registFrame = new RegistFrame();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                registFrame.setVisible(true);
            }
        });
        registerButton.setBounds(179, 195, 93, 34);
        contentPane.add(registerButton);

        JButton exitButton = new JButton("退出");
        //退出功能
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                // 1.使用dispose()方法关闭窗体会释放该窗体的占用的部分资源，不过呢不是全部的，只是屏幕资源。
                // 2.使用dispose()方法关闭的窗体可以使用pack 或 show 方法恢复，并且可以恢复到dispose前的状态
                userDataClient.closeSocket();//关闭线程，需要补充UserDataClient的closeSocket()方法
            }
        });
        exitButton.setBounds(317, 195, 93, 34);
        contentPane.add(exitButton);

        setResizable(false); //设置窗口大小不能改变
    }
}
