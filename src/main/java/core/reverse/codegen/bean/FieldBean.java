package core.reverse.codegen.bean;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import core.reverse.codegen.annotation.AutoIncrement;
import core.reverse.codegen.annotation.FieldDesc;
import core.reverse.codegen.annotation.InputType;
import core.reverse.codegen.annotation.NoList;
import core.reverse.codegen.annotation.PK;

public class FieldBean
{
	private Class<?> type;
	private String typeSimpleName;
	private String fieldName;
	private String columnName;
	private boolean isPK;
	private boolean isJoin;
	private boolean isAutoIncrement;
	private static Map<Class<?>,String> typeMap = new HashMap<Class<?>,String>();
	
	//以下用于产生界面
	private String inputType;
	
	//for list
	private boolean query;
	private boolean list=true;
	
	//for lable
	private String meaning = null;
	
	//for add&update
	private boolean add;
	private boolean update;
	private String htmlClass;
	private int minLen = 0;
	private int maxLen = 128;
	private boolean required = false;
	private String validType;
	private String format;
	
	static{
		typeMap.put(String.class, "String");
		typeMap.put(Character.class, "char");
		typeMap.put(Byte.class, "byte");
		typeMap.put(Short.class, "short");
		typeMap.put(Integer.class, "int");
		typeMap.put(Boolean.class, "boolean");
		typeMap.put(Double.class, "double");
		typeMap.put(Float.class, "float");
		typeMap.put(Date.class, "Date");
	}
	
	public FieldBean(Field field) throws Exception
	{
		this.type = field.getType();
		this.typeSimpleName = getTypeSimpleNameByType(type);
		if(typeSimpleName==null){
			throw new Exception(this.type.toString()+" 找不到匹配的类型");
		}
		this.fieldName = field.getName();
		if(field.isAnnotationPresent(PK.class))
		{
			this.isPK = true;
		}
		if(field.isAnnotationPresent(NoList.class))
		{
			this.list = false;
		}
		if(field.isAnnotationPresent(AutoIncrement.class))
		{
			this.isAutoIncrement = true;
		}
		if(field.isAnnotationPresent(FieldDesc.class))
		{
			initDesc(field);
		}
	}

	private void initDesc(Field field)
	{
		FieldDesc desc = field.getAnnotation(FieldDesc.class);
		InputType inputType = desc.inputType();
		InputHandler handler = inputHandlers.get(inputType);
		if(handler!=null)
		{
			handler.process(this, field, desc);
		}
	}

	private String getTypeSimpleNameByType(Class<?> type) {
		String result = typeMap.get(type);
		if(result!=null&&result.trim().length()>0)
		{
			return result;
		}
		return type.getSimpleName();
	}

	public FieldBean(Class<?> type, String fieldName) throws Exception
	{
		super();
		this.type = type;
		this.typeSimpleName = typeMap.get(type);
		if(typeSimpleName==null){
			throw new Exception(this.type.toString()+" 找不到匹配的类型");
		}
		this.fieldName = fieldName;
	}

	public Class<?> getType()
	{
		return type;
	}

	public void setType(Class<?> type)
	{
		this.type = type;
	}

	public String getTypeSimpleName()
	{
		return typeSimpleName;
	}

	public void setTypeSimpleName(String typeSimpleName)
	{
		this.typeSimpleName = typeSimpleName;
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}

	public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean isPK()
	{
		return isPK;
	}

	public void setPK(boolean isPK)
	{
		this.isPK = isPK;
	}

	public boolean isList()
	{
		return list;
	}

	public void setList(boolean list)
	{
		this.list = list;
	}

	public boolean isJoin()
	{
		return isJoin;
	}

	public void setJoin(boolean isJoin)
	{
		this.isJoin = isJoin;
	}


	public int getMinLen()
	{
		return minLen;
	}

	public void setMinLen(int minLen)
	{
		this.minLen = minLen;
	}

	public int getMaxLen()
	{
		return maxLen;
	}

	public void setMaxLen(int maxLen)
	{
		this.maxLen = maxLen;
	}

	public String getMeaning()
	{
		return meaning;
	}

	public void setMeaning(String meaning)
	{
		this.meaning = meaning;
	}

	public boolean isRequired()
	{
		return required;
	}

	public void setRequired(boolean required)
	{
		this.required = required;
	}

	public boolean isQuery() {
		return query;
	}

	public void setQuery(boolean query) {
		this.query = query;
	}

	public boolean isAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public String getHtmlClass()
	{
		return htmlClass;
	}

	public void setHtmlClass(String htmlClass)
	{
		this.htmlClass = htmlClass;
	}

	public String getValidType()
	{
		return validType;
	}

	public void setValidType(String validType)
	{
		this.validType = validType;
	}

	public String getFormat()
	{
		return format;
	}

	public void setFormat(String format)
	{
		this.format = format;
	}

	public String getInputType()
	{
		return inputType;
	}

	public void setInputType(String inputType)
	{
		this.inputType = inputType;
	}

	public boolean isAutoIncrement()
	{
		return isAutoIncrement;
	}

	public void setAutoIncrement(boolean isAutoIncrement)
	{
		this.isAutoIncrement = isAutoIncrement;
	}

	@Override
	public String toString()
	{
		return "FieldBean [type=" + type + ", typeSimpleName=" + typeSimpleName + ", fieldName=" + fieldName + ", isPK=" + isPK + "]";
	}
	
	private static Map<InputType,InputHandler> inputHandlers = new HashMap<InputType, InputHandler>();
	static
	{
		InputHandler handler = new TextHandler();
		inputHandlers.put(handler.key(), handler);
		handler = new EmailHandler();
		inputHandlers.put(handler.key(), handler);
		handler = new IndentityHandler();
		inputHandlers.put(handler.key(), handler);
		handler = new PIntHandler();
		inputHandlers.put(handler.key(), handler);
		handler = new TelHandler();
		inputHandlers.put(handler.key(), handler);
		handler = new MobileHandler();
		inputHandlers.put(handler.key(), handler);
		handler = new DateHandler();
		inputHandlers.put(handler.key(), handler);
		handler = new DateTimeHandler();
		inputHandlers.put(handler.key(), handler);
		handler = new ZipHandler();
		inputHandlers.put(handler.key(), handler);
	}
}

abstract class InputHandler
{
	abstract InputType key();
	
	protected void process(FieldBean bean,Field field,FieldDesc desc)
	{
		bean.setInputType(desc.inputType().toString());
		bean.setMinLen(desc.minLen());
		bean.setMaxLen(desc.maxLen());
		bean.setMeaning(desc.mean()==null?bean.getFieldName():desc.mean());
		bean.setRequired(desc.required());
		bean.setQuery(desc.query());
		bean.setAdd(desc.add());
		bean.setUpdate(desc.update());
		bean.setHtmlClass("input easyui-validatebox");
	}
}

class TextHandler extends InputHandler
{
	@Override
	public InputType key()
	{
		return InputType.text;
	}

	@Override
	public void process(FieldBean bean, Field field, FieldDesc desc)
	{
		super.process(bean, field, desc);
		bean.setValidType(String.format("validType:'text[%s,%s]'", bean.getMinLen(),bean.getMaxLen()));
	}
}

class EmailHandler extends InputHandler
{
	@Override
	public InputType key()
	{
		return InputType.email;
	}

	@Override
	public void process(FieldBean bean, Field field, FieldDesc desc)
	{
		super.process(bean, field, desc);
		bean.setValidType("validType:'email'");
	}
}

class IndentityHandler extends InputHandler
{
	@Override
	public InputType key()
	{
		return InputType.identity;
	}

	@Override
	public void process(FieldBean bean, Field field, FieldDesc desc)
	{
		super.process(bean, field, desc);
		bean.setValidType("validType:'identity'");
	}
}

class PIntHandler extends InputHandler
{
	@Override
	public InputType key()
	{
		return InputType.pint;
	}

	@Override
	public void process(FieldBean bean, Field field, FieldDesc desc)
	{
		super.process(bean, field, desc);
		bean.setHtmlClass("input easyui-numberbox");
		bean.setFormat("precision:2,groupSeparator:','");
	}
}

class TelHandler extends InputHandler
{
	@Override
	public InputType key()
	{
		return InputType.tel;
	}

	@Override
	public void process(FieldBean bean, Field field, FieldDesc desc)
	{
		super.process(bean, field, desc);
		bean.setValidType("validType:'telephone'");
	}
}

class MobileHandler extends InputHandler
{
	@Override
	public InputType key()
	{
		return InputType.mobile;
	}

	@Override
	public void process(FieldBean bean, Field field, FieldDesc desc)
	{
		super.process(bean, field, desc);
		bean.setValidType("validType:'mobile'");
	}
}

class DateHandler extends InputHandler
{
	@Override
	public InputType key()
	{
		return InputType.date;
	}

	@Override
	public void process(FieldBean bean, Field field, FieldDesc desc)
	{
		super.process(bean, field, desc);
		bean.setHtmlClass("input easyui-datebox");
	}
}

class DateTimeHandler extends InputHandler
{
	@Override
	public InputType key()
	{
		return InputType.datetime;
	}

	@Override
	public void process(FieldBean bean, Field field, FieldDesc desc)
	{
		super.process(bean, field, desc);
		bean.setHtmlClass("input easyui-datetimebox");
	}
}

class ZipHandler extends InputHandler
{
	@Override
	public InputType key()
	{
		return InputType.zip;
	}

	@Override
	public void process(FieldBean bean, Field field, FieldDesc desc)
	{
		super.process(bean, field, desc);
		bean.setValidType("validType:'zip'");
	}
}



