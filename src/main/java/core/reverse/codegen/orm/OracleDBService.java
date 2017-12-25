package core.reverse.codegen.orm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OracleDBService extends DBService
{
	@Override
	public String[] getTables()
	{
//		String sql = "select table_name from user_tables";
		String sql = "show tables";
		List<Object[]> list = this.getDataSource().query(sql);
		if(list!=null&&list.size()>0)
		{
			String[] arr = new String[list.size()];
			for(int i=0;i<list.size();i++)
			{
				arr[i] = list.get(i)[0].toString();
			}
			return arr;
		}
		return null;
	}
	
	@Override
	public List<String> getTableList()
	{
		String sql = "select table_name from user_tables";
		List<Object[]> list = this.getDataSource().query(sql);
		if(list!=null&&list.size()>0)
		{
			List<String> arr = new ArrayList<String>(list.size());//new String[list.size()];
			for(int i=0;i<list.size();i++)
			{
				arr.add(list.get(i)[0].toString());
			}
			return arr;
		}
		return null;
	}

	@Override
	public DBField[] getFiels(String tableName)
	{
		//String[] pks = getPKFiels(tableName);
//		String sql = String.format("select " +
//					"a.column_id as 列号, " +
//					"a.column_name as 列名, " +
//					"a.data_type as 类型,"+
//					"decode(a.data_type,'NUMBER',a.data_precision,a.data_length) as 长度, " +
//					"a.data_scale as 小数位,"+
//					"decode(e.uniqueness,'UNIQUE','Y','N') as 是否是唯一的, " +
//					"decode(e.key,'Y','Y','N') 是否是主键,"+
//					"f.comments as 注释, " +
//					"a.nullable as 是否允许空, " +
//					"a.data_default as 默认值 "+
//					"from user_tab_columns a, user_col_comments f,"+
//					"(select b.table_name, b.index_name,b.uniqueness, c.column_name,"+
//					"decode(d.constraint_name,NULL,'N','Y') key "+
//					"from user_indexes b, user_ind_columns c,( select constraint_name from user_constraints where constraint_type='P' ) d "+
//					"where b.index_name=c.index_name and b.index_name=d.constraint_name(+) ) e "+
//					"where a.table_name='%s' and a.table_name=e.table_name(+) and a.column_name=e.column_name(+) "+
//					"and a.table_name=f.table_name and a.column_name=f.column_name "+
//					"order by a.column_id", tableName.toUpperCase());
		String sql = String.format("select column_name,data_type,data_length,nullable from user_tab_columns where TABLE_NAME='%s'", tableName.toUpperCase());
		List<Object[]> list = this.getDataSource().query(sql);
		if(list!=null&&list.size()>0)
		{
			DBField[] dbFields = new DBField[list.size()];
			for(int i=0;i<list.size();i++)
			{
				Object[] objs = list.get(i);
				dbFields[i] = new DBField();
				dbFields[i].setName(objs[1].toString());
				dbFields[i].setType(objs[2].toString());
				try
				{
					dbFields[i].setLength(Integer.parseInt(objs[3].toString()));
				}
				catch(Exception e)
				{
					dbFields[i].setLength(0);
				}
				dbFields[i].setNullable("Y".equals(objs[8].toString()));
				dbFields[i].setPk("Y".equals(objs[6].toString()));
			}
			return dbFields;
		}
		return null;
	}

	@Override
	public String[] getPKFiels(String tableName)
	{
		String sql = String.format("select a.column_name from user_cons_columns a,user_constraints b where a.constraint_name=b.constraint_name and b.constraint_type='P' and a.table_name='%s'", tableName);
		List<Object[]> list = this.getDataSource().query(sql);
		if(list!=null&&list.size()>0)
		{
			String[] pks = new String[list.size()];
			for(int i=0;i<list.size();i++)
			{
				Object[] objs = list.get(i);
				pks[i] = objs[0].toString();
			}
			return pks;
		}
		return null;
	}
	
	static Map<String,String> db2JavaMap = new HashMap<String, String>();
	
	static
	{
		db2JavaMap.put("VARCHAR2", "String");
		db2JavaMap.put("DATE", "Date");
		db2JavaMap.put("NUMBER", "int");
		db2JavaMap.put("BLOB", "String");
	}

	@Override
	public EntityFieldBean toEntityFieldBean(DBField dbField,String tableName)
	{
		EntityFieldBean efBean = new EntityFieldBean();
		
		efBean.setName(dbField.getName().toLowerCase());
		efBean.setType(getType(dbField,tableName));
		efBean.setRequired(!dbField.isNullable());
		efBean.setMinLen(dbField.isNullable()?0:1);
		efBean.setMaxLen(dbField.getLength());
		efBean.setPk(dbField.isPk());
		
		return efBean;
	}

	private static String getType(DBField dbField,String tableName)
	{
		String result = db2JavaMap.get(dbField.getType());
		if(result==null)
		{
			//System.out.println(tableName+":"+dbField.getName()+"未找到对应的java类型映射，被映射为String");
			return "String";
		}
		return result;
	}
}
