package com.topclass.bean;

/**
 * ʵ����Product������������Ʒ����Ϣ��
 * bean����Щ����������һ��ҩƷ������ϵͳ��Javaʵ�֡����а�������ʵ���࣬һ��������Ʒ��Ϣ����Product��һ�������û���Ϣ����User��
 * <p>
 * Product����������ֶΣ�
 * <p>
 * productname��ҩƷ����
 * cas����ѧ��ժ�ǼǺ�
 * structure���ṹͼ����
 * formula����ʽ
 * price���۸�
 * realstock������
 * category�����
 * ���⣬Product�໹�������·�����
 * <p>
 * Ĭ�Ϲ��췽��
 * �������Ĺ��췽��
 * ��ȡ/����Cas��Category��Formula��Price��ProductName��Realstock��Structure�ֶεķ���
 * toString�������ڷ���ҩƷ���ƺͻ�ѧ��ժ�ǼǺ�
 * ʵ��Comparable�ӿڵ�compareTo���������ڰ���ҩƷ��������
 *
 * @author cjc
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Product implements java.lang.Comparable, java.io.Serializable {

    private String productname; // ҩƷ����

    private String cas; // ��ѧ��ժ�ǼǺ�

    private String structure; // �ṹͼ����

    private String formula; // ��ʽ

    private String price; // �۸�

    private String realstock; // ����

    private String category; // ���

    public Product() {
    }

    public Product(String productname, String cas, String structure, String formula, String price, String realstock, String category) {
        this.productname = productname;
        this.cas = cas;
        this.structure = structure;
        this.formula = formula;
        this.price = price;
        this.realstock = realstock;
        this.category = category;
    }

    @Override
    public String toString() {
        return this.getProductname() + "    CAS��:    " + this.getCas();
    }



    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getCas() {
        return cas;
    }

    public void setCas(String cas) {
        this.cas = cas;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRealstock() {
        return realstock;
    }

    public void setRealstock(String realstock) {
        this.realstock = realstock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Object o) {
        Product product = (Product) o;
        return this.getProductname().compareTo(product.getProductname());
    }
    /*
        public int compareTo(String anotherString) {
        byte v1[] = value;
        byte v2[] = anotherString.value;
        if (coder() == anotherString.coder()) {
            return isLatin1() ? StringLatin1.compareTo(v1, v2)
                              : StringUTF16.compareTo(v1, v2);
        }
        return isLatin1() ? StringLatin1.compareToUTF16(v1, v2)
                          : StringUTF16.compareToLatin1(v1, v2);
     }
     */
}

