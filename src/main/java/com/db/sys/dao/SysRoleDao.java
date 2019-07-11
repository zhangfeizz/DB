package com.db.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.db.common.vo.CheckBox;
import com.db.sys.entity.SysRole;
import com.db.sys.vo.SysRoleMenuVo;
public interface SysRoleDao {
	
	 /**
	  * 获取角色相关信息:id,name
	  * @return
	  */
	 List<CheckBox> findObjects();
	 
	/**
	 * 基于角色id查询角色信息以及角色对应的菜单信息
	 * 重点关注:one2many查询的实现
	 * @param id
	 * @return 
	 */
	 SysRoleMenuVo findObjectById(Integer id);
	
	 /**
	  * 将角色自身信息更新到数据库
	  * @param entity
	  * @return
	  */
	 int updateObject(SysRole entity);
	 /**
	  * 将角色自身信息写到数据库
	  * @param entity
	  * @return
	  */
	 int insertObject(SysRole entity);
	  /**
	   * 基于角色id删除角色自身信息
	   * @param id表示角色id
	   * @return 表示删除的行数
	   */
	  int deleteObject(Integer id);
	
	  /**
	   * 依据条件查询总记录数
	   * @param name
	   * @return
	   */
	  int getRowCount(@Param("name")String name);
      /**
       * 依据条件查询当前页记录
       * @param name  查询条件
       * @param startIndex 当前页起始位置
       * @param pageSize 页面大小
       * @return
       */
	  List<SysRole> findPageObjects(
			  @Param("name")String name,
			  @Param("startIndex")Integer startIndex,
			  @Param("pageSize")Integer pageSize);
}










