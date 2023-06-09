package com.topclass.ui.adminOper;

import com.topclass.bean.User;
import com.topclass.ui.MainFrame;
import com.topclass.ui.RegistFrame;
import com.topclass.util.UserDataClient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

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
                    AddProduct addProduct = new AddProduct();
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
            deletetProductButton.setBounds(224, 75, 93, 34);
            contentPane.add(deletetProductButton);

            JButton viewUserButton = new JButton("查看用户");
            //查看用户
            viewUserButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //进入管理员登陆界面
                    AdminLogin adminLogin = new AdminLogin();
                    adminLogin.setVisible(true);
                    //将登录界面设置为不可见
                    setVisible(false);
                    dispose();
                }
            });
            viewUserButton.setBounds(129, 75, 93, 34);
            contentPane.add(viewUserButton);

            JButton exitButton = new JButton("退出");
            //退出功能
            exitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    dispose();
                }
            });
            exitButton.setBounds(317, 75, 93, 34);
            contentPane.add(exitButton);

            setResizable(false); //设置窗口大小不能改变
        }
    }

