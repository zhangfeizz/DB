package com.db.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
/**
 * 切面:一个封装了扩展业务的对象
 * 切面构成分析:
 * 1)切入点(在哪些方法执行时织入扩展业务)
 * 1.1)bean()
 * 1.2)@annotation
 * 1.3)....
 * 2)通知(扩展业务中的几个步骤)
 * 2.1)@Before
 * 2.2)@After
 * 2.3)@AfterReturning
 * 2.4)@AfterThrowing
 * 2.5)@Around
 * 3)连接点(指向一个目标方法对象)
 * 3.1)JointPoint (非环绕通知中使用的连接点)
 * 3.2)ProceedingJoinPoint (环绕通知中使用)
 */
@Order(2)
@Service
@Aspect 
public class DemoAspect {

	  @Pointcut("bean(*ServiceImpl)")
	  public void demoPointCut(){}
	
	  @Before("demoPointCut()")
	  public void beforeMethod(){
		  System.out.println("@before");
	  }
	  @After("demoPointCut()")
	  public void afterMethod(){
		  System.out.println("@After");
	  }
	  @AfterReturning("demoPointCut()")
	  public void afterReturnMethod(){
		  System.out.println("@AfterReturning");
	  }
	  @AfterThrowing("demoPointCut()")
	  public void afterThrowMethod(){
		  System.out.println("@AfterThrowing");
	  }
	  /**
	   * 环绕通知
	   * @param jp  连接点(必须有)
	   * @return 目标方法的执行结果
	   * @throws Throwable
	   */
	  @Around("demoPointCut()")
	  public Object aroundMethod(ProceedingJoinPoint jp)
	  throws Throwable{
		  System.out.println("@Around before");
		  Object result=jp.proceed();
		  System.out.println("@Around after");
		  return result;
		  
	  }
}
















