package com.db.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysRoleMenuDao {
	/**
	 * 基于角色id获取菜单id
	 * @param roleIds
	 * @return
	 */
	List<Integer> findMenuIdsByRoleIds(
	@Param("roleIds")Integer[] roleIds);

	
	int insertObject(
			@Param("roleId")Integer roleId,
			@Param("menuIds")Integer[] menuIds);

	 /**
	  * 基于角色id删除角色和菜单关系数据
	  * @param roleId
	  * @return
	  */
	 int deleteObjectsByRoleId(Integer roleId);
	
	 /**
	  * 基于菜单id删除关系表数据
	  * @param menuId
	  * @return
	  */
	 int deleteObjectsByMenuId(Integer menuId);
}
