package ui;

/**
 * 这个类是客户端启动类
 * 这是一个名为Ascentsys的类，它是客户端启动类。它有一个静态的main方法，当程序执行时，这个方法会被调用。
 * 这个方法实例化了一个LoginFrame对象，并将其设置为可见的。在此之后，程序将运行LoginFrame中定义的逻辑。
 *
 * @author cjc
 * @version 1.0
 */
public class Ascentsys {
    public static void main(String[] args) {
        LoginFrame loginFrame = new LoginFrame();
        //将其设置为可见的
        loginFrame.setVisible(true);
    }
}
