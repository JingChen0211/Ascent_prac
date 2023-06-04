package ui;

import util.UserDataClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * 用户注册窗体
 * 这是一个Java语言编写的用户注册窗体类。
 * 窗体包含输入用户帐号、密码和重复密码的文本框、注册和退出按钮，以及一个提示标签。
 * <p>
 * 其中注册按钮会通过UserDataClient类将输入的用户信息发送给服务器进行注册，注册成功后提示标签会显示"注册成功"，否则会显示"用户名已存在"。
 * 退出按钮用于关闭窗口，窗口关闭时也会调用窗口关闭事件处理类。
 * 另外，重复密码文本框添加了焦点事件监听器，当焦点失去时会检查两次输入的密码是否一致，并在提示标签上显示相应信息。
 */
public class RegistFrame extends JFrame {
    private JTextField userText;
    private JPasswordField password;
    private JPasswordField repassword;
    private JLabel tip;

    private UserDataClient userDataClient;

    public RegistFrame() throws IOException {
        this.setTitle("用户注册");//设置注册窗口标题

        userDataClient = new UserDataClient();

        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());//设置容器布局是border布局
        JPanel registPanel = new JPanel();
        JLabel userLabel = new JLabel("用户帐号：");
        JLabel passwordLabel = new JLabel("用户密码：");
        JLabel repasswordLabel = new JLabel("重复密码：");
        userText = new JTextField(15);
        password = new JPasswordField(15);
        repassword = new JPasswordField(15);
        JButton regist = new JButton("注册");
        JButton exitButton = new JButton("退出");
        registPanel.add(userLabel);
        registPanel.add(new JScrollPane(userText));
        registPanel.add(passwordLabel);
        registPanel.add(new JScrollPane(password));
        registPanel.add(repasswordLabel);
        registPanel.add(new JScrollPane(repassword));
        registPanel.add(regist);
        registPanel.add(exitButton);

        setResizable(false);//设置窗口大小不可变
        setSize(260, 180);
        setLocation(300, 100);
        JPanel tipPanel = new JPanel();
        tip = new JLabel();//用于显示提示信息
        tipPanel.add(tip);

        container.add(BorderLayout.CENTER, registPanel);
        container.add(BorderLayout.NORTH, tip);

        exitButton.addActionListener(new ExitActionListener());    //退出按钮添加监听
        regist.addActionListener(new RegistActionListener());     //注册按钮添加监听
        this.addWindowListener(new WindowCloser());         //窗口关闭的监听
    }

    /**
     * 退出按钮事件监听
     */
    class ExitActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            setVisible(false);
            dispose();
        }
    }

    /**
     * 注册按钮事件监听
     */
    class RegistActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String username = userText.getText();
            String password = new String(RegistFrame.this.password.getPassword());
            String repassword = new String(RegistFrame.this.repassword.getPassword());

            // 检查密码是否一致
            if (!password.equals(repassword)) {
                tip.setText("密码不一致");
                return;
            }

            try {
                // 将用户信息写入数据库
                boolean success = userDataClient.addUser(username, password);
                if (success) {
                    tip.setText("注册成功！");
                } else {
                    tip.setText("用户名已存在！");
                }
            } finally {
                userDataClient.closeSocket();
            }
        }
    }

    /**
     * "关闭窗口"事件处理内部类
     */
    class WindowCloser extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            setVisible(false);
            dispose();
        }
    }
}


