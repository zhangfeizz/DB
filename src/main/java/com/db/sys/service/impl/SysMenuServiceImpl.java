package com.db.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.db.common.exception.ArgumentException;
import com.db.common.exception.ServiceException;
import com.db.common.vo.Node;
import com.db.sys.dao.SysMenuDao;
import com.db.sys.dao.SysRoleMenuDao;
import com.db.sys.entity.SysMenu;
import com.db.sys.service.SysMenuService;

@Service
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private SysMenuDao sysMenuDao;
	
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Override
	public int updateObject(SysMenu entity) {
		//1.判定参数是否有效
		if(entity==null)
			throw new ArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new ArgumentException("菜单名称不允许为空");
		//....
		//2.将数据持久化到数据
		return sysMenuDao.updateObject(entity);
	}
	@Override
	public int saveObject(SysMenu entity) {
		//1.判定参数是否有效
		if(entity==null)
		throw new ArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
		throw new ArgumentException("菜单名称不允许为空");
		//....
		//2.将数据持久化到数据
		int rows=0;
		try{
	    rows=sysMenuDao.insertObject(entity);
		}catch(Throwable e){
		e.printStackTrace();
		//报警(给运维人员发短信)
		throw new ServiceException("服务端维护中");
		}
		return rows;
	}
	@Override
	public List<Node> findZtreeMenuNodes() {
		List<Node> list=sysMenuDao.findZtreeMenuNodes();
		if(list==null||list.size()==0)
		throw new ServiceException("没有菜单信息");
		return list;
	}
	
	@Override
	public int deleteObject(Integer id) {
	    //1.判断id有效性
		if(id==null||id<1)
		throw new ArgumentException("id值无效");
		//2.统计当前菜单对应的子菜单数,并进行判定
		int rowCount=sysMenuDao.getChildCount(id);
		if(rowCount>0)
		throw new ServiceException("请先删除子元素");
		//3.删除菜单自身信息
		int rows=sysMenuDao.deleteObject(id);
		if(rows==0)
		throw new ServiceException("此记录可能已经不存在");
		//4.删除菜单关系数据
		sysRoleMenuDao.deleteObjectsByMenuId(id);
		return rows;
	}
	
	
	@Override
	public List<Map<String, Object>> findObjects() {
		List<Map<String,Object>> list=sysMenuDao.findObjects();
		if(list==null||list.size()==0)
		throw new ServiceException("没有对应记录");
		return list;
	}
}
