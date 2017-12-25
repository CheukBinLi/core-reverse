package core.reverse.codegen.bean;

import java.util.LinkedList;
import java.util.List;

public class ClassDtoBean
{
	private String dtoName;
	private String dtoPackage;
	List<FieldBean> fields = new LinkedList<FieldBean>();
	int fieldSize;
	
	public ClassDtoBean(ClassBean classBean)
	{
		this.dtoName = classBean.getSimpleName()+"Dto";
		this.dtoPackage = classBean.getDtoPackage();
		
		List<FieldBean> fieldBeans = classBean.getFields();
		for(FieldBean fieldBean:fieldBeans)
		{
			if(fieldBean.isList())
			{
				addField(fieldBean);
			}
		}
		List<JoinBean> joinBeans = classBean.getJoins();
		for(JoinBean joinBean:joinBeans)
		{
			for(JoinFieldBean joinFieldBean:joinBean.getFields())
			{
				try
				{
					FieldBean fieldBean = new FieldBean(joinFieldBean.getFieldClass(), joinFieldBean.getSelectField());
					addField(fieldBean);
				}
				catch (Exception e)
				{
				}
			}
		}
	}
	
	public void addField(FieldBean fieldBean)
	{
		this.fields.add(fieldBean);
		this.fieldSize = this.fields.size();
	}

	public List<FieldBean> getFields()
	{
		return fields;
	}

	public void setFields(List<FieldBean> fields)
	{
		this.fields = fields;
	}

	public int getFieldSize()
	{
		return fieldSize;
	}

	public void setFieldSize(int fieldSize)
	{
		this.fieldSize = fieldSize;
	}

	public String getDtoName()
	{
		return dtoName;
	}

	public void setDtoName(String dtoName)
	{
		this.dtoName = dtoName;
	}

	public String getDtoPackage()
	{
		return dtoPackage;
	}

	public void setDtoPackage(String dtoPackage)
	{
		this.dtoPackage = dtoPackage;
	}
}
