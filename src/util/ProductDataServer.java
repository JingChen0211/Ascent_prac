package util;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 数据服务器类
 * <p>
 * 这是一个 Java 代码文件，定义了 ProductDataServer 和 ProductDataClient 两个类。它们被用来实现一个数据服务器和客户端，用于提供产品信息和类别。以下是每个类的主要方法：
 * <p>
 * ProductDataServer 类：
 * <p>
 * ProductDataServer(): 默认构造方法，启动服务器端口为 ProtocolPort.DEFAULT_PORT。
 * ProductDataServer(int thePort): 带一个参数构造方法，启动服务器端口为 thePort。
 * listenForConnections(): 监听客户端发送请求连接。
 * log(Object msg): 日志方法，打印日志的消息。
 * ProductDataClient 类：
 * <p>
 * ProductDataClient(): 默认构造方法，连接服务器默认主机和端口。
 * ProductDataClient(String hostName, int port): 接受主机名和端口号的构造方法。
 * getCategories(): 返回类别集合。
 * getProducts(String category): 返回产品集合。
 * log(Object msg): 日志方法，打印日志的消息。
 *
 * @author cjc
 * @version 1.0
 */
public class ProductDataServer implements ProtocolPort {
    protected ServerSocket myServerSocket;
    protected ProductDataAccessor myProductDataAccessor;
    protected boolean done;

    public ProductDataServer() {
        this(ProtocolPort.DEFAULT_PORT);
    }

    public ProductDataServer(int thePort) {
        try {
            done = false;
            log("启动服务器 " + thePort);
            myServerSocket = new ServerSocket(thePort);
            myProductDataAccessor = new ProductDataAccessor();
            log("\n服务器准备就绪!");
            listenForConnections();
        } catch (Exception exc) {
            log(exc);
            System.exit(1);
        }
    }

    protected void listenForConnections() {
        Socket clientSocket = null;
        Handler aHandler = null;
        try {
            while (!done) {
                log("\n等待请求...");
                clientSocket = myServerSocket.accept();
                String clientHostName = clientSocket.getInetAddress()
                        .getHostName();
                log("收到连接: " + clientHostName);
                aHandler = new Handler(clientSocket, myProductDataAccessor);
                aHandler.start();
            }
        } catch (Exception exc) {
            log("listenForConnections()中发生异常:  " + exc);
        }
    }

    protected void log(Object msg) {
        System.out.println("ProductDataServer类: " + msg);
    }

    // 主方法， 启动服务器
    public static void main(String[] args) {
        ProductDataServer myServer;
        if (args.length == 1) {
            int port = Integer.parseInt(args[0]);
            myServer = new ProductDataServer(port);
        } else {
            myServer = new ProductDataServer();
        }
    }
}
