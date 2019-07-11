package com.db.common.web;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.db.common.exception.ServiceException;

@Component
public class TimeAccessInterceptor extends HandlerInterceptorAdapter{
	/* 控制层目标方法执行之前执行
	 * @return 此方法值会决定我们的请求是否要交给后端控制器执行
	 * */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandle");
		
		//获取当前时间的日历对象
		Calendar c = Calendar.getInstance();  //日历 今日的 年月日
		//修改当前时间   时分秒
		c.set(Calendar.HOUR_OF_DAY, 8);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
			//获取修改以后的时间毫秒值(可以作为访问开始时间)
		long startTime = c.getTimeInMillis();
		//修改当前时分秒
		c.set(Calendar.HOUR_OF_DAY, 23);
			//获取修改以后的时间毫秒值(可以最为访问结束时间)
		long endTime = c.getTimeInMillis();
		long currentTime = System.currentTimeMillis();
		if (currentTime<startTime||currentTime>endTime) {
			throw new ServiceException("不在访问时间段");
		}
//		return false;	//false表示拦截
		return true;	//false表示拦截
	}
	
	
	
	
	
}
