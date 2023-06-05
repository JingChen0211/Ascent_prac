package com.topclass.util;

import com.topclass.bean.Product;
import com.topclass.bean.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

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

/**
 * 这个抽象类定义了如何读取一个数据文件。
 * 它提供的方法可以用来获得产品的分类和具体的产品信息
 * @author ascent
 * @version 1.0
 */
public abstract class DataAccessor {

    /**
     * 存放产品信息的HashMap/Hashtable .
     */
    protected HashMap<String, ArrayList<Product>> dataTable;

    /**
     * 存放用户信息的HashMap/Hashtable .
     */
    protected HashMap<String,User> userTable;

    /**
     * 最近增加的产品
     */
    protected ArrayList<Product> recentProductList;

    /**
     * 默认构造方法
     */
    public DataAccessor() {
        dataTable = new HashMap<String,ArrayList<Product>>();
        userTable = new HashMap<String,User>();
        recentProductList = new ArrayList<Product>();
    }

    /**
     * 获得产品分类名称集合
     * @return categorySet 产品分类名称集合
     */
    public ArrayList<String> getCategories() {
        Set<String> categorySet = dataTable.keySet();
        log("获得分类...");
        ArrayList<String> categories = new ArrayList<String>(categorySet);
        // 排序
        Collections.sort(categories);
        log("完成获得分类!\n");
        return categories;
    }

    /**
     * 获得某类产品的集合
     * @param category 分类名称
     * @return productList 商品集合
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Product> getProducts(String category) {
        log("获得产品集合信息， 它们属于: " + category);
        ArrayList<Product> productList = dataTable.get(category);
        log("该类产品数量：" + productList.size());
        // 排序
        Collections.sort(productList);
        log("完成获得产品集合信息!\n");
        return productList;
    }

    /**
     * 获取用户
     * @return userTable Key:用户名，Value:用户对象
     */
    public HashMap<String,User> getUsers() {
        return this.userTable;
    }

    /**
     * 增加新的产品
     * @param theProduct 被添加到购物车的商品
     */
    public void addProduct(Product theProduct) {
        String category = theProduct.getCategory();
        log("添加新的产品:  " + theProduct);
        ArrayList<Product> productList = dataTable.get(category);
        productList.add(theProduct);
        recentProductList.add(theProduct);
        log("完成添加新的产品!\n");
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
        System.out.println("数据存取类　Data Accessor:  " + msg);
    }
}