package com.topclass.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.topclass.util.UserDataClient;
/**
 * 用户注册窗体
 * 这是一个Java语言编写的用户注册窗体类。
 * 窗体包含输入用户帐号、密码和重复密码的文本框、注册和退出按钮，以及一个提示标签。
 * <p>
 * 其中注册按钮会通过UserDataClient类将输入的用户信息发送给服务器进行注册，注册成功后提示标签会显示"注册成功"，否则会显示"用户名已存在"。
 * 退出按钮用于关闭窗口，窗口关闭时也会调用窗口关闭事件处理类。
 * 另外，重复密码文本框添加了焦点事件监听器，当焦点失去时会检查两次输入的密码是否一致，并在提示标签上显示相应信息。
 *
 * @author cjc
 * @version 1.0
 */

@SuppressWarnings("serial")
public class RegistFrame extends JFrame {
    private JTextField userText;

    private JPasswordField password;

    private JPasswordField repassword;

    private JLabel tip;

    private UserDataClient userDataClient;

    public RegistFrame() {
        this.setTitle("用户注册");

        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());

        JPanel registPanel = new JPanel();

        JLabel userLabel = new JLabel("用户账户");
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

        setResizable(false);
        setSize(260, 180);
        setLocation(300, 100);

        JPanel tipPanel = new JPanel();

        tip = new JLabel();

        tipPanel.add(tip);

        container.add(BorderLayout.CENTER, registPanel);
        container.add(BorderLayout.NORTH, tip);

        exitButton.addActionListener(new ExitActionListener());
        regist.addActionListener(new RegistActionListener());
        repassword.addFocusListener(new MyFocusListener());
        this.addWindowListener(new WindowCloser());
        this.addWindowFocusListener(new WindowFocusListener() {// ?????????
            public void windowGainedFocus(WindowEvent e) {
            }
            public void windowLostFocus(WindowEvent e) {
                e.getWindow().toFront();
            }
        });
        try {
            userDataClient = new UserDataClient();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 退出按钮事件监听
     * @author ascent
     */
    class ExitActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            setVisible(false);
            dispose();
        }
    }

    /**
     * 注册按钮事件监听
     * @author ascent
     */
    class RegistActionListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            // 将用户信息写入数据库
            boolean bo = userDataClient.addUser(userText.getText(), new String(password.getPassword()));
            if (bo) {
                tip.setText("注册成功");
            } else {
                tip.setText("用户名已存在");
            }
        }
    }

    /**
     * "关闭窗口"事件处理内部类
     * @author ascent
     */
    class WindowCloser extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            setVisible(false);
            dispose();
        }
    }

    /**
     * 焦点失去时会检查两次输入的密码是否一致
     * @author ascent
     */
    class MyFocusListener implements FocusListener {

        public void focusGained(FocusEvent arg0) {
        }

        public void focusLost(FocusEvent e) {
            if (e.getSource().equals(password)) {
                if (new String(password.getPassword()) == "" || new String(password.getPassword()) == null) {
                    tip.setText("密码为空!");
                }
            } else if (e.getSource().equals(repassword)) {
                if (!new String(password.getPassword()).equals(new String(password.getPassword()))) {
                    tip.setText("密码不一致");
                }
            } else {
                tip.setText("");
            }
        }
    }
}
