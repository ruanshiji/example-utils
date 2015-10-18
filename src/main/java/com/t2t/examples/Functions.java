package com.t2t.examples;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 自定义公用库
 * 
 * @author ypf
 */
public class Functions {

	/**
	 * 将list数组转换成"'',''"形式的字符串
	 * 
	 * @param obj
	 *            List
	 * @return String
	 */
	public static String getInSQLStr(Object obj) {
		if (obj == null)
			return "";

		List<?> list = (List<?>) obj;
		String step = "";
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				String temp = list.get(i).toString().replace("'", "''");// 屏蔽单引号
				step += "'{0}',".replace("{0}", temp);
			}
		}
		step = step.length() == 0 ? "" : step.substring(0, step.length() - 1);
		return step;
	}

	/**
	 * 去两个数的百分比,80和100则返回80.00%
	 * 
	 * @param obj1
	 * @param obj2
	 * @return 若果obj1、obj2其中有一个或全部小于等于0，均返回0%
	 */
	public static String formartPercent(Object obj1, Object obj2) {
		obj1 = (obj1 == null ? 0 : obj1);
		obj2 = (obj2 == null ? 0 : obj2);

		try {
			Double d1 = Double.parseDouble(obj1.toString());
			Double d2 = Double.parseDouble(obj2.toString());
			if (d1 <= 0 || d2 <= 0)
				return "0%";
			return formartPercent(d1 / d2);
		} catch (Exception e) {
			e.printStackTrace();
			return "--";
		}
	}

	/**
	 * 统一转换成double，然后四舍五入 如果0.985 格式化后 98.60%
	 * 
	 * @param obj
	 * @return String
	 */
	public static String formartPercent(Object obj) {
		DecimalFormat df = new DecimalFormat("##.00%");
		try {
			// 统一转成double
			Object d = Double.parseDouble(obj.toString());
			return df.format(d);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "--";
		}
	}

	/**
	 * 判读map是否存在obj的键
	 * 
	 * @param map
	 * @param obj
	 * @return boolean
	 */
	public static boolean containsKey(Map<?, ?> map, Object obj) {
		return map.containsKey(obj);
	}

	/**
	 * 判读map是否存在obj的值
	 * 
	 * @param map
	 * @param obj
	 * @return boolean
	 */
	public static boolean containsValue(Map<?, ?> map, Object obj) {
		return map.containsValue(obj);
	}

	/**
	 * 截取字符串
	 * 
	 * @param str
	 *            要转换的字符串
	 * @param length
	 *            长度
	 * @param prefix
	 *            要加的后缀
	 * @return String
	 */
	public static String subString(String str, int length, Object... prefix) {
		String _prefix = (String) getSimpleObject(prefix);
		;
		if (str == null)
			return "";
		if (str.length() > length) {
			str = str.substring(0, length) + _prefix;
		}
		return str;
	}

	/**
	 * 将null转换成字符串并去掉空格
	 * 
	 * @param obj
	 * @return String
	 */
	public static String convertNULL2String(Object obj) {
		if (obj == null)
			return "";
		return obj.toString().trim().replace("'", "''");
	}

	public static String convertNULL2String(Object obj, String $) {
		if (obj == null)
			return $;
		return obj.toString().trim().replace("'", $);
	}

	/**
	 * 是否为空
	 * 
	 * @param obj
	 *            (注：如果obj的只为null、""、-1、-1L、-1D、-1F返回值都为true)
	 * @return boolean
	 */
	public static boolean isSpaceOrNull(Object obj) {
		if (obj == null)
			return true;

		if (obj instanceof String)
			return "".equals(obj.toString()) || "null".equalsIgnoreCase(obj.toString());
		if (obj instanceof Integer)
			return (Integer.parseInt(obj.toString()) == -1L);
		if (obj instanceof Long)
			return (Long.parseLong(obj.toString()) == -1L);
		if (obj instanceof Double)
			return (Double.parseDouble(obj.toString()) == -1L);
		if (obj instanceof Float)
			return (Float.parseFloat(obj.toString()) == -1L);

		return false;
	}

	/**
	 * 格式化日期 主要用于显示在文本框中
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDateTime(Date date, Object... format) {
		if (date == null) {
			return null;
		}
		String fmt = (String) getSimpleObject(format);
		if (fmt == null || fmt.length() == 0)
			fmt = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.format(new Date());
	}

	/**
	 * 获取当前时间
	 * 
	 * @param formats
	 *            格式
	 * @return String
	 */
	public static String getCurrentTime(Object... format) {
		return formatDateTime(new Date(), format);
	}

	/**
	 * 判断是否是当前月份
	 * 
	 * @param Object
	 * @return boolean
	 */
	public synchronized static boolean isInCurrentMonth(Object obj) {
		if (obj == null)
			return false;

		Date date = null;
		if (obj instanceof Date) {
			date = (Date) obj;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
		return sdf.format(date).equals(sdf.format(new Date()));
	}

	/**
	 * 返回对象第一个值
	 * 
	 * @param obj
	 * @return Object
	 */
	public synchronized static Object getSimpleObject(Object... objs) {
		Object obj = null;
		// 如果对象长度大于等于1则，值取第一个
		if (objs != null && objs.length >= 1)
			obj = objs[0];
		return obj;
	}

	/**
	 * 用来比较两个对象的值是否相等，如1="1", false="false"
	 * 
	 * @param source
	 * @param target
	 * @return boolean
	 */
	public synchronized static boolean compare(Object source, Object target) {
		boolean flag = false;
		if (source == null && target != null)
			flag = false;
		else if (source != null && target == null)
			flag = false;
		else if (source == null && target == null)
			flag = true;
		else {
			flag = source.toString().equals(target.toString());
		}
		return flag;
	}

	/**
	 * 格式化字符串(去除单引号)
	 * 
	 * <pre>
	 * @param input 输入字符串
	 * @param args 动态参数
	 * @return String 格式化后的结果
	 * </pre>
	 */
	public static String formatSql(String input, Object... args) {
		StringBuffer sb = new StringBuffer();
		formatSql(sb, input, args);
		return sb.toString();
	}

	/**
	 * 格式化字符串
	 * 
	 * <pre>
	 * @param input 输入字符串
	 * @param appendInput 需要追加的值
	 * @param args 动态参数
	 * </pre>
	 */
	public static void formatSql(StringBuffer input, String appendInput, Object... args) {
		for (int i = 0; i < args.length; i++) {
			appendInput = appendInput.replace("{" + i + "}", filerString(args[i]));
		}
		input.append(appendInput);
	}

	/**
	 * 过滤sql特殊字符
	 * 
	 * <pre>
	 * @param input 输入参数
	 * @return String 过滤后的结果
	 * </pre>
	 */
	public static String filerString(Object input) {
		if (input == null)
			return "";
		String str = input.toString().trim().replace("'", "''");
		return str;
	}

	/**
	 * 格式化输入字符串
	 * 
	 * <pre>
	 * @param input 输入字符串
	 * @param args 动态参数
	 * @return String 格式化后的结果
	 * </pre>
	 */
	public static String formatMsg(String input, Object... args) {
		for (int i = 0; i < args.length; i++) {
			input = input.replace("{" + i + "}", args[i] + "");
		}
		return input;
	}

	/**
	 * <pre>
	 * 将字节转换成GB\MB\KB
	 * @param size 大小
	 * @return String
	 * </pre>
	 */
	public static String getBitStr(long size) {
		DecimalFormat df1 = new DecimalFormat("####.##");
		int CONSTANT = 1024;
		int KB_Bit = CONSTANT;
		int MB_Bit = CONSTANT * CONSTANT;
		int GB_Bit = CONSTANT * CONSTANT * CONSTANT;
		String str = "";
		double d = 0;
		if (size > GB_Bit) {
			d = 1.0 * size / GB_Bit;
			str = df1.format(d) + " GB";
		} else if (size > MB_Bit) {
			d = 1.0 * size / MB_Bit;
			str = df1.format(d) + " MB";
		} else {
			d = 1.0 * size / KB_Bit;
			str = df1.format(d) + " KB";
		}
		return str;
	}

	public static Date str2Date(Object obj, String pattern) throws ParseException {
		if (obj == null || obj.toString().equals(""))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(obj + "");
	}

	public static void main(String[] args) {
		String str = "{0}a{1}";
		Object[] params = { "a", null };
		System.out.println(formatMsg(str, params));
	}
}
