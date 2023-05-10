package util;

/**
 * socket编程所涉及的标志
 * 这是一个Java语言编写的数据服务器类，用于处理客户端发来的请求。
 * 该类实现了一个自定义接口ProtocolPort，其中定义了一些常量用于标识不同的请求类型。
 * 该类默认监听本地主机的端口号为5150的请求连接，当客户端发起连接请求时，会创建一个新的Handler对象进行处理。
 * 同时，该类还提供了一些日志方法，用于记录服务器的状态和响应信息。
 * 该类可以通过命令行参数指定不同的端口号来启动服务器。
 *
 * @author cjc
 * @version 1.0
 */
public interface ProtocolPort {
    public static final int OP_GET_PRODUCT_CATEGORIES = 100;
    public static final int OP_GET_PRODUCTS = 101;
    public static final int OP_GET_USERS = 102;
    public static final int OP_ADD_USERS = 103;
    public static final int DEFAULT_PORT = 5150;
    public static final String DEFAULT_HOST = "localhost";

}
