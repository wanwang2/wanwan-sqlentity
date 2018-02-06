package org.wanwanframework.sqlentity;

import org.wanwanframework.file.FileUtil;
import org.wanwanframework.file.Log;
import org.wanwanframework.file.core.FileController;

public class FieldTool extends FileController<String>{
	
	private String[] fields;
	private String template = FileUtil.readFile("./src/main/resources/field/templatejs.txt");
	
	public void init() {
		String replaceList = FileUtil.readFile("./src/main/resources/field/replace.txt");
		String[] replaceArray = this.processReplace(replaceList);
		core = FileUtil.readFile("./src/main/resources/field/laifs_info_service.sql");
		for (int i = 0; i < replaceArray.length; i++) {
			core = processReplaceAll(replaceArray[i], core);
		}
		Log.log(core);
		fields = core.split(",");
		process();
	}

	public void process() {
		String r = "\r\n";
		for (int i = 0; i < fields.length; i++) {
			r += processField(fields[i]) + "\r\n";
		}
		Log.log("r:" + r);
	}
	
	private String[] processReplace(String replaceList) {
		replaceList = replaceList.replaceAll("\\.replaceAll\\(", "")
				.replaceAll("\"", "")
				.replaceAll("\\)\\s", "\r\n")
				.replaceAll("\\);", "")
				;
		Log.log("replaceList:" + replaceList);
		return replaceList.split("\r\n");
	}
	
	private String processReplaceAll(String line, String content) {
		String[] keyValue = line.split(",");
		content = content.replaceAll(keyValue[0].trim(), keyValue[1].trim());
		return content;
	}
	
	private String processField(String field) {
		return this.template.replace("@", field);
	}

	public static void main(String[] args) {
		FieldTool.start(new FieldTool());
	}
}
