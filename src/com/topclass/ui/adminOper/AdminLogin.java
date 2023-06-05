package com.topclass.ui.adminOper;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.topclass.bean.User;
import com.topclass.ui.MainFrame;
import com.topclass.util.UserDataClient;

/**
 * 用户登陆窗体
 * @author ascent
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AdminLogin extends JFrame {

	protected JTextField userText;

	protected JPasswordField password;

	protected JLabel tip;

	protected UserDataClient userDataClient;

	/**
	 * 默认的构造方法，初始化登陆窗体
	 */
	public AdminLogin() {

		setTitle("管理员登陆");

		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());

		JPanel loginPanel = new JPanel();

		JLabel userLabel = new JLabel("管理员帐号：");
		JLabel passwordLabel = new JLabel("管理员密码：");

		userText = new JTextField(15);
		password = new JPasswordField(15);

		JButton loginButton = new JButton("登陆");
		//JButton regist = new JButton("注册");
		JButton exitButton = new JButton("退出");

		loginPanel.add(userLabel);
		loginPanel.add(new JScrollPane(userText));
		loginPanel.add(passwordLabel);
		loginPanel.add(new JScrollPane(password));
		loginPanel.add(loginButton);
		//loginPanel.add(regist);
		loginPanel.add(exitButton);

		setResizable(true);
		setSize(260, 150);
		setLocation(300, 100);

		JPanel tipPanel = new JPanel();

		tip = new JLabel();

		tipPanel.add(tip);

		container.add(BorderLayout.CENTER, loginPanel);
		container.add(BorderLayout.NORTH, tip);

		exitButton.addActionListener(new ExitActionListener());
		loginButton.addActionListener(new LoginActionListener());
		//regist.addActionListener(new RegistActionListener());
		this.addWindowListener(new WindowCloser());
		try {
			userDataClient = new UserDataClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 处理"退出"按钮事件监听的内部类
	 */
	class ExitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			setVisible(false);
			dispose();
			//userDataClient.closeSocKet();
		}
	}

	/**
	 * 处理"登陆"按钮事件监听的内部类
	 */
	class LoginActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			boolean bo = false;
			HashMap userTable = userDataClient.getUsers();
			if (userTable != null) {
				if (userTable.containsKey(userText.getText())) {
					User userObject = (User) userTable.get(userText.getText());
					char[] chr = password.getPassword();
					String pwd = new String(chr);
					String admin = "admin";
					if (userObject.getPassword().equals(pwd)&&userObject.getUsername().equals(admin)) {
						bo = true;
					}
				}
				if (bo) {
					//密码正确
					//关闭用户数据客户端
					userDataClient.closeSocket();
					//将登录界面设置为不可见
					setVisible(false);
					dispose();
					//进入主界面
					AddProduct addFrame = new AddProduct();
					addFrame.setVisible(true);
				} else {
					tip.setText(" 管理员密码错误.");
				}
			} else {
				tip.setText("服务器连接失败,请稍候再试.");
			}
		}
	}

	/**
	 * 处理"注册"按钮事件监听的内部类.
	 */
	class RegistActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			// 打开注册用户的窗口
			AddProduct addFrame = new AddProduct();
			addFrame.setVisible(true);
		}
	}

	/**
	 * 处理"关闭窗口"事件监听的内部类.
	 */
	class WindowCloser extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			setVisible(false);
			dispose();
			userDataClient.closeSocket();
		}
	}
}
