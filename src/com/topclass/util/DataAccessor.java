package com.topclass.util;

import com.topclass.bean.Product;
import com.topclass.bean.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 * ��������ඨ������ζ�ȡһ�������ļ���
 * ���ṩ�ķ�������������ò�Ʒ�ķ���;���Ĳ�Ʒ��Ϣ
 * <p>
 * ��δ���ʵ����һ�������� DataAccessor������������ζ�ȡ�����ļ��Ի�ȡ��Ʒ����;����Ʒ��Ϣ�ķ�����
 * �������һ���洢��Ʒ��Ϣ�� HashMap/Hashtable��һ���洢�û���Ϣ�� HashMap/Hashtable ��һ���洢������Ӳ�Ʒ�� ArrayList��
 * <p>
 * �����ṩ�˻�ò�Ʒ�����ĳһ�����²�Ʒ��Ϣ�ķ����������²�Ʒ�ķ����Լ����ļ��ж�ȡ�ͱ������ݵĳ��󷽷������⣬���໹����һ����־�������������һЩ��Ϣ��
 *
 * @author cjc
 * @version 1.0
 */

/**
 * ��������ඨ������ζ�ȡһ�������ļ���
 * ���ṩ�ķ�������������ò�Ʒ�ķ���;���Ĳ�Ʒ��Ϣ
 * @author ascent
 * @version 1.0
 */
public abstract class DataAccessor {

    /**
     * ��Ų�Ʒ��Ϣ��HashMap/Hashtable .
     */
    protected HashMap<String, ArrayList<Product>> dataTable;

    /**
     * ����û���Ϣ��HashMap/Hashtable .
     */
    protected HashMap<String,User> userTable;

    /**
     * ������ӵĲ�Ʒ
     */
    protected ArrayList<Product> recentProductList;

    /**
     * Ĭ�Ϲ��췽��
     */
    public DataAccessor() {
        dataTable = new HashMap<String,ArrayList<Product>>();
        userTable = new HashMap<String,User>();
        recentProductList = new ArrayList<Product>();
    }

    /**
     * ��ò�Ʒ�������Ƽ���
     * @return categorySet ��Ʒ�������Ƽ���
     */
    public ArrayList<String> getCategories() {
        Set<String> categorySet = dataTable.keySet();
        log("��÷���...");
        ArrayList<String> categories = new ArrayList<String>(categorySet);
        // ����
        Collections.sort(categories);
        log("��ɻ�÷���!\n");
        return categories;
    }

    /**
     * ���ĳ���Ʒ�ļ���
     * @param category ��������
     * @return productList ��Ʒ����
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Product> getProducts(String category) {
        log("��ò�Ʒ������Ϣ�� ��������: " + category);
        ArrayList<Product> productList = dataTable.get(category);
        log("�����Ʒ������" + productList.size());
        // ����
        Collections.sort(productList);
        log("��ɻ�ò�Ʒ������Ϣ!\n");
        return productList;
    }

    /**
     * ��ȡ�û�
     * @return userTable Key:�û�����Value:�û�����
     */
    public HashMap<String,User> getUsers() {
        return this.userTable;
    }

    /**
     * �����µĲ�Ʒ
     * @param theProduct ����ӵ����ﳵ����Ʒ
     */
    public void addProduct(Product theProduct) {
        String category = theProduct.getCategory();
        log("����µĲ�Ʒ:  " + theProduct);
        ArrayList<Product> productList = dataTable.get(category);
        productList.add(theProduct);
        recentProductList.add(theProduct);
        log("�������µĲ�Ʒ!\n");
    }

    /**
     * ���ļ��ж�ȡ����
     */
    public abstract void load();

    /**
     * ���ļ��б�������
     */
    public abstract void save(User user);

    /**
     * ��־����
     */
    protected void log(Object msg) {
        System.out.println("���ݴ�ȡ�ࡡData Accessor:  " + msg);
    }
}