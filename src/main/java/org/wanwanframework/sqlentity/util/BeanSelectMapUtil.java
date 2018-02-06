package org.wanwanframework.sqlentity.util;

import org.apache.commons.lang.StringUtils;
import org.wanwanframework.file.FileReader;
import org.wanwanframework.file.Log;

public class BeanSelectMapUtil {

	public static void getField(String sql) {
		sql = sql.replace("\r\n", "");
		String[] fields = sql.split(","); 
		String content = "";
		for(int i = 0; i < fields.length; i++) {
			content += printSingle(fields[i].trim());
		}
		content = content.substring(0, content.length() - 1);
		Log.log("\r\n" + content);
	}
	
	public static String printSingle(String key) {
		String sourceKey = key;
		key = StringUtils.lowerCase(key);
		key = replaceUnderlineAndfirstToUpper(key, "_", "");
		key = firstCharacterToUpper(key);
		//udmService.setSubsId((String)  map.get("SUBSID"));
		String result = "bean.set" + key + "((String)  map.get(\"" + sourceKey + "\"));\r\n";
		return result;
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
		String content = FileReader.load("./src/main/resources/sqlBeanField.txt");
		Log.log(content);
		String[] line = content.split("\r\n");
		for (int i = 0; i < line.length; i++) {
			getField(line[i]);
		}
	}
	
	public static void main(String[] args) {
		process();
	}
}
