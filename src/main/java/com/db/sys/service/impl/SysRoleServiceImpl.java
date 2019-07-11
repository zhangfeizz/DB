package com.db.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.db.common.exception.ArgumentException;
import com.db.common.exception.ServiceException;
import com.db.common.utils.PageUtil;
import com.db.common.vo.CheckBox;
import com.db.common.vo.PageObject;
import com.db.sys.dao.SysRoleDao;
import com.db.sys.dao.SysRoleMenuDao;
import com.db.sys.dao.SysUserRoleDao;
import com.db.sys.entity.SysRole;
import com.db.sys.service.SysRoleService;
import com.db.sys.vo.SysRoleMenuVo;

@Service
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	@Qualifier("sysRoleDao")
	//当出现多个类型相同的对象还可以借助@Qualifier指定要主要的对象名
	private SysRoleDao sysRoleDao;
	
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	public List<CheckBox> findObjects() {
		return sysRoleDao.findObjects();
	}
	
	@Override
	public SysRoleMenuVo findObjectById(Integer id) {
	 	//1.合法性验证
    	if(id==null||id<=0)
    	throw new ArgumentException("id的值不合法");
    	//2.执行查询
    	SysRoleMenuVo result=sysRoleDao.findObjectById(id);
  	    //3.验证结果并返回
    	if(result==null)
    	throw new ServiceException("此记录已经不存在");
    	return result;

	}
	@Override
	public int saveObject(SysRole entity,
			Integer[] menuIds) {
		//1.参数校验
		if(entity==null)
		throw new ArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
		throw new ArgumentException("角色名不能为空");
		//....
		if(menuIds==null||menuIds.length==0)
		throw new ArgumentException("必须为角色指定权限");
		//2.保存角色自身信息
		int rows=sysRoleDao.insertObject(entity);
		//3.保存角色和菜单关系数据
		sysRoleMenuDao.insertObject(entity.getId(),
				menuIds);
		//4.返回结果
		return rows;
	}
	@Override
	public int updateObject(SysRole entity,
			Integer[] menuIds) {
		//1.参数校验
		if(entity==null)
			throw new ArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new ArgumentException("角色名不能为空");
		//....
		if(menuIds==null||menuIds.length==0)
			throw new ArgumentException("必须为角色指定权限");
		//2.保存角色自身信息
		int rows=sysRoleDao.updateObject(entity);
		if(rows==0)
	    throw new ServiceException("记录可能已经不存在");
		//3.保存角色和菜单关系数据
		sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
		sysRoleMenuDao.insertObject(entity.getId(),menuIds);
		//4.返回结果
		return rows;
	}
	
	@Override
	public int deleteObject(Integer id) {
	    //1.判定参数有效性
		if(id==null||id<1)
		throw new ArgumentException("id值不正确");
		//2.删除角色自身信息
		int rows=sysRoleDao.deleteObject(id);
		if(rows==0)
		throw new ServiceException("记录可能已经不存在");
		//3.删除角色菜单关系数据
		sysRoleMenuDao.deleteObjectsByRoleId(id);
		//4.删除角色用户关系数据
		sysUserRoleDao.deleteObjectsByRoleId(id);
		return rows;
	}
	
	@Override
	public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
		//1.参数校验
		if(pageCurrent==null||pageCurrent<1)
		throw new ArgumentException("页码值不正确");
		//2.查询总记录数
		int rowCount=sysRoleDao.getRowCount(name);
		if(rowCount==0)
		throw new ServiceException("记录不存在");
        //3.执行分页查询
		int pageSize=3;
		int startIndex=pageSize*(pageCurrent-1);
		List<SysRole> records=
		sysRoleDao.findPageObjects(name, startIndex, pageSize);
		return PageUtil.newPageObject(pageCurrent, rowCount, pageSize, records);
	}

}
