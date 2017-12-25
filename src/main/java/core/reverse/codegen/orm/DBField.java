package core.reverse.codegen.orm;

public class DBField
{
	private boolean pk;
	private String name;
	private String fieldName;
	private String comment;
	private String type;
	private boolean nullable;
	private int length;
	private boolean autoIncrement;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public boolean isNullable()
	{
		return nullable;
	}

	public void setNullable(boolean nullable)
	{
		this.nullable = nullable;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public boolean isPk()
	{
		return pk;
	}

	public void setPk(boolean pk)
	{
		this.pk = pk;
	}

	public boolean isAutoIncrement()
	{
		return autoIncrement;
	}

	public void setAutoIncrement(boolean autoIncrement)
	{
		this.autoIncrement = autoIncrement;
	}
}
