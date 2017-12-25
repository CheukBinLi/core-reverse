package core.reverse.codegen.testdomain;

import core.reverse.codegen.annotation.FieldDesc;
import core.reverse.codegen.annotation.InputType;
import core.reverse.codegen.annotation.PK;

public class Test1
{
	@PK
	@FieldDesc(mean = "文本字段1",required=true,inputType=InputType.text,minLen=1,maxLen=20)
	private String field1;
	@FieldDesc(mean = "文本字段2",required=false,inputType=InputType.text,minLen=1,maxLen=20)
	private String field2;
	@FieldDesc(mean = "电子邮箱",required=false,inputType=InputType.email)
	private String field3;
	@FieldDesc(mean = "公民身份号码",required=false,inputType=InputType.identity)
	private String field4;
	@FieldDesc(mean = "数字",required=false,inputType=InputType.pint)
	private String field5;
	@FieldDesc(mean = "固定电话",required=false,inputType=InputType.tel)
	private String field6;
	@FieldDesc(mean = "手机号码",required=false,inputType=InputType.mobile)
	private String field7;
	@FieldDesc(mean = "日期",required=false,inputType=InputType.date)
	private String field8;
	@FieldDesc(mean = "日期时间",required=false,inputType=InputType.datetime)
	private String field9;
	@FieldDesc(mean = "邮政编码",required=false,inputType=InputType.zip)
	private String field10;
}
