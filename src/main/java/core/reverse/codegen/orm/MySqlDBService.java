package core.reverse.codegen.orm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqlDBService extends DBService {
    @Override
    public String[] getTables() {
        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema='boomhopeoa' AND table_type='base table' AND table_name NOT LIKE 'act_%'";
        List<Object[]> list = this.getDataSource().query(sql);
        if (list != null && list.size() > 0) {
            String[] arr = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                arr[i] = list.get(i)[0].toString();
            }
            return arr;
        }
        return null;
    }

    @Override
    public List<String> getTableList() {
        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema='boomhopeoa' AND table_type='base table' AND table_name NOT LIKE 'act_%'";
        List<Object[]> list = this.getDataSource().query(sql);
        if (list != null && list.size() > 0) {
            List<String> arr = new ArrayList<String>(list.size());// new
                                                                  // String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                arr.add(list.get(i)[0].toString());
            }
            return arr;
        }
        return null;
    }

    @Override
    public DBField[] getFiels(String tableName) {
        // String[] pks = getPKFiels(tableName);
        String sql = String.format("SELECT column_name,data_type,character_maximum_length,is_nullable,column_key,extra,column_comment FROM Information_schema.columns WHERE table_Name = '%s'", tableName);
        // String sql = String.format("SELECT
        // column_name,data_type,character_maximum_length,is_nullable,column_key,extra
        // FROM Information_schema.columns WHERE table_schema='boomhopeoa' AND
        // table_Name = '%s'",tableName);
        // String sql = String.format("select
        // column_name,data_type,data_length,nullable from user_tab_columns
        // where TABLE_NAME='%s'", tableName.toUpperCase());
        List<Object[]> list = this.getDataSource().query(sql);
        if (list != null && list.size() > 0) {
            DBField[] dbFields = new DBField[list.size()];
            for (int i = 0; i < list.size(); i++) {
                Object[] objs = list.get(i);
                dbFields[i] = new DBField();
                dbFields[i].setName(objs[0].toString());
                dbFields[i].setType(objs[1].toString());
                dbFields[i].setComment(objs[6].toString());
                dbFields[i].setFieldName(converyField(objs[0].toString()));
                try {
                    dbFields[i].setLength(Integer.parseInt(objs[2].toString()));
                } catch (Exception e) {
                    dbFields[i].setLength(0);
                }
                dbFields[i].setNullable("YES".equals(objs[3].toString()));
                dbFields[i].setPk("PRI".equals(objs[4].toString()));
                if ("auto_increment".equals(objs[5].toString())) {
                    dbFields[i].setAutoIncrement(true);
                }
            }
            return dbFields;
        }
        return null;
    }

    @Override
    public String[] getPKFiels(String tableName) {
        String sql = String.format("select a.column_name from user_cons_columns a,user_constraints b where a.constraint_name=b.constraint_name and b.constraint_type='P' and a.table_name='%s'", tableName);
        List<Object[]> list = this.getDataSource().query(sql);
        if (list != null && list.size() > 0) {
            String[] pks = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                Object[] objs = list.get(i);
                pks[i] = objs[0].toString();
            }
            return pks;
        }
        return null;
    }

    static Map<String, String> db2JavaMap = new HashMap<String, String>();

    static {
        db2JavaMap.put("varchar", "String");
        db2JavaMap.put("date", "Date");
        db2JavaMap.put("datetime", "Date");
        db2JavaMap.put("number", "Integer");
        db2JavaMap.put("decimal", "BigDecimal");
        db2JavaMap.put("bigint", "Long");
        db2JavaMap.put("longtext", "Byte[]");
        db2JavaMap.put("blob", "String");
        db2JavaMap.put("float", "Float");
    }

    @Override
    public EntityFieldBean toEntityFieldBean(DBField dbField, String tableName) {
        EntityFieldBean efBean = new EntityFieldBean();

        efBean.setName(dbField.getName().toLowerCase());
        efBean.setType(getType(dbField, tableName));
        efBean.setRequired(!dbField.isNullable());
        efBean.setMinLen(dbField.isNullable() ? 0 : 1);
        efBean.setMaxLen(dbField.getLength());
        efBean.setPk(dbField.isPk());
        efBean.setAutoIncrement(dbField.isAutoIncrement());
        efBean.setFieldName(dbField.getFieldName());
        efBean.setComment(dbField.getComment());

        return efBean;
    }

    private static String getType(DBField dbField, String tableName) {
        String result = db2JavaMap.get(dbField.getType().toLowerCase());
        if (result == null) {
            // System.out.println(tableName+":"+dbField.getName()+"未找到对应的java类型映射，被映射为String");
            return "String";
        }
        return result;
    }

    private static String converyField(String name) {
        String[] str = name.toLowerCase().split("_");
        StringBuilder sb = new StringBuilder(str[0]);
        for (int i = 1, len = str.length; i < len; i++) {
            char[] cs = str[i].toCharArray();
            cs[0]=Character.toUpperCase(cs[0]);
            sb.append(String.valueOf(cs));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String[] a = "String_a_b_c".split("_");
        for (String aa : a)
            System.out.println(aa);
    }
}
