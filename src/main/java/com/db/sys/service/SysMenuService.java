package com.db.sys.service;
import java.util.List;
import java.util.Map;

import com.db.common.vo.Node;
import com.db.sys.entity.SysMenu;
public interface SysMenuService {
	

	/**
	 * 更新菜单信息
	 * @param entity
	 * @return
	 */
	int updateObject(SysMenu entity);
	 /**
	  * 保存菜单信息
	  * @param entity
	  * @return
	  */
	 int saveObject(SysMenu entity);
	
	 List<Node> findZtreeMenuNodes();
	 /**
	  * 基于菜单id执行删除操作
	  * @param id
	  * @return
	  */
	 int deleteObject(Integer id);

	 List<Map<String,Object>> findObjects();
}
