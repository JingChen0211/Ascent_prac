package util;

import bean.User;

import java.util.HashMap;

/**
 * 这个抽象类定义了如何读取一个数据文件。
 * 它提供的方法可以用来获得产品的分类和具体的产品信息
 * <p>
 * 这段代码实现了一个抽象类 DataAccessor，它定义了如何读取数据文件以获取产品分类和具体产品信息的方法。
 * 该类包括一个存储产品信息的 HashMap/Hashtable，一个存储用户信息的 HashMap/Hashtable 和一个存储最近增加产品的 ArrayList。
 * <p>
 * 它还提供了获得产品分类和某一分类下产品信息的方法，增加新产品的方法以及从文件中读取和保存数据的抽象方法。此外，该类还包含一个日志方法，用于输出一些消息。
 *
 * @author cjc
 * @version 1.0
 */
public abstract class DataAccessor {
    /**
     * 默认构造方法
     */
    public DataAccessor() {
        userTable = new HashMap<String, User>();
    }

    /**
     * 从文件中读取数据
     */
    public abstract void load();

    /**
     * 向文件中保存数据
     */
    public abstract void save(User user);

    /**
     * 日志方法
     */
    protected void log(Object msg) {
        System.out.println("数据存取类　DataAccessor:  " + msg);
    }

    /**
     * 存放用户信息的HashMap/Hashtable .
     */
    protected HashMap<String, User> userTable;

    /**
     * 获取用户
     *
     * @return userTable Key:用户名，Value:用户对象
     */
    public HashMap<String, User> getUsers() {
        return this.userTable;
    }


}
