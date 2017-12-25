package core.reverse.codegen.orm;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import cn.com.boomhope.tms.codegen.util.FreemarkerUtil;

public class TableToEntity
{
	public static void main(String[] args)
	{
		// genDB();
		// genTableEntity("BATCHINDEXSC");
		// genTableEntity("BATCHINDEXBK");
		// genTableEntity("BATCHINDEXHK");
		// genTableEntity("EXAMINEINDICATOR");
		// genTableEntity("BANKOPENACCTBATCH");
		// genTableEntity("UnconfirmedApplyInfo");
		// genTableEntity("ApplyScanFile");
		// genTableEntity("ApplyInfo");
		// genTableEntity("CardInfo");
		// genTableEntity("orginfo");
		// genTableEntity("cardnumres");
		// genTableEntity("PubDrawBatch");
		// genTableEntity("AppInfo");
		// genTableEntity("ApplyChannel");
		// genTableEntity("UncommonWord");
		// genTableEntity("t1");genTableEntity("t2");
		// genTableEntity("MAKECARDINFO");
		// genTableEntity("ABNORMALISSUEINFO");
		// genTableEntity("AbnormalAssignRec");
//		genTableEntity("BmProcessInstance");
//		genTableEntity("BmProcessTask");
//		genTableEntity("BmProcTaskRec");
//		genTableEntity("BmProcessHis");
//		genTableEntity("BmTaskHis");
//		genTableEntity("BmProcTaskRecHis");
//		genTableEntity("PolictyInfo");
//		genTableEntity("statapply");
//		genTableEntity("statstore");
		//genTableEntity("MsgCode");
		//genTableEntity("ChannelApplyDetail");
		//genTableEntity("AbnormalInfo");
		//genTableEntity("AbnormalDetail");
		//genTableEntity("AbnormalCard");
		//genTableEntity("PHOTOPROCMAKEUPBATCH");
		//2014-05-09
		//genTableEntity("recovercard");
		//genTableEntity("recoverbatch");
		genTableEntity("OrgInfo");
	}

	private static void genTableEntity(String tableName)
	{
		DBService dbService = new MySqlDBService();
		dbService.setDataSource(new DataSource());
		DBField[] fields = dbService.getFiels(tableName);
		if (fields != null && fields.length > 0)
		{
			EntityBean entityBean = new EntityBean();
			entityBean.setEntityName(tableName.toLowerCase());
			for (DBField dbField : fields)
			{
				EntityFieldBean entityFieldBean = dbService.toEntityFieldBean(dbField, tableName);
				entityBean.addEntityFieldBean(entityFieldBean);
			}
			genEntity(entityBean);
		}
	}

	private static void genDB()
	{
		DBService dbService = new OracleDBService();
		dbService.setDataSource(new DataSource());
		String[] tables = dbService.getTables();
		int count = 0;
		if (tables != null && tables.length > 0)
		{
			for (String tableName : tables)
			{
				DBField[] fields = dbService.getFiels(tableName);
				if (fields != null && fields.length > 0)
				{
					EntityBean entityBean = new EntityBean();
					entityBean.setEntityName(tableName.toLowerCase());
					for (DBField dbField : fields)
					{
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

	private static void genEntity(EntityBean bean)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("context", bean);
		FreemarkerUtil.buildTemplate(map, "model", bean.getEntityName() + ".java", "model.java.ftl");
	}
}
