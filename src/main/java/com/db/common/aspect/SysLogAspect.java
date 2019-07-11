package com.db.common.aspect;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.db.common.annotation.RequiredLog;
import com.db.common.utils.IPUtils;
import com.db.sys.dao.SysLogDao;
import com.db.sys.entity.SysLog;
import com.db.sys.entity.SysUser;
/**
 * @Aspect 描述类为一个切面对象
 * 定义日志切面:为我们业务的执行添加日志记录
 */
@Order(1)
@Service
@Aspect
public class SysLogAspect {
	@Autowired
	private SysLogDao sysLogDao;
	/**定义切入点表达式:(切入点可以理解为切入扩展功能的点)
	 * 1)bean(表达式):表达式一般为一个bean的名字,例如
	 * a)bean(sysUserServiceImpl)
	 * b)bean(*ServiceImpl)
	 * 2)@annotation(表达式):注解方式的切入点表达式
	 * a)@annotation(com.db.common.annotation.RequiredLog)
	 */
	//@Pointcut("bean(sysUserServiceImpl)")
	@Pointcut("@annotation(com.db.common.annotation.RequiredLog)")
	public void logPointCut(){}
	/**
	 * 环绕通知:可以在目标方法执行之前和之后添加扩展功能.
	 * @param  jp连接点(要执行的某个目标方法)
	 * @return 目标方法的执行结果
	 * @throws Throwable
	 * 其中bean(bean的名字)为一个切入点表达式,当
	 * bean对象的业务方法执行时,执行@Around注解描述的方法
	 */
	//@Around("bean(sysUserServiceImpl)")
	@Around("logPointCut()")
	public Object aroundMethod(ProceedingJoinPoint jp)
	throws Throwable{
		long t1=System.currentTimeMillis();
		//执行目标方法
	    Object result=jp.proceed();
		long t2=System.currentTimeMillis();
		System.out.println("execute time "+(t2-t1));
		//获取日志信息,并将其写入到数据库
		saveLog(jp,(t2-t1));
		return result;
	}
	private void saveLog(ProceedingJoinPoint jp,long time) throws NoSuchMethodException, SecurityException{
		//1.获取日志
		//1.1获取登录用户
		SysUser user=(SysUser)SecurityUtils.getSubject().getPrincipal();
		String username=user.getUsername();
		//1.2获取访问方法(类全名+"."+方法名)
		Object target=jp.getTarget();
		String targetClsName=
		target.getClass().getName();
		MethodSignature ms=
		(MethodSignature)jp.getSignature();//方法
		String methodName=ms.getName();
		String method=targetClsName+"."+methodName;
		//1.3获取方法参数(执行方法时传入的实际参数)
		String params=Arrays.toString(jp.getArgs());
		//1.4获取操作名称
		//1.4.1获取目标方法对象
		Method targetMethod=
		target.getClass().getDeclaredMethod(
		methodName,ms.getParameterTypes());
		System.out.println("targetMethod="+targetMethod);
		//1.4.2获取目标方法上的RequiredLog注解
		RequiredLog rLog=
		targetMethod.getDeclaredAnnotation(RequiredLog.class);
		String operation=methodName;
		//1.4.3获取注解中的操作名称
		if(rLog!=null&&!StringUtils.isEmpty(rLog.value())){
		  operation=rLog.value();
		}
		//1.5获取ip地址
		String ip=IPUtils.getIpAddr();
		//2.封装日志
		SysLog log=new SysLog();
		log.setUsername(username);
		log.setMethod(method);
		log.setParams(params);
		log.setOperation(operation);
		log.setIp(ip);
		log.setTime(time);
		log.setCreatedTime(new Date());
		//3.将日志写入到数据库
		sysLogDao.insertObject(log);
	}

}









