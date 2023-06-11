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
import com.topclass.util.UserDataClient;

/**
 * 用户登陆窗体
 * @author ascent
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AdminRefresh extends JFrame {

	protected JTextField userText;

	protected JPasswordField password;

	protected JLabel tip;

	protected UserDataClient userDataClient;

	/**
	 * 默认的构造方法，初始化登陆窗体
	 */
public void Rrefresh()
{
	AdminMainFrame adminPMFrame= new AdminMainFrame();
	adminPMFrame.setVisible(true);
}
	public AdminRefresh() {

		setTitle("操作成功");

		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());

		JPanel loginPanel = new JPanel();

	

		JButton loginButton = new JButton("确定");
	
		loginPanel.add(loginButton);


		setResizable(false);
		setSize(300, 120);
		setLocation(300, 100);

		JPanel tipPanel = new JPanel();

		tip = new JLabel();

		tipPanel.add(tip);

		container.add(BorderLayout.NORTH, loginPanel);
	
		loginButton.addActionListener(new LoginActionListener());
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
			boolean bo = true;
			HashMap userTable = userDataClient.getUsers();
			if (userTable != null) {
				if (bo) {
					//密码正确
					//关闭用户数据客户端
					userDataClient.closeSocket();
					//将登录界面设置为不可见
					setVisible(false);
					dispose();
					//进入主界面
					AdminProductMainFrame adminMainFrame = new AdminProductMainFrame();
					adminMainFrame.setVisible(true);
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
			AddProductFrame addFrame = new AddProductFrame();
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
