package ${context.className};

import java.util.List;
import java.util.Map;
import java.io.Serializable;

import ${context.packageName}.${context.className};

import com.midea.jiebao.jframework.core.persistent.mybatisplus.plugins.Page;


public interface ${context.className}Service {
	
	String getIdField();
	
    ${context.className} save(${context.className} data);
    
    Integer update(${context.className} data);
    
    Integer saveOrUpdate(Long tenantId, ${context.className} ${context.className?uncap_first});

    boolean batchSave(List<${context.className}> list);

	boolean delete(Serializable id);

    boolean delete(Long tenantId,Serializable id);

    ${context.className} find(Serializable id);
    
    ${context.className} find(Long tenantId,Serializable id);
    
    /**
     * @param tenantId 租户ID
     * @param queryParam 搜索条件
     * @return
     */
    List<${context.className}> query(Long tenantId,Map<String, Object> param);

    /**
     * @param tenantId 租户ID
     * @param queryParam 搜索条件
     * @param page 分页
		pageNumber	页码	pageNumber	
		pageSize	每页记录数	pageSize	
     * @return
     */
	Page<${context.className}> pageQuery(Long tenantId,Page<Map<String, Object>> page, Map<String, Object> queryParam)throws RuntimeException;
	
}
