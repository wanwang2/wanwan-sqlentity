package org.wanwanframework.sqlentity.util;

import org.apache.commons.lang.StringUtils;
import org.wanwanframwork.file.FileReader;
import org.wanwanframwork.file.Log;

public class BeanUtil {

	public static void getField(String sql) {
		sql = sql.replace("\r\n", "");
		String[] fields = sql.split(","); 
		for(int i = 0; i < fields.length; i++) {
			printSingle(fields[i].trim());
		}
	}
	
	public static void printSingle(String key) {
		key = StringUtils.lowerCase(key);
		key = replaceUnderlineAndfirstToUpper(key, "_", "");
		key = firstCharacterToUpper(key);
		Log.print_head = "";
		System.out.print(" bean.get" + key + "(),");
	}
	
	/**
	 * 首字母大写
	 * 
	 * @param srcStr
	 * @return
	 */
	public static String firstCharacterToUpper(String srcStr) {
		return srcStr.substring(0, 1).toUpperCase() + srcStr.substring(1);
	}

	/** 
	* 替换字符串并让它的下一个字母为大写 
	* @param srcStr 
	* @param org 
	* @param ob 
	* @return 
	*/  
	public static String replaceUnderlineAndfirstToUpper(String srcStr, String org, String ob) {
		String newString = "";
		int first = 0;
		while (srcStr.indexOf(org) != -1) {
			first = srcStr.indexOf(org);
			if (first != srcStr.length()) {
				newString = newString + srcStr.substring(0, first) + ob;
				srcStr = srcStr.substring(first + org.length(), srcStr.length());
				srcStr = firstCharacterToUpper(srcStr);
			}
		}
		newString = newString + srcStr;
		return newString;
	}
	
	public static void process() {
		String content = FileReader.load("./src/main/resources/sqlBean.txt");
		Log.log(content);
		getField(content);
		//printMap(map);
	}
	
	public static void main(String[] args) {
		process();
	}
}
