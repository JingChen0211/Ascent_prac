package util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


/**
 * 这个类连接数据服务器来获得数据
 * <p>
 * 这是一个Java类，名为ProductDataClient，用于连接数据服务器以获取数据。它实现了ProtocolPort接口。该类包含以下字段：
 * <p>
 * hostSocket：Socket引用，用于连接到数据服务器。
 * outputToServer：ObjectOutputStream引用，用于向服务器发送数据。
 * inputFromServer：ObjectInputStream引用，用于从服务器接收数据。
 * 该类具有以下构造函数和方法：
 * <p>
 * ProductDataClient()：默认构造函数，使用默认主机和端口连接数据服务器。
 * ProductDataClient(String hostName, int port)：接受主机名和端口号的构造函数，用于连接到指定的数据服务器。
 * getCategories()：返回类别集合。
 * getProducts(String category)：返回给定类别的产品集合。
 * log(Object msg)：日志方法，用于输出信息。
 * 该类用于从数据服务器获取产品类别和产品信息。
 *
 * @author cjc
 * @version 1.0
 */
public class ProductDataClient implements ProtocolPort {
    //socket引用
    protected Socket hostSocket;
    //输出流引用
    protected ObjectOutputStream outputToServer;
    //输入流引用
    protected ObjectInputStream inputFromServer;

    //日志方法
    protected void log(Object msg){
        System.out.println("ProductDataClient类：" + msg);
    }

    public ProductDataClient() throws IOException {
        this(ProtocolPort.DEFAULT_HOST, ProtocolPort.DEFAULT_PORT);
    }
    //接受主机名和端口号的构造方法
    public ProductDataClient(String hostName, int port) throws IOException {
        log("连接数据服务器..."+hostName+":"+port);

        hostSocket = new Socket(hostName,port);
        outputToServer = new ObjectOutputStream(hostSocket.getOutputStream());
        inputFromServer = new ObjectInputStream(hostSocket.getInputStream());

        log("连接成功");
    }
    //返回类别集合
    public ArrayList<String> getCategories() throws IOException {
        ArrayList<String> categoryList = null;

        log("发送请求：OP_GET_PRODUCT_CATEGORIES");
        outputToServer.writeInt(ProtocolPort.OP_GET_PRODUCT_CATEGORIES);
        outputToServer.flush();

        log("接收数据...");
        try {
            categoryList = (ArrayList<String>) inputFromServer.readObject();
        } catch (ClassNotFoundException e) {
            log("=========>>   异常：" + e);
            throw new IOException("找不到相关类");
        }
        return categoryList;
    }
}
