package com.db.sys.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.db.common.vo.Node;
import com.db.sys.entity.SysMenu;
/**
 * 菜单数据层对象
 * @author ta
 */
public interface SysMenuDao {
	/**
	 * 基于菜单id获取权限标识
	 * @param menuIds
	 * @return
	 */
	List<String> findPermissions(
			@Param("menuIds")
			Integer[] menuIds);

	/**
	 * 将菜单信息更新到数据库
	 * @param entity
	 * @return
	 */
	 int updateObject(SysMenu entity);
	 /**
	  * 将菜单信息持久化到数据库
	  * @param entity
	  * @return
	  */
	 int insertObject(SysMenu entity);
	
     /**
      * 查询菜单节点信息,最后要将此信息在客户端
      * 以树结构形式进行呈现.
      * @return
      */
     List<Node> findZtreeMenuNodes();
     
	 /**
	  * 统计当前菜单对应的子菜单
	  * @param id 为当前菜单的id
	  */
	 int getChildCount(Integer id);
	 
	 /**
	  * 删除叶子节点元素
	  * @param id 表示菜单id
	  */
	 int deleteObject(Integer id);
	 
	
     /**
      * 查询所有菜单以及上级菜单(获取上级菜单名)相关信息
      * 1)一行记录映射为1个map
      * 2)多行记录存储到list集合
      * @return
      */
	 List<Map<String,Object>> findObjects();
}







