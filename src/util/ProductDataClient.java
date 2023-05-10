package util;

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
}
