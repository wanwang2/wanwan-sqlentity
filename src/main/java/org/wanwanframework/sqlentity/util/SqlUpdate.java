package org.wanwanframework.sqlentity.util;

import java.util.HashMap;
import java.util.Map;

import org.wanwanframework.file.FileReader;
import org.wanwanframework.file.Log;
import org.wanwanframework.file.map.LineTool;
import org.wanwanframework.file.map.MappingUtil;

/**
 * update sql
 * @author coco
 *
 */
public class SqlUpdate {

	private static Map<String, String> fieldMap = new HashMap<String, String>();

	/**
	 * 加载
	 * @param url ./src/main/resources/sqlBean.field
	 */
	public static void process(String url, String template, Map<String, String>[] maps) {
		Map<String, String> map = LineTool.getLine(url);
		Map<String, String> mapping = maps[0];
		Map<String, String> whereMapping = maps[1];
		String content;
		for (String key :map.keySet()) { 
			int tableIndex = key.indexOf("(");
			String table = key.substring(0, tableIndex);
			String where = key.substring(tableIndex + 1, key.length() - 1);
			Log.log(where);
			String lineTemplate = "UPDATE " + table.trim() + " SET@content WHERE " + MappingUtil.toMapping(where, whereMapping);
			Log.log(where);
			content = MappingUtil.toField(map.get(key), " @=?,");
			//content = MappingUtil.toLine(content, lineTemplate, "@content");
			content = lineTemplate.replace("@content", content);
			fieldMap.put(table, content);
			content = MappingUtil.toField(map.get(key), "\t\tbean.get@(),\r\n"); 
			//content = MappingUtil.toLine(content, "\r\n@content", "@content");
			content = "\r\n@content".replace("@content", content);
			fieldMap.put(table + ".bean", content);
		}

		String[] regexs = new String[]{".bean", ""};
		template = MappingUtil.processReplace(template, fieldMap, mapping, regexs);
		Log.log("..." + template + "...");
	}
	
	public static void main(String[] args) {
		String template = FileReader.load("./src/main/resources/template/update.template");
		String[] resources = new String[]{
				"./src/main/resources/template/mapping.txt",
				"./src/main/resources/where.replace"};
		process("./src/main/resources/sqlBean.field", template, MappingUtil.getMapping(resources));
	}
}
