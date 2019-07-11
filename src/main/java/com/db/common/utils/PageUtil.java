package com.db.common.utils;
import java.util.List;
import com.db.common.vo.PageObject;
public class PageUtil {
	public static <T>PageObject<T> newPageObject(
		Integer pageCurrent, int rowCount, 
		int pageSize, List<T> records)
	{
		PageObject<T> po=new PageObject<>();
		po.setRowCount(rowCount);
		po.setRecords(records);
		po.setPageSize(pageSize);
		po.setPageCurrent(pageCurrent);
		//计算分页方法1
	/*	int pageCount=rowCount/pageSize;
		if(rowCount%pageSize!=0){
			pageCount++;
		}
		po.setPageCount(pageCount);
		*/
		//计算分页方法2
		po.setPageCount((rowCount-1)/pageSize+1);    
		return po;
	}
}
