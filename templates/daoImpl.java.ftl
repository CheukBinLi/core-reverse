package cn.com.boomhope.tms.dao.impl;

<#if context.pkSize gt 1>
import java.util.HashMap;
import java.util.Map;

</#if>
import org.springframework.stereotype.Repository;

import cn.com.boomhope.base.dao.impl.BaseDao;
import cn.com.boomhope.tms.dao.I${context.simpleName}Dao;
import ${context.name};
<#if context.joinSize gt 0>
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
</#if>

/**
 * 
 * @author 博宏半自动代码生成
 *
 */
@Repository
public class ${context.simpleName}Dao extends BaseDao<${context.simpleName}> implements I${context.simpleName}Dao
{
	public ${context.simpleName}Dao()
	{
		super(${context.simpleName}.class);
	}
	
	@Override
	public ${context.simpleName} getByPK(<#assign i=0/><#list context.pks as field><#if i gt 0>,</#if>${field.typeSimpleName} ${field.fieldName}<#assign i=i+1/></#list>)
	{
		<#if context.pkSize gt 1>
		Map<String,Object> map = new HashMap<String,Object>();
		<#list context.pks as field>
		map.put("${field.fieldName}",${field.fieldName});
		</#list>
		</#if>
		<#if context.pkSize gt 1>
		
		return this.sqlSession.selectOne(toSqlId(GETBYPK),map);
		<#elseif context.pkSize==1>
		return this.sqlSession.selectOne(toSqlId(GETBYPK),<#assign i=0/><#list context.pks as field><#if i gt 0>,</#if>${field.fieldName}</#list>);	
		</#if>
	}
	<#if context.joinSize gt 0>
	
	@Override
	public Page joinPage(Map<String, String> params, int pageNo, int pageSize)
	{
		int offset = (pageNo - 1) * pageSize;
		int limit = pageSize;
		List<${context.simpleName}Dto> list = this.sqlSession.selectList(className + JOINLIST, params, new RowBounds(offset, limit));
		int count = this.sqlSession.selectOne(toSqlId(JOINCOUNT), params);
		Page page = new Page(list, pageSize, count, pageNo);
		return page;
	}
	
	@Override
	public Page joinPage(Map<String, String> params, int pageNo)
	{
		return this.joinPage(params,pageNo,Page.PAGESIZE);
	}
	</#if>
}