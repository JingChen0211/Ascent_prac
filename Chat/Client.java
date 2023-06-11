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
 
//˵�������һ���࣬��Ҫ�н������ʾ����ô�������Ҫ�̳���JFrame����ʱ������Ϳ��Ա���Ϊһ����������
//1.����JFrame�����е����
//2.�ڹ��췽���г�ʼ�����
public class Client extends JFrame implements ActionListener, KeyListener {
 
    public static void main(String[] args) {
        // ���ù�����
        new Client();
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
 
    //ͨ�ŵ�IP��Port
    public static String clientIP ="127.0.0.1";
    public static int clientPort = 8800;
    //��̬���������ⲿ�����ļ�
    //�ص�1���������ʱ���Զ�ִ��
    //�ص�2��һ����ֻ�����һ�Σ���˾�̬�����ֻ��ִ��һ��
    
 
    //����������
    public  Client(){
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
 
        //���� ���� ��С��λ�ã��رգ��Ƿ�ɼ�
        this.setTitle(" �ͷ�");
        this.setSize(300,300);
        this.setLocation(300,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//����رգ������˳�
        this.setVisible(true);
 
        /******************TCP �ͻ��� start*****************/
        //�����Ͱ�ť��һ����������¼�
        jb.addActionListener(this);
        //�����̰󶨵����¼�
        jtf.addKeyListener(this);
        //���ܷ�������ݣ������Ե��Լ����ı���
        try {
            //1.����һ���ͻ��˵��׽���socket
            Socket socket = new Socket(clientIP, clientPort);
            //2.��ȡsocket ͨ����������
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //3.��ȡsocket ͨ���������
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
 
            
            
            //ѭ����ȡ���ݣ�������ƴ�ӵ��ı�����  ���Կͻ�������
            String line =null;
            while ((line=br.readLine())!=null){
                jta.append(line+System.lineSeparator());
            }
 
            //4.�ر�socket ͨ��
            socket.close();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
 
        /******************TCP �ͻ��� end*****************/
 
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
 
    //�������ܣ��������ݵ�����ˣ��������ݻ��Ե��Լ����ı���
    private void sendDataToSocket(){
        //1.��ȡ�ı����е�����
        String text = jtf.getText();
        //2.ƴ����Ҫ���͵���������
        text = "�ͻ���"+text;
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

