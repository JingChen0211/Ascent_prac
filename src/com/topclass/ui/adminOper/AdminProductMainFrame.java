package com.topclass.ui.adminOper;

import com.topclass.ui.adminOper.AdminLogin;
import com.topclass.ui.ProductPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 艾斯医药管理员管理药品的主框架界面
 * @author cjc
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AdminProductMainFrame extends JFrame {

	/**
	 * tabbed pane组件
	 */
	protected JTabbedPane tabbedPane;

	/**
	 * 产品 panel
	 */
	protected AdminProductPanel adminProductPanel;

	/**
	 * 默认构造方法
	 */
	public AdminProductMainFrame() {

		setTitle("欢迎您管理AscentSys应用! ");

		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());

		tabbedPane = new JTabbedPane();

		adminProductPanel = new AdminProductPanel(this);
		tabbedPane.addTab("管理界面", adminProductPanel);

		container.add(BorderLayout.CENTER, tabbedPane);

		JMenuBar myMenuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("文件");
		JMenu openMenu = new JMenu("打开");

		JMenuItem localMenuItem = new JMenuItem("本地硬盘...");
		openMenu.add(localMenuItem);

		JMenuItem networkMenuItem = new JMenuItem("网络...");
		openMenu.add(networkMenuItem);

		JMenuItem webMenuItem = new JMenuItem("互联网...");
		openMenu.add(webMenuItem);
		fileMenu.add(openMenu);

		JMenuItem saveMenuItem = new JMenuItem("保存");
		fileMenu.add(saveMenuItem);

		JMenuItem exitMenuItem = new JMenuItem("退出");
		fileMenu.add(exitMenuItem);

		myMenuBar.add(fileMenu);

		exitMenuItem.addActionListener(new ExitActionListener());

		setupLookAndFeelMenu(myMenuBar);

		JMenu helpMenu = new JMenu("帮助");
		JMenuItem aboutMenuItem = new JMenuItem("关于");
		JMenuItem teachMenuItem = new JMenuItem("使用方法");
		helpMenu.add(aboutMenuItem);
		helpMenu.add(teachMenuItem);

		myMenuBar.add(helpMenu);

		aboutMenuItem.addActionListener(new AboutActionListener());

		teachMenuItem.addActionListener(new TeachActionListener());

		this.setJMenuBar(myMenuBar);

		setSize(500, 400);
		setLocation(100, 100);

		this.addWindowListener(new WindowCloser());

		fileMenu.setMnemonic('f');
		exitMenuItem.setMnemonic('x');
		helpMenu.setMnemonic('h');
		aboutMenuItem.setMnemonic('a');

		// 设定快捷键
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.CTRL_MASK));

		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));

		aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.CTRL_MASK));
	}

	/**
	 * 设定和选择外观
	 */
	protected void setupLookAndFeelMenu(JMenuBar theMenuBar) {

		UIManager.LookAndFeelInfo[] lookAndFeelInfo = UIManager
				.getInstalledLookAndFeels();
		JMenu lookAndFeelMenu = new JMenu("选项");
		JMenuItem anItem = null;
		LookAndFeelListener myListener = new LookAndFeelListener();

		try {
			for (int i = 0; i < lookAndFeelInfo.length; i++) {
				anItem = new JMenuItem(lookAndFeelInfo[i].getName() + " 外观");
				anItem.setActionCommand(lookAndFeelInfo[i].getClassName());
				anItem.addActionListener(myListener);

				lookAndFeelMenu.add(anItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		theMenuBar.add(lookAndFeelMenu);
	}

	/**
	 * 退出方法.
	 */
	public void exit() {
		setVisible(false);
		dispose();
		System.exit(0);
	}

	/**
	 * "退出"事件处理内部类.
	 */
	class ExitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			exit();
		}
	}

	/**
	 * 处理"关闭窗口"事件的内部类.
	 */
	class WindowCloser extends WindowAdapter {

		/**
		 * let's call our exit() method defined above
		 */
		public void windowClosing(WindowEvent e) {
			exit();
		}
	}

	/**
	 * 处理"外观"选择监听器的内部类
	 */
	class LookAndFeelListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String className = event.getActionCommand();
			try {
				UIManager.setLookAndFeel(className);
				SwingUtilities.updateComponentTreeUI(AdminProductMainFrame.this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 处理"关于"菜单监听器的内部类
	 */
	class AboutActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String msg = "超值享受!";
			JOptionPane.showMessageDialog(AdminProductMainFrame.this, msg);
		}
	}
	class TeachActionListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String msg = "根据选择类别对药品进行分类，点击详细信息对药品进行处理，";
			JOptionPane.showMessageDialog(AdminProductMainFrame.this, msg);
		}
	}
}