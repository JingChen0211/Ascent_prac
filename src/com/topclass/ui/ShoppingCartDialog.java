package com.topclass.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.topclass.bean.Product;
import com.topclass.util.ShoppingCart;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * 显示购物车所购买商品信息
 * <p>
 * 这是一个Java Swing图形界面程序，用于显示购物车中的商品信息。
 * 程序包括一个名为ShoppingCartDialog的类，它继承了JDialog类，用于创建一个模态对话框，显示购物车中的商品信息。
 * 程序中还有一个名为ShoppingCart的类，用于管理购物车中的商品信息。
 * 将提交的购物车信息保持在文件save.db中
 * <p>
 * 程序首先在构造方法中接收一个父窗体和一个查看购物车按钮，然后调用lookShoppingCar()方法创建界面
 * 界面包括一个显示购物车里商品信息的面板和一个底部面板，底部面板包含一个提交表单按钮和一个清空按钮。
 * <p>
 * 显示购物车里商品信息的面板使用GridBagLayout布局管理器，将购物车中的商品信息和对应的数量文本框以及输入数量的标签都添加到面板中。
 * 底部面板中的按钮分别添加了OkButtonActionListener和ClearButtonActionListener,checkoutButtonActionListener，ViewFormButtonActionListener，LoadFormButtonActionListener五个内部类用于监听按钮的点击事件。
 * <p>
 *OkButtonActionListener内部类处理提交表单按钮的点击事件，它检查每个商品的数量文本框是否为空，如果为空则提示用户输入数量。
 * 如果所有数量都已输入，则隐藏对话框并显示另一个名为ShoppingMessageDialog的对话框，用于显示提交成功的消息。
 * 添加对数量输入的验证逻辑，即对输入数字判断是否是正整数
 * <p>
 * ClearButtonActionListener内部类处理清空按钮的点击事件，它调用ShoppingCart类中的clearProduct()方法清空购物车，并禁用查看购物车按钮。
 *增加确认清空功能
 *增加重置负值，默认为1
 *<p>
 *checkoutButtonActionListener"内部类处理结算表单功能，加入了我的购买界面，显示用户在结算后所购买的商品，后续会加上价格，完善支付功能
 *<p>
 *ViewFormButtonActionListener内部类实现表单文件显示功能，读取保存的表单数据，并显示
 *<p>
 *LoadFormButtonActionListener内部类实现表单文件的读取功能，加载保存的表单数据，并读取
 *<p>
 *新增读取保存的表单数据的两个实现方法readFormData和loadFormData
 * @author dak119
 * @version 2.0
 */
@SuppressWarnings("serial")
public class ShoppingCartDialog extends JDialog {

    protected ShoppingCart shoppingCart;

    protected Frame parentFrame;

    private  JButton shoppingButton;

    private  HashMap<String,JTextField> textMap;

    private JLabel tipLabel;

    /**
     * 带两个参数的构造方法
     * @param theParentFrame 父窗体
     * @param shoppingButton 查看购物车按钮
     */
    public ShoppingCartDialog(Frame theParentFrame, JButton shoppingButton) {
        super(theParentFrame, "购物车", true);
        textMap = new HashMap<String,JTextField>();
        parentFrame = theParentFrame;
        this.shoppingButton = shoppingButton;

        lookShoppingCar();
    }


    /**
     * 新建类，用于构建：”我的购买“界面构建
     *添加相关按钮
     */
     class MyPurchaseDialog extends JDialog {
        public MyPurchaseDialog(Frame parentFrame) {
            super(parentFrame, "我的购买", true);

            // 创建并配置"我的购买"界面的内容
            JPanel purchasePanel = new JPanel();
            purchasePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
            purchasePanel.setLayout(new GridLayout(10, 10));

            // 读取保存的表单数据
            String formData = readFormData();

            // 加载保存的表单数据并更新界面
            loadFormData();
            // 显示表单数据
            JOptionPane.showMessageDialog(ShoppingCartDialog.this, formData, "购买信息", JOptionPane.INFORMATION_MESSAGE);

            // 将已购买的商品信息存储在一个列表中
            ArrayList<String> purchasedProducts = new ArrayList<>();
            purchasedProducts.add("购买的商品：");
            purchasedProducts.add("对应金额：");
            purchasedProducts.add("图片：");
            purchasedProducts.add("功能待完善");
            // 创建标签显示已购买的商品信息
            for (String product : purchasedProducts) {
                JLabel productLabel = new JLabel(product);
                purchasePanel.add(productLabel);
            }

            getContentPane().add(purchasePanel);

            setResizable(false);
            setSize(400, 300); // 设置对话框的大小
            // pack();//动态调整界面大小

            setLocationRelativeTo(parentFrame);
        }
    }

    /**
     * 构建显示购物车里商品信息的界面
     * 添加结算按钮
     */
    public void lookShoppingCar() {
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel();
        tipLabel = new JLabel("");
        infoPanel.add(tipLabel);
        infoPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
        infoPanel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(10, 0, 2, 10);

        shoppingCart = new ShoppingCart();
        ArrayList<Product> shoppingList = shoppingCart.getShoppingList();

        JLabel pruductLabel;
        Product product = null;
        for (int i = 0; i < shoppingList.size(); i++) {
            c.gridy = c.gridy + 2;
            String str = "";
            product = shoppingList.get(i);
            str = str + "产品名：" + product.getProductname() + "    ";
            str = str + "CAS号：" + product.getCas() + "    ";
            str = str + "公式：" + product.getFormula() + "    ";
            str = str + "类别：" + product.getCategory();
            pruductLabel = new JLabel(str);
            JPanel panel = new JPanel(new FlowLayout());
            JLabel l = new JLabel("数量：");
            JTextField jtf = new JTextField(7);
            jtf.setText("1");
            panel.add(pruductLabel);
            panel.add(l);
            panel.add(jtf);
            textMap.put(product.getProductname(), jtf);
            pruductLabel.setForeground(Color.black);
            infoPanel.add(panel, c);
        }

        container.add(BorderLayout.NORTH, infoPanel);

        JPanel bottomPanel = new JPanel();
        JButton okButton = new JButton("提交表单");
        bottomPanel.add(okButton);
        JButton clearButton = new JButton("清空");
        bottomPanel.add(clearButton);
        JButton checkoutButton = new JButton("结算"); // 添加结算按钮
        bottomPanel.add(checkoutButton);
        JButton ViewFormButton = new JButton("查看表单"); // 添加查看表单按钮
        bottomPanel.add(ViewFormButton);
        JButton loadFormButton = new JButton("更新读取表单"); // 添加更新读取表单按钮
        bottomPanel.add(loadFormButton);
        JButton myPurchaseButton = new JButton("我的购买");// 添加我的购买按钮
        bottomPanel.add(myPurchaseButton);


        container.add(BorderLayout.SOUTH, bottomPanel);

        okButton.addActionListener(new OkButtonActionListener());
        clearButton.addActionListener(new ClearButtonActionListener());
        checkoutButton.addActionListener(new checkoutButtonActionListener()); // 添加结算按钮的事件监听器
        ViewFormButton.addActionListener(new ViewFormButtonActionListener()); // 添加查看表单的事件监听器
        loadFormButton.addActionListener(new LoadFormButtonActionListener()); // 添加更新读取表单的事件监听器
        myPurchaseButton.addActionListener(new MyPurchaseButtonActionListener());// 添加我的购买的事件监听器

        setResizable(false);
        this.pack();//动态调整界面大小
        Point parentLocation = parentFrame.getLocation();
        this.setLocation(parentLocation.x + 50, parentLocation.y + 50);
    }

    /**
     * 购物车内部功能，提交表单
     */
    class OkButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // 创建StringBuilder用于保存表单数据
            StringBuilder formText = new StringBuilder();

            // 遍历购物车中的商品列表
            for (Product product : shoppingCart.getShoppingList()) {
                // 获取商品对应的数量输入框
                JTextField textField = textMap.get(product.getProductname());
                if (textField != null) {
                    String quantityText = textField.getText().trim();
                    try {
                        int quantity = Integer.parseInt(quantityText);
                        if (quantity <= 0) {
                            // 数量必须是正整数
                            throw new NumberFormatException();
                        }
                        // 将商品和对应的数量保存到StringBuilder
                        formText.append(product.getProductname()).append(": ").append(quantity).append("\n");
                    } catch (NumberFormatException e) {
                        // 处理数量输入不是正整数的情况
                        JOptionPane.showMessageDialog(ShoppingCartDialog.this, "数量必须是正整数", "错误",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            // 将表单数据保存到文件
            try (FileWriter writer = new FileWriter("save.db")) {
                writer.write(formText.toString());
            } catch (IOException e) {
                // 处理文件写入错误
                JOptionPane.showMessageDialog(ShoppingCartDialog.this, "保存表单数据失败", "错误", JOptionPane.ERROR_MESSAGE);
            }

            // 隐藏购物车对话框
            setVisible(false);
            // 启用查看购物车按钮
            shoppingButton.setEnabled(true);


        }
    }
    /**
     * 购物车内部功能，清空表单
     */
    class ClearButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            int choice = JOptionPane.showConfirmDialog(ShoppingCartDialog.this,
                    "确定要清空购物车吗？", "确认清空", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                for (Map.Entry<String, JTextField> entry : textMap.entrySet()) {
                    String productName = entry.getKey();
                    JTextField textField = entry.getValue();
                    if (textField != null) {
                        // 检查默认值是否为正整数，如果不是，则重置为"1"
                        if (!textField.getText().matches("\\d+")) {
                            textField.setText("1");
                        } else {
                            textField.setText("");
                        }
                    }
                }
            }
        }
    }

    /**
     * 购物车内部功能，结算功能
     */
    class checkoutButtonActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event) {
            int confirm = JOptionPane.showConfirmDialog(null, "确认购买这些商品吗？", "确认购买", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION)
            {
                JOptionPane.showMessageDialog(null, "购买成功！", "购买成功", JOptionPane.INFORMATION_MESSAGE);
                shoppingButton.setEnabled(false);

                // 在事件调度线程中创建并显示"我的购买"界面
                MyPurchaseDialog purchaseDialog = new MyPurchaseDialog(parentFrame);
                purchaseDialog.setVisible(true);
                dispose();
            }
        }
    }


    class MyPurchaseButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            MyPurchaseDialog purchaseDialog = new MyPurchaseDialog(parentFrame);
            purchaseDialog.setVisible(true);
        }
    }

    class ViewFormButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // 读取保存的表单数据
            String formData = readFormData();
            // 显示表单数据
            JOptionPane.showMessageDialog(ShoppingCartDialog.this, formData, "表单数据", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    class LoadFormButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // 加载保存的表单数据
            loadFormData();
            // 显示成功提示
            JOptionPane.showMessageDialog(ShoppingCartDialog.this, "读取表单数据成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 读取保存的表单数据
     *
     * @return 保存的表单数据
     */
    private String readFormData() {
        StringBuilder formData = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("save.db"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                formData.append(line).append("\n");
            }
        } catch (IOException e) {
            // 处理文件读取错误
            JOptionPane.showMessageDialog(ShoppingCartDialog.this, "读取表单数据失败", "错误", JOptionPane.ERROR_MESSAGE);
        }
        return formData.toString();
    }

    /**
     * 加载保存的表单数据并更新界面
     */
    private void loadFormData() {
        String formData = readFormData();
        if (!formData.isEmpty()) {
            String[] lines = formData.split("\n");
            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String productName = parts[0].trim();
                    String quantityText = parts[1].trim();

                    // 获取对应的数量输入框
                    JTextField textField = textMap.get(productName);
                    if (textField != null) {
                        textField.setText(quantityText);
                        textField.repaint(); // 重绘数量输入框
                    }
                }
            }
        }
    }

}