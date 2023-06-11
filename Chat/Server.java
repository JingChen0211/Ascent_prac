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
 
//˵�������һ���࣬��Ҫ�н������ʾ����ô�������Ҫ�̳���JFrame����ʱ������Ϳ��Ա���Ϊһ����������"
//1.����JFrame�����е����
//2.�ڹ��췽���г�ʼ�����
//3.ʹ��������������ݵĴ��䣨TCP,UDPЭ�飩
//4.ʵ�֡����͡���ť�ļ�������¼�����Ҫע����ǣ��ı���ƴ������ʱ����Ҫ�Լ����У���Ҫ�ڷ�������ʱ���С�
public class Server extends JFrame implements ActionListener, KeyListener {
 
    public static void main(String[] args) {
        // ���ù�����
        new Server();
    }
    //����
 
    //�ı���
    private JTextArea jta;
    //������
    private JScrollPane jsp;
    //���
    private JPanel jp;
    //�ı���
    private JTextField jtf;
    //��ť
    private JButton jb;
 
    //��
    BufferedReader br;
    BufferedWriter bw;
 
    //����˶˿ں�
    public static int serverPort= 8800;
    //��̬���������ⲿ�����ļ�
    //�ص�1���������ʱ���Զ�ִ��
    //�ص�2��һ����ֻ�����һ�Σ���˾�̬�����ֻ��ִ��һ��
    
 
    //�����ղι�����
    public Server(){
        //��ʼ�����
 
        //��ʼ���ı���
        jta = new JTextArea();
        //�����ı��򲻿ɱ༭
        jta.setEditable(false);
        //���ı�����ӵ��������У�ʵ�ֹ���Ч��
        jsp = new JScrollPane(jta);
 
        //���
        jp = new JPanel();
        jtf = new JTextField(15);
        jb = new JButton("����");
        //���ı����밴ť��ӵ������
        jp.add(jtf);
        jp.add(jb);
 
        //�������������ȫ����ӵ�������
        this.add(jsp, BorderLayout.CENTER);
        this.add(jp, BorderLayout.SOUTH);
 
        //���� ���⣬��С��λ�ã��رգ��Ƿ�ɼ�
        this.setTitle("�ͻ�");
        this.setSize(300,300);
        this.setLocation(600,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//����رգ������˳�
        this.setVisible(true);
 
        /******************TCP ����� start*****************/
 
        //�����Ͱ�ť��һ����������¼�
        jb.addActionListener(this);
        //���ı����һ�����̵���¼�
        jtf.addKeyListener(this);
        //���տͻ������ݣ������Ե��Լ����ı���
        try {
            //1.����һ������˵��׽���socket
            ServerSocket serverSocket = new ServerSocket(serverPort);
            //2.�ȴ��ͻ��˵�����
            Socket socket = serverSocket.accept();//�ͻ��˶���
            //3.��ȡsocket ͨ��������������������ʵ�ֶ�ȡ���ݵģ�һ��һ�ж�ȡ�������BufferedReader-->readLine
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 
            //4.��ȡsocket ͨ���������(�����ʵ��д�����ݣ�Ҳ��дһ�У���һ�У�ˢ�£�BufferedWriter-->new Line()
            //ʲôʱ����Ҫд�����ݣ����û�������Ͱ�ťʱ
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
 
            bw.write("�ͷ�����������Ҫʲô������");
            jta.append("�ͷ�����������Ҫʲô������"+System.lineSeparator());             //System.lineSeparator()���зָ���
            bw.newLine();   //newLine()�ǻس����з�
            bw.flush();         //flush()����ջ�����
            
            
            
            //ѭ����ȡ���ݣ�������ƴ�ӵ��ı�����
            String line = null;
            while ((line=br.readLine())!=null){
                //����ȡ������ƴ�ӵ��ı�������ʾ
                jta.append(line+System.lineSeparator());
            }
            //5.�ر�serverSocket ͨ��
            serverSocket.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
 
 
 
        /******************TCP ����� end*****************/
 
    }
 
    //��Ϊ
    /**
     * ����ActionListener�е�1��������ʵ�ֵ�� ���ͼ� ����
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        sendDataToSocket();
    }
    /**
     * ��дKeyListener�е�3��������ʵ��Enter������
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        //�س���
        if (e.getKeyCode()== KeyEvent.VK_ENTER){
            //�������ݵ�socketͨ��
            sendDataToSocket();
        }
    }
 
    @Override
    public void keyTyped(KeyEvent e) {
 
    }
 
    @Override
    public void keyReleased(KeyEvent e) {
 
    }
 
    //�������ܣ��������ݵ��ͻ��ˣ��������ݻ��Ե��Լ����ı���
    public void sendDataToSocket(){
        //1.��ȡ�ı����е�����
        String text = jtf.getText();
        //2.ƴ����Ҫ���͵���������
        text = "�ͷ���"+text;
        //3.�Լ�Ҳ��Ҫ��ʾ
        jta.append(text+System.lineSeparator());
 
        try {
            //4.����
            bw.write(text);
            bw.newLine();
            bw.flush();
            //5.����ı���
            jtf.setText("");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

