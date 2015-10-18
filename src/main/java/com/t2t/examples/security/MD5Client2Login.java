package com.t2t.examples.security;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 模拟登录
 * 
 * @author ypf
 */
public class MD5Client2Login {
	private static Map<String, String> users = new HashMap<String, String>();

	public static void main(String[] args) {
		String userName = "ypf";
		String password = "111";
		registerUser(userName, password);// 注册用户1

		userName = "jln";
		password = "222";
		registerUser(userName, password);// 注册用户2

		System.out.println(users);

		String loginUserId = "ypf";
		String pwd = "111";
		try {
			if (valid(loginUserId, pwd)) {
				//System.out.println("欢迎登陆！！！");
			} else {
				//System.out.println("口令错误，请重新输入！！！");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 注册用户
	 * 
	 * @param userName
	 * @param password
	 */
	public static void registerUser(String userName, String password) {
		String encryptedPwd = MD5Util.encrypt(password);
		users.put(userName, encryptedPwd);
	}

	/**
	 * 验证
	 */
	public static boolean valid(String userName, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String pwdInDb = (String) users.get(userName);
		if (null != pwdInDb) { // 该用户存在
			return MD5Util.compare(password, pwdInDb);
		} else {
//			System.out.println("不存在该用户！！！");
			return false;
		}
	}
}