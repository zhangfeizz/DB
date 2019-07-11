package com.db.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysUserRoleDao {


	/**
	 * 基于用户id获取所有的角色id
	 * @param id
	 * @return
	 */
	List<Integer> findRoleIdsByUserId(Integer id);
	
	
	/**
	 * 保存用户和角色关系数据
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	int insertObjects(@Param("userId")Integer userId,
			          @Param("roleIds")Integer[] roleIds);
     /**
      * 基于用户id删除角色与用户关系数据
      * @param8 UserId
      * @return
      */
	 int deleteObjectsByUserId(Integer userId);
	 /**
	  * 基于角色id删除角色与用户关系数据
	  * @param roleId
	  * @return
	  */
	 int deleteObjectsByRoleId(Integer roleId);
}
