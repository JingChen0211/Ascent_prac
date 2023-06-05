package com.topclass.bean;

/**
 * ʵ����User�����������û�����Ϣ��
 * <p>
 * User����������ֶΣ�
 * <p>
 * username���û���
 * password������
 * authority���û�Ȩ��
 * ���⣬User�໹�������·�����
 * <p>
 * Ĭ�Ϲ��췽��
 * �������Ĺ��췽��
 * �����в����Ĺ��췽��
 * ��ȡ/����Username��Password��Authority�ֶεķ���
 * toString�������ڷ����û���������
 *
 * @author cjc
 * @version 1.0
 */
public class User implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private int authority;

    public User() {
    }

    @Override
    public String toString() {
        return "�û�����" + username +
                " ���룺" + password
                ;
    }

    /**
     * �вι���
     * @param username
     * @param password
     * @param authority
     */
    public User(String username, String password, int authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}


