package com.topclass.util;

import com.topclass.bean.Product;
import com.topclass.bean.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * 产品数据读取的实现类
 * <p>
 * 该代码实现了一个商品数据读取的类ProductDataAccessor，该类可以从文件中读取商品数据和用户数据，并将其保存在内存中的数据表中。
 * 其中，商品数据和用户数据分别存储在不同的文件中，数据的分割符为逗号，每个记录都以分隔符"----------"结束。
 * 通过load方法，该类可以读取文件并将数据存储在内存中的HashMap数据结构中，其中商品数据按照类别进行分类存储。
 * 通过getProductObject方法，可以生成新的商品对象并返回。
 * 该类还提供了保存用户数据的方法save和一个日志方法log。
 * 最后，getUsers方法用于获取用户表中的数据。
 *
 * @author cjc
 * @version 1.0
 */

// 产品文件格式如下
// 产品名称,化学文摘登记号,结构图,公式,价格,数量,类别

// 用户文件格式如下
// 用户帐号,用户密码,用户权限

public class ProductDataAccessor extends DataAccessor {
    /**
     * 商品信息数据文件名
     */
    protected static final String PRODUCT_FILE_NAME = "product.db";

    /**
     * 用户信息数据文件名
     */
    protected static final String USER_FILE_NAME = "user.db";

    /**
     * 数据记录的分割符
     */
    protected static final String RECORD_SEPARATOR = "----------";

    /**
     * 默认构造方法
     */
    public ProductDataAccessor() {
        load();
    }

    /**
     * 读取数据的方法
     */
    @Override
    public void load() {

        dataTable = new HashMap<String,ArrayList<Product>>();
        userTable = new HashMap<String,User>();

        ArrayList<Product> productArrayList = null;
        StringTokenizer st = null;

        Product productObject = null;
        User userObject = null;
        String line = "";

        String productName, cas, structure, formula, price, realstock, category;
        String userName, password, authority;

        try {
            log("读取文件: " + PRODUCT_FILE_NAME + "...");
            BufferedReader inputFromFile1 = new BufferedReader(new FileReader(PRODUCT_FILE_NAME));

            while ((line = inputFromFile1.readLine()) != null) {

                st = new StringTokenizer(line, ",");

                productName = st.nextToken().trim();
                cas = st.nextToken().trim();
                structure = st.nextToken().trim();
                formula = st.nextToken().trim();
                price = st.nextToken().trim();
                realstock = st.nextToken().trim();
                category = st.nextToken().trim();

                productObject = getProductObject(productName, cas, structure,formula, price, realstock, category);

                if (dataTable.containsKey(category)) {
                    productArrayList = dataTable.get(category);
                } else {
                    productArrayList = new ArrayList<Product>();
                    dataTable.put(category, productArrayList);
                }
                productArrayList.add(productObject);
            }

            inputFromFile1.close();
            log("文件读取结束!");

            line = "";
            log("读取文件: " + USER_FILE_NAME + "...");
            BufferedReader inputFromFile2 = new BufferedReader(new FileReader(USER_FILE_NAME));
            while ((line = inputFromFile2.readLine()) != null) {

                st = new StringTokenizer(line, ",");

                userName = st.nextToken().trim();
                password = st.nextToken().trim();
                authority = st.nextToken().trim();
                userObject = new User(userName, password, Integer.parseInt(authority));

                if (!userTable.containsKey(userName)) {
                    userTable.put(userName, userObject);
                }
            }

            inputFromFile2.close();
            log("文件读取结束!");
            log("准备就绪!\n");
        } catch (FileNotFoundException exc) {
            log("没有找到文件: " + PRODUCT_FILE_NAME + " 或 "+USER_FILE_NAME+".");
            log(exc);
        } catch (IOException exc) {
            log("读取文件发生异常: " + PRODUCT_FILE_NAME+ " 或 "+USER_FILE_NAME+".");
            log(exc);
        }
    }

    /**
     * 返回带有这些参数的商品对象
     * @param productName 药品名称
     * @param cas 化学文摘登记号
     * @param structure 结构图名称
     * @param formula 公式
     * @param price 价格
     * @param realstock 数量
     * @param category 类别
     * @return new Product(productName, cas, structure, formula, price, realstock, category);
     */
    private Product getProductObject(String productName, String cas,
                                     String structure, String formula, String price, String realstock, String category) {
        return new Product(productName, cas, structure, formula, price, realstock, category);
    }

    /**
     * 保存数据
     */
    @Override
    public void save(User user) {
        log("读取文件: " + USER_FILE_NAME + "...");
        try {
            String userinfo = user.getUsername() + "," + user.getPassword() + "," + user.getAuthority();
            RandomAccessFile fos = new RandomAccessFile(USER_FILE_NAME, "rws");
            fos.seek(fos.length());
            fos.write(("\n" + userinfo).getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(Product product) {
        log("读取文件: " + PRODUCT_FILE_NAME + "...");
        try {
            String productinfo = product.getProductname() + "," + product.getCas() + "," + product.getStructure() +"," + product.getFormula()+"," + product.getPrice()+"," + product.getRealstock()+"," + product.getCategory();
            RandomAccessFile fos = new RandomAccessFile(PRODUCT_FILE_NAME, "rws");
            fos.seek(fos.length());
            fos.write((productinfo+ "\n" ).getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 日志方法.
     */
    @Override
    protected void log(Object msg) {
        System.out.println("ProductDataAccessor类: " + msg);
    }

    @Override
    public HashMap<String,User> getUsers() {
        this.load();
        return this.userTable;
    }
}