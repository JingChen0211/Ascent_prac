package com.topclass.util;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * ���ݷ�������
 * <p>
 * ����һ�� Java �����ļ��������� ProductDataServer �� ProductDataClient �����ࡣ���Ǳ�����ʵ��һ�����ݷ������Ϳͻ��ˣ������ṩ��Ʒ��Ϣ�����������ÿ�������Ҫ������
 * <p>
 * ProductDataServer �ࣺ
 * <p>
 * ProductDataServer(): Ĭ�Ϲ��췽���������������˿�Ϊ ProtocolPort.DEFAULT_PORT��
 * ProductDataServer(int thePort): ��һ���������췽���������������˿�Ϊ thePort��
 * listenForConnections(): �����ͻ��˷����������ӡ�
 * log(Object msg): ��־��������ӡ��־����Ϣ��
 * <p>
 * ProductDataClient �ࣺ
 * <p>
 * ProductDataClient(): Ĭ�Ϲ��췽�������ӷ�����Ĭ�������Ͷ˿ڡ�
 * ProductDataClient(String hostName, int port): �����������Ͷ˿ںŵĹ��췽����
 * getCategories(): ������𼯺ϡ�
 * getProducts(String category): ���ز�Ʒ���ϡ�
 * log(Object msg): ��־��������ӡ��־����Ϣ��
 *
 * @author cjc
 * @version 1.0
 */
public class ProductDataServer implements ProtocolPort {
    protected ServerSocket myServerSocket;
    protected ProductDataAccessor myProductDataAccessor;
    protected boolean done;

    public ProductDataServer() {
        this(ProtocolPort.DEFAULT_PORT);
    }

    public ProductDataServer(int thePort) {
        try {
            done = false;
            log("���������� " + thePort);
            myServerSocket = new ServerSocket(thePort);
            myProductDataAccessor = new ProductDataAccessor();
            log("\n������׼������!");
            listenForConnections();
        } catch (Exception exc) {
            log(exc);
            System.exit(1);
        }
    }

    protected void listenForConnections() {
        Socket clientSocket = null;
        Handler aHandler = null;
        try {
            while (!done) {
                log("\n�ȴ�����...");
                clientSocket = myServerSocket.accept();
                String clientHostName = clientSocket.getInetAddress()
                        .getHostName();
                log("�յ�����: " + clientHostName);
                aHandler = new Handler(clientSocket, myProductDataAccessor);
                aHandler.start();
            }
        } catch (Exception exc) {
            log("listenForConnections()�з����쳣:  " + exc);
        }
    }

    protected void log(Object msg) {
        System.out.println("ProductDataServer��: " + msg);
    }

    // �������� ����������
    public static void main(String[] args) {
        ProductDataServer myServer;
        if (args.length == 1) {
            int port = Integer.parseInt(args[0]);
            myServer = new ProductDataServer(port);
        } else {
            myServer = new ProductDataServer();
        }
    }
}