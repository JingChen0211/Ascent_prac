package util;

import bean.User;
import ui.ProductDataAccessor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 用来对客户端的请求进行处理的，客户端可以有不同的请求类型，这时候需要在这个类中进行判断，判断在这个支持多线程类的run()方法里面写出。
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
        System.out.println("获得用户的信息…");
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
