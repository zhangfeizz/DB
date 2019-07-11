package com.db.sys.service;

import com.db.common.vo.PageObject;
import com.db.sys.entity.SysLog;
/**
 * 日志业务接口
 * @author ta
 */
public interface SysLogService {
	 /**
	  * 基于id删除日志信息
	  * @param ids
	  * @return
	  */
	 int deleteObjects(Integer...ids);
	 
	 /**
	  * 基于条件查询日志相关信息,并对其结果进行封装
	  * @param username 查询条件
	  * @param pageCurrent 当前页码值
	  * @return 当前页记录以及分页信息
	  */
	 PageObject<SysLog> findPageObjects(
			 String username,
			 Integer pageCurrent);

}
