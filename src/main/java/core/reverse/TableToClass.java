package core.reverse;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import core.reverse.codegen.orm.DBField;
import core.reverse.codegen.orm.DBService;
import core.reverse.codegen.orm.DataSource;
import core.reverse.codegen.orm.EntityBean;
import core.reverse.codegen.orm.EntityFieldBean;
import core.reverse.codegen.orm.MySqlDBService;
import core.reverse.codegen.orm.OracleDBService;
import core.reverse.codegen.util.FreemarkerUtil;

/***
 * 
 * @Title: boomhope-tms-reverse
 * @Description: 表返向生成
 * @Company: 
 * @Email: 20796698@qq.com
 * @author cheuk.bin.li
 * @date 2017年12月25日  下午2:19:25
 *
 */
public class TableToClass {
	public static void main(String[] args) {
		// genDB();
		// genTableEntity("Org_Role");
		// genTableEntity("Org_ StaffRole");
		// genTableEntity("Org_ RoleRes");
		String packageName = "com.midea.smart.buy.hotel.dao.model";
		genTableEntity("t_vendor_config", "VendorConfig", packageName);
		genTableEntity("t_consumer_voucher", "ConsumerVoucher", packageName);
		genTableEntity("t_contact_Info", "ContactInfo", packageName);
		genTableEntity("t_hotel_passenger", "HotelPassenger", packageName);
		genTableEntity("t_hotel_order", "HotelOrder", packageName);
		genTableEntity("t_transaction_message", "TransactionMessage", packageName);
		genTableEntity("v_enterprise_employee", "EnterpriseEmployee", packageName);
		genTableEntity("v_enterprise_tenant", "EnterpriseTenant", packageName);
		genTableEntity("v_key_management", "KeyManagement", packageName);

	}

	private static void genTableEntity(String tableName) {
		genTableEntity(tableName, null, null);
	}

	private static void genTableEntity(String tableName, String className, String packageName) {
		// DBService dbService = new OracleDBService();
		DBService dbService = new MySqlDBService();
		dbService.setDataSource(new DataSource());
		DBField[] fields = dbService.getFiels(tableName);
		if (fields != null && fields.length > 0) {
			EntityBean entityBean = new EntityBean();
			entityBean.setEntityName(tableName.toLowerCase());
			entityBean.setPackageName(packageName);
			entityBean.setClassName(null == className ? entityBean.getEntityName() : className);
			for (DBField dbField : fields) {
				EntityFieldBean entityFieldBean = dbService.toEntityFieldBean(dbField, tableName);
				entityBean.addEntityFieldBean(entityFieldBean);
			}
			genEntity(entityBean);
			genModelMapperXML(entityBean);
			genDao(entityBean);
			genService(entityBean);
			genController(entityBean);
		}
	}

	public static void genModelMapperXML(EntityBean entityBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("context", entityBean);
		FreemarkerUtil.buildTemplate(map,File.separator + "mapperxml", entityBean.getClassName() + "Dao-mapper.xml", "modelMapper.xml.ftl");
	}
	public static void genDao(EntityBean entityBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("context", entityBean);
		FreemarkerUtil.buildTemplate(map,File.separator + "dao", entityBean.getClassName() + "Dao.java", "dao.java.ftl");
	}
	public static void genService(EntityBean entityBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("context", entityBean);
		FreemarkerUtil.buildTemplate(map,File.separator + "service", entityBean.getClassName() + "Service.java", "service.java.ftl");
		FreemarkerUtil.buildTemplate(map,File.separator + "service/impl", entityBean.getClassName() + "ServiceImpl.java", "serviceImpl.java.ftl");
	}
	public static void genController(EntityBean entityBean) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("context", entityBean);
		FreemarkerUtil.buildTemplate(map,File.separator + "contro", entityBean.getClassName() + "Controller.java", "modelController.java.ftl");
	}

	private static void genDB() {
		DBService dbService = new OracleDBService();
		dbService.setDataSource(new DataSource());
		String[] tables = dbService.getTables();
		int count = 0;
		if (tables != null && tables.length > 0) {
			for (String tableName : tables) {
				DBField[] fields = dbService.getFiels(tableName);
				if (fields != null && fields.length > 0) {
					EntityBean entityBean = new EntityBean();
					entityBean.setEntityName(tableName.toLowerCase());
					for (DBField dbField : fields) {
						EntityFieldBean entityFieldBean = dbService.toEntityFieldBean(dbField, tableName);
						entityBean.addEntityFieldBean(entityFieldBean);
					}
					genEntity(entityBean);
					count++;
				}
			}
		}
		// System.out.println("共生成实体类文件"+count+"个");
	}

	private static void genEntity(EntityBean bean) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("context", bean);
		FreemarkerUtil.buildTemplate(map, "model", bean.getClassName() + ".java", "model.java.ftl");
	}
}
