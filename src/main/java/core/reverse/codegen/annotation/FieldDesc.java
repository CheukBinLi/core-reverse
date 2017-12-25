package core.reverse.codegen.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) 
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldDesc
{
	/**
	 * 是否必填
	 * @return
	 */
	boolean required() default false;
	/**
	 * 最小长度
	 * @return
	 */
	int minLen() default 0;
	/**
	 * 最大长度
	 * @return
	 */
	int maxLen() default 128;
	/**
	 * 中文名称
	 * @return
	 */
	String mean();
	/**
	 * 输入类型
	 * @return
	 */
	InputType inputType() default InputType.text;
	/**
	 * 是否为查询字段
	 * @return
	 */
	boolean query() default false;
	/**
	 * 新增界面是否可输入
	 * @return
	 */
	boolean add() default true;
	/**
	 * 编辑界面是否可编辑
	 * @return
	 */
	boolean update() default true;
}
