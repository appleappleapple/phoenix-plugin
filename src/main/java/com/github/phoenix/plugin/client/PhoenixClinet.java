package com.github.phoenix.plugin.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.phoenix.plugin.factory.ConnectionFactory;
import com.github.phoenix.plugin.handle.BeanListHandler;
import com.github.phoenix.plugin.utils.StringUtil;

public class PhoenixClinet extends PhoenixCommon implements PhoenixBase {

	private static final Logger logger = LoggerFactory.getLogger(PhoenixClinet.class);

	public final static ConnectionFactory connectionFactory = ConnectionFactory.getInstance();

	/**
	 * 构造函数，传入id请确保xml文件中有配置
	 * 
	 * @param id
	 */
	public PhoenixClinet(String id) {
		Connection connection = null;
		try {
			logger.info(">>11");
			connection = connectionFactory.getConnection(id);

			if (connection == null) {
				logger.error("id = {} find connection fail ", id);
				throw new Exception("not find connection for id " + id);
			}

			logger.info(">>22");

			this.id = id;

			logger.debug("connection info is {}", connectionFactory.getConnection(id).toString());

			logger.info(">>33");

		} catch (Exception e) {
			logger.error("init connection error = {}", e.getLocalizedMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("init connection error = {}", e);
			}
		}
	}

	@Override
	public <T> void save(T data) {
		if (data != null) {
			List<T> datas = new ArrayList<T>();
			datas.add(data);
			this.save(datas);
		}
	}

	@Override
	public <T> void save(List<T> datas) {
		if (datas != null && !datas.isEmpty()) {

			String tableName = this.getTableName(datas.get(0));
			List<String> columnNames = this.getColumnNamesIgnoreNull(datas.get(0));

			String sql = this.buildInserSql(tableName, columnNames);

			Connection connection = null;
			PreparedStatement preState = null;

			try {
				connection = connectionFactory.getConnection(id);
				preState = connection.prepareStatement(sql);

				this.addBatch(preState, columnNames, datas);

				preState.executeBatch();
				connection.commit();

				logger.debug("save Lists datas success for sql :{},datas:{}", sql, datas);
			} catch (Exception e) {
				logger.error("save datas error = {}", e.getLocalizedMessage());
				throw new RuntimeException(e);
			} finally {
				release(connection, preState, null);
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findAll(Class<T> clazz) {
		T t = null;
		Connection connection = null;
		PreparedStatement preState = null;
		ResultSet rs = null;

		try {
			t = clazz.newInstance();

			String tableName = this.getTableName(t);
			List<String> columnNames = this.getColumnNamesWithNull(t);

			String sql = this.buildfindAllSql(tableName, columnNames);

			connection = connectionFactory.getConnection(id);
			preState = connection.prepareStatement(sql);

			rs = preState.executeQuery();

			BeanListHandler handle = new BeanListHandler(clazz);

			return (List<T>) handle.handler(rs);
		} catch (Exception e) {
			logger.error("findAll datas error:{}", e.getLocalizedMessage());
			throw new RuntimeException(e);
		} finally {
			release(connection, preState, rs);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findByCondition(Class<T> clazz, Map<String, Object> map) {
		T t = null;
		Connection connection = null;
		PreparedStatement preState = null;
		ResultSet rs = null;

		try {
			t = clazz.newInstance();

			String tableName = this.getTableName(t);
			List<String> columnNames = this.getColumnNamesWithNull(t);

			String sql = this.findByConditionSql(tableName, columnNames, map);

			connection = connectionFactory.getConnection(id);
			preState = connection.prepareStatement(sql);

			rs = preState.executeQuery();

			BeanListHandler handle = new BeanListHandler(clazz);

			return (List<T>) handle.handler(rs);
		} catch (Exception e) {
			logger.error("findAll datas error:{}", e.getLocalizedMessage());
			throw new RuntimeException(e);
		} finally {
			release(connection, preState, rs);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findByCondition(Class<T> clazz, String condition) {
		T t = null;
		Connection connection = null;
		PreparedStatement preState = null;
		ResultSet rs = null;

		try {
			t = clazz.newInstance();

			String tableName = this.getTableName(t);
			List<String> columnNames = this.getColumnNamesWithNull(t);

			String sql = this.buildfindAllSql(tableName, columnNames);
			sql = (!StringUtil.isBlank(condition)) ?sql + " " + condition : sql;

			connection = connectionFactory.getConnection(id);
			
			preState = connection.prepareStatement(sql);

			rs = preState.executeQuery();

			BeanListHandler handle = new BeanListHandler(clazz);

			return (List<T>) handle.handler(rs);
		} catch (Exception e) {
			logger.error("findAll datas error:{}", e.getLocalizedMessage());
			throw new RuntimeException(e);
		} finally {
			release(connection, preState, rs);
		}
	}
}
