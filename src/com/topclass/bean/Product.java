package com.topclass.bean;

/**
 * 实体类Product，用来描述商品的信息类
 * bean中这些代码是用于一个药品库存管理系统的Java实现。其中包括两个实体类，一个描述商品信息的类Product和一个描述用户信息的类User。
 * <p>
 * Product类包含以下字段：
 * <p>
 * productname：药品名称
 * cas：化学文摘登记号
 * structure：结构图名称
 * formula：公式
 * price：价格
 * realstock：数量
 * category：类别
 * 此外，Product类还包含以下方法：
 * <p>
 * 默认构造方法
 * 带参数的构造方法
 * 获取/设置Cas、Category、Formula、Price、ProductName、Realstock、Structure字段的方法
 * toString方法用于返回药品名称和化学文摘登记号
 * 实现Comparable接口的compareTo方法，用于按照药品名称排序
 *
 * @author cjc
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Product implements java.lang.Comparable, java.io.Serializable {

    private String productname; // 药品名称

    private String cas; // 化学文摘登记号

    private String structure; // 结构图名称

    private String formula; // 公式

    private String price; // 价格

    private String realstock; // 数量

    private String category; // 类别

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
        return this.getProductname() + "    CAS号:    " + this.getCas();
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

