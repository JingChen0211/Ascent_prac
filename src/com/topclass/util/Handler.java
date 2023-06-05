package com.topclass.util;

import com.topclass.bean.Product;
import com.topclass.bean.User;
import com.topclass.ui.MainFrame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * �������socket���ӵĴ����� ���磺
 * <pre>
 * Handler aHandler = new Handler(clientSocket, myProductDataAccessor);
 * aHandler.start();
 * </pre>
 * <p>
 * ��δ�����һ��Java�࣬���ڴ���Socket���ӡ����������¹��ܣ�
 * <p>
 * �ӿͻ��˽��������Ӧ
 * ��ȡ��Ʒ�����Ʒ��Ϣ���û���Ϣ
 * ������û�
 * �����־
 * <p>
 * ����̳���Thread�࣬��˿����ڶ��̻߳��������С�
 * �ڹ��캯���У������˿ͻ���Socket����ʹ�����Ʒ���ݵĶ���Ȼ���ʼ�����������������
 * ��run()�����У�ʹ��whileѭ�������ϵȴ����Ȼ����ݲ�ͬ�����������Ӧ�Ĳ�����
 * <p>
 * ���У�
 * opGetUsers()���������û���Ϣ��
 * opGetProductCategories()����������Ʒ���
 * opGetProducts()��������ĳ����Ʒ����������Ʒ��Ϣ��
 * opAddUser()��������������û�������
 * ���ʹ��log()���������־��Ϣ��
 *
 * @author cjc
 * @version 1.0
 */
public class Handler extends Thread implements ProtocolPort {

    protected Socket clientSocket;

    protected ObjectOutputStream outputToClient;

    protected ObjectInputStream inputFromClient;

    protected ProductDataAccessor myProductDataAccessor;

    protected boolean done;

    /**
     * �����������Ĺ��췽��
     *
     * @param theClientSocket        �ͻ���Socket����
     * @param theProductDataAccessor ������Ʒ���ݵĶ���
     * @throws IOException �������ʱ���ܷ���IOException�쳣
     */
    public Handler(Socket theClientSocket, ProductDataAccessor theProductDataAccessor) throws IOException {
        clientSocket = theClientSocket;
        outputToClient = new ObjectOutputStream(clientSocket.getOutputStream());
        inputFromClient = new ObjectInputStream(clientSocket.getInputStream());
        myProductDataAccessor = theProductDataAccessor;
        done = false;
    }

    /**
     * �����û�ע��
     */

    public void run() {

        try {
            while (!done) {

                log("�ȴ�����...");

                int opCode = inputFromClient.readInt();
                log("opCode = " + opCode);

                switch (opCode) {
                    case ProtocolPort.OP_GET_PRODUCT_CATEGORIES:
                        opGetProductCategories();
                        break;
                    case ProtocolPort.OP_GET_PRODUCTS:
                        opGetProducts();
                        break;
                    case ProtocolPort.OP_GET_USERS:
                        opGetUsers();
                        break;
                    case ProtocolPort.OP_ADD_USERS:
                        opAddUser();
                        break;
                    case ProtocolPort.OP_ADD_PRODUCTS:
                        opAddProducts();
                        break;
                    default:
                        System.out.println("�������");
                }
            }
        } catch (Exception exc) {
            log(exc);
        }
    }

    /**
     * �����û�ע��
     */
    public void opAddUser() {
        try {
            User user = (User) this.inputFromClient.readObject();
            this.myProductDataAccessor.save(user);
        } catch (IOException e) {
            log("�����쳣:  " + e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            log("�����쳣:  " + e);
            e.printStackTrace();
        }
    }

    //���products
    private void opAddProducts() {
        try {
            Product product = (Product) this.inputFromClient.readObject();
            this.myProductDataAccessor.save(product);
        } catch (IOException e) {
            log("�����쳣:  " + e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            log("�����쳣:  " + e);
            e.printStackTrace();
        }
    }

    private void opGetUsers() {
        try {
            HashMap<String,User> userTable = myProductDataAccessor.getUsers();
            outputToClient.writeObject(userTable);
            outputToClient.flush();
        } catch (IOException exe) {
            log("�����쳣��" + exe);
        }
    }
    protected void opGetProductCategories() {
        try {
            ArrayList<String> categoryList = myProductDataAccessor.getCategories();
            outputToClient.writeObject(categoryList);
            outputToClient.flush();
            log("���� " + categoryList.size() + " �����Ϣ���ͻ���");
        } catch (IOException exc) {
            log("�����쳣:  " + exc);
        }
    }
    protected void opGetProducts() {
        try {
            log("��ȡ������Ϣ");
            String category = (String) inputFromClient.readObject();
            log("����� " + category);

            ArrayList<Product> recordingList = myProductDataAccessor.getProducts(category);

            outputToClient.writeObject(recordingList);
            outputToClient.flush();
            log("���� " + recordingList.size() + "����Ʒ��Ϣ���ͻ���.");
        } catch (IOException exc) {
            log("�����쳣:  " + exc);
            exc.printStackTrace();
        } catch (ClassNotFoundException exc) {
            log("�����쳣:  " + exc);
            exc.printStackTrace();
        }
    }

    public void setDone(boolean flag) {
        done = flag;
    }

    protected void log(Object msg) {
        System.out.println("������: " + msg);
    }
}
