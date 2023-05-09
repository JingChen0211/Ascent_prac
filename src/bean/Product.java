package bean;

/**
 * 实体类Product，用来描述商品的信息类
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
        return "Product{" +
                "productname='" + productname + '\'' +
                ", cas='" + cas + '\'' +
                ", structure='" + structure + '\'' +
                ", formula='" + formula + '\'' +
                ", price='" + price + '\'' +
                ", realstock='" + realstock + '\'' +
                ", category='" + category + '\'' +
                '}';
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

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}

	