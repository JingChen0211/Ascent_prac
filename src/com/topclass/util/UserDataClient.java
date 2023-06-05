package com.topclass.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import com.topclass.bean.User;
import com.topclass.bean.Product;


/**
 * ������������ݷ��������������
 * <p>
 * ��δ���ʵ����һ�����ӵ����ݷ������Ŀͻ��ˣ�ͨ���ÿͻ��˿��Ի���û���Ϣ���Լ������û�ע�������
 * <p>
 * �ͻ���ͨ��Socket���ӵ����ݷ������������Է�����������ȡ�û���Ϣ������û�ע�ᡣ
 * ���У��ͻ��˶�����һ��log�������ڴ�ӡ��־��Ϣ�������outputToServer����������������������ݣ�������inputFromServer���ڴӷ�������ȡ��Ӧ���ݡ�
 * <p>
 * �ÿͻ���ʵ���������������ֱ���getUsers��addUser��
 * getUsers�������������ȡ�û���Ϣ��Ȼ����������ж�ȡHashMap<String,User>���͵����ݷ��ء�
 * addUser������ͨ��getUsers������ȡ��ǰ���е��û���Ϣ���ٸ��ݴ�����û��������봴��һ���µ��û��������������������û������󲢽����û�����д��������������ݷ�������Ӧ����ע������
 *
 * @author ascent
 * @version 1.0
 */

public class UserDataClient implements ProtocolPort {

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
    public UserDataClient() throws IOException {
        this(ProtocolPort.DEFAULT_HOST, ProtocolPort.DEFAULT_PORT);
    }

    /**
     * �����������Ͷ˿ںŵĹ��췽��
     */
    public UserDataClient(String hostName, int port) throws IOException {

        log("�������ݷ�����..." + hostName + ":" + port);
        try {
            hostSocket = new Socket(hostName, port);
            outputToServer = new ObjectOutputStream(hostSocket.getOutputStream());
            inputFromServer = new ObjectInputStream(hostSocket.getInputStream());
            log("���ӳɹ�.");
        } catch (Exception e) {
            log("����ʧ��.");
        }
    }

    /**
     * �����û�
     *
     * @return userTable
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, User> getUsers() {
        HashMap<String, User> userTable = null;

        try {
            log("��������: OP_GET_USERS  ");

            outputToServer.writeInt(ProtocolPort.OP_GET_USERS);
            outputToServer.flush();

            log("��������...");
            userTable = (HashMap<String, User>) inputFromServer.readObject();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userTable;
    }

    /**
     * �رյ�ǰSocKet
     */
    public void closeSocket() {
        try {
            this.hostSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ��־����.
     *
     * @param msg ��ӡ����־��Ϣ
     */
    protected void log(Object msg) {
        System.out.println("UserDataClient��: " + msg);
    }

    /**
     * ע���û�
     *
     * @param username �û���
     * @param password ����
     * @return boolean true:ע��ɹ���false:ע��ʧ��
     */
    public boolean addUser(String username, String password) {
        HashMap<String, User> map = this.getUsers();
        if (map.containsKey(username)) {
            return false;
        } else {
            try {
                log("��������: OP_ADD_USERS  ");
                outputToServer.writeInt(ProtocolPort.OP_ADD_USERS);
                outputToServer.writeObject(new User(username, password, 0));
                outputToServer.flush();
                log("��������...");
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public boolean addPro(String a,String b,String c,String d,String kk,String f,String g) {
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
}
