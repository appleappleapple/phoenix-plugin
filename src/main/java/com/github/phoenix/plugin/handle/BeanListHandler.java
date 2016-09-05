package com.github.phoenix.plugin.handle;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import com.github.phoenix.plugin.utils.StringUtil;

/**
 * 
 * 功能概要：返回list对象
 * 
 * @author linbingwen
 * @since  2016年8月30日
 */
public class BeanListHandler implements ResultSetHandler {

	private Class clazz;

	public BeanListHandler(Class clazz) {
		this.clazz = clazz;
	}

	public Object handler(ResultSet rs) {
		List list = new ArrayList();
		try {
			while (rs.next()) {
				Object bean = clazz.newInstance();

				ResultSetMetaData meta = rs.getMetaData();
				int count = meta.getColumnCount();
				for (int i = 0; i < count; i++) {
					String name = meta.getColumnName(i + 1);
					Object value = rs.getObject(name);
                    name = StringUtil.underlineToCamel(name.toLowerCase());
					Field f = bean.getClass().getDeclaredField(name);
					f.setAccessible(true);
					f.set(bean, value);
				}
				list.add(bean);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
}
