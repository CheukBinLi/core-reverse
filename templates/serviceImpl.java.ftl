package ${context.className};

import java.util.List;
import java.util.Map;
import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.midea.jiebao.jframework.core.base.BaseService;
import com.midea.jiebao.jframework.core.persistent.mybatisplus.plugins.Page;
import com.midea.jiebao.jframework.core.persistent.mybatisplus.toolkit.CollectionUtils;
import com.midea.jiebao.jframework.core.util.DbOperationUtil;
import ${context.packageName}.${context.className};

@Service
public class ${context.className}ServiceImpl extends BaseService implements ${context.className}Service {

	private final static Logger logger = LoggerFactory.getLogger(${context.className}ServiceImpl.class);
    
    @Resource
	private ${context.className}Dao ${context.className?uncap_first}Dao;
	
	public String getIdField(){
    	return "id";
    }

    @Override
    public ${context.className} save(${context.className} ${context.className?uncap_first}) {
        ${context.className?uncap_first}Dao.insert(${context.className?uncap_first});
        return ${context.className?uncap_first};
    }
    
    @Override
    public boolean batchSave(List<${context.className}> list) {
        try {
            if (CollectionUtils.isNotEmpty(list)) {
                DbOperationUtil.batchSave(list,null,${context.className?uncap_first}Dao,getUser());
            } else {
                throw new RuntimeException("请至少传入一条记录！");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return true;
    }
    
    public Integer update(${context.className} ${context.className?uncap_first}) {
		return ${context.className?uncap_first}Dao.updateById(${context.className?uncap_first});
	}
    
    public Integer update(Long tenantId, ${context.className} ${context.className?uncap_first}) {
		${context.className} record = find(tenantId, ${context.className?uncap_first}.pkVal());
		if (null == record) {
			throw new RuntimeException("can't find the record by id is " + ${context.className?uncap_first}.pkVal());
		} else {
			return ${context.className?uncap_first}Dao.updateById(record);
		}
	}

	public Integer saveOrUpdate(Long tenantId, ${context.className} ${context.className?uncap_first}) {
		${context.className} record = null;
		if (null == ${context.className?uncap_first}.pkVal())
			record = find(tenantId, ${context.className?uncap_first}.pkVal());
		if (null == record) {
			return ${context.className?uncap_first}Dao.insert(${context.className?uncap_first});
		} else {
			return ${context.className?uncap_first}Dao.updateById(${context.className?uncap_first});
		}
	}
    
    @Override
    public boolean delete(Serializable id) {
        return ${context.className?uncap_first}Dao.deleteById(id) > 0;
    }
    
    @Override
	public boolean delete(Long tenantId, Serializable id) {
		${context.className} record = find(tenantId, id);
		if (null == record) {
			throw new RuntimeException("没有找到文件记录");
		}
		return ${context.className?uncap_first}Dao.deleteById(id) > 0;
	}
    
    @Override
    public ${context.className} find(Serializable id) {
        return ${context.className?uncap_first}Dao.selectById(id);
    }
    
	@Override
	public ${context.className} find(Long tenantId, Serializable id) {
		List<${context.className}> list = ${context.className?uncap_first}Dao.query(CollectionUtil.newInstance().toMap("tenantId", tenantId, getIdField(), id));
		return list.size() > 0 ? list.get(0) : null;
	}
    
    @Override
    public List<${context.className}> query(Long tenantId,Map<String, Object> param) {
    	param.put("tenantId",tenantId);
        return ${context.className?uncap_first}Dao.query(param);
    }

    /**
     * @param tenantId 租户ID
     * @param queryParam 搜索条件
     * @param page 分页
		pageNumber	页码	pageNumber	
		pageSize	每页记录数	pageSize	
     * @return
     */
	@Override
	public Page<${context.className}> pageQuery(Long tenantId,Page<Map<String, Object>> page, Map<String, Object> queryParam)throws RuntimeException{
		try {
			queryParam.put("tenantId",tenantId);
        	List<${context.className}> rs = ${context.className?uncap_first}Dao.pageQuery(page,queryParam);
			Page<${context.className}> result = new Page<${context.className}>();
			result.setRecords(rs);
			return result;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

}
