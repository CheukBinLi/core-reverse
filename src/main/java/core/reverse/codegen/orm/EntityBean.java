package core.reverse.codegen.orm;

import java.util.LinkedList;
import java.util.List;

public class EntityBean {
	private String entityName;
	private String className;
	private String packageName;
	private List<EntityFieldBean> fields = new LinkedList<EntityFieldBean>();

	public void addEntityFieldBean(EntityFieldBean entityFieldBean) {
		fields.add(entityFieldBean);
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<EntityFieldBean> getFields() {
		return fields;
	}

	public void setFields(List<EntityFieldBean> fields) {
		this.fields = fields;
	}
}
