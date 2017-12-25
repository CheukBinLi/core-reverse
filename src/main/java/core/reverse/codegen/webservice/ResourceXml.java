package core.reverse.codegen.webservice;

import java.io.File;

public class ResourceXml
{
	
	static String path = "C:\\dev\\workspace\\shiminka\\gzcard\\gzcard-webservice\\src\\main\\resources";
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		File folder = new File(path);
		
		File[] files = folder.listFiles();
		for(File f:files)
		{
			//System.out.println("<import resource=\""+f.getName()+"\"/>");
		}
	}
}
