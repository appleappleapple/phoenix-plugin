package com.github.phoenix.plugin.handle;

import java.sql.ResultSet;

/**
 * 
 * 功能概要：对查询结果的处理接口
 * 
 * @author linbingwen
 * @since  2016年8月30日
 */
public interface ResultSetHandler {
	
	public Object handler(ResultSet rs);

}
