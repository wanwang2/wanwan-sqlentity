package org.wanwanframework.sqlentity.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.wanwanframework.file.FileReader;
import org.wanwanframework.file.Log;
import org.wanwanframework.file.util.NameUtil;

/**
 * 提供从sql-create 语句到entity之间的转换
 * @author coco
 *
 */
public class EntityUtil {

	public static Map<String, String> typeMap;
	public static Map<String, String> commentMap;
	
	public static String getCreate(String sql) {
		int start 	= sql.indexOf("(");
		int end	  	= sql.lastIndexOf(")");
		String sub 	= sql.substring(start + 1, end);
		return sub;
	}
	
	public static Map<String, String> getComment(String comment) {
		comment = comment.replaceAll("comment on column \\w+.", "");
		comment = comment.replace("\r\n", "");
		String[] fields = comment.split(";");
		String[] keyValue;
		Map<String, String> map = new HashMap<String, String>();
		for(int i = 0; i < fields.length; i++) {
			keyValue = fields[i].split("is");
			map.put(keyValue[0].trim(), keyValue[1].trim());
		}
		return map;
	}
	
	public static Map<String, String> getType(String content) {
		Map<String, String> map = new HashMap<String, String>();
		String[] types = content.split("\r\n");
		for (int i = 0; i < types.length; i++) {
			String[] keyValue = types[i].split("\\s+");
			if(keyValue != null && keyValue.length == 2){
				map.put(keyValue[0].trim(), keyValue[1].trim());	
			}
		}
		return map;
	}
	
	public static Map<String, String> getField(String sql) {
		sql = sql.replace("\r\n", "");
		String[] fields = sql.split(",");
		Map<String, String> map = new LinkedHashMap<String, String>();
		for(int i = 0; i < fields.length; i++) {
			String fieldString = fields[i].trim();
			String[] keyValue = fieldString.split("\\s+");
			if(keyValue != null && keyValue.length == 2){
				map.put(keyValue[0].trim(), toType(keyValue[1].trim()));	
			}
		}
		return map;
	}
	
	public static String toType(String key) {
		String value = null;
		for(String mapKey : typeMap.keySet()) {
			if(key.indexOf(mapKey) >= 0){
				value = typeMap.get(mapKey);
			}
		}
		return value;
	}
	
	public static void printMap(Map<String, String> map, Map<String, String> comment) {
		Set<String> keySet = map.keySet();
		for (String key:keySet) {
			String value = map.get(key);
			String commentValue = comment.get(key);
			printSingle(key, value, commentValue);
		}
	}
	
	public static void printSingle(String key, String value, String commentValue) {
		key = StringUtils.lowerCase(key);
		key = key.replace("id", "Id");
		key = NameUtil.replace(key, "_", "");
	
//		Log.print_head = "";
		Log.log("private " + value + " " + key + "; // " + commentValue);
	}

	public static void process() {
		String type = FileReader.load("./src/main/resources/entity/entityType.txt");
		
		typeMap = getType(type);
		String sqltxt = FileReader.load("./src/main/resources/sql.txt");
		String[] contentArray = sqltxt.split("\r\n\r\n");
		String content = contentArray[0];
		String comment = contentArray[1];
		content = content.replaceAll("not null", "");
		String sql = getCreate(content);
		Log.log(sql);
		Map<String, String> map = getField(sql);
		Log.log(map);
		printMap(map, getComment(comment));
	}
	
	public static void main(String[] args) {
		process();
	}
}
