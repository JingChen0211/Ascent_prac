package util;

import bean.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

/**
 * 这个类是socket连接的处理器 例如：
 * <pre>
 * Handler aHandler = new Handler(clientSocket, myProductDataAccessor);
 * aHandler.start();
 * </pre>
 * <p>
 * 这段代码是一个Java类，用于处理Socket连接。它包括以下功能：
 * <p>
 * 从客户端接收命令并响应
 * 获取商品类别、商品信息和用户信息
 * 添加新用户
 * 输出日志
 * <p>
 * 该类继承了Thread类，因此可以在多线程环境下运行。
 * 在构造函数中，传入了客户端Socket对象和处理商品数据的对象，然后初始化了输出和输入流。
 * 在run()方法中，使用while循环来不断等待命令，然后根据不同的命令进行相应的操作。
 * <p>
 * 其中，
 * opGetUsers()方法返回用户信息，
 * opGetProductCategories()方法返回商品类别，
 * opGetProducts()方法返回某个商品类别的所有商品信息，
 * opAddUser()方法处理添加新用户的请求。
 * 最后，使用log()方法输出日志信息。
 *
 * @author cjc
 * @version 1.0
 */
public class Handler extends Thread {

    protected Socket clientSocket;

    private int opCode;

    protected boolean done;
//Handler类的添加人员的方法处理，需要在Handler类中添加一些属性及方法：

    protected ObjectOutputStream outputToClient;
    protected ObjectInputStream inputFromClient;
    protected ProductDataAccessor myProductDataAccessor;

    /**
     * 带两个参数的构造方法
     *
     * @param theClientSocket        客户端Socket对象
     * @param theProductDataAccessor 处理商品数据的对象
     * @throws IOException 构造对象时可能发生IOException异常
     */
    public Handler(Socket theClientSocket, ProductDataAccessor theProductDataAccessor) throws IOException {
        clientSocket = theClientSocket;
        outputToClient = new ObjectOutputStream(clientSocket.getOutputStream());
        inputFromClient = new ObjectInputStream(clientSocket.getInputStream());
        myProductDataAccessor = theProductDataAccessor;
        done = false;
    }

    /**
     * 处理用户注册
     */
    public void opAddUser() {
        try {
            User user = (User) this.inputFromClient.readObject();
            this.myProductDataAccessor.save(user);
        } catch (IOException e) {
            log("发生异常:  " + e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            log("发生异常:  " + e);
            e.printStackTrace();
        }
    }

    public Handler() {
    }

    public void run() {

        try {
            while (!done) {

                log("等待命令...");

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
                    default:
                        System.out.println("错误代码");
                }
            }
        } catch (Exception exc) {
            log(exc);
        }
    }

    private void opGetUsers() {
        try {
            HashMap userTable = myProductDataAccessor.getUsers();
            outputToClient.writeObject(userTable);
            outputToClient.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void opGetProductCategories() {
        System.out.println("根据类别获得产品的信息…");
    }

    protected void opGetProducts() {
        System.out.println("获得所有产品的信息…");
    }


    public void setDone(boolean flag) {
        done = flag;
    }

    protected void log(Object msg) {
        System.out.println("处理器: " + msg);
    }
}
