package core.reverse.codegen.bean;

public class JoinFieldBean
{
	private Class<?> fieldClass;
	private String fieldClassName;
	private String selectField;
	private String showField;
	
	public JoinFieldBean(Class<?> fieldClass, String selectField, String showField)
	{
		super();
		this.fieldClass = fieldClass;
		this.fieldClassName = fieldClass.getSimpleName();
		this.selectField = selectField;
		this.showField = showField;
	}

	public Class<?> getFieldClass()
	{
		return fieldClass;
	}

	public void setFieldClass(Class<?> fieldClass)
	{
		this.fieldClass = fieldClass;
	}

	public String getFieldClassName()
	{
		return fieldClassName;
	}

	public void setFieldClassName(String fieldClassName)
	{
		this.fieldClassName = fieldClassName;
	}

	public String getSelectField()
	{
		return selectField;
	}

	public void setSelectField(String selectField)
	{
		this.selectField = selectField;
	}

	public String getShowField()
	{
		return showField;
	}

	public void setShowField(String showField)
	{
		this.showField = showField;
	}
}
