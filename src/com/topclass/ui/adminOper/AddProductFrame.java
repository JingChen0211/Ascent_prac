package com.topclass.ui.adminOper;

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

import com.topclass.util.ProductDataClient;
import com.topclass.util.UserDataClient;

/**
 * 用户注册窗体
 *
 * @author ascent
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AddProductFrame extends JFrame {
    private JTextField productNameText;

    private JTextField casText;

    private JTextField structureText;

    private JTextField formulaText;

    private JTextField priceText;

    private JTextField realstockText;

    private JTextField categoryText;

    private JLabel tip;

    private UserDataClient userDataClient;

    /**
     * 默认构造方法，初始化用户注册窗体
     */
    public AddProductFrame() {
        this.setTitle("添加产品");

        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());

        JPanel addPanel = new JPanel();

        JLabel nameLabel = new JLabel("产品名：");
        JLabel CasLabel = new JLabel("CAS号：");
        JLabel structureLabel = new JLabel("结构图：");
        JLabel formulaLabel = new JLabel("公式：");
        JLabel priceLabel = new JLabel("价格：");
        JLabel realstockLabel = new JLabel("数量：");
        JLabel categoryLabel = new JLabel("类别：");

        productNameText = new JTextField(15);
        casText = new JTextField(15);
        structureText = new JTextField(15);
        formulaText = new JTextField(15);
        priceText = new JTextField(15);
        realstockText = new JTextField(15);
        categoryText = new JTextField(15);

        JButton exitButton = new JButton("退出");
        JButton addProduct = new JButton("添加");

        addPanel.add(nameLabel);
        addPanel.add(new JScrollPane(productNameText));
        addPanel.add(CasLabel);
        addPanel.add(new JScrollPane(casText));
        addPanel.add(structureLabel);
        addPanel.add(new JScrollPane(structureText));
        addPanel.add(formulaLabel);
        addPanel.add(new JScrollPane(formulaText));
        addPanel.add(priceLabel);
        addPanel.add(new JScrollPane(priceText));
        addPanel.add(realstockLabel);
        addPanel.add(new JScrollPane(realstockText));
        addPanel.add(categoryLabel);
        addPanel.add(new JScrollPane(categoryText));
        addPanel.add(addProduct);
        addPanel.add(exitButton);

        setResizable(false);
        setSize(240, 380);
        setLocation(300, 100);

        JPanel tipPanel = new JPanel();

        tip = new JLabel();

        tipPanel.add(tip);

        container.add(BorderLayout.CENTER, addPanel);
        container.add(BorderLayout.NORTH, tip);

        exitButton.addActionListener(new ExitActionListener());
        addProduct.addActionListener(new AddActionListener());
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
     *
     * @author ascent
     */
    class ExitActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            AdminMainFrame adminMainFrame = new AdminMainFrame();
            adminMainFrame.setVisible(true);
            setVisible(false);
            dispose();
        }
    }

    /**
     * 添加按钮事件监听
     *
     * @author ascent
     */
    class AddActionListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            ProductDataClient productDataClient = null;
            try {
                productDataClient = new ProductDataClient();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
			/**
			 * 		productNameText = new JTextField(15);
			 * 		casText = new JTextField(15);
			 * 		structureText = new JTextField(15);
			 * 		formulaText = new JTextField(15);
			 * 		priceText = new JTextField(15);
			 * 		realstockText = new JTextField(15);
			 * 		categoryext = new JTextField(15);
			 */
            boolean bo = productDataClient.addProduct(productNameText.getText(), casText.getText()
                    , structureText.getText(), formulaText.getText(), priceText.getText(), realstockText.getText(), categoryText.getText());
            if (bo) {
                AdminRefresh refresh =new AdminRefresh();
                refresh.setVisible(true);
                setVisible(false);
            } else {
                tip.setText("该商品已存在！");
            }
        }
    }

    /**
     * "关闭窗口"事件处理内部类
     *
     * @author ascent
     */
    class WindowCloser extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            setVisible(false);
            dispose();
        }
    }
}

