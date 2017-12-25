package core.reverse.codegen.orm;

import java.util.List;

public abstract class DBService
{
	private DataSource dataSource;
	public abstract String[] getTables();
	public abstract List<String> getTableList();
	public abstract DBField[] getFiels(String tableName);
	public abstract String[] getPKFiels(String tableName);
	public abstract EntityFieldBean toEntityFieldBean(DBField dbField, String tableName);
	public DataSource getDataSource()
	{
		return dataSource;
	}
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
}
