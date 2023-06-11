package com.topclass.Chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;
import java.util.Properties;
 
//说明：如果一个类，需要有界面的显示，那么该类就需要继承自JFrame，此时，该类就可以被称为一个“窗体类
//1.定义JFrame窗体中的组件
//2.在构造方法中初始化组件
public class Client extends JFrame implements ActionListener, KeyListener {
 
    public static void main(String[] args) {
        // 调用构造器
        new Client();
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
 
    //通信的IP和Port
    public static String clientIP ="127.0.0.1";
    public static int clientPort = 8800;
    //静态代码块加载外部配置文件
    //特点1：在类加载时，自动执行
    //特点2：一个类只会加载一次，因此静态代码块只会执行一次
    
 
    //创建构造器
    public  Client(){
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
 
        //设置 标题 大小，位置，关闭，是否可见
        this.setTitle(" 客服");
        this.setSize(300,300);
        this.setLocation(300,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//窗体关闭，程序退出
        this.setVisible(true);
 
        /******************TCP 客户端 start*****************/
        //给发送按钮绑定一个监听点击事件
        jb.addActionListener(this);
        //给键盘绑定单击事件
        jtf.addKeyListener(this);
        //接受服务端数据，并回显到自己的文本域
        try {
            //1.创建一个客户端的套接字socket
            Socket socket = new Socket(clientIP, clientPort);
            //2.获取socket 通道的输入流
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //3.获取socket 通道的输出流
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
 
            
            
            //循环读取数据，并把它拼接到文本域中  来自客户的数据
            String line =null;
            while ((line=br.readLine())!=null){
                jta.append(line+System.lineSeparator());
            }
 
            //4.关闭socket 通道
            socket.close();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
 
        /******************TCP 客户端 end*****************/
 
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
        if (e.getKeyCode()==KeyEvent.VK_ENTER){
            sendDataToSocket();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
 
    }
 
    @Override
    public void keyReleased(KeyEvent e) {
 
    }
 
    //方法功能：发送数据到服务端，并将内容回显到自己的文本域
    private void sendDataToSocket(){
        //1.获取文本框中的内容
        String text = jtf.getText();
        //2.拼接需要发送的数据内容
        text = "客户："+text;
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

