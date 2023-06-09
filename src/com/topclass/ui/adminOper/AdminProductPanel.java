package com.topclass.ui.adminOper;

import javax.swing.*;
import javax.swing.event.*;

import com.topclass.bean.Product;
import com.topclass.ui.MainFrame;
import com.topclass.ui.ProductDetailsDialog;
import com.topclass.ui.ShoppingCartDialog;
import com.topclass.ui.adminOper.AdminProductMainFrame;
import com.topclass.util.ProductDataClient;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import java.io.*;
/**
 * 这个类构建产品面板
 * 这是一个Java Swing GUI程序，用于展示商品信息的面板。
 * 该面板包括一个下拉列表框，用于选择商品类别，一个商品列表框，用于显示选定类别的商品，以及一些按钮，用于执行不同的操作，
 * 如显示商品详细信息、清空商品列表、退出程序等。
 * <p>
 * 该面板继承自JPanel类，具有自己的构造函数和方法。
 * 它还包括许多内部类，这些类实现了不同的事件监听器，用于处理按钮点击、下拉列表框选择等各种事件。
 * <p>
 * 该程序还涉及到网络连接和数据读取，使用了ProductDataClient类来获取商品和类别数据。
 *
 * @author cjc
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AdminProductPanel extends JPanel {

    protected JLabel selectionLabel;

    protected JComboBox categoryComboBox;

    protected JPanel topPanel;

    protected JList productListBox;

    protected JScrollPane productScrollPane;

    protected JButton detailsButton;

    protected JButton clearButton;

    protected JButton exitButton;

    protected JButton deleteButton;

    protected JPanel bottomPanel;

    protected AdminProductMainFrame adminParentFrame;

    protected ArrayList<Product> productArrayList;

    protected ProductDataClient myDataClient;

    /**
     * 构建产品面板的构造方法
     * @param theParentFrame 面板的父窗体
     */

    public AdminProductPanel(AdminProductMainFrame theParentFrame) {
        try {
            adminParentFrame = theParentFrame;
            myDataClient = new ProductDataClient();
            selectionLabel = new JLabel("选择类别");
            categoryComboBox = new JComboBox();
            categoryComboBox.addItem("-------");

            ArrayList categoryArrayList = myDataClient.getCategories();

            Iterator iterator = categoryArrayList.iterator();
            String aCategory;

            while (iterator.hasNext()) {
                aCategory = (String) iterator.next();
                categoryComboBox.addItem(aCategory);
            }

            topPanel = new JPanel();
            productListBox = new JList();
            productListBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            productScrollPane = new JScrollPane(productListBox);

            deleteButton = new JButton("删除");
            detailsButton = new JButton("详细...");
            clearButton = new JButton("清空");
            exitButton = new JButton("退出");

            bottomPanel = new JPanel();

            this.setLayout(new BorderLayout());

            topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            topPanel.add(selectionLabel);
            topPanel.add(categoryComboBox);

            this.add(BorderLayout.NORTH, topPanel);
            this.add(BorderLayout.CENTER, productScrollPane);

            bottomPanel.setLayout(new FlowLayout());
            bottomPanel.add(detailsButton);
            bottomPanel.add(clearButton);
            bottomPanel.add(exitButton);

            this.add(BorderLayout.SOUTH, bottomPanel);

            detailsButton.addActionListener(new com.topclass.ui.adminOper.AdminProductPanel.DetailsActionListener());
            clearButton.addActionListener(new com.topclass.ui.adminOper.AdminProductPanel.ClearActionListener());
            exitButton.addActionListener(new com.topclass.ui.adminOper.AdminProductPanel.ExitActionListener());
            categoryComboBox.addItemListener(new com.topclass.ui.adminOper.AdminProductPanel.GoItemListener());
            productListBox.addListSelectionListener(new com.topclass.ui.adminOper.AdminProductPanel.ProductListSelectionListener());

            detailsButton.setEnabled(false);
            clearButton.setEnabled(false);

        } catch (IOException exc) {
            JOptionPane.showMessageDialog(this, "网络问题 " + exc, "网络问题", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * 设置下拉列选中的分类选项
     */
    protected void populateListBox() {
        try {
            String category = (String) categoryComboBox.getSelectedItem();
            if (!category.startsWith("---")) {
                productArrayList = myDataClient.getProducts(category);
            } else {
                productArrayList = new ArrayList<Product>();
            }

            Object[] theData = productArrayList.toArray();

            System.out.println(productArrayList + ">>>>>>>>>>>");
            productListBox.setListData(theData);
            productListBox.updateUI();

            if (productArrayList.size() > 0) {
                clearButton.setEnabled(true);
            } else {
                clearButton.setEnabled(false);
            }
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(this, "网络问题: " + exc, "网络问题", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * 处理选择详细...按钮时触发的事件监听器
     * @author cjc
     */
    class DetailsActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            int index = productListBox.getSelectedIndex();
            Product product = (Product) productArrayList.get(index);
            AdminProductDetailsDialog adminProductDetailsDialog = new AdminProductDetailsDialog(adminParentFrame, product, deleteButton);
            adminProductDetailsDialog.setVisible(true);
        }
    }

    /**
     * 处理选择退出按钮时触发的事件监听器
     * @author cjc
     */
    class ExitActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            AdminMainFrame adminMainFrame = new AdminMainFrame();
            adminMainFrame.setVisible(true);
            adminParentFrame.setVisible(false);
            setVisible(false);
        }
    }

    /**
     * 处理选择清空按钮时触发的事件监听器
     * @author cjc
     */
    class ClearActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            Object[] noData = new Object[1];
            productListBox.setListData(noData);
            categoryComboBox.setSelectedIndex(0);
        }
    }

    /**
     * 处理选中分类下拉列选的选项时触发的事件监听器
     * @author cjc
     */
    class GoItemListener implements ItemListener {
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                populateListBox();
            }
        }
    }

    /**
     * 处理选中分类列表中选项时触发的事件监听器
     * @author cjc
     */
    class ProductListSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (productListBox.isSelectionEmpty()) {
                detailsButton.setEnabled(false);
            } else {
                detailsButton.setEnabled(true);
            }
        }
    }
}
