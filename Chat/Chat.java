package com.topclass.Chat;

public class Chat {

	//�����ͻ��˺ͷ���������
public Chat() {
	new Thread(() -> {
        Client client = new Client();
    }).start();

    // ���������
    new Thread(() -> {
        Server server = new Server();
    }).start();
}

	
	public static void main(String[] args) {
        // �����ͻ���
	Chat chat=new Chat();
	}
}
