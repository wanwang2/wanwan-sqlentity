package org.wanwanframework.sqlentity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.wanwanframework.file.FileReader;
import org.wanwanframework.file.FileUtil;
import org.wanwanframework.file.Log;

public class TableConfigController {

	private String content;// create table
	private String writeContent; // 输出文本

	public TableConfigController() {
		String sqltxt = FileReader.load("./src/main/resources/sql.txt");
		String[] contentArray = sqltxt.split("\r\n\r\n");
		content = contentArray[0];
	}
	
	public void init() {
		List<String> list = parseContent(getField(content));
		Log.log(list);
		writeContent = list.toString();
	}
	
	public void write() throws IOException {
		FileUtil.createFile("./src/main/resources/sql.txt.write", writeContent);
	}
	
	private String getField(String sql) {
		int start = sql.indexOf("(");
		int end = sql.lastIndexOf(")");
		String sub = sql.substring(start + 1, end);
		return sub;
	}
	
	/**
	 * 解析内容
	 * @param field
	 * @return
	 */
	private List<String> parseContent(String field) {
		String[] fields = field.split(",");
		List<String> list = new ArrayList<String>();
		String value;
		for (int i = 0; i < fields.length; i++) {
			value = fields[i];
			list.add(value);
		}
		return list;
	}
	
	public static void main(String[] args) throws IOException {
		TableConfigController controller = new TableConfigController();
		controller.init();
		controller.write();
	}
}
