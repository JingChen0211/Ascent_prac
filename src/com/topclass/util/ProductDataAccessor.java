package com.topclass.util;

import com.topclass.bean.Product;
import com.topclass.bean.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 * ��Ʒ���ݶ�ȡ��ʵ����
 * <p>
 * �ô���ʵ����һ����Ʒ���ݶ�ȡ����ProductDataAccessor��������Դ��ļ��ж�ȡ��Ʒ���ݺ��û����ݣ������䱣�����ڴ��е����ݱ��С�
 * ���У���Ʒ���ݺ��û����ݷֱ�洢�ڲ�ͬ���ļ��У����ݵķָ��Ϊ���ţ�ÿ����¼���Էָ���"----------"������
 * ͨ��load������������Զ�ȡ�ļ��������ݴ洢���ڴ��е�HashMap���ݽṹ�У�������Ʒ���ݰ��������з���洢��
 * ͨ��getProductObject���������������µ���Ʒ���󲢷��ء�
 * ���໹�ṩ�˱����û����ݵķ���save��һ����־����log��
 * ���getUsers�������ڻ�ȡ�û����е����ݡ�
 *
 * @author cjc
 * @version 1.0
 */

// ��Ʒ�ļ���ʽ����
// ��Ʒ����,��ѧ��ժ�ǼǺ�,�ṹͼ,��ʽ,�۸�,����,���

// �û��ļ���ʽ����
// �û��ʺ�,�û�����,�û�Ȩ��

public class ProductDataAccessor extends DataAccessor {
    /**
     * ��Ʒ��Ϣ�����ļ���
     */
    protected static final String PRODUCT_FILE_NAME = "product.db";

    /**
     * �û���Ϣ�����ļ���
     */
    protected static final String USER_FILE_NAME = "user.db";

    /**
     * ���ݼ�¼�ķָ��
     */
    protected static final String RECORD_SEPARATOR = "----------";

    /**
     * Ĭ�Ϲ��췽��
     */
    public ProductDataAccessor() {
        load();
    }

    /**
     * ��ȡ���ݵķ���
     */
    @Override
    public void load() {

        dataTable = new HashMap<String, ArrayList<Product>>();
        userTable = new HashMap<String, User>();

        ArrayList<Product> productArrayList = null;
        StringTokenizer st = null;

        Product productObject = null;
        User userObject = null;
        String line = "";

        String productName, cas, structure, formula, price, realstock, category;
        String userName, password, authority;

        try {
            log("��ȡ�ļ�: " + PRODUCT_FILE_NAME + "...");
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

                productObject = getProductObject(productName, cas, structure, formula, price, realstock, category);

                if (dataTable.containsKey(category)) {
                    productArrayList = dataTable.get(category);
                } else {
                    productArrayList = new ArrayList<Product>();
                    dataTable.put(category, productArrayList);
                }
                productArrayList.add(productObject);
            }

            inputFromFile1.close();
            log("�ļ���ȡ����!");

            line = "";
            log("��ȡ�ļ�: " + USER_FILE_NAME + "...");
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
            log("�ļ���ȡ����!");
            log("׼������!\n");
        } catch (FileNotFoundException exc) {
            log("û���ҵ��ļ�: " + PRODUCT_FILE_NAME + " �� " + USER_FILE_NAME + ".");
            log(exc);
        } catch (IOException exc) {
            log("��ȡ�ļ������쳣: " + PRODUCT_FILE_NAME + " �� " + USER_FILE_NAME + ".");
            log(exc);
        }
    }

    /**
     * ���ش�����Щ��������Ʒ����
     *
     * @param productName ҩƷ����
     * @param cas         ��ѧ��ժ�ǼǺ�
     * @param structure   �ṹͼ����
     * @param formula     ��ʽ
     * @param price       �۸�
     * @param realstock   ����
     * @param category    ���
     * @return new Product(productName, cas, structure, formula, price, realstock, category);
     */
    private Product getProductObject(String productName, String cas,
                                     String structure, String formula, String price, String realstock, String category) {
        return new Product(productName, cas, structure, formula, price, realstock, category);
    }

    /**
     * ��������
     */
    @Override
    public void save(User user) {
        log("��ȡ�ļ�: " + USER_FILE_NAME + "...");
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
        log("��ȡ�ļ�: " + PRODUCT_FILE_NAME + "...");
        try {
            String productinfo = product.getProductname() + "," + product.getCas() + "," + product.getStructure() + "," + product.getFormula() + "," + product.getPrice() + "," + product.getRealstock() + "," + product.getCategory();
            RandomAccessFile fos = new RandomAccessFile(PRODUCT_FILE_NAME, "rws");
            fos.seek(fos.length());
            fos.write((productinfo + "\n").getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ��־����.
     */
    @Override
    protected void log(Object msg) {
        System.out.println("ProductDataAccessor��: " + msg);
    }

    @Override
    public HashMap<String, User> getUsers() {
        this.load();
        return this.userTable;
    }

    public void addProduct(Product theProduct) {
        String category = theProduct.getCategory();
        log("����µĲ�Ʒ:  " + theProduct);
        ArrayList<Product> productList = dataTable.get(category);
        productList.add(theProduct);
        recentProductList.add(theProduct);
        log("�������µĲ�Ʒ!\n");
    }

    @Override
    public void deleteProduct(String productName) throws IOException {
        String[] productinf0 = new String[8];
        String[] productinf1 = new String[8];

        File file = new File(PRODUCT_FILE_NAME);
        FileInputStream intput = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(intput));
        String tempString;//����һ���ַ�����ÿһ�ζ��������ַ�������
        List<String> list = new ArrayList<>();//����һ��list�ַ���������������ÿһ�е��ַ�����Ϣ
        while ((tempString = reader.readLine()) != null) {
            list.add(tempString);
        }

        //Ҫɾ�������� productName
        for (String delProduct : list) {
            productinf1 = delProduct.split(",");
            //�ҵ�����ɾ�����鼮�ڼ����е�λ�ã����ò������ݴӼ�����ɾ����Ȼ����������ļ�
            if (productName.equals(productinf1[0])) {
                list.remove(delProduct);//�ڼ�����ɾ������
                FileWriter fd = new FileWriter(file, false);//append����false��ʾд������ʱ���Ḳ���ļ���֮ǰ���ڵ�����
                fd.write("");//ִ��ɾ��������д������ݸ���֮ǰ������
                fd.close();
                break;
            }
        }
        //���±���һ����ĺ�ļ��ϣ�����������д���ļ���
        for (String user : list) {
            productinf1 = user.split(",");
            FileWriter fw = new FileWriter(file, true);//append����true��ʾд������ʱ�����Ḳ���ļ���֮ǰ���ڵ����ݣ����µ�����д��֮ǰ���ݵĺ���
            fw.write(productinf1[0] + "," + productinf1[1] +
                    "," + productinf1[2] + "," + productinf1[3] +
                    "," + productinf1[4] + "," + productinf1[5] +
                    "," + productinf1[6]);//ִ������д�����ݵĲ��������޸Ĺ��ļ���ͨ��������±�������´�д���ļ���
            fw.write(System.getProperty("line.separator"));//�ڶ�������һ�����з�
            fw.close();
            log("ɾ���ɹ���");
        }
    }
}