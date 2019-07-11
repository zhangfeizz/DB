package com.db.sys.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.db.sys.entity.SysLog;

public interface SysLogDao {
	/**
	 * 写入日志
	 * @param log
	 * @return
	 */
	int insertObject(SysLog log);
	/**
	 * 基于日志id,执行日志删除操作
	 * @param ids 可以是多个id
	 * @return 删除的行数
	 */
	int deleteObjects(@Param("ids")Integer... ids);
	
	Map<String,Object> findById(Integer id);
	
	/**
	 * 基于查询条件,分页查询用户行为日志信息
	 * @param username 查询条件
	 * @param startIndex 起始位置
	 * @param pageSize 页面大小
	 * @return
	 */
	List<SysLog> findPageObjects(
			        @Param("username")String username,
			        @Param("startIndex")Integer startIndex,
			        @Param("pageSize")Integer pageSize);
	
	/**
	 * 基于条件统计用户日志信息
	 * @param username
	 * @return 此用户的行为日志信息总数
	 */
	int getRowCount(
		@Param("username")String username);
	
	  
}
