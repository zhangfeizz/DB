package com.db.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.db.common.vo.JsonResult;
import com.db.common.vo.PageObject;
import com.db.sys.entity.SysLog;
import com.db.sys.service.SysLogService;

@RequestMapping("/log/")
@Controller
public class SysLogController {
	
	 @Autowired
	 private SysLogService sysLogService;
	 
	 @RequestMapping("doLogListUI")
	 public String doLogListUI(){
		// try{Thread.sleep(10000);}
		// catch(Exception e){e.printStackTrace();}
		 return "sys/log_list";
	 }
	 //../log/doDeleteObjects.do?ids=67,68
	 @PostMapping("doDeleteObjects")
	 @ResponseBody
	 public JsonResult doDeleteObjects(
			 Integer... ids){
		 sysLogService.deleteObjects(ids);
		 return new JsonResult("delete ok");
	 }//jackson
	 
	 @GetMapping("doFindPageObjects")
	 @ResponseBody
	 public JsonResult doFindPageObjects(
			 Integer pageCurrent,String username){
		 System.out.println("pageCurrent="+pageCurrent);
		 System.out.println("username="+username);
		 PageObject<SysLog> po=
		 sysLogService.findPageObjects(username,
				 pageCurrent);
		 return new JsonResult(po);
	 }//mvc 底层会借助第三方api将返回值转换为指定格式的json串
	 
}
