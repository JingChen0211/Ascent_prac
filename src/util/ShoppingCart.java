/**
 * �����ʵ�ֹ��ﳵ����ҳ�����ʾ
 * <p>
 * <p>
 * 
 * <p>
 * getShoppingList()��ȡ���й�����Ʒ��Ϣ
 * addProduct()�����Ʒ�����ﳵ
 * clearProduct()��չ��ﳵ��������Ʒ
 *
 * @author dak119
 * @version 2.0
 */
package util;

import java.util.ArrayList;
import bean.Product;

/**
 * ���ﳵ
 * @author ascent
 * @version 1.0
 */
public class ShoppingCart {

	/**
	 * ��Ź�����Ʒ��Ϣ
	 */
	private static ArrayList<Product> shoppingList = new ArrayList<Product>();

	/**
	 * ��ȡ���й�����Ʒ��Ϣ
	 * @return shoppingList
	 */
	public ArrayList<Product> getShoppingList() {
		return ShoppingCart.shoppingList;
	}

	/**
	 * �����Ʒ�����ﳵ
	 * @param myProduct
	 */
	public void addProduct(Product myProduct) {
		Product product;
		boolean bo = false;
		for (int i = 0; i < shoppingList.size(); i++) {
			product = shoppingList.get(i);
			if (myProduct.getProductname().trim().equals(product.getProductname().trim())) {
				bo = true;
				break;
			}
		}
		if (!bo) {
			shoppingList.add(myProduct);
		}
	}

	/**
	 * ��չ��ﳵ��������Ʒ
	 */
	public void clearProduct() {
		shoppingList.clear();
	}

}

