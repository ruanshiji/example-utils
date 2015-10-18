package com.t2t.examples.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 文件读写练习
 * 
 * @author ypf
 */
public class FileReaderWrite {

	public static void main(String[] args) throws Exception {
		run("IDP_Oracle");
		run("ORA_Oracle");
		run("数据");

		System.out.println();

		println("IDP_Oracle");
		println("ORA_Oracle");
		println("数据");
	}

	public static void println(String name) throws IOException {
		String destPath = "C:\\cs\\{0}_1.sql".replace("{0}", name);

		File file = new File(destPath);
		InputStream in = new FileInputStream(file);
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(in, "GBK"));
		String str = null;

		while ((str = bufferReader.readLine()) != null) {
			if (str.getBytes().length > 2400) {
				System.out.println(str.getBytes().length);
			}
		}
	}

	public static void run(String name) throws IOException {
		String srcPath = "C:\\cs\\{0}.sql".replace("{0}", name);
		String destPath = "C:\\cs\\{0}_1.sql".replace("{0}", name);

		File file = new File(srcPath);
		InputStream in = new FileInputStream(file);
		System.out.println("文件读取中...");
		String s = inputStream2String(in);
		System.out.println("文件转换中...");
		fileWrite(s, destPath);
		System.out.println("success! 生成的文件见：" + destPath);
	}

	// 文件写入
	public static void fileWrite(String str, String path) {
		FileOutputStream stream;
		OutputStreamWriter writer;
		try {
			stream = new FileOutputStream(path);
			writer = new OutputStreamWriter(stream, "GBK");
			writer.write(str);
			writer.close();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String formartStr(String str, int length) {
		char data[] = str.toCharArray();
		int i = 0;
		StringBuffer step = new StringBuffer("");
		boolean isInsert = false;

		for (Character c : data) {
			if (i >= length) {
				step.append("\r\n");
				i = 0;
			}

			i += String.valueOf(c).getBytes().length;
			step.append(c);
		}

		return step.toString();
	}

	public static String inputStream2String(InputStream inStream) throws IOException {
		int length = 2400;
		StringBuffer sb = new StringBuffer();
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inStream, "GBK"));
		String str = null;

		while ((str = bufferReader.readLine()) != null) {

			// 如果字节大于length，则截取0到length
			if (str.getBytes().length >= length) {
				str = formartStr(str, length);
			}

			sb.append(str + "\r\n");
		}
		if (null == sb || sb.length() == 0) {
			return null;
		}
		return sb.toString();
	}
}
