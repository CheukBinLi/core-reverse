package core.reverse;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import core.reverse.codegen.bean.ClassBean;
import core.reverse.codegen.bean.ClassDtoBean;
import core.reverse.codegen.util.ClassBeanUtil;
import core.reverse.codegen.util.FreemarkerUtil;

public class ClassToAllFile
{
	public static final String GENFOLDER = "./gen";
	
	public static void main(String[] args)
	{
//		genEntityCode(Staff.class,"用户表","system.org");
//		genEntityCode(RoleRes.class,"角色权限表","system.org");
//		genEntityCode(StaffRole.class,"角色用户表","system.org");
//		genEntityCode(Role.class,"角色表","system.org");
		
	}

	private static void genEntityCode(Class<?> cl,String mean,String module)
	{
		ClassBean cBean = ClassBeanUtil.toClassBean(cl,mean);
		cBean.setModule(module);
		////System.out.println(cBean);
		
		genModelMapperXML(cBean);
		genDao(cBean);
		genDaoImpl(cBean);
//		genDaoXML(cBean);
//		genMybatisConfig(cBean);
//		genService(cBean);
//		genServiceImpl(cBean);
//		genHandlers(cBean);
		genController(cBean);
		genHtml(cBean);
//		genCmd(cBean);
//		genHtml(cBean);
		//genCmd(cBean);
//		genModelMap(cBean);
//		genMethodHelper(cBean);
		
		if(cBean.getJoinSize()>0)
		{
			ClassDtoBean dtoBean = new ClassDtoBean(cBean);
			genDto(dtoBean,cBean);
		}
	}

	private static void genCmd(ClassBean bean)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("context", bean);
		FreemarkerUtil.buildTemplate(map,bean.getSimpleName()+File.separator+"cmd", "Cmd.java", "Cmd.java.ftl");
	}

	private static void genHtml(ClassBean bean)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("context", bean);
		FreemarkerUtil.buildTemplate(map,bean.getSimpleName()+File.separator+"html", "index.html", "index.html.ftl");
		FreemarkerUtil.buildTemplate(map,bean.getSimpleName()+File.separator+"html", "add.html", "add.html.ftl");
		FreemarkerUtil.buildTemplate(map,bean.getSimpleName()+File.separator+"html", "update.html", "update.html.ftl");
	}

	private static void genController(ClassBean bean)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("context", bean);
		FreemarkerUtil.buildTemplate(map,bean.getSimpleName()+File.separator+"controller", bean.getSimpleName()+"Controller.java", "modelController.java.ftl");
	}

	private static void genHandlers(ClassBean bean)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("context", bean);
		FreemarkerUtil.buildTemplate(map,bean.getSimpleName()+File.separator+"handlers", bean.getSimpleName()+"ListHandler.java", "modelListHandler.java.ftl");
		FreemarkerUtil.buildTemplate(map,bean.getSimpleName()+File.separator+"handlers", bean.getSimpleName()+"ByPKHandler.java", "modelByPKHandler.java.ftl");
		FreemarkerUtil.buildTemplate(map,bean.getSimpleName()+File.separator+"handlers", bean.getSimpleName()+"AddHandler.java", "modelAddHandler.java.ftl");
		FreemarkerUtil.buildTemplate(map,bean.getSimpleName()+File.separator+"handlers", bean.getSimpleName()+"UpdateHandler.java", "modelUpdateHandler.java.ftl");
		FreemarkerUtil.buildTemplate(map,bean.getSimpleName()+File.separator+"handlers", bean.getSimpleName()+"DeleteHandler.java", "modelDeleteHandler.java.ftl");
	}

	private static void genServiceImpl(ClassBean bean)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("context", bean);
		FreemarkerUtil.buildTemplate(map,bean.getSimpleName()+File.separator+"service", bean.getSimpleName()+"ManagerService.java", "service.java.ftl");
	}

	private static void genService(ClassBean bean)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("context", bean);
		FreemarkerUtil.buildTemplate(map,bean.getSimpleName()+File.separator+"service", bean.getSimpleName()+"ManagerServiceImpl.java", "serviceImpl.java.ftl");
	}

	private static void genDto(ClassDtoBean bean,ClassBean cBean)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("context", bean);
		FreemarkerUtil.buildTemplate(map,cBean.getSimpleName()+File.separator+"dto", bean.getDtoName()+".java", "dto.java.ftl");
	}

	/**
	 * 生成配置文件
	 * @param bean
	 */
	public static void genModelMapperXML(ClassBean bean)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("context", bean);
		FreemarkerUtil.buildTemplate(map,bean.getSimpleName()+File.separator+"mapperxml", bean.getSimpleName()+"Mapper.xml", "modelMapper.xml.ftl");
	}
	
	/**
	 * 生成配置文件
	 * @param bean
	 */
	public static void genMybatisConfig(ClassBean bean)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("context", bean);
		FreemarkerUtil.buildTemplate(map,bean.getSimpleName()+File.separator+"mybatisconfig", bean.getSimpleName()+"MyBatis-Configuration.xml", "MyBatis-Configuration.xml.ftl");
	}
	
	/**
	 * 生成配置文件
	 * @param bean
	 */
	public static void genDao(ClassBean bean)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("context", bean);
		FreemarkerUtil.buildTemplate(map,bean.getSimpleName()+File.separator+"dao", "I"+bean.getSimpleName()+"Dao.java", "dao.java.ftl");
	}
	
	/**
	 * 生成配置文件
	 * @param bean
	 */
	public static void genDaoImpl(ClassBean bean)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("context", bean);
		FreemarkerUtil.buildTemplate(map,bean.getSimpleName()+File.separator+"dao", bean.getSimpleName()+"Dao.java", "daoImpl.java.ftl");
	}
	
	/**
	 * 生成配置文件
	 * @param bean
	 */
	public static void genDaoXML(ClassBean bean)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("context", bean);
		FreemarkerUtil.buildTemplate(map,bean.getSimpleName()+File.separator+"daoxml", bean.getSimpleName()+"Dao.xml", "dao.xml.ftl");
	}
	
	/**
	 * 生成map文件
	 * @param bean
	 */
	public static void genModelMap(ClassBean bean)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("context", bean);
		FreemarkerUtil.buildTemplate(map,bean.getSimpleName()+File.separator+"map", bean.getSimpleName()+"Map.java", "modelMap.java.ftl");
	}
	
	private static void genMethodHelper(ClassBean bean)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("context", bean);
		FreemarkerUtil.buildTemplate(map,bean.getSimpleName()+File.separator+"dao", bean.getSimpleName()+"MethodHelper.java", "MethodHelper.java.ftl");
	}
}
