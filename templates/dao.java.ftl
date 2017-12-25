package ${context.packageName?substring(0,context.packageName?last_index_of("."))}.dao;

import java.util.List;
import java.util.Map;

import com.midea.jiebao.jframework.core.persistent.mybatisplus.mapper.BaseMapper;
import com.midea.jiebao.jframework.core.persistent.mybatisplus.plugins.Page;
import ${context.packageName}.${context.className};


public interface ${context.className}Dao extends BaseMapper<${context.className}>
{
    /**
     * @param queryParam 搜索条件	
     * @return
     */
	List<${context.className}> query(Map<String,Object> param);
	
	/**
     * @param queryParam 搜索条件
     * @return
     */
	int count(Map<String,Object> param);
	
    /**
     * @param queryParam 搜索条件
		pageNumber	页码	pageNumber	
		pageSize	每页记录数	pageSize	
     * @return
     */
	List<${context.className}> pageQuery(Page<Map<String, Object>> page, Map<String, Object> queryParam)throws Throwable;
}