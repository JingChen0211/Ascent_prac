package com.topclass.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.topclass.util.UserDataClient;

/**
 * �û�ע�ᴰ��
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
	 * Ĭ�Ϲ��췽������ʼ���û�ע�ᴰ��
	 */
	public Add() {
		this.setTitle("��Ӳ�Ʒ");

		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());

		JPanel registPanel = new JPanel();

		JLabel name = new JLabel("��Ʒ����");
		JLabel Cas = new JLabel("CAS�ţ�");
		JLabel repasswordLabel = new JLabel("��ʽ��");
		JLabel number = new JLabel("������");
		JLabel type = new JLabel("���");

		userText = new JTextField(15);
		password = new JTextField(15);
		repassword = new JTextField(15);
		Number = new JTextField(15);
		Type = new JTextField(15);
		JButton exitButton = new JButton("�˳�");
		JButton regist = new JButton("���");

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
		this.addWindowFocusListener(new WindowFocusListener() {// ���ø�����
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
			// �û�ע�����
			String a="830g";
			String b="ά����";
			boolean bo = userDataClient.addPro(userText.getText(), password.getText()
					,repassword.getText(),Number.getText(),Type.getText(),a,b);
			if (bo) {
				tip.setText("��ӳɹ���");
			} else {
				tip.setText("�û����Ѵ��ڣ�");
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
}

