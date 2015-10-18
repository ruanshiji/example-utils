package com.t2t.examples;

/*
 * file.separator :文件分隔符，     Windows环境下为“\ "，Unix环境下为“/”；         
 * user.home      ：用户主目录；         
 * java.home      ：Java实时运行环境的安装目录；         
 * java.ext.dirs  ：JDK的安装目录；         
 * os.name        ：操作系统名称；         
 * user.name      ：用户登录名称；         
 * os.version     ：操作系统版本；         
 * path.separator ：当前操作系统的路径分隔符；         
 * user.dir       ：当前用户程序所在目录。
 * line.separator :回车换行，Mac为“/r”，Unix/Linux为“/n”，Windows为“/r/n”
 */

/**
 * @author ypf
 */
public class EnvironmentVar {
	
	public static final String FILE_SEPARATOR = "file.separator";// 文件分隔符
	public static final String USER_HOME = "user.home";// 用户主目录
	public static final String JAVA_HOME = "java.home";// Java实时运行环境的安装目录
	public static final String JAVA_EXT_DIRS = "java.ext.dirs";// JDK的安装目录
	public static final String os_name = "os.name";// 操作系统名称
	public static final String USER_NAME = "user.name";// 用户登录名称
	public static final String OS_VERSION = "os.version";// 操作系统版本
	public static final String PATH_SEPARATOR = "path.separator";// 当前操作系统的路径分隔符
	public static final String USER_DIR = "user.dir";// 当前用户程序所在目录
	public static final String LINE_SEPARATOR = "line.separator";// 回车换行

	@SuppressWarnings("restriction")
	public static String get(String key) {
		String step = (String) java.security.AccessController.doPrivileged(new sun.security.action.GetPropertyAction(key));
		return step;
	}
}
