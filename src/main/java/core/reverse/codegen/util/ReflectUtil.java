package core.reverse.codegen.util;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectUtil
{
	/**
	 * 读取类列表
	 * @param projectFolder
	 * @param packageName
	 * @return
	 */
	public static List<String> getClassName(String projectFolder,String packageName)
	{
		String filePath = projectFolder + "\\" + packageName.replace(".", "\\");
		List<String> fileNames = getClassName(filePath, (List<String>)null);
		return fileNames;
	}
	
	/**
	 * 读取类列表
	 * @param packageName
	 * @return
	 */
	public static List<String> getClassName(String packageName)
	{
		String filePath = ClassLoader.getSystemResource("").getPath() + packageName.replace(".", "\\");
		List<String> fileNames = getClassName(filePath, (List<String>)null);
		return fileNames;
	}
	
	private static List<String> getClassName(String filePath, List<String> className) {
		List<String> myClassName = new ArrayList<String>();
		File file = new File(filePath);
		File[] childFiles = file.listFiles();
		for (File childFile : childFiles) {
			if (childFile.isDirectory()) {
				myClassName.addAll(getClassName(childFile.getPath(), myClassName));
			} else {
				String childFilePath = childFile.getPath();
				childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));
				childFilePath = childFilePath.replace("\\", ".");
				myClassName.add(childFilePath);
			}
		}
		return myClassName;
	}
	
	public static List<Field> getFields(String className) throws Exception
	{
		Class c = Class.forName(className);
		Field[] fields = c.getDeclaredFields();
		if(fields==null||fields.length==0)
		{
			return null;
		}
		List<Field> fieldList = new ArrayList<Field>();
		for(Field field:fields)
		{
			fieldList.add(field);
		}
		return fieldList;
	}
}
