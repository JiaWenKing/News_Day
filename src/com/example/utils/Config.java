package com.example.utils;
/**
 * ����
 * */
public class Config {
	/**
	 * url�б�
	 * */
	//http://118.244.212.82:9092/newsClient/
	//ver=0000000&subid=1&dir=1&nid=1&stamp=201609211&cnt=20
	public static String ip="http://118.244.212.82:9092/newsClient/";
	
	public static String news_list="news_list?";
//	��¼         user_login?ver=�汾��&uid=�û���&pwd=����&device=0
	public static String user_login="user_login?";
//	ע��         user_register?ver=�汾��&uid=�û���&email=����&pwd=��½����
	public static String user_register="user_register?";
//	��������   user_forgetpass?ver=�汾��&email=����
	public static String user_forgetpass="user_forgetpass?";
//	�û�����   user_home?ver=�汾��&imei=�ֻ���ʶ��&token =�û�����
	public static String user_home="user_home?";
//	ͷ���ϴ�   user_image?token=�û�����& portrait =ͷ��
	public static String user_image="user_image?";
//	��������   ver=�汾��&nid=���ű��&token=�û�����&imei=�ֻ���ʶ��&ctx=��������
	public static String cmt_commit="cmt_commit?";
//  ��ʾ����   ver=�汾��&nid=����id&type=1&stamp=yyyyMMdd&cid=����id&dir=0&cnt=20
	public static String cmt_list="cmt_list?";
}
