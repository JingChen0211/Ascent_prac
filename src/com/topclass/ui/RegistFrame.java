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
 * �û�ע�ᴰ��
 * ����һ��Java���Ա�д���û�ע�ᴰ���ࡣ
 * ������������û��ʺš�������ظ�������ı���ע����˳���ť���Լ�һ����ʾ��ǩ��
 * <p>
 * ����ע�ᰴť��ͨ��UserDataClient�ཫ������û���Ϣ���͸�����������ע�ᣬע��ɹ�����ʾ��ǩ����ʾ"ע��ɹ�"���������ʾ"�û����Ѵ���"��
 * �˳���ť���ڹرմ��ڣ����ڹر�ʱҲ����ô��ڹر��¼������ࡣ
 * ���⣬�ظ������ı��������˽����¼���������������ʧȥʱ������������������Ƿ�һ�£�������ʾ��ǩ����ʾ��Ӧ��Ϣ��
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
        this.setTitle("�û�ע��");

        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());

        JPanel registPanel = new JPanel();

        JLabel userLabel = new JLabel("�û��˻�");
        JLabel passwordLabel = new JLabel("�û����룺");
        JLabel repasswordLabel = new JLabel("�ظ����룺");

        userText = new JTextField(15);
        password = new JPasswordField(15);
        repassword = new JPasswordField(15);
        JButton regist = new JButton("ע��");
        JButton exitButton = new JButton("�˳�");

        registPanel.add(userLabel);
        registPanel.add(new JScrollPane(userText));
        registPanel.add(passwordLabel);
        registPanel.add(new JScrollPane(password));
        registPanel.add(repasswordLabel);
        registPanel.add(new JScrollPane(repassword));
        registPanel.add(regist);
        registPanel.add(exitButton);

        setResizable(true);
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
     * �˳���ť�¼�����
     * @author ascent
     */
    class ExitActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            setVisible(false);
            dispose();
        }
    }

    /**
     * ע�ᰴť�¼�����
     * @author ascent
     */
    class RegistActionListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            // ���û���Ϣд�����ݿ�
            boolean bo = userDataClient.addUser(userText.getText(), new String(password.getPassword()));
            if (bo) {
                tip.setText("ע��ɹ�");
            } else {
                tip.setText("�û����Ѵ���");
            }
        }
    }

    /**
     * "�رմ���"�¼������ڲ���
     * @author ascent
     */
    class WindowCloser extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            setVisible(false);
            dispose();
        }
    }

    /**
     * ����ʧȥʱ������������������Ƿ�һ��
     * @author ascent
     */
    class MyFocusListener implements FocusListener {

        public void focusGained(FocusEvent arg0) {
        }

        public void focusLost(FocusEvent e) {
            if (e.getSource().equals(password)) {
                if (new String(password.getPassword()) == "" || new String(password.getPassword()) == null) {
                    tip.setText("����Ϊ��!");
                }
            } else if (e.getSource().equals(repassword)) {
                if (!new String(password.getPassword()).equals(new String(password.getPassword()))) {
                    tip.setText("���벻һ��");
                }
            } else {
                tip.setText("");
            }
        }
    }
}