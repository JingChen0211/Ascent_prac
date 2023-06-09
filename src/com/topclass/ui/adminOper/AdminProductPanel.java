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
 * ����๹����Ʒ���
 * ����һ��Java Swing GUI��������չʾ��Ʒ��Ϣ����塣
 * ��������һ�������б������ѡ����Ʒ���һ����Ʒ�б��������ʾѡ��������Ʒ���Լ�һЩ��ť������ִ�в�ͬ�Ĳ�����
 * ����ʾ��Ʒ��ϸ��Ϣ�������Ʒ�б��˳�����ȡ�
 * <p>
 * �����̳���JPanel�࣬�����Լ��Ĺ��캯���ͷ�����
 * ������������ڲ��࣬��Щ��ʵ���˲�ͬ���¼������������ڴ���ť����������б��ѡ��ȸ����¼���
 * <p>
 * �ó����漰���������Ӻ����ݶ�ȡ��ʹ����ProductDataClient������ȡ��Ʒ��������ݡ�
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
     * ������Ʒ���Ĺ��췽��
     * @param theParentFrame ���ĸ�����
     */

    public AdminProductPanel(AdminProductMainFrame theParentFrame) {
        try {
            adminParentFrame = theParentFrame;
            myDataClient = new ProductDataClient();
            selectionLabel = new JLabel("ѡ�����");
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

            deleteButton = new JButton("ɾ��");
            detailsButton = new JButton("��ϸ...");
            clearButton = new JButton("���");
            exitButton = new JButton("�˳�");

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
            JOptionPane.showMessageDialog(this, "�������� " + exc, "��������", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * ����������ѡ�еķ���ѡ��
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
            JOptionPane.showMessageDialog(this, "��������: " + exc, "��������", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * ����ѡ����ϸ...��ťʱ�������¼�������
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
     * ����ѡ���˳���ťʱ�������¼�������
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
     * ����ѡ����հ�ťʱ�������¼�������
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
     * ����ѡ�з���������ѡ��ѡ��ʱ�������¼�������
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
     * ����ѡ�з����б���ѡ��ʱ�������¼�������
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
