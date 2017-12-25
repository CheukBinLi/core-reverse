package core.reverse.codegen.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Join
{
	Class<?> joinClass();
	String joinClassPK();
	JoinField[] fields();
}
