package core.reverse.codegen.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerUtil
{
	static final String GENFOLDER = "gen";
	static final String TEMPLATEFOLDER = "templates";
	
	static Configuration cfg = new Configuration();
	static
	{
		File templateFolder = new File(TEMPLATEFOLDER);
		try
		{
			cfg.setDirectoryForTemplateLoading(templateFolder);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		File genFolder = new File(GENFOLDER);
		if(!genFolder.exists())
		{
			genFolder.mkdirs();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
	}

//	public static void process() throws Exception
//	{
//		Map root = new HashMap();
//		String Module = "";
//		String model_name = "User";
//		String model_name_list = "Users";
//		String instant = "user";
//		String model_name_cn = "用户";
//		String author = "张何兵";
//		String link = "<a href=http://www.media999.com.cn>北京华亚美科技有限公司</a>";// 模块开发公司网地址
//		Date date = new Date();
//		root.put("module", Module);
//		root.put("model_name", model_name);
//		root.put("model_name_list", model_name_list);
//		root.put("instant", instant);
//		root.put("model_name_cn", model_name_cn);
//		root.put("author", author);
//		root.put("link", link);
//		root.put("date", date);
//		String projectPath = "D://Workspaces//MyEclipse75//s2sh//";
//		String fileName = "I" + model_name + "DAO.java";
//		String savePath = "src//com//media//dao//";
//		Template template = cfg.getTemplate("IDAO.ftl");
//		hf.buildTemplate(root, projectPath, savePath, fileName, template);
//		fileName = model_name + "DAOHibernate.java";
//		savePath = "src//com//media//dao//hibernate//";
//		template = cfg.getTemplate("DAOHibernate.ftl");
//		hf.buildTemplate(root, projectPath, savePath, fileName, template);
//		fileName = model_name + "Service.java";
//		savePath = "src//com//media//service//";
//		template = cfg.getTemplate("Service.ftl");
//		hf.buildTemplate(root, projectPath, savePath, fileName, template);
//		fileName = model_name + "ServiceImpl.java";
//		savePath = "src//com//media//service//impl//";
//		template = cfg.getTemplate("ServiceImpl.ftl");
//		hf.buildTemplate(root, projectPath, savePath, fileName, template);
//	}

	public static void buildTemplate(Map<String,Object> root, String fileName, Template template)
	{
		String realFileName = GENFOLDER + File.separator + fileName;
		File newsDir = new File(GENFOLDER);
		if (!newsDir.exists())
		{
			newsDir.mkdirs();
		}
		try
		{
			String SYSTEM_ENCODING = "UTF-8";
			Writer out = new OutputStreamWriter(new FileOutputStream(realFileName), SYSTEM_ENCODING);
			template.process(root, out);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void buildTemplate(Map<String,Object> root, String fileName, String templateName)
	{
		try
		{
			Template template = cfg.getTemplate(templateName);
			buildTemplate(root, fileName, template);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void buildTemplate(Map<String,Object> root,String folder, String fileName, Template template)
	{
		String realFileName = GENFOLDER + File.separator +folder+File.separator+ fileName;
		File newsDir = new File(GENFOLDER + File.separator +folder);
		if (!newsDir.exists())
		{
			newsDir.mkdirs();
		}
		try
		{
			String SYSTEM_ENCODING = "UTF-8";
			Writer out = new OutputStreamWriter(new FileOutputStream(realFileName), SYSTEM_ENCODING);
			template.process(root, out);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void buildTemplate(Map<String,Object> root,String folder, String fileName, String templateName)
	{
		try
		{
			Template template = cfg.getTemplate(templateName);
			buildTemplate(root,folder, fileName, template);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
