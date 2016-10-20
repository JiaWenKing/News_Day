package com.example.utils;
/**
 * 配置
 * */
public class Config {
	/**
	 * url列表
	 * */
	//http://118.244.212.82:9092/newsClient/
	//ver=0000000&subid=1&dir=1&nid=1&stamp=201609211&cnt=20
	public static String ip="http://118.244.212.82:9092/newsClient/";
	
	public static String news_list="news_list?";
//	登录         user_login?ver=版本号&uid=用户名&pwd=密码&device=0
	public static String user_login="user_login?";
//	注册         user_register?ver=版本号&uid=用户名&email=邮箱&pwd=登陆密码
	public static String user_register="user_register?";
//	忘记密码   user_forgetpass?ver=版本号&email=邮箱
	public static String user_forgetpass="user_forgetpass?";
//	用户中心   user_home?ver=版本号&imei=手机标识符&token =用户令牌
	public static String user_home="user_home?";
//	头像上传   user_image?token=用户令牌& portrait =头像
	public static String user_image="user_image?";
//	发布评论   ver=版本号&nid=新闻编号&token=用户令牌&imei=手机标识符&ctx=评论内容
	public static String cmt_commit="cmt_commit?";
//  显示评论   ver=版本号&nid=新闻id&type=1&stamp=yyyyMMdd&cid=评论id&dir=0&cnt=20
	public static String cmt_list="cmt_list?";
}
