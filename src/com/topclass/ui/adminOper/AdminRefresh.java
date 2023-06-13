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
 * �û���½����
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
	 * Ĭ�ϵĹ��췽������ʼ����½����
	 */
public void Rrefresh()
{
	AdminMainFrame adminPMFrame= new AdminMainFrame();
	adminPMFrame.setVisible(true);
}
	public AdminRefresh() {

		setTitle("�����ɹ�");

		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());

		JPanel loginPanel = new JPanel();

	

		JButton loginButton = new JButton("ȷ��");
	
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
	 * ����"�˳�"��ť�¼��������ڲ���
	 */
	class ExitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			setVisible(false);
			dispose();
			//userDataClient.closeSocKet();
		}
	}

	/**
	 * ����"��½"��ť�¼��������ڲ���
	 */
	class LoginActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			boolean bo = true;
			HashMap userTable = userDataClient.getUsers();
			if (userTable != null) {
				if (bo) {
					//������ȷ
					//�ر��û����ݿͻ���
					userDataClient.closeSocket();
					//����¼��������Ϊ���ɼ�
					setVisible(false);
					dispose();
					//����������
					AdminProductMainFrame adminMainFrame = new AdminProductMainFrame();
					adminMainFrame.setVisible(true);
				} else {
					tip.setText(" ����Ա�������.");
				}
			} else {
				tip.setText("����������ʧ��,���Ժ�����.");
			}
		}
	}

	/**
	 * ����"ע��"��ť�¼��������ڲ���.
	 */
	class RegistActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			// ��ע���û��Ĵ���
			AddProductFrame addFrame = new AddProductFrame();
			addFrame.setVisible(true);
		}
	}

	/**
	 * ����"�رմ���"�¼��������ڲ���.
	 */
	class WindowCloser extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			setVisible(false);
			dispose();
			userDataClient.closeSocket();
		}
	}
}
