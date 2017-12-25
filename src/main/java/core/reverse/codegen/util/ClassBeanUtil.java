package core.reverse.codegen.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import core.reverse.codegen.annotation.Join;
import core.reverse.codegen.annotation.JoinField;
import core.reverse.codegen.bean.ClassBean;
import core.reverse.codegen.bean.FieldBean;
import core.reverse.codegen.bean.JoinBean;
import core.reverse.codegen.bean.JoinFieldBean;

public class ClassBeanUtil
{
	public static ClassBean toClassBean(Class<?> c,String mean)
	{
		ClassBean cBean = new ClassBean(c,mean);
		cBean.setPackageName(c.getPackage().getName());
		initParentClassFields(cBean, c);
		Field[] fields = c.getDeclaredFields();
		if (fields != null && fields.length > 0)
		{
			for (Field field : fields)
			{
				addField(cBean, field);
			}
		}
		if(cBean.getJoinSize()>0)
		{
			cBean.setDtoPackage(cBean.getPackageName().replace("domain", "dto"));
		}
		return cBean;
	}
	
	public static void initParentClassFields(ClassBean cBean,Class<?> clz){
		if(!clz.getSuperclass().equals(Object.class))
		{
			Class<?> superClz = clz.getSuperclass();
			initParentClassFields(cBean,superClz);
			Field[] fields = superClz.getDeclaredFields();
			if (fields != null && fields.length > 0)
			{
				for (Field field : fields)
				{
					addField(cBean, field);
				}
			}
		}
	}

	protected static void addField(ClassBean cBean, Field field)
	{
		int m = field.getModifiers();
		if((m&Modifier.STATIC)==0)
		{
			try
			{
				FieldBean fBean = new FieldBean(field);
				cBean.addField(fBean);
				if(field.isAnnotationPresent(Join.class))
				{
					initJoins(cBean, field);
				}
			}
			catch (Exception e)
			{
			}
		}
	}
	
	private static void initJoins(ClassBean context, Field field)
	{
		Join join = field.getAnnotation(Join.class);
		Class<?> joinClass = join.joinClass();
		String joinClassPK = join.joinClassPK();
		JoinField[] joinFields = join.fields();
		
		JoinBean joinBean = new JoinBean(joinClass, joinClassPK,field.getName());
		for(int i=0;i<joinFields.length;i++)
		{
			JoinField joinField = joinFields[i];
			String selectField = joinField.selectField();
			Field selectF;
			try
			{
				selectF = joinClass.getDeclaredField(selectField);
			}
			catch (Exception e)
			{
				continue;
			}
			JoinFieldBean joinFieldBean = new JoinFieldBean(selectF.getType(), selectField, joinField.showField());
			joinBean.addField(joinFieldBean);
		}
		context.addJoin(joinBean);
	}
}
