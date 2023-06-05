package com.topclass.util;

/**
 * socket������漰�ı�־
 * ����һ��Java���Ա�д�����ݷ������࣬���ڴ���ͻ��˷���������
 * ����ʵ����һ���Զ���ӿ�ProtocolPort�����ж�����һЩ�������ڱ�ʶ��ͬ���������͡�
 * ����Ĭ�ϼ������������Ķ˿ں�Ϊ5150���������ӣ����ͻ��˷�����������ʱ���ᴴ��һ���µ�Handler������д���
 * ͬʱ�����໹�ṩ��һЩ��־���������ڼ�¼��������״̬����Ӧ��Ϣ��
 * �������ͨ�������в���ָ����ͬ�Ķ˿ں���������������
 *
 * @author cjc
 * @version 1.0
 */
public interface ProtocolPort {
    public static final int OP_GET_PRODUCT_CATEGORIES = 100;
    public static final int OP_GET_PRODUCTS = 101;
    public static final int OP_GET_USERS = 102;
    public static final int OP_ADD_USERS = 103;
    public static final int OP_ADD_PRODUCTS = 104;
    public static final int DEFAULT_PORT = 5150;
    public static final String DEFAULT_HOST = "localhost";

}
