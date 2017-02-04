package org.wanwanframework.sqlentity.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.wanwanframework.file.map.LineTool;
import org.wanwanframwork.file.FileReader;
import org.wanwanframwork.file.Log;
import org.wanwanframwork.file.util.NameUtil;

public class Controller {

	public static void printSingle(String key, String value) {
		key = StringUtils.lowerCase(key);
		key = NameUtil.replace(key, "_", "");
		Log.print_head = "";
		Log.log("private " + value + " " + key + ";");
	}

	public Map<String, String> getField(String table, String sql, String fieldTemplate, String lineTemplate) {
		Map<String, String> fieldMap = new HashMap<String, String>();
		
		String content = "";
		sql = sql.replace("\r\n", "");
		String[] fields = sql.split(","); 
		for(int i = 0; i < fields.length; i++) {
			content += LineTool.printSingle(fields[i].trim(), fieldTemplate);
		}
		content = content.substring(0, content.length() - 1);
		content = lineTemplate.replace("@content", content);
		fieldMap.put(table, content);
		return fieldMap;
	}
	
	/**
	 * 处理资源读取
	 * @param resources
	 * @return
	 */
	public String[] processResource(String[] resources) {
		String[] contentArray = new String[2];
		for (int i = 0; i < resources.length; i++) {
			contentArray[i] = FileReader.load(resources[i]);
		}
		return contentArray;
	}
	
	/**
	 * 处理字符替换映射表
	 * @param line
	 * @param mapping
	 * @return
	 */
	public String processMapping(String line, Map<String, String> mapping) {
		for (String key: mapping.keySet()) {
			String value = mapping.get(key); 
			if(value != null && value.length() > 0) {
				line = line.replace(key, value);
			}
		}
		return line;
	}
	
	/**
	 * 核心处理方法
	 * @param mapping
	 */
	public void process(Map<String, String>[] mapping) {
		
	}
}
