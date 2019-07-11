package com.db.sys.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.db.sys.entity.SysUser;
import com.db.sys.vo.SysUserDeptResult;

public interface SysUserDao {
	
	int updatePassword(
			@Param("username")String username,
			@Param("newPwd")String newPwd,
			@Param("salt")String salt);
	
	/**
	 * 基于用户名查询用户信息
	 * @param username
	 * @return
	 */
	SysUser findUserByUserName(String username);
	/**
	 * 基于id查询用户以及对应的部门信息
	 * @param id
	 * @return
	 */
	SysUserDeptResult findObjectById(Integer id);
	/**
	 * 将用户自身信息写入到数据库
	 * @param entity
	 * @return
	 */
	int insertObject(SysUser entity);
	/**
	 * 将用户自身信息更新到数据库
	 * @param entity
	 * @return
	 */
	int updateObject(SysUser entity);
	
	/**
	 * 执行用户禁用或启用操作
	 * @param id 用户id
	 * @param valid 表示状态(1,0)
	 * @param modifiedUser 修改用户
	 * @return
	 */
	int validById(@Param("id")Integer id,
			     @Param("valid")Integer valid,
			     @Param("modifiedUser")String modifiedUser);
	
	/**
	 * 依据条件统计总记录数
	 * @param username
	 * @return
	 */
	int getRowCount(@Param("username")
	                String username);
	/**
	 * 依据条件查询当前页记录
	 * @param username
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<SysUserDeptResult> findPageObjects(
			@Param("username")String username,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
}
