package com.topclass.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
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
 * ��ʾ���ﳵ��������Ʒ��Ϣ
 * <p>
 * ����һ��Java Swingͼ�ν������������ʾ���ﳵ�е���Ʒ��Ϣ��
 * �������һ����ΪShoppingCartDialog���࣬���̳���JDialog�࣬���ڴ���һ��ģ̬�Ի�����ʾ���ﳵ�е���Ʒ��Ϣ��
 * �����л���һ����ΪShoppingCart���࣬���ڹ����ﳵ�е���Ʒ��Ϣ��
 * ���ύ�Ĺ��ﳵ��Ϣ�������ļ�save.db��
 * <p>
 * ���������ڹ��췽���н���һ���������һ���鿴���ﳵ��ť��Ȼ�����lookShoppingCar()������������
 * �������һ����ʾ���ﳵ����Ʒ��Ϣ������һ���ײ���壬�ײ�������һ���ύ����ť��һ����հ�ť��
 * <p>
 * ��ʾ���ﳵ����Ʒ��Ϣ�����ʹ��GridBagLayout���ֹ������������ﳵ�е���Ʒ��Ϣ�Ͷ�Ӧ�������ı����Լ����������ı�ǩ����ӵ�����С�
 * �ײ�����еİ�ť�ֱ������OkButtonActionListener��ClearButtonActionListener,checkoutButtonActionListener��ViewFormButtonActionListener��LoadFormButtonActionListener����ڲ������ڼ�����ť�ĵ���¼���
 * <p>
 *OkButtonActionListener�ڲ��ദ���ύ����ť�ĵ���¼��������ÿ����Ʒ�������ı����Ƿ�Ϊ�գ����Ϊ������ʾ�û�����������
 * ������������������룬�����ضԻ�����ʾ��һ����ΪShoppingMessageDialog�ĶԻ���������ʾ�ύ�ɹ�����Ϣ��
 * ��Ӷ������������֤�߼����������������ж��Ƿ���������
 * <p>
 * ClearButtonActionListener�ڲ��ദ����հ�ť�ĵ���¼���������ShoppingCart���е�clearProduct()������չ��ﳵ�������ò鿴���ﳵ��ť��
 *����ȷ����չ���
 *�������ø�ֵ��Ĭ��Ϊ1
 *<p>
 *checkoutButtonActionListener"�ڲ��ദ���������ܣ�������
 *<p>
 *ViewFormButtonActionListener�ڲ���ʵ�ֱ��ļ���ʾ���ܣ���ȡ����ı����ݣ�����ʾ
 *<p>
 *LoadFormButtonActionListener�ڲ���ʵ�ֱ��ļ��Ķ�ȡ���ܣ����ر���ı����ݣ�����ȡ
 *<p>
 *������ȡ����ı����ݵ�����ʵ�ַ���readFormData��loadFormData
 * @author dak119
 * @version 2.0
 */
@SuppressWarnings("serial")
public class ShoppingCartDialog extends JDialog {

	protected ShoppingCart shoppingCart;

	protected Frame parentFrame;

	private JButton shoppingButton;

	private HashMap<String,JTextField> textMap;

	private JLabel tipLabel;

	/**
	 * �����������Ĺ��췽��
	 * @param theParentFrame ������
	 * @param shoppingButton �鿴���ﳵ��ť
	 */
	public ShoppingCartDialog(Frame theParentFrame, JButton shoppingButton) {
		super(theParentFrame, "���ﳵ", true);
		textMap = new HashMap<String,JTextField>();
		parentFrame = theParentFrame;
		this.shoppingButton = shoppingButton;

		lookShoppingCar();
	}

	
	/**
	 * ������ʾ���ﳵ����Ʒ��Ϣ�Ľ���
	 * ��ӽ��㰴ť
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
			str = str + "��Ʒ����" + product.getProductname() + "    ";
			str = str + "CAS�ţ�" + product.getCas() + "    ";
			str = str + "��ʽ��" + product.getFormula() + "    ";
			str = str + "���" + product.getCategory();
			pruductLabel = new JLabel(str);
			JPanel panel = new JPanel(new FlowLayout());
			JLabel l = new JLabel("������");
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
		JButton okButton = new JButton("�ύ��");
		bottomPanel.add(okButton);
		JButton clearButton = new JButton("���");
		bottomPanel.add(clearButton);
		JButton checkoutButton = new JButton("����"); // ��ӽ��㰴ť
        bottomPanel.add(checkoutButton);
        JButton ViewFormButton = new JButton("�鿴��"); // ��Ӳ鿴����ť
        bottomPanel.add(ViewFormButton);
        JButton loadFormButton = new JButton("��ȡ��"); // ��Ӷ�ȡ����ť
        bottomPanel.add(loadFormButton);
        
		container.add(BorderLayout.SOUTH, bottomPanel);

		okButton.addActionListener(new OkButtonActionListener());
		clearButton.addActionListener(new ClearButtonActionListener());
		checkoutButton.addActionListener(new checkoutButtonActionListener()); // ��ӽ��㰴ť���¼�������
		ViewFormButton.addActionListener(new ViewFormButtonActionListener()); // ��Ӳ鿴�����¼�������
		loadFormButton.addActionListener(new LoadFormButtonActionListener()); // ��Ӷ�ȡ�����¼�������
		
		
		setResizable(false);
		this.pack();
		Point parentLocation = parentFrame.getLocation();
		this.setLocation(parentLocation.x + 50, parentLocation.y + 50);
	}
	

	class OkButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // ����StringBuilder���ڱ��������
            StringBuilder formText = new StringBuilder();

            // �������ﳵ�е���Ʒ�б�
            for (Product product : shoppingCart.getShoppingList()) {
                // ��ȡ��Ʒ��Ӧ�����������
                JTextField textField = textMap.get(product.getProductname());
                if (textField != null) {
                    String quantityText = textField.getText().trim();
                    try {
                        int quantity = Integer.parseInt(quantityText);
                        if (quantity <= 0) {
                            // ����������������
                            throw new NumberFormatException();
                        }
                        // ����Ʒ�Ͷ�Ӧ���������浽StringBuilder
                        formText.append(product.getProductname()).append(": ").append(quantity).append("\n");
                    } catch (NumberFormatException e) {
                        // �����������벻�������������
                        JOptionPane.showMessageDialog(ShoppingCartDialog.this, "����������������", "����",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            // �������ݱ��浽�ļ�
            try (FileWriter writer = new FileWriter("save.db")) {
                writer.write(formText.toString());
            } catch (IOException e) {
                // �����ļ�д�����
                JOptionPane.showMessageDialog(ShoppingCartDialog.this, "���������ʧ��", "����", JOptionPane.ERROR_MESSAGE);
            }

            // ���ع��ﳵ�Ի���
            setVisible(false);
            // ���ò鿴���ﳵ��ť
            shoppingButton.setEnabled(true);
        }
    }

    class ClearButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
        	 int choice = JOptionPane.showConfirmDialog(ShoppingCartDialog.this,
                     "ȷ��Ҫ��չ��ﳵ��", "ȷ�����", JOptionPane.YES_NO_OPTION);
        	 if (choice == JOptionPane.YES_OPTION) {
                 for (Map.Entry<String, JTextField> entry : textMap.entrySet()) {
                     String productName = entry.getKey();
                     JTextField textField = entry.getValue();
                     if (textField != null) {
                         // ���Ĭ��ֵ�Ƿ�Ϊ��������������ǣ�������Ϊ"1"
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

    class checkoutButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JOptionPane.showMessageDialog(ShoppingCartDialog.this, "����ɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
       
        }
    }

    class ViewFormButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // ��ȡ����ı�����
            String formData = readFormData();

            // ��ʾ������
            JOptionPane.showMessageDialog(ShoppingCartDialog.this, formData, "������", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    class LoadFormButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // ���ر���ı�����
            loadFormData();
            // ��ʾ�ɹ���ʾ
            JOptionPane.showMessageDialog(ShoppingCartDialog.this, "��ȡ�����ݳɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * ��ȡ����ı�����
     * 
     * @return ����ı�����
     */
    private String readFormData() {
        StringBuilder formData = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("save.db"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                formData.append(line).append("\n");
            }
        } catch (IOException e) {
            // �����ļ���ȡ����
            JOptionPane.showMessageDialog(ShoppingCartDialog.this, "��ȡ������ʧ��", "����", JOptionPane.ERROR_MESSAGE);
        }
        return formData.toString();
    }

    /**
     * ���ر���ı����ݲ����½���
     */
    private void loadFormData() {
        String formData = readFormData();
        if (!formData.isEmpty()) {
            // ����������
            String[] lines = formData.split("\n");
            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String productName = parts[0].trim();
                    String quantityText = parts[1].trim();
                    // ��������������ֵ
                    JTextField textField = textMap.get(productName);
                    if (textField != null) {
                        textField.setText(quantityText);
                    }
                }
            }
        }
    }
}