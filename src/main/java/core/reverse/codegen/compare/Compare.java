package core.reverse.codegen.compare;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import core.reverse.codegen.orm.DBField;
import core.reverse.codegen.orm.DBService;
import core.reverse.codegen.orm.DataSource;
import core.reverse.codegen.orm.OracleDBService;
import core.reverse.codegen.util.ReflectUtil;

public class Compare
{
	static DataSource dataSource;
	static DBService dbService;
	
	static
	{
		dataSource = new DataSource();
		dbService = new OracleDBService();
		dbService.setDataSource(dataSource);
	}
	
	public static void main(String[] args) throws Exception
	{
//		List<Field> fields = ReflectUtil.getFields(OperatorInfo.class.getName());
//		Iterator<Field> it = fields.iterator();
//		while(it.hasNext())
//		{
//			Field f = it.next();
//			System.out.println(f.getName()+":"+Modifier.isStatic(f.getModifiers()));
//		}
		//List<String> dbTables = dbService.getTableList();
		//List<String> domains = ReflectUtil.getClassName("C:\\dev\\workspace\\shiminka\\gzcard\\gzcard-domain\\target\\classes","cn.com.bmsoft.gzcard.domain");
		//compareCount(CollectionUtil.copy(dbTables), CollectionUtil.copy(domains));
		//
		//compareField(CollectionUtil.copy(domains));
		//compareField(CardFacRetInfo.class.getName());
	}
	
	public static void compareCount(List<String> dbTables,List<String> domains)
	{
		//取简称
		List<String> simples = new ArrayList<String>(domains.size());
		for(String domain:domains)
		{
			String simple = domain.substring(domain.lastIndexOf(".")+1);
			//System.out.println(simple);
			simples.add(simple);
		}
		//
		Iterator<String> dbIt = dbTables.iterator();
		Iterator<String> doIt = null;
		while(dbIt.hasNext())
		{
			String dbTable = dbIt.next();
			doIt = simples.iterator();
			while(doIt.hasNext())
			{
				String domain = doIt.next();
				if(dbTable.compareToIgnoreCase(domain)==0)
				{
					dbIt.remove();
					doIt.remove();
					break;
				}
			}
		}
		//
		if(dbTables.size()>0)
		{
			System.out.println("数据库存在,实体类不存在,"+dbTables.size());
			for(String dbTable:dbTables)
			{
				System.out.println(dbTable);
			}
		}
		//
		if(simples.size()>0)
		{
			System.out.println("数据库不存在,实体类存在");
			for(String simple:simples)
			{
				System.out.println(simple);
			}
		}
	}
	
	public static void compareField(List<String> domains) throws Exception
	{
		for(String domain:domains)
		{
			compareField(domain);
		}
		
	}

	private static void compareField(String domain) throws Exception
	{
		String simple = domain.substring(domain.lastIndexOf(".")+1);
		DBField[] dbFields = dbService.getFiels(simple);
		List<Field> fields = ReflectUtil.getFields(domain);
		Iterator<Field> it = fields.iterator();
		while(it.hasNext())
		{
			Field f = it.next();
			if(Modifier.isStatic(f.getModifiers()))
			{
				it.remove();
			}
		}
		List<String> unMatchDBFields = new ArrayList<String>();
		List<String> unMatchCoFields = new ArrayList<String>();
		boolean ex = false;
		for(DBField dbField:dbFields)
		{
			boolean found = false;
			it = fields.iterator();
			while(it.hasNext())
			{
				Field f = it.next();
				if(Modifier.isStatic(f.getModifiers()))
				{
					it.remove();
				}
				else if(dbField.getName().compareToIgnoreCase(f.getName())==0)
				{
					found = true;
					it.remove();
					break;
				}
			}
			if(!found)
			{
				ex = true;
				unMatchDBFields.add(dbField.getName());
			}
		}
		if(fields.size()>0)
		{
			for(Field field:fields)
			{
				ex = true;
				unMatchCoFields.add(field.getName());
			}
		}
		//
		if(ex)
		{
			System.out.print(simple+"表异常,");
			boolean exdb = false;
			if(unMatchDBFields.size()>0)
			{
				exdb = true;
				System.out.print("字段对应不到实体属性[");
				boolean first = true;
				for(String field:unMatchDBFields)
				{
					if(!first)
					{
						System.out.print(",");
					}
					first = false;
					System.out.print(field);
				}
				System.out.print("]");
			}
			if(unMatchCoFields.size()>0)
			{
				if(exdb)
				{
					System.out.print(",");
				}
				System.out.print("实体属性对应不到字段[");
				boolean first = true;
				for(String field:unMatchCoFields)
				{
					if(!first)
					{
						System.out.print(",");
					}
					first = false;
					System.out.print(field);
				}
				System.out.print("]");
			}
			System.out.println();
		}
	}
}
