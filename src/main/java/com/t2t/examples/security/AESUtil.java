package com.t2t.examples.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密和解密
 * 
 * @author ypf
 */
public class AESUtil {

	private static final String AES = "AES";

	private static final String CRYPT_KEY = "YUUAtestYUUAtest";

	/**
	 * 加密
	 */
	public static byte[] encrypt(byte[] src, String key) throws Exception {
		Cipher cipher = Cipher.getInstance(AES);
		SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), AES);
		cipher.init(Cipher.ENCRYPT_MODE, securekey);// 设置密钥和加密形式
		return cipher.doFinal(src);
	}

	/**
	 * 解密
	 */
	public static byte[] decrypt(byte[] src, String key) throws Exception {
		Cipher cipher = Cipher.getInstance(AES);
		SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), AES);// 设置加密Key
		cipher.init(Cipher.DECRYPT_MODE, securekey);// 设置密钥和解密形式
		return cipher.doFinal(src);
	}

	/**
	 * 二行制转十六进制字符串
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	/**
	 * 解密
	 */
	public final static String decrypt(String data) {
		try {
			return new String(decrypt(hex2byte(data.getBytes()), CRYPT_KEY));
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 加密
	 */
	public final static String encrypt(String data) {
		try {
			return byte2hex(encrypt(data.getBytes(), CRYPT_KEY));
		} catch (Exception e) {
		}
		return null;
	}

	public static void main(String[] args) {
		String ID = "飞/asdf84a1##$%^&*()_RFGHJKL:?545asdf;'\"$%^&*()_+";

		String idEncrypt = encrypt(ID);
		System.out.println(idEncrypt);
		String idDecrypt = decrypt(idEncrypt);
		System.out.println(idDecrypt);
	}

}
