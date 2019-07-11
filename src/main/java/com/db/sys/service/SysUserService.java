package com.db.sys.service;

import java.util.Map;

import com.db.common.vo.PageObject;
import com.db.sys.entity.SysUser;
import com.db.sys.vo.SysUserDeptResult;
/**
 * 用户业务接口
 * @author ta
 */
public interface SysUserService {
	
	int updatePassword(String pwd,
			String newPwd,String cfgPwd);
	 /**
	  * 基于用户id获取用户信息以及对应的部门信息,以及
	  * 用户对应的角色信息.
	  * @param id
	  * @return
	  */
	 Map<String,Object> findObjectById(
			 Integer id);
	 
	 /**
	  * 更新用户信息
	  * @param entity
	  * @param roleIds
	  * @return
	  */
	 int updateObject(SysUser entity,Integer[] roleIds);
	
	 int saveObject(SysUser entity,Integer[] roleIds);
	
	 /**
	  * 禁用或启动用户
	  * @param id
	  * @param valid
	  * @param modifiedUser
	  * @return
	  */
	 int validById(Integer id,Integer valid,String modifiedUser);
	
	 /**
	  * 基于条件查询用户相关信息,并对其结果进行封装
	  * @param username 查询条件
	  * @param pageCurrent 当前页码值
	  * @return 当前页记录以及分页信息
	  */
	 PageObject<SysUserDeptResult> findPageObjects(
			 String username,
			 Integer pageCurrent);

}
