package com.github.phoenix.plugin.client;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.phoenix.plugin.criterion.Criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.phoenix.plugin.utils.StringUtil;

public class PhoenixCommon {
	private static final Logger logger = LoggerFactory.getLogger(PhoenixCommon.class);

	/* 指定库名 */
	protected String id;

	/**
	 * 将数据批量放入
	 * 
	 * @author linbingwen
	 * @since 2016年8月30日
	 * @param preState
	 * @param columnNames
	 * @param datas
	 * @throws SQLException
	 */
	protected <T> void addBatch(PreparedStatement preState, List<String> columnNames, List<T> datas) throws SQLException {
		for (T data : datas) {
			int i = 1;
			for (String columnName : columnNames) {
				try {
					preState.setObject(i++, this.get(data, StringUtil.underlineToCamel(columnName)));
				} catch (Exception e) {
					throw new SQLException(e.getMessage());
				}
			}
			preState.addBatch();
		}
	}

	/**
	 * 根据属性名取值
	 * 
	 * @author linbingwen
	 * @since 2016年8月30日
	 * @param object
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	protected <T> Object get(T object, String propertyName) throws Exception {

		String methodName = "get";
		if (propertyName.length() > 1 && Character.isUpperCase(propertyName.charAt(1))) {
			methodName += propertyName;
		} else {
			methodName += (Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1));
		}

		try {
			Method method = object.getClass().getMethod(methodName, new Class[] {});

			Object fieldValue = method.invoke(object, new Object[] {});

			return fieldValue;

		} catch (Exception e) {
			logger.info("get value for propertyName:{} from :{} fail,Exception:{}", propertyName, object, e);
			throw new Exception(e);
		}
	}

	/**
	 * 根据VO类获取表名、表结构
	 * 
	 * @author linbingwen
	 * @since 2016年8月29日
	 * @param object
	 * @return
	 */
	// public <T> Map<String, Object> getTableInfo(T object) {
	// Map<String, Object> map = new HashMap<String, Object>();
	// List<String> columnNames = new ArrayList<String>();
	//
	// Field[] fields = object.getClass().getDeclaredFields();
	//
	// for (int i = 0; i < fields.length; i++) {
	// if (!"serialVersionUID".equals(fields[i].getName())) { // 排除序列化字段
	// columnNames.add(StringUtil.underlineToCamel(fields[i].getName()));
	// }
	// }
	//
	// String[] results = object.getClass().getName().split("\\.");
	// map.put("tableName", StringUtil.camelToUnderline(results[results.length -
	// 1]));
	// map.put("columnNames", columnNames);
	//
	// return map;
	// }

	protected <T> String getTableName(T object) {
		String[] results = object.getClass().getName().split("\\.");

		String tableName = StringUtil.camelToUnderline(results[results.length - 1]);

		logger.info("getTableName for {} is {}", object, tableName);

		return tableName;
	}

	/**
	 * 不管传入的object里的字段是否为null都转换为对应的表字段
	 * 
	 * @author linbingwen
	 * @since 2016年8月31日
	 * @param object
	 * @return
	 */
	protected <T> List<String> getColumnNamesWithNull(T object) {
		List<String> columnNames = new ArrayList<String>();

		Field[] fields = object.getClass().getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			if (!"serialVersionUID".equals(fields[i].getName())) { // 排除序列化字段
				columnNames.add(StringUtil.camelToUnderline(fields[i].getName()));
			}
		}

		logger.info("getColumnNamesWithNull for {} is {}", object, columnNames);

		return columnNames;
	}

	/**
	 * 传入的object里的字段有为null就不转换为对应的表字段
	 * 
	 * @author linbingwen
	 * @since 2016年8月31日
	 * @param object
	 * @return
	 * @throws Exception
	 */
	protected <T> List<String> getColumnNamesIgnoreNull(T object) throws Exception {
		List<String> columnNames = new ArrayList<String>();

		Field[] fields = object.getClass().getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			if (!"serialVersionUID".equals(fields[i].getName())) { // 排除序列化字段

				Object fieldValue = this.get(object, fields[i].getName());

				if (fieldValue != null) {
					columnNames.add(StringUtil.camelToUnderline(fields[i].getName()));
				}
			}
		}

		logger.debug("getColumnNamesIgnoreNull for {} is {}", object, columnNames);

		return columnNames;
	}

	/**
	 * 生成upsert语句
	 * 
	 * @author linbingwen
	 * @since 2016年8月29日
	 * @param tableName
	 * @param columnNames
	 * @return
	 */
	protected String buildInserSql(String tableName, List<String> columnNames) {
		StringBuilder before = new StringBuilder();
		StringBuilder after = new StringBuilder();

		before.append("upsert into " + id + "." + tableName + " (");
		after.append("(");

		for (String columnName : columnNames) {
			before.append(StringUtil.camelToUnderline(columnName) + ",");
			after.append("?,");
		}

		before = new StringBuilder(before.substring(0, before.length() - 1)).append(") values ");
		after = new StringBuilder(after.substring(0, after.length() - 1)).append(")");
		String result = before.append(after).toString();

		logger.debug("buildInserSql result is {}", result);
		return result;
	}

	/**
	 * 生成不带条件的查询语句
	 * 
	 * @author linbingwen
	 * @since 2016年8月30日
	 * @param tableName
	 * @param columnNames
	 * @return
	 */
	protected String buildfindAllSql(String tableName, List<String> columnNames) {
		StringBuilder sql = new StringBuilder();
		sql.append("select ");

		for (String columnName : columnNames) {
			sql.append(columnName + ",");
		}
		sql = new StringBuilder(sql.substring(0, sql.length() - 1)).append(" from ").append(id).append(".").append(tableName);

		logger.debug("buildInserSql result is {}", sql);
		return sql.toString();
	}

	/**
	 * 生成带条件的sql语句
	 * 
	 * @author linbingwen
	 * @since 2016年8月31日
	 * @param tableName
	 * @param columnNames
	 * @param map
	 * @return
	 */
	protected String findByConditionSql(String tableName, List<String> columnNames, Map<String, Object> map) {
		String result = buildfindAllSql(tableName, columnNames) + buildCondition(map);

		logger.debug("findByConditionSql result is {}", result);
		return result;
	}

	/**
	 * 生成条件语句
	 * 
	 * @author linbingwen
	 * @since 2016年8月31日
	 * @param map
	 * @return
	 */
	protected String buildCondition(Map<String, Object> map) {
		StringBuilder condition = new StringBuilder(" where 1 = 1");

		if (map != null) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {

				String key = entry.getKey();
				Object value = entry.getValue();

				if (value instanceof String) {
					condition.append(" and ").append(StringUtil.camelToUnderline(key)).append(" = '").append(value).append("'");
				} else {
					condition.append(" and ").append(StringUtil.camelToUnderline(key)).append(" = ").append(value);
				}
			}
		}

		return condition.toString();
	}

	/**
	 * 释放资源
	 * 
	 * @author linbingwen
	 * @since 2016年8月31日
	 * @param conn
	 * @param st
	 * @param rs
	 */
	protected void release(Connection conn, Statement st, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if (st != null) {
			try {
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			st = null;
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
