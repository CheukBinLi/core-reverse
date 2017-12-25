<?xml version="1.0" encoding="UTF-8" ?>  
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${context.packageName}.${context.className}Dao">
	<!-- 通用查询映射结果 -->
	<resultMap id="${context.className}ResultMap" type="${context.packageName}.${context.className}">
		<#list context.fields as field>
		<id column="${field.name}" property="${field.fieldName}" />
		</#list>
	</resultMap>
	
	<sql id="whereEqual">
		<#list context.fields as field>
		<if test="${field.fieldName} != null and ${field.fieldName} != ''">
			AND A.${field.fieldName} = ${'#{'}${field.fieldName}${'}'}
		</if>
		</#list>
	</sql>
	
	<sql id="columnsField">
	<#assign i =0> 
	<#list context.fields as field>
		<#if i gt 0>,</#if>A.${field.fieldName} 
		<#assign i=1/>
	</#list>
	</sql>
	
	<select id="count" parameterType="map" resultType="int">
		SELECT COUNT(*) FROM ${context.entityName} A 
		WHERE 1=1 
		<include refid="whereEqual"/> 
	</select>

	<select id="query" parameterType="map" resultMap="${context.className}ResultMap">
		SELECT
			<include refid="columnsField"/> 
		FROM 
			${context.entityName} A 
		WHERE 1=1 
			<include refid="whereEqual"/>
	</select>
	
	<select id="pageQuery" parameterType="Map" resultMap="${context.className}ResultMap">
		SELECT 
			<include refid="columnsField"/>
        FROM ${context.entityName} A 
		WHERE 1=1 
			<include refid="whereEqual"/>
	</select>
	
</mapper>