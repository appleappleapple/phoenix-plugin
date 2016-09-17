package com.github.phoenix.plugin.handle;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 功能概要：返回单个bean对象
 * 
 * @author linbingwen
 * @since  2016年8月30日
 */
public class BeanHandler implements ResultSetHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(BeanHandler.class);

	private Class clazz;

	public BeanHandler(Class clazz) {
		this.clazz = clazz;
	}

	public Object handler(ResultSet rs) throws Exception {
		try {
			if (!rs.next()) {
				return null;
			}
			// 创建封装结果集的bean
			Object bean = clazz.newInstance();

			// 得到结果集的元数据，以获取结果集的信息
			ResultSetMetaData meta = rs.getMetaData();
			int count = meta.getColumnCount();
			for (int i = 0; i < count; i++) {
				String name = meta.getColumnName(i + 1); // 获取到结果集每列的列名 id
				Object value = rs.getObject(name); // 1
				
                try {
                	Field f = bean.getClass().getDeclaredField(name);
					if (f != null) {
						f.setAccessible(true);
						f.set(bean, value);	
					}
                } catch (NoSuchFieldException e) {
                	logger.error("表中字段：{}在类：{}没有对应的属性",name,clazz);
                }
			}
			
			return bean;
		} catch (Exception e) {
			throw e;
		}
	}

}
