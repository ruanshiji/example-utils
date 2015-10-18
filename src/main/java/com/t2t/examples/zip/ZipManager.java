package com.t2t.examples.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * @author ypf
 * 
 *         备注:解决中文乱码
 *         将java.util.zip.ZipEntry用org.apache.tools.zip.ZipEntry代替即可,org
 *         .apache.tools.zip.ZipEntry在ant包下 JDK7已经解决文件名乱码问题，不需要依赖ant包。
 */
public class ZipManager {

	private Logger log = Logger.getLogger(ZipManager.class);

	/**
	 * 取得指定目录下的所有文件列表，包括子目录.
	 * 
	 * @param baseDir
	 *            File 指定的目录
	 * @return 包含java.io.File的List
	 */
	private List<File> getSubFiles(File baseDir) {
		List<File> ret = new ArrayList<File>();
		File[] tmp = baseDir.listFiles();
		for (int i = 0; i < tmp.length; i++) {
			if (tmp[i].isFile()) {
				ret.add(tmp[i]);
			}
			if (tmp[i].isDirectory()) {
				ret.addAll(getSubFiles(tmp[i]));
			}
		}
		return ret;
	}

	/**
	 * 压缩文件. 如D:\\temp目录下的所有文件连同子目录压缩到D:\\temp.zip.
	 * 
	 * @param sourceFile
	 *            要压缩的目录或文件
	 * @param targetFile
	 *            压缩后的文件(可以是不存在的)
	 */
	public void zip(File sourceFile, File targetFile) throws Exception {
		if (sourceFile.exists()) {
			// 压缩文件名
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(targetFile));
			// zos.setEncoding("GBK");//指定编码为GBK，否则部署到linux下会出现乱码，很重要，高版本正常

			// 如果要压缩的文件为目录
			if (sourceFile.isDirectory()) {
				List<File> fileList = getSubFiles(sourceFile);
				byte[] buf = new byte[1024];
				int readLen = -1;
				for (int i = 0; i < fileList.size(); i++) {
					File zipFile = (File) fileList.get(i);
					ZipEntry zip = new ZipEntry(getAbsFileName(sourceFile.getAbsolutePath(), zipFile));
					readLen = zipping(zipFile, zos, zip, buf, readLen);
				}
				zos.close();
			}

			// 如果要压缩的文件为文件
			if (sourceFile.isFile()) {
				byte[] buf = new byte[1024];
				// 创建一个ZipEntry，并设置Name和其它的一些属性
				ZipEntry zip = new ZipEntry(sourceFile.getName());
				zipping(sourceFile, zos, zip, buf, -1);
				zos.close();
			}
			log.info("压缩成功!");
		} else {
			throw new Exception("文件不存在({0})".replace("{0}", sourceFile.getAbsolutePath()));
		}
	}

	private int zipping(File zipFile, ZipOutputStream zos, ZipEntry zip, byte[] buf, int readLen) throws IOException {
		log.info("压缩文件: " + zipFile.getAbsolutePath());

		zip.setSize(zipFile.length());// 设置长度
		zip.setTime(zipFile.lastModified());// 设置压缩时间

		zos.putNextEntry(zip);// 将ZipEntry加到zos中，再写入实际的文件内容
		InputStream is = new BufferedInputStream(new FileInputStream(zipFile));
		while ((readLen = is.read(buf, 0, 1024)) != -1) {
			zos.write(buf, 0, readLen);
		}
		is.close();
		return readLen;
	}

	/**
	 * zip压缩功能测试. 将指定文件压缩后存到一压缩文件中
	 * 
	 * @param sourceFile
	 *            所要压缩的文件名
	 * @param zipFile
	 *            压缩后的文件名
	 * @return 压缩后文件的大小
	 * @throws Exception
	 */
	public long createFileToZip(File sourceFile, File zipFile) throws IOException {

		byte[] buf = new byte[1024];

		// 压缩文件名
		File objFile = zipFile;

		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(objFile));

		ZipEntry ze = null;
		// 创建一个ZipEntry，并设置Name和其它的一些属性
		ze = new ZipEntry(sourceFile.getName());
		ze.setSize(sourceFile.length());
		ze.setTime(sourceFile.lastModified());

		// 将ZipEntry加到zos中，再写入实际的文件内容
		zos.putNextEntry(ze);

		InputStream is = new BufferedInputStream(new FileInputStream(sourceFile));

		int readLen = -1;
		while ((readLen = is.read(buf, 0, 1024)) != -1) {
			zos.write(buf, 0, readLen);
		}
		is.close();
		zos.close();

		return objFile.length();
	}

	public void unZip(File sourceZip, File outFileDir, boolean isToRoot) throws IOException {
		ZipFile zfile = new ZipFile(sourceZip);
		Enumeration<?> zList = zfile.getEntries();
		ZipEntry zip = null;
		byte[] buf = new byte[1024];
		while (zList.hasMoreElements()) {
			// 从ZipFile中得到一个ZipEntry
			zip = (ZipEntry) zList.nextElement();
			if (zip.isDirectory()) {
				continue;
			}

			String name = zip.getName();// 默认，安装压缩的目录进行解压
			// 是否解压到根目录
			if (isToRoot) {
				if (zip.getName().lastIndexOf("/") > 0) {
					name = zip.getName().substring(zip.getName().lastIndexOf("/") + 1, zip.getName().length());
				} else {
					name = zip.getName();
				}
			}
			// 以ZipEntry为参数得到一个InputStream，并写到OutputStream中
			OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName(outFileDir.getAbsolutePath(), name)));
			InputStream is = new BufferedInputStream(zfile.getInputStream(zip));
			int readLen = 0;
			while ((readLen = is.read(buf, 0, 1024)) != -1) {
				os.write(buf, 0, readLen);
			}
			is.close();
			os.close();
			log.info("解压缩: " + zip.getName());
		}
		zfile.close();
	}

	/**
	 * 测试解压缩功能.
	 * 
	 * <pre>
	 * 将d:\\download\\test.zip，解压到d:\\temp\\zipout目录下.
	 * 默认，安装压缩的目录进行解压
	 * </pre>
	 * 
	 * @throws Exception
	 */
	public void unZip(File sourceZip, File outFileDir) throws IOException {
		unZip(sourceZip, outFileDir, false);
	}

	/**
	 * 给定根目录，返回一个相对路径所对应的实际文件名.
	 * 
	 * @param baseDir
	 *            指定根目录
	 * @param absFileName
	 *            相对路径名，来自于ZipEntry中的name
	 * @return java.io.File 实际的文件
	 */
	private File getRealFileName(String baseDir, String absFileName) {
		String[] dirs = absFileName.split("/");
		File ret = new File(baseDir);
		if (dirs.length > 1) {
			for (int i = 0; i < dirs.length - 1; i++) {
				ret = new File(ret, dirs[i]);
			}
		}
		if (!ret.exists()) {
			ret.mkdirs();
		}
		ret = new File(ret, dirs[dirs.length - 1]);
		return ret;
	}

	/**
	 * 给定根目录，返回另一个文件名的相对路径，用于zip文件中的路径.
	 * 
	 * @param baseDir
	 *            java.lang.String 根目录
	 * @param realFileName
	 *            java.io.File 实际的文件名
	 * @return 相对文件名
	 */
	private String getAbsFileName(String baseDir, File realFileName) {
		File real = realFileName;
		File base = new File(baseDir);
		String ret = real.getName();
		while (true) {
			real = real.getParentFile();
			if (real == null)
				break;
			if (real.equals(base))
				break;
			else {
				ret = real.getName() + "/" + ret;
			}
		}
		return ret;
	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件(或删除单个文件)
	 * 
	 * @param dir
	 *            将要删除的文件目录
	 */
	public boolean deleteFileCascade(File file) {
		if (file.isDirectory()) {
			String[] children = file.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteFileCascade(new File(file, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return file.delete();
	}

	public static void main(String args[]) {
		ZipManager manager = new ZipManager();
		try {
			// manager.unZip(new File("D:\\zip\\test.zip"), new
			// File("D:\\zip\\test1"));
			// manager.zip(new File("D:\\zip\\test\\新建 文本文档.txt"), new
			// File("D:\\zip\\test\\新建 文本文档.txt.zip"));
			manager.unZip(new File("F:\\010.zip"), new File("F:\\010"));
			// manager.zip(new File("F:\\010"), new File("F:\\010.zip"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("over");
	}

}