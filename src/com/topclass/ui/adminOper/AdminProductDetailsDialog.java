package com.topclass.ui.adminOper;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.*;

import com.topclass.bean.Product;
import com.topclass.ui.MainFrame;
import com.topclass.ui.LoginFrame;
import com.topclass.util.ProductDataClient;
import com.topclass.ui.adminOper.AdminRefresh;
import com.topclass.ui.adminOper.AdminProductMainFrame;
/**
 * �������ʾ��Ʒ��ϸ��Ϣ�Ի���
 * @author ascent
 * @version 1.0
 */
@SuppressWarnings("serial")
public class AdminProductDetailsDialog extends JDialog {
    JPanel infoPanel = new JPanel();

    protected Product myProduct;

    protected Frame parentFrame;

    protected JButton deleteButton;

    /**
     * �����������Ĺ��췽��
     * @param theParentFrame ������
     * @param theProduct ��ǰ�鿴����Ʒ����
     * @param deleteButton ɾ����ť
     */
    public AdminProductDetailsDialog(Frame theParentFrame, Product theProduct,
                                JButton deleteButton) {
        this(theParentFrame, "ҩƷ��ϸ��Ϣ " + theProduct.getProductname(),
                theProduct, deleteButton);
    }

    /**
     * ���ĸ������Ĺ��췽��
     * @param theParentFrame ������
     * @param theTitle �������
     * @param theProduct ��ǰ�鿴����Ʒ����
     * @param deleteButton ɾ����ť
     */
    public AdminProductDetailsDialog(Frame theParentFrame, String theTitle,
                                Product theProduct, JButton deleteButton) {

        super(theParentFrame, theTitle, true);

        myProduct = theProduct;
        parentFrame = theParentFrame;
        this.deleteButton = deleteButton;

        buildGui();
    }

    /**
     * ����������ʾ��Ʒ��Ϣ����
     */
    private void buildGui() {

        Container container = this.getContentPane();

        container.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        infoPanel.setBorder(new EmptyBorder(10, 10, 0, 10));

        infoPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(10, 0, 2, 10);
        JLabel artistLabel = new JLabel("��Ʒ��:  " + myProduct.getProductname());
        artistLabel.setForeground(Color.black);
        infoPanel.add(artistLabel, c);

        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(2, 0, 10, 10);
        JLabel titleLabel = new JLabel("CAS��:  " + myProduct.getCas());
        titleLabel.setForeground(Color.black);
        infoPanel.add(titleLabel, c);

        JLabel categoryLabel = new JLabel("��ʽ:  " + myProduct.getFormula());
        c.insets = new Insets(2, 0, 2, 0);
        categoryLabel.setForeground(Color.black);
        infoPanel.add(categoryLabel, c);

        JLabel durationLabel = new JLabel("����:  " + myProduct.getRealstock());
        durationLabel.setForeground(Color.black);
        infoPanel.add(durationLabel, c);

        JLabel priceLabel = new JLabel("��� " + myProduct.getCategory());
        c.insets = new Insets(10, 0, 2, 0);
        priceLabel.setForeground(Color.black);
        infoPanel.add(priceLabel, c);

        c.gridx = 3;
        c.gridy = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridheight = 5;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(5, 5, 20, 0);
        String imageName = myProduct.getStructure();
        ImageIcon recordingIcon = null;
        JLabel recordingLabel = null;

        // ��ȡͼƬ
        try {
            if (imageName.trim().length() == 0) {
                recordingLabel = new JLabel("  ͼƬ������  ");
            } else {
                recordingIcon = new ImageIcon(getClass().getResource("/images/" + imageName));
                recordingLabel = new JLabel(recordingIcon);
            }
        } catch (Exception exc) {
            recordingLabel = new JLabel("  ͼƬ������  ");
        }

        recordingLabel.setBorder(BorderFactory.createRaisedBevelBorder());
        recordingLabel.setToolTipText(myProduct.getProductname());

        infoPanel.add(recordingLabel, c);

        container.add(BorderLayout.NORTH, infoPanel);

        JPanel bottomPanel = new JPanel();
        JButton okButton = new JButton("OK");
        bottomPanel.add(okButton);
        JButton deleteButton = new JButton("ɾ��");
        bottomPanel.add(deleteButton);
        container.add(BorderLayout.SOUTH, bottomPanel);

        okButton.addActionListener(new OkButtonActionListener());
        deleteButton.addActionListener(new DeleteButtonActionListener());

        this.pack();

        Point parentLocation = parentFrame.getLocation();
        this.setLocation(parentLocation.x + 50, parentLocation.y + 50);
    }

    /**
     * ����"OK"��ť���ڲ���
     */
    class OkButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            setVisible(false);
        }
    }

    /**
     * ����"ɾ��"��ť���ڲ���
     */
    class DeleteButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            int n = JOptionPane.showConfirmDialog(null, "ȷ��ɾ����?", "ȷ�϶Ի���", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                ProductDataClient productDataClient = null;
                try {
                    productDataClient = new ProductDataClient();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                boolean bo = productDataClient.deleteProduct(myProduct.getProductname());
                if(bo){
    
                }
               // JOptionPane.showMessageDialog(new JFrame(),"��ɾ��");
                AdminRefresh refresh=new AdminRefresh();
                setVisible(false);
                refresh.setVisible(true);
                toFront();
                parentFrame.setVisible(false);
               
            } else if (n == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(new JFrame(),"��ȡ��");
            }

        }
    }
}
