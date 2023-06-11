package com.topclass.Chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
 
//说明：如果一个类，需要有界面的显示，那么该类就需要继承自JFrame，此时，该类就可以被称为一个“窗体类"
//1.定义JFrame窗体中的组件
//2.在构造方法中初始化组件
//3.使用网络编程完成数据的传输（TCP,UDP协议）
//4.实现“发送”按钮的监听点击事件。需要注意的是，文本域拼接数据时，需要自己换行，不要在发送数据时换行。
public class Server extends JFrame implements ActionListener, KeyListener {
 
    public static void main(String[] args) {
        // 调用构造器
        new Server();
    }
    //属性
 
    //文本域
    private JTextArea jta;
    //滚动条
    private JScrollPane jsp;
    //面板
    private JPanel jp;
    //文本框
    private JTextField jtf;
    //按钮
    private JButton jb;
 
    //流
    BufferedReader br;
    BufferedWriter bw;
 
    //服务端端口号
    public static int serverPort= 8800;
    //静态代码块加载外部配置文件
    //特点1：在类加载时，自动执行
    //特点2：一个类只会加载一次，因此静态代码块只会执行一次
    
 
    //创建空参构造器
    public Server(){
        //初始化组件
 
        //初始化文本域
        jta = new JTextArea();
        //设置文本域不可编辑
        jta.setEditable(false);
        //将文本域添加到滚动条中，实现滚动效果
        jsp = new JScrollPane(jta);
 
        //面板
        jp = new JPanel();
        jtf = new JTextField(15);
        jb = new JButton("发送");
        //将文本框与按钮添加到面板中
        jp.add(jtf);
        jp.add(jb);
 
        //将滚动条与面板全部添加到窗体中
        this.add(jsp, BorderLayout.CENTER);
        this.add(jp, BorderLayout.SOUTH);
 
        //设置 标题，大小，位置，关闭，是否可见
        this.setTitle("客户");
        this.setSize(300,300);
        this.setLocation(600,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//窗体关闭，程序退出
        this.setVisible(true);
 
        /******************TCP 服务端 start*****************/
 
        //给发送按钮绑定一个监听点击事件
        jb.addActionListener(this);
        //给文本框绑定一个键盘点击事件
        jtf.addKeyListener(this);
        //接收客户端数据，并回显到自己的文本域
        try {
            //1.创建一个服务端的套接字socket
            ServerSocket serverSocket = new ServerSocket(serverPort);
            //2.等待客户端的链接
            Socket socket = serverSocket.accept();//客户端对象
            //3.获取socket 通道的输入流（输入流是实现读取数据的，一行一行读取，因此用BufferedReader-->readLine
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 
            //4.获取socket 通道的输出流(输出流实现写出数据，也是写一行，换一行，刷新）BufferedWriter-->new Line()
            //什么时候需要写出数据？当用户点击发送按钮时
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
 
            bw.write("客服：请问您需要什么帮助？");
            jta.append("客服：请问您需要什么帮助？"+System.lineSeparator());             //System.lineSeparator()是行分隔符
            bw.newLine();   //newLine()是回车换行符
            bw.flush();         //flush()是清空缓冲区
            
            
            
            //循环读取数据，并把它拼接到文本域中
            String line = null;
            while ((line=br.readLine())!=null){
                //将读取的数据拼接到文本域中显示
                jta.append(line+System.lineSeparator());
            }
            //5.关闭serverSocket 通道
            serverSocket.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
 
 
 
        /******************TCP 服务端 end*****************/
 
    }
 
    //行为
    /**
     * 重新ActionListener中的1个方法，实现点击 发送键 发送
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        sendDataToSocket();
    }
    /**
     * 重写KeyListener中的3个方法，实现Enter键发送
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        //回车键
        if (e.getKeyCode()== KeyEvent.VK_ENTER){
            //发送数据到socket通道
            sendDataToSocket();
        }
    }
 
    @Override
    public void keyTyped(KeyEvent e) {
 
    }
 
    @Override
    public void keyReleased(KeyEvent e) {
 
    }
 
    //方法功能：发送数据到客户端，并将内容回显到自己的文本域
    public void sendDataToSocket(){
        //1.获取文本框中的内容
        String text = jtf.getText();
        //2.拼接需要发送的数据内容
        text = "客服："+text;
        //3.自己也需要显示
        jta.append(text+System.lineSeparator());
 
        try {
            //4.发送
            bw.write(text);
            bw.newLine();
            bw.flush();
            //5.清空文本框
            jtf.setText("");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

