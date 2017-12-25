package core.reverse.codegen.doclet;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.RootDoc;

public class ExtractCommentsDoclet {
	private static final String fileName = "Cmd";
	
    public static boolean start(RootDoc root) throws IOException {
        for (ClassDoc c : root.classes()) {
        	boolean isCmdClass = handleClassDoc(c);
        	if(isCmdClass)
        	{
        		Map<String,FieldDoc> fieldMap = new HashMap<String,FieldDoc>();
        		List<String> cmdList = new ArrayList<String>();
        	    for (FieldDoc f : c.fields(false)) {
        	    	String value = f.constantValue().toString();
        	    	cmdList.add(value);
        	    	fieldMap.put(value, f);
        	    }
        	    Collections.sort(cmdList);
//        		for (MethodDoc m : c.methods(false)) {
//        			handleMethodDoc(m);
//        		}
        	    saveToJavaFile(fieldMap, cmdList);
        	    saveToJsFile(fieldMap, cmdList);
        	}
        }
        return true;
    }

	private static boolean handleClassDoc(ClassDoc c)
    {
    	String clsName = c.qualifiedName();
		return clsName.endsWith(".Cmd");
    }
    
    /*
    private static void handleMethodDoc(MethodDoc m)
	{
    	
		//print(m.qualifiedName(), m.commentText());
		if (m.commentText() != null && m.commentText().length() > 0) {
		    for (ParamTag p : m.paramTags())
		        //print(m.qualifiedName() + "@" + p.parameterName(), p.parameterComment());
		    for (Tag t : m.tags("return")) {
		        if (t.text() != null && t.text().length() > 0){}
		            //print(m.qualifiedName() + "@return", t.text());
		    }
		}
	}
	*/
    
    private static void saveToJavaFile(Map<String,FieldDoc> fieldMap,List<String> cmdList)
    {
    	try
		{
			@SuppressWarnings("resource")
			FileWriter fw = new FileWriter(fileName + ".java");
			String packageLine = "package cn.com.bmsoft.gzcard.webservice;\r\n\r\n";
			fw.append(packageLine);
			String classLine = "public interface Cmd{\r\n";
			fw.append(classLine);
			for(String cmd:cmdList)
			{
				FieldDoc f = fieldMap.get(cmd);
				String commentLine = String.format("\t/**%s*/\r\n", f.commentText());
				String fieldName = f.qualifiedName();
				fieldName = fieldName.substring(fieldName.lastIndexOf(".")+1);
		    	String fieldLine = String.format("\tString %s=\"%s\";\r\n", fieldName,cmd);
				fw.append(commentLine).append(fieldLine);
			}
			fw.append("}").close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
    }
    
    private static void saveToJsFile(Map<String, FieldDoc> fieldMap, List<String> cmdList)
	{
    	try
		{
			FileWriter fw = new FileWriter(fileName + ".js");
			for(String cmd:cmdList)
			{
				FieldDoc f = fieldMap.get(cmd);
				String commentLine = String.format("/**%s*/\r\n", f.commentText());
				String fieldName = f.qualifiedName();
				fieldName = fieldName.substring(fieldName.lastIndexOf(".")+1);
		    	String fieldLine = String.format("var %s=\"%s\";\r\n", fieldName,cmd);
				fw.append(commentLine).append(fieldLine);
			}
			fw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
