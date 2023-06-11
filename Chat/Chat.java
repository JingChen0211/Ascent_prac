package com.topclass.Chat;

public class Chat {

	//创建客户端和服务器对象
public Chat() {
	new Thread(() -> {
        Client client = new Client();
    }).start();

    // 启动服务端
    new Thread(() -> {
        Server server = new Server();
    }).start();
}

	
	public static void main(String[] args) {
        // 启动客户端
	Chat chat=new Chat();
	}
}
