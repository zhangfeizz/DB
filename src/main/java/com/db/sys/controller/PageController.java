package com.db.sys.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class PageController {//Spring MVC中后端控制器通常会称之为handler
	 //方法调用过程?
	 //1.请求的url会交给DispatcherServlet对象
	 //2.DispatcherServlet对象基于请求url从HandlerMapping中获取HandlerMethod对象
	 //3.DispatcherServlet对象会基于反射技术调用此controller中的方法
	 @RequestMapping("doIndexUI")
	 public String doIndexUI(){
		 return "starter";//WEB-INF/pages/starter.html
	 }
	 /**
	  * 返回登录页面
	  * @return
	  */
	 @RequestMapping("doLoginUI")
	 public String doLoginUI(){
	 		return "login";
	 }

	 //方法返回值的处理过程?
	 //1.此值("starter")会返回给DispatcherServlet(前端控制器)
	 //2.DispatcherServlet调用视图解析器(ViewResolver)对返回的view进行解析
	 //3.视图解析器将解析结果返回给DispatcherServlet对象
	 //4.DispatcherServlet将页面响应到客户端.
	 
	 /**
	  * 基于此方法返回分页页面.
	  * @return
	  */
	 @RequestMapping("doPageUI")
	 public String doPageUI(){
	 	 return "common/page";
	 }

}
