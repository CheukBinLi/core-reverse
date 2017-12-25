package core.reverse.codegen.bean;

import java.util.LinkedList;
import java.util.List;

public class JoinBean
{
	private Class<?> joinClass;
	private String joinTableName;
	private String joinClassPK;
	private String fk;
	private List<JoinFieldBean> fields = new LinkedList<JoinFieldBean>();
	private int fieldsSize;

	public JoinBean(Class<?> joinClass, String joinClassPK,String fk)
	{
		super();
		this.joinClass = joinClass;
		this.joinTableName = joinClass.getSimpleName().toUpperCase();
		this.fk = fk;
		this.joinClassPK = joinClassPK;
	}
	
	public void addField(JoinFieldBean joinFieldBean)
	{
		this.fields.add(joinFieldBean);
		this.fieldsSize = this.fields.size();
	}
	
	public Class<?> getJoinClass()
	{
		return joinClass;
	}
	public void setJoinClass(Class<?> joinClass)
	{
		this.joinClass = joinClass;
	}
	public String getJoinTableName()
	{
		return joinTableName;
	}
	public void setJoinTableName(String joinTableName)
	{
		this.joinTableName = joinTableName;
	}
	public String getJoinClassPK()
	{
		return joinClassPK;
	}
	public void setJoinClassPK(String joinClassPK)
	{
		this.joinClassPK = joinClassPK;
	}
	public List<JoinFieldBean> getFields()
	{
		return fields;
	}
	public void setFields(List<JoinFieldBean> fields)
	{
		this.fields = fields;
	}
	public int getFieldsSize()
	{
		return fieldsSize;
	}

	public void setFieldsSize(int fieldsSize)
	{
		this.fieldsSize = fieldsSize;
	}

	public String getFk()
	{
		return fk;
	}

	public void setFk(String fk)
	{
		this.fk = fk;
	}
}
