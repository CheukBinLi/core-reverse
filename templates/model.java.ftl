package ${context.packageName};

import java.io.Serializable;
import java.util.Date;

import com.midea.jiebao.jframework.core.base.BaseModel;
import com.midea.jiebao.jframework.core.persistent.mybatisplus.annotations.TableField;
import com.midea.jiebao.jframework.core.persistent.mybatisplus.annotations.TableId;
import com.midea.jiebao.jframework.core.persistent.mybatisplus.annotations.TableName;

@TableName("${context.entityName}")
public class ${context.className} extends BaseModel
{
	<#list context.fields as field>
	/**${field.comment!}*/
	@TableField("${field.name}")
	private ${field.type} ${field.fieldName};
	
	</#list>
	
	<#list context.fields as field>
	public ${field.type} get${field.fieldName?cap_first}() {
		return ${field.fieldName};
	}
	
	public ${context.className} set${field.fieldName?cap_first}(${field.type} ${field.fieldName}) {
		this.${field.fieldName} = ${field.fieldName};
		return this;
	}
		
	</#list>
	@Override
	public Serializable pkVal() {
		return getId();
	}
	
}