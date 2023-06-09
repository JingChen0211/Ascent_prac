package com.topclass.util;

import com.topclass.bean.Product;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


/**
 * ������������ݷ��������������
 * <p>
 * ����һ��Java�࣬��ΪProductDataClient�������������ݷ������Ի�ȡ���ݡ���ʵ����ProtocolPort�ӿڡ�������������ֶΣ�
 * <p>
 * hostSocket��Socket���ã��������ӵ����ݷ�������
 * outputToServer��ObjectOutputStream���ã�������������������ݡ�
 * inputFromServer��ObjectInputStream���ã����ڴӷ������������ݡ�
 * ����������¹��캯���ͷ�����
 * <p>
 * ProductDataClient()��Ĭ�Ϲ��캯����ʹ��Ĭ�������Ͷ˿��������ݷ�������
 * ProductDataClient(String hostName, int port)�������������Ͷ˿ںŵĹ��캯�����������ӵ�ָ�������ݷ�������
 * getCategories()��������𼯺ϡ�
 * getProducts(String category)�����ظ������Ĳ�Ʒ���ϡ�
 * log(Object msg)����־���������������Ϣ��
 * �������ڴ����ݷ�������ȡ��Ʒ���Ͳ�Ʒ��Ϣ��
 *
 * @author cjc
 * @version 1.0
 */
public class ProductDataClient implements ProtocolPort {

    /**
     * socket����
     */
    protected Socket hostSocket;

    /**
     * �����������
     */
    protected ObjectOutputStream outputToServer;

    /**
     * ������������
     */
    protected ObjectInputStream inputFromServer;

    /**
     * Ĭ�Ϲ��췽��
     */
    public ProductDataClient() throws IOException {
        this(ProtocolPort.DEFAULT_HOST, ProtocolPort.DEFAULT_PORT);
    }

    /**
     * �����������Ͷ˿ںŵĹ��췽��
     */
    public ProductDataClient(String hostName, int port) throws IOException {

        log("�������ݷ�����..." + hostName + ":" + port);

        hostSocket = new Socket(hostName, port);
        outputToServer = new ObjectOutputStream(hostSocket.getOutputStream());
        inputFromServer = new ObjectInputStream(hostSocket.getInputStream());

        log("���ӳɹ�.");
    }

    /**
     * ������𼯺�
     */
    @SuppressWarnings("unchecked")
    public ArrayList<String> getCategories() throws IOException {

        ArrayList<String> categoryList = null;

        try {
            log("��������: OP_GET_PRODUCT_CATEGORIES");
            outputToServer.writeInt(ProtocolPort.OP_GET_PRODUCT_CATEGORIES);
            outputToServer.flush();

            log("��������...");
            categoryList = (ArrayList<String>) inputFromServer.readObject();
            log("�յ� " + categoryList.size() + " ���.");
        } catch (ClassNotFoundException exc) {
            log("=====>>>  �쳣: " + exc);
            throw new IOException("�Ҳ��������");
        }

        return categoryList;
    }

    /**
     * ���ز�Ʒ����
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Product> getProducts(String category) throws IOException {

        ArrayList<Product> productList = null;

        try {
            log("��������: OP_GET_PRODUCTS  ��� = " + category);
            outputToServer.writeInt(ProtocolPort.OP_GET_PRODUCTS);
            outputToServer.writeObject(category);
            outputToServer.flush();

            log("��������...");
            productList = (ArrayList<Product>)inputFromServer.readObject();
            log("�յ� " + productList.size() + " ��Ʒ.");
        } catch (ClassNotFoundException exc) {
            log("=====>>>  �쳣: " + exc);
            throw new IOException("�Ҳ��������");
        }

        return productList;
    }

    /**
     * ��������Ʒ
     * @param a
     * @param b
     * @param c
     * @param d
     * @param kk
     * @param f
     * @param g
     * @return
     */
    public boolean addProduct(String a,String b,String c,String d,String kk,String f,String g) {
		/*HashMap<String,ArrayList<Product>> map = this.getPros();
		if (map.containsKey(name)) {
			return false;
		} else {*/
        try {
            log("��������: OP_ADD_PRODUCTS  ");
            outputToServer.writeInt(ProtocolPort.OP_ADD_PRODUCTS);
            outputToServer.writeObject(new Product(a,b,c,d,kk,f,g));
            outputToServer.flush();
            log("��������...");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteProduct(String productName){
        try {
            log("��������: OP_DELETE_PRODUCT  ");
            outputToServer.writeInt(ProtocolPort.OP_DELETE_PRODUCT);
            outputToServer.writeObject(productName);
            outputToServer.flush();
            log("��������...");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * ��־����.
     */
    protected void log(Object msg) {
        System.out.println("ProductDataClient��: " + msg);
    }
}
