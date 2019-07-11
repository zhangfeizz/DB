package com.db.sys.service.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.db.sys.dao.SysMenuDao;
import com.db.sys.dao.SysRoleMenuDao;
import com.db.sys.dao.SysUserDao;
import com.db.sys.dao.SysUserRoleDao;
import com.db.sys.entity.SysUser;

@Service
public class ShiroUserRealm extends AuthorizingRealm {
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysMenuDao sysMenuDao;
	
	/**设置加密匹配器*/
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher hMatcher=new HashedCredentialsMatcher();
		hMatcher.setHashAlgorithmName("MD5");
		hMatcher.setHashIterations(1);
		super.setCredentialsMatcher(hMatcher);
	}
	/**
	 * 负责认证信息的获取及封装
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		System.out.println("==doGetAuthenticationInfo==");
		//1.获取用户输入的用户信息
		UsernamePasswordToken uToken=
	    (UsernamePasswordToken)token;
		String username=uToken.getUsername();
		//2.基于用户名查询数据库
		SysUser user=sysUserDao.findUserByUserName(username);
		//3.判定用户是否存在
		if(user==null)
		throw new UnknownAccountException();
		//4.判定用户是否已经锁定
		if(user.getValid()==0)
		throw new LockedAccountException();
		//5.对用户信息进行封装.
		ByteSource credentialsSalt=
		ByteSource.Util.bytes(user.getSalt());
		
		SimpleAuthenticationInfo info=
		new SimpleAuthenticationInfo(
				user,//principal 用户身份
				user.getPassword(),//hashedCredentials 已加密的密码
				credentialsSalt,//已经加工好的salt对象
				getName());//realmName
		return info;//此信息会返回给认证管理器
	}
	/**
	 * 负责授权信息的获取及封装
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
		PrincipalCollection principals) {
		System.out.println("==doGetAuthorizationInfo==");
		//1.获取登录用户
		SysUser user=(SysUser)
		principals.getPrimaryPrincipal();
		
		//2.基于用户id获取角色id(sys_user_roles)
		List<Integer> roleIds=
		sysUserRoleDao.findRoleIdsByUserId(user.getId());
		if(roleIds==null||roleIds.size()==0)
		throw new AuthorizationException();
		
		//3.基于角色id获取菜单id(sys_role_menus)
		Integer[] array={};
		List<Integer> menuIds=
		sysRoleMenuDao.findMenuIdsByRoleIds(
				roleIds.toArray(array));
		if(menuIds==null||menuIds.size()==0)
		throw new AuthorizationException();
		
		//4.基于菜单id获取权限标识(permission)
		List<String> permissions=
		sysMenuDao.findPermissions(menuIds.toArray(array));
		if(permissions==null||permissions.size()==0)
		throw new AuthorizationException();
	/*	for(String p:permissions){
			System.out.println(p);
		}*/
		
		//5.封装用户权限信息.
		//5.1去除重复的权限标识
		Set<String> pSet=new HashSet<>();
		for(String p:permissions){
			if(!StringUtils.isEmpty(p)){
				pSet.add(p);
			}
		}
		//5.2封装
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		info.setStringPermissions(pSet);
		return info;
	}
}








