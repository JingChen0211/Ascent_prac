package com.ascent.ui;

import javax.swing.*;
import javax.swing.event.*;

import com.ascent.bean.Product;
import com.ascent.util.ProductDataClient;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import java.io.*;

/**
 * 商品面板，用于展示商品列表
 * 
 * @author ascent
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ProductPanel extends JPanel {

    protected JLabel selectionLabel;

    protected JComboBox<String> categoryComboBox;

    protected JPanel topPanel;

    protected JList<Product> productListBox;

    protected JScrollPane productScrollPane;

    protected JButton detailsButton;

    protected JButton clearButton;

    protected JButton exitButton;

    protected JButton shoppingButton;

    protected JPanel bottomPanel;

    protected MainFrame parentFrame;

    protected ArrayList<Product> productArrayList;

    protected ProductDataClient myDataClient;

    /**
     * 构造商品面板
     * 
     * @param theParentFrame 父窗口
     */
    public ProductPanel(MainFrame theParentFrame) {
        try {
            parentFrame = theParentFrame;
            myDataClient = new ProductDataClient();
            selectionLabel = new JLabel("选择类别");
            categoryComboBox = new JComboBox<>();
            categoryComboBox.addItem("-------");

            ArrayList<String> categoryArrayList = myDataClient.getCategories();

            for (String aCategory : categoryArrayList) {
                categoryComboBox.addItem(aCategory);
            }

            topPanel = new JPanel();
            productListBox = new JList<>();
            productListBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            productScrollPane = new JScrollPane(productListBox);

            detailsButton = new JButton("详情...");
            clearButton = new JButton("清除");
            exitButton = new JButton("退出");
            shoppingButton = new JButton("查看购物车");

            bottomPanel = new JPanel();

            this.setLayout(new BorderLayout());

            topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            topPanel.add(selectionLabel);
            topPanel.add(categoryComboBox);

            this.add(BorderLayout.NORTH, topPanel);
            this.add(BorderLayout.CENTER, productScrollPane);

            bottomPanel.setLayout(new FlowLayout());
            bottomPanel.add(shoppingButton);
            bottomPanel.add(detailsButton);
            bottomPanel.add(clearButton);
            bottomPanel.add(exitButton);

            this.add(BorderLayout.SOUTH, bottomPanel);

            detailsButton.addActionListener(new DetailsActionListener());
            clearButton.addActionListener(new ClearActionListener());
            exitButton.addActionListener(new ExitActionListener());
            shoppingButton.addActionListener(new ShoppingActionListener());
            categoryComboBox.addItemListener(new CategoryComboBoxItemListener());
            productListBox.addListSelectionListener(new ProductListSelectionListener());

            detailsButton.setEnabled(false);
            clearButton.setEnabled(false);
            shoppingButton.setEnabled(false);

        } catch (IOException exc) {
            JOptionPane.showMessageDialog(this, "数据访问异常：" + exc, "数据访问异常", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * 根据选定的类别填充商品列表
     */
    protected void populateListBox() {
        try {
            String category = (String) categoryComboBox.getSelectedItem();
            if (!category.startsWith("---")) {
                productArrayList = myDataClient.getProducts(category);
            } else {
                productArrayList = new ArrayList<>();
            }

            Product[] theData = productArrayList.toArray(new Product[productArrayList.size()]);


            productListBox.setListData(theData);
            productListBox.updateUI();

            if (productArrayList.size() > 0) {
                clearButton.setEnabled(true);
            } else {
                clearButton.setEnabled(false);
            }
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(this, "数据访问异常：" + exc, "数据访问异常", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * 点击详情按钮的事件监听器
     */
    class DetailsActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            int index = productListBox.getSelectedIndex();
            Product product = productArrayList.get(index);
            ProductDetailsDialog myDetailsDialog = new ProductDetailsDialog(parentFrame, product, shoppingButton);
            myDetailsDialog.setVisible(true);
        }
    }

    /**
     * 点击查看购物车按钮的事件监听器
     */
    class ShoppingActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            ShoppingCartDialog myShoppingDialog = new ShoppingCartDialog(parentFrame, shoppingButton);
            myShoppingDialog.setVisible(true);
        }
    }

    /**
     * 点击退出按钮的事件监听器
     */
    class ExitActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            parentFrame.exit();
        }
    }

    /**
     * 点击清除按钮的事件监听器
     */
    class ClearActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            Object[] noData = new Object[1];
            productListBox.setListData((Product[]) noData);
            categoryComboBox.setSelectedIndex(0);
        }
    }

    /**
     * 类别下拉框的选项改变事件监听器
     */
    class CategoryComboBoxItemListener implements ItemListener {
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                populateListBox();
            }
        }
    }

    /**
     * 商品列表的选择事件监听器
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
