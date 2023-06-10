package com.topclass.ui.adminOper;

import com.topclass.ui.LoginFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMainFrame extends JFrame {

    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminMainFrame frame = new AdminMainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public AdminMainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 150);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel headingLabel = new JLabel("欢迎您！管理员！");
        headingLabel.setFont(new Font("宋体", Font.BOLD, 20));
        headingLabel.setBounds(160, 22, 186, 28);
        contentPane.add(headingLabel);

        JButton addProductButton = new JButton("添加商品");
        //添加商品的事件监听
        addProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddProductFrame addProduct = new AddProductFrame();
                addProduct.setVisible(true);
                setVisible(false);
                dispose();
            }
        });
        addProductButton.setBounds(29, 75, 93, 34);
        contentPane.add(addProductButton);

        JButton deletetProductButton = new JButton("删除商品");
        //删除商品
        deletetProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AdminProductMainFrame adminProductMainFrame = new AdminProductMainFrame();
                adminProductMainFrame.setVisible(true);
                setVisible(false);
            }
        });
        deletetProductButton.setBounds(130, 75, 93, 34);
        contentPane.add(deletetProductButton);

        JButton exitButton = new JButton("退出");
        //退出功能
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
                setVisible(false);
                dispose();
            }
        });
        exitButton.setBounds(317, 75, 93, 34);
        contentPane.add(exitButton);

        setResizable(false); //设置窗口大小不能改变
    }
}

