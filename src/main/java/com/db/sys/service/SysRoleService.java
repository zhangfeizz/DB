package com.db.sys.service;

import java.util.List;

import com.db.common.vo.CheckBox;
import com.db.common.vo.PageObject;
import com.db.sys.entity.SysRole;
import com.db.sys.vo.SysRoleMenuVo;

public interface SysRoleService {
	/**
	 * 查询所有角色的角色id和name
	 * @return
	 */
	List<CheckBox> findObjects();
	/**
	 * 基于角色id执行角色信息以及角色与菜单关系数据
	 * @param id
	 * @return
	 */
 	 SysRoleMenuVo findObjectById(Integer id);
	
 	 /**
 	  * 更新角色以及角色和菜单的关系数据
 	  * @param entity
 	  * @param menuIds
 	  * @return
 	  */
 	 int updateObject(SysRole entity,Integer[] menuIds);
	 
 	 /**
	  * 保存角色以及角色和菜单的关系数据
	  * @param entity
	  * @param menuIds
	  * @return
	  */
	 int saveObject(SysRole entity,Integer[] menuIds);
	 /**
	  * 基于角色id删除角色以及对应的关系数据
	  * @param id
	  * @return
	  */
	 int deleteObject(Integer id);

	 PageObject<SysRole> findPageObjects(String name,
			 Integer pageCurrent);
}
