package com.t2t.examples.file;

import com.t2t.examples.EnvironmentVar;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.sql.Blob;

/**
 * 
 * @author ypf
 * 关于字符串、字节、流及java.sql.Blob之间转换
 */
@SuppressWarnings("restriction")
public class FileUtil {
	
	/**
	 * 将字符串转换成java.sql.Blob
	 * @param str
	 * @return Blob
	 * @throws UnsupportedEncodingException 
	 */
	public static Blob string2Blob(String str) throws UnsupportedEncodingException{
		return null;
	}
	
	/**
	 * 将字节转换成java.sql.Blob
	 * @param buf
	 * @return Blob
	 */
	public static Blob byte2Blob(byte[] buf){
		Blob blob = null;
		return blob;
	}
	/**
	 * 将流转换成java.sql.Blob
	 * @param inStream
	 * @return
	 * @throws IOException
	 */
	public static Blob byte2Blob(InputStream inStream) throws IOException{
		Blob blob = null;
		return blob;
	}
	
	
	/**
	 * 将java.sql.Blob转换为字符串
	 * @param blob
	 * @return String
	 * @throws Exception
	 */
	public static String blob2String(Blob blob) throws Exception {
		InputStream inStream = blob.getBinaryStream();
		return inputStream2String(inStream);
	}
	/**
	 * 将流转换为字符串
	 * @param inStream 输入流
	 * @return String
	 * @throws Exception
	 */
	public static String inputStream2String(InputStream inStream) throws IOException {
		String lineSeparator = EnvironmentVar.get(com.t2t.examples.EnvironmentVar.LINE_SEPARATOR);

		StringBuffer sb = new StringBuffer();
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
		String str = null;
		while ((str = bufferReader.readLine()) != null) {
			sb.append(str + lineSeparator);
		}
		if (null == sb || sb.length() == 0) {
			return null;
		}

		return sb.toString();
	}
	/**
	 * 将字节转换为字符串
	 * @param buf 字节数据
	 * @return String
	 * @throws IOException
	 */
	public static String byte2String(byte[] buf) throws IOException {
		return new String(buf,"UTF-8");
	}
	/**
     * 根据图片路径，将图片转换成字符串
     * @param imgURL 
     * @return String
     * @throws IOException 
     */
	public static String convertImgToBASE64(String imgURL) throws IOException{
		FileInputStream fis = new FileInputStream(imgURL);
		return convertImgToBASE64(fis);
	}
	
	/**
	 * 将流转换成字符串
	 * @param is
	 * @return String
	 * @throws IOException 
	 */
	public static String convertImgToBASE64(InputStream is) throws IOException{
		byte[] buffer = new byte[is.available()];
		is.read(buffer);
		is.close();
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(buffer);
	}
	
	/**
	 * 将字节转换成流
	 * @param buf 字节
	 * @return InputStream
	 */
    public static final InputStream byteToInputStream(byte[] buf) {   
        return new ByteArrayInputStream(buf);   
    }   
    
    /**
     * 将流转换成字节
     * @param inStream
     * @return
     * @throws IOException
     */
    public static final byte[] inputStreamToByte(InputStream inStream)throws IOException {   
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();   
        int rc;   
        while ((rc = inStream.read()) != -1) {   
            swapStream.write(rc);   
        }   
        byte[] in2b = swapStream.toByteArray();   
        return in2b;   
    } 


}
