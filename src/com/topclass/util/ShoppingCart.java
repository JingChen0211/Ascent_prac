/**
 * 这个类实现购物车内容页面的显示
 * <p>
 * <p>
 * 
 * <p>
 * getShoppingList()获取所有购买商品信息
 * addProduct()添加商品到购物车
 * clearProduct()清空购物车所购买商品
 *
 * @author dak119
 * @version 2.0
 */
package com.topclass.util;

import java.util.ArrayList;
import com.topclass.bean.Product;

/**
 * 购物车
 * @author ascent
 * @version 1.0
 */
public class ShoppingCart {

	/**
	 * 存放购买商品信息
	 */
	private static ArrayList<Product> shoppingList = new ArrayList<Product>();

	/**
	 * 获取所有购买商品信息
	 * @return shoppingList
	 */
	public ArrayList<Product> getShoppingList() {
		return ShoppingCart.shoppingList;
	}

	/**
	 * 添加商品到购物车
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
	 * 清空购物车所购买商品
	 */
	public void clearProduct() {
		shoppingList.clear();
	}

}

