package ui;

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

import util.UserDataClient;

/**
 * 用户注册窗体
 * @author ascent
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Add extends JFrame {
	private JTextField userText;

	private JTextField password;

	private JTextField repassword;
	
	private JTextField Number;
	
	private JTextField Type;

	private JLabel tip;

	private UserDataClient userDataClient;

	/**
	 * 默认构造方法，初始化用户注册窗体
	 */
	public Add() {
		this.setTitle("添加产品");

		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());

		JPanel registPanel = new JPanel();

		JLabel name = new JLabel("产品名：");
		JLabel Cas = new JLabel("CAS号：");
		JLabel repasswordLabel = new JLabel("公式：");
		JLabel number = new JLabel("数量：");
		JLabel type = new JLabel("类别：");

		userText = new JTextField(15);
		password = new JTextField(15);
		repassword = new JTextField(15);
		Number = new JTextField(15);
		Type = new JTextField(15);
		JButton exitButton = new JButton("退出");
		JButton regist = new JButton("添加");

		registPanel.add(name);
		registPanel.add(new JScrollPane(userText));
		registPanel.add(Cas);
		registPanel.add(new JScrollPane(password));
		registPanel.add(repasswordLabel);
		registPanel.add(new JScrollPane(repassword));
		registPanel.add(number);
		registPanel.add(new JScrollPane(Number));
		registPanel.add(type);
		registPanel.add(new JScrollPane(Type));
		registPanel.add(regist);
		registPanel.add(exitButton);

		setResizable(false);
		setSize(400, 380);
		setLocation(300, 100);

		JPanel tipPanel = new JPanel();

		tip = new JLabel();

		tipPanel.add(tip);

		container.add(BorderLayout.CENTER, registPanel);
		container.add(BorderLayout.NORTH, tip);

		exitButton.addActionListener(new ExitActionListener());
		regist.addActionListener(new RegistActionListener());
		this.addWindowListener(new WindowCloser());
		this.addWindowFocusListener(new WindowFocusListener() {// 设置父窗口
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
			// 用户注册操作
			String a="830g";
			String b="维生素";
			boolean bo = userDataClient.addPro(userText.getText(), password.getText()
					,repassword.getText(),Number.getText(),Type.getText(),a,b);
			if (bo) {
				tip.setText("添加成功！");
			} else {
				tip.setText("用户名已存在！");
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
	 * 密码不一致触发的事件监听器处理类
	 * @author ascent
	 */
}

