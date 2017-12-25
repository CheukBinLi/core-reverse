package core.reverse.codegen.bean;

import java.util.LinkedList;
import java.util.List;

public class ClassBean
{
	private String module;
	private String mean;
	private Class<?> c;
	private String name;
	private String simpleName;
	private String packageName;
	private String dtoPackage;
	private String table;
	private List<FieldBean> fields = new LinkedList<FieldBean>();
	private List<FieldBean> pks = new LinkedList<FieldBean>();
	private int pkSize;
	private List<JoinBean> joins = new LinkedList<JoinBean>();
	private int joinSize;
	
	public ClassBean(Class<?> c,String mean)
	{
		super();
		this.mean = mean;
		this.c = c;
		this.name = c.getName();
		this.simpleName = c.getSimpleName();
		this.table = this.simpleName.toUpperCase();
	}

	public void addField(FieldBean field)
	{
		if(field.isPK())
		{
			pks.add(field);
			pkSize=pks.size();
		}
		fields.add(field);
	}
	
	public void addJoin(JoinBean joinBean)
	{
		joins.add(joinBean);
		this.joinSize = joins.size();
	}

	public Class<?> getC()
	{
		return c;
	}

	public void setC(Class<?> c)
	{
		this.c = c;
	}

	public String getSimpleName()
	{
		return simpleName;
	}

	public void setSimpleName(String simpleName)
	{
		this.simpleName = simpleName;
	}

	public List<FieldBean> getFields()
	{
		return fields;
	}

	public void setFields(List<FieldBean> fields)
	{
		this.fields = fields;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<FieldBean> getPks()
	{
		return pks;
	}

	public void setPks(List<FieldBean> pks)
	{
		this.pks = pks;
	}

	public String getTable()
	{
		return table;
	}

	public void setTable(String table)
	{
		this.table = table;
	}

	public int getPkSize()
	{
		return pkSize;
	}

	public void setPkSize(int pkSize)
	{
		this.pkSize = pkSize;
	}

	public List<JoinBean> getJoins()
	{
		return joins;
	}

	public void setJoins(List<JoinBean> joins)
	{
		this.joins = joins;
	}

	public int getJoinSize()
	{
		return joinSize;
	}

	public void setJoinSize(int joinSize)
	{
		this.joinSize = joinSize;
	}

	public String getPackageName()
	{
		return packageName;
	}

	public void setPackageName(String packageName)
	{
		this.packageName = packageName;
	}

	public String getDtoPackage()
	{
		return dtoPackage;
	}

	public void setDtoPackage(String dtoPackage)
	{
		this.dtoPackage = dtoPackage;
	}
	
	public String getMean()
	{
		return mean;
	}

	public void setMean(String mean)
	{
		this.mean = mean;
	}

	public String getModule()
	{
		return module;
	}

	public void setModule(String module)
	{
		this.module = module;
	}

	@Override
	public String toString()
	{
		return "ClassBean [c=" + c + ", name=" + name + ", simpleName=" + simpleName + ", fields=" + fields + ", pks=" + pks + "]";
	}
}
