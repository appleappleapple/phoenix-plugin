package com.github.phoenix.plugin.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.phoenix.plugin.criterion.Criteria;
import com.github.phoenix.plugin.factory.ConnectionFactory;
import com.github.phoenix.plugin.handle.BeanListHandler;
import com.github.phoenix.plugin.utils.StringUtil;

public class PhoenixClient extends PhoenixCommon implements PhoenixBase {

	private static final Logger logger = LoggerFactory.getLogger(PhoenixClient.class);

	public final static ConnectionFactory connectionFactory = ConnectionFactory.getInstance();

	/**
	 * 构造函数，传入id请确保xml文件中有配置
	 * 
	 * @param id
	 */
	public PhoenixClient(String id) {
		Connection connection = null;
		try {
			connection = connectionFactory.getConnection(id);

			if (connection == null) {
				logger.error("id = {} find connection fail ", id);
				throw new Exception("not find connection for id " + id);
			}

			this.id = id;

			logger.debug("connection info is {}", connectionFactory.getConnection(id).toString());
		} catch (Exception e) {
			logger.error("init connection error = {}", e.getLocalizedMessage());
			throw new RuntimeException(e);
		} finally {
			release(connection, null, null);
		}
	}

	@Override
	public boolean execute(String sql) throws Exception {
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = connectionFactory.getConnection(id);
			statement = connection.createStatement();

			return statement.execute(sql);
		} catch (Exception e) {
			logger.error("execute for sql = {}执行出错,Exception = {}", sql, e.getLocalizedMessage());
			throw new Exception(e);
		} finally {
			release(connection, statement,null);
		}
	}

	@Override
	public <T> void upsert(T data) throws Exception {
		if (data != null) {
			List<T> datas = new ArrayList<T>();
			datas.add(data);
			this.upsert(datas);
		}
	}

	@Override
	public <T> void upsert(List<T> datas) throws Exception {
		if (datas != null && !datas.isEmpty()) {

			String tableName = this.getTableName(datas.get(0));
			List<String> columnNames = this.getColumnNamesWithNull(datas.get(0));

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
				throw new Exception(e);
			} finally {
				release(connection, preState, null);
			}
		}

	}

	@Override
	public <T> void upsertIgnoreNull(T data) throws Exception {
		if (data != null) {
			List<T> datas = new ArrayList<T>();
			datas.add(data);
			this.upsertIgnoreNull(datas);
		}
	}

	@Override
	public <T> void upsertIgnoreNull(List<T> datas) throws Exception {
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
				throw new Exception(e);
			} finally {
				release(connection, preState, null);
			}
		}

	}

	@Override
	public <T> List<T> find(Class<T> clazz, String sql) throws Exception {
		if (StringUtil.isBlank(sql)) {
			logger.error("查询sql语句不能为空");
			throw new Exception("查询sql语句不能为空");
		}

		Connection connection = null;
		PreparedStatement preState = null;
		ResultSet rs = null;

		try {
			connection = connectionFactory.getConnection(id);
			preState = connection.prepareStatement(sql);
			rs = preState.executeQuery();

			BeanListHandler handle = new BeanListHandler(clazz);

			return (List<T>) handle.handler(rs);
		} catch (Exception e) {
			logger.error("sql = {}执行出错,Exception = {}", sql, e.getLocalizedMessage());
			throw new Exception(e);
		} finally {
			release(connection, preState, rs);
		}
	}

	@Override
	public <T> List<T> find(Class<T> clazz, Criteria criteria) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
