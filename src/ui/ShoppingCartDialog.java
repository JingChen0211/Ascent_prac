package ui;

import javax.swing.*;

/**
 * 显示购物车所购买商品信息
 * <p>
 * 这是一个Java Swing图形界面程序，用于显示购物车中的商品信息。
 * 程序包括一个名为ShoppingCartDialog的类，它继承了JDialog类，用于创建一个模态对话框，显示购物车中的商品信息。
 * 程序中还有一个名为ShoppingCart的类，用于管理购物车中的商品信息。
 * <p>
 * 程序首先在构造方法中接收一个父窗体和一个查看购物车按钮，然后调用lookShoppingCar()方法创建界面。
 * 界面包括一个显示购物车里商品信息的面板和一个底部面板，底部面板包含一个提交表单按钮和一个清空按钮。
 * <p>
 * 显示购物车里商品信息的面板使用GridBagLayout布局管理器，将购物车中的商品信息和对应的数量文本框以及输入数量的标签都添加到面板中。
 * 底部面板中的按钮分别添加了OkButtonActionListener和ClearButtonActionListener两个内部类用于监听按钮的点击事件。
 * <p>
 * OkButtonActionListener内部类处理提交表单按钮的点击事件，它检查每个商品的数量文本框是否为空，如果为空则提示用户输入数量。
 * 如果所有数量都已输入，则隐藏对话框并显示另一个名为ShoppingMessageDialog的对话框，用于显示提交成功的消息。
 * <p>
 * ClearButtonActionListener内部类处理清空按钮的点击事件，它调用ShoppingCart类中的clearProduct()方法清空购物车，并禁用查看购物车按钮。
 *
 * @author cjc
 * @version 1.0
 */
public class ShoppingCartDialog extends JDialog {
}
