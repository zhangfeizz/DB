package com.db.sys.service.impl;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.db.common.exception.ArgumentException;
import com.db.common.exception.ServiceException;
import com.db.common.utils.PageUtil;
import com.db.common.vo.PageObject;
import com.db.sys.dao.SysLogDao;
import com.db.sys.entity.SysLog;
import com.db.sys.service.SysLogService;
/**
 * 日志业务对象
 * 1)核心业务
 * 
 * 1.1)参数校验
 * 1.2)执行数据查询(总记录数,当前页记录)
 * 1.3)计算总页数
 * 1.4)对查询结果进行封装?(也可封装到map,只不过不建议,可读性性差)
 * 
 * 2)扩展业务
 * 2.1)权限控制
 * 2.2)数据缓存
 * 2.3)事务控制
 * ..........
 *
 */
@Transactional(timeout=30,rollbackFor=Throwable.class)
@Service
public class SysLogServiceImpl implements SysLogService {
	//此属性会基于spring中的DI机制进行赋值
	@Autowired
	private SysLogDao sysLogDao;
			
	/**
	 * 指定访问此方法需要进行授权操作,当用户的权限
	 * 中包含这个字符串"sys:log:delete"则可以访问
	 * 此方法.
	 */
	@RequiresPermissions("sys:log:delete")
//	@Transactional  //事务的注解
	@Override
	public int deleteObjects(Integer... ids) {
		//1.验证参数有效性
		if(ids==null||ids.length==0)
		throw new ArgumentException("请先选择要删除的记录");
		//2.执行删除操作
		int rows=sysLogDao.deleteObjects(ids);
		//3.判定删除结果并返回
		if(rows>0) //事务代码判断 回滚
//		if(rows==0)
		throw new ServiceException("记录可能已经不存在");
		return rows;
	}
	
	@Override
	public PageObject<SysLog> findPageObjects(
			String username, 
			Integer pageCurrent) {
		//1.对参数进行校验
		if(pageCurrent==null||pageCurrent<1)
		throw new ArgumentException("当前页码值不正确");
		//2.基于用户名查找总记录数并进行校验
		System.out.println("username="+username);
		int rowCount=sysLogDao.getRowCount(username);
		if(rowCount==0)
		throw new ServiceException("没有找到对应记录");
		//3.基于条件查询当前页记录List<SysLog>
		int pageSize=3;
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysLog> records=
		sysLogDao.findPageObjects(username,
				startIndex, pageSize);
		//4.对查询结果进行封装并返回
		PageObject<SysLog> po =
		PageUtil.newPageObject(pageCurrent, rowCount, pageSize, records);
		return po;
	}//作业:此方法写完以后要进行单元测试
	
}
