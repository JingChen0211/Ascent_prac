package com.topclass.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import com.topclass.bean.User;
import com.topclass.bean.Product;


/**
 * 这个类连接数据服务器来获得数据
 * <p>
 * 这段代码实现了一个连接到数据服务器的客户端，通过该客户端可以获得用户信息，以及进行用户注册操作。
 * <p>
 * 客户端通过Socket连接到数据服务器，并可以发送请求来获取用户信息或进行用户注册。
 * 其中，客户端定义了一个log方法用于打印日志信息，输出流outputToServer用于向服务器发送请求数据，输入流inputFromServer用于从服务器获取响应数据。
 * <p>
 * 该客户端实现了两个方法，分别是getUsers和addUser。
 * getUsers方法发送请求获取用户信息，然后从输入流中读取HashMap<String,User>类型的数据返回。
 * addUser方法先通过getUsers方法获取当前所有的用户信息，再根据传入的用户名和密码创建一个新的用户对象，向服务器发送添加用户的请求并将新用户对象写入输出流，最后根据服务器响应返回注册结果。
 *
 * @author ascent
 * @version 1.0
 */

public class UserDataClient implements ProtocolPort {

    /**
     * socket引用
     */
    protected Socket hostSocket;

    /**
     * 输出流的引用
     */
    protected ObjectOutputStream outputToServer;

    /**
     * 输入流的引用
     */
    protected ObjectInputStream inputFromServer;

    /**
     * 默认构造方法
     */
    public UserDataClient() throws IOException {
        this(ProtocolPort.DEFAULT_HOST, ProtocolPort.DEFAULT_PORT);
    }

    /**
     * 接受主机名和端口号的构造方法
     */
    public UserDataClient(String hostName, int port) throws IOException {

        log("连接数据服务器..." + hostName + ":" + port);
        try {
            hostSocket = new Socket(hostName, port);
            outputToServer = new ObjectOutputStream(hostSocket.getOutputStream());
            inputFromServer = new ObjectInputStream(hostSocket.getInputStream());
            log("连接成功.");
        } catch (Exception e) {
            log("连接失败.");
        }
    }

    /**
     * 返回用户
     *
     * @return userTable
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, User> getUsers() {
        HashMap<String, User> userTable = null;

        try {
            log("发送请求: OP_GET_USERS  ");

            outputToServer.writeInt(ProtocolPort.OP_GET_USERS);
            outputToServer.flush();

            log("接收数据...");
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
     * 关闭当前SocKet
     */
    public void closeSocket() {
        try {
            this.hostSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 日志方法.
     *
     * @param msg 打印的日志信息
     */
    protected void log(Object msg) {
        System.out.println("UserDataClient类: " + msg);
    }

    /**
     * 注册用户
     *
     * @param username 用户名
     * @param password 密码
     * @return boolean true:注册成功，false:注册失败
     */
    public boolean addUser(String username, String password) {
        HashMap<String, User> map = this.getUsers();
        if (map.containsKey(username)) {
            return false;
        } else {
            try {
                log("发送请求: OP_ADD_USERS  ");
                outputToServer.writeInt(ProtocolPort.OP_ADD_USERS);
                outputToServer.writeObject(new User(username, password, 0));
                outputToServer.flush();
                log("接收数据...");
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
            log("发送请求: OP_ADD_PRODUCTS  ");
            outputToServer.writeInt(ProtocolPort.OP_ADD_PRODUCTS);
            outputToServer.writeObject(new Product(a,b,c,d,kk,f,g));
            outputToServer.flush();
            log("接收数据...");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
