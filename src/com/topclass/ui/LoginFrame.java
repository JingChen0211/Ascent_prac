package com.topclass.ui;

import com.topclass.bean.User;
import com.topclass.util.UserDataClient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

/**
 * �û���½����
 * ����һ��Java�����Դ���룬��Ҫʵ�����û���½���ڵĹ��ܡ�
 * �����ж�����һ��LoginFrame�࣬�̳���JFrame�࣬ʵ�����û���½�����UI��
 * �ڸ����У�������һ�����췽�����������û���½����ĸ�����������û����������������򡢵�½��ť��ע�ᰴť���˳���ť�ȡ�
 * ͬʱ�������˴����˳�����ť��������½����ť������ע�ᡱ��ť�������رմ��ڡ��¼����ڲ��࣬ͨ����Щ�ڲ���ʵ���˵�½���˳���ע��ȹ��ܡ�
 * <p>
 * ��ʵ���û���½�Ĺ���ʱ������ͨ������UserDataClient���е�getUsers()��������ȡ�������ϱ�����û����ݣ�
 * �����бȶԣ��ж�������û����������Ƿ���ȷ��
 * �����ȷ��رյ�ǰ���ڣ��������ڣ������ڽ�������ʾ��Ӧ�Ĵ�����Ϣ��
 * <p>
 * �����л�������һ��WindowCloser�࣬�������û��رմ���ʱ���ر�������������ӡ�
 *
 * @author cjc
 * @version 1.0
 */

public class LoginFrame extends JFrame {

    private JPanel contentPane;
    private JTextField userField;
    private JPasswordField keyField;//������JPasswordField

    protected UserDataClient userDataClient;//�˿�

    /**
     * Launch the application.��Ascentsys��Ĺ�����ͬ
     * ���Խ⿪ע�ͣ���������
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
        //�˺�
        userField = new JTextField();
        userField.setBounds(137, 65, 183, 28);
        contentPane.add(userField);
        userField.setColumns(10);
        //����
        keyField = new JPasswordField();
        keyField.setColumns(10);
        keyField.setBounds(137, 134, 183, 28);
        contentPane.add(keyField);

        JLabel userLabel = new JLabel("�˺ţ�");
        userLabel.setFont(new Font("����", Font.PLAIN, 16));
        userLabel.setBounds(61, 67, 54, 21);
        contentPane.add(userLabel);

        JLabel passwordLabel = new JLabel("���룺");
        passwordLabel.setFont(new Font("����", Font.PLAIN, 16));
        passwordLabel.setBounds(61, 141, 54, 21);
        contentPane.add(passwordLabel);

        JLabel tip = new JLabel();
        tip.setBounds(138, 170, 150, 15);
        contentPane.add(tip);

        try {
            userDataClient = new UserDataClient();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JButton loginButton = new JButton("��¼");
        //��¼���ܵ��¼�����
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean flag = false;
                HashMap<String, User> users = userDataClient.getUsers();
                if (users != null) {
                    if (users.containsKey(userField.getText())) {
                        User user = users.get(userField.getText());//get(Object o) ������ָ�� key �������� value��
                        char[] password = keyField.getPassword();
                        String pwd = new String(password);
                        if (user.getPassword().equals(pwd)) {
                            flag = true;
                        }
                    }
                    if (flag) {
                        //������ȷ
                        //�ر��û����ݿͻ���
                        userDataClient.closeSocket();
                        //����¼��������Ϊ���ɼ�
                        setVisible(false);
                        dispose();
                        //����������
                        MainFrame mainFrame = new MainFrame();
                        mainFrame.setVisible(true);
                    } else {
                        tip.setText("�˺Ų����ڣ����������");
                    }
                } else {
                    tip.setText("����������ʧ��...");
                }
            }
        });
        loginButton.setBounds(29, 195, 93, 34);
        contentPane.add(loginButton);

        JLabel headingLabel = new JLabel("��¼����");
        headingLabel.setFont(new Font("����", Font.BOLD, 20));
        headingLabel.setBounds(175, 22, 93, 28);
        contentPane.add(headingLabel);

        JButton registerButton = new JButton("ע��");
        //ע�Ṧ��
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegistFrame registFrame = null;
                registFrame = new RegistFrame();
                registFrame.setVisible(true);
            }
        });
        registerButton.setBounds(179, 195, 93, 34);
        contentPane.add(registerButton);

        JButton exitButton = new JButton("�˳�");
        //�˳�����
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                // 1.ʹ��dispose()�����رմ�����ͷŸô����ռ�õĲ�����Դ�������ز���ȫ���ģ�ֻ����Ļ��Դ��
                // 2.ʹ��dispose()�����رյĴ������ʹ��pack �� show �����ָ������ҿ��Իָ���disposeǰ��״̬
                userDataClient.closeSocket();//�ر��̣߳���Ҫ����UserDataClient��closeSocket()����
            }
        });
        exitButton.setBounds(317, 195, 93, 34);
        contentPane.add(exitButton);

        setResizable(false); //���ô��ڴ�С���ܸı�
    }
}
