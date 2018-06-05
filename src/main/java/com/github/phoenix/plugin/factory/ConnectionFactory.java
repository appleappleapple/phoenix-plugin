package com.github.phoenix.plugin.factory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.phoenix.plugin.bulider.XMLInstanceBuilder;
import com.github.phoenix.plugin.config.DataSource;

/**
 * 功能概要：connection工厂类
 *
 * @author linbingwen
 * @since 2015年12月28日
 */
public class ConnectionFactory {

	private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

	private List<DataSource> datasources;

	private List<String> datasourceIds;

	private Map<String, DataSource> datasourceMaps;

	private static ConnectionFactory instance;

	static String configFile = "/hbase-phoenix.xml";

	private static String propsFile = "hbase-phoenix.properties";

	static XMLInstanceBuilder xMLInstanceBuilder = new XMLInstanceBuilder();

	private static Properties props = new Properties();

	static {
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(propsFile);
			if (is == null) {
				throw new IOException("Cannot load properties file:" + propsFile);
			}
			props.load(is);
			logger.info("Loaded properties from " + propsFile + " successfully...");
		} catch (IOException e) {
			logger.error(propsFile + " does not exist! You may need one to configure connection properties..." + e.getMessage());
		}
	}

	/** for test **/
	public static void main(String[] args) throws ClassNotFoundException {
		ConnectionFactory test = getInstance();
		System.out.println("ff");
	}

	public Connection getConnection(String id) throws Exception {
		if (id == null) {
			return null;
		}

		Connection conn = null;
		DataSource datasourceConfig = datasourceMaps.get(id);

		if (datasourceConfig == null) {
			return null;
		}

		try {
			Class.forName(datasourceConfig.getDriver());
			conn = DriverManager.getConnection(datasourceConfig.getUrl(), props);
		} catch (Exception e) {
			logger.info("fail to jdbc getConnection,exception:{}", e.getLocalizedMessage());
			throw e;
		}
		return conn;
	}

	private void init() {
		if (datasources != null && !datasources.isEmpty()) {
			datasourceIds = new ArrayList<String>();
			datasourceMaps = new HashMap<String, DataSource>();
			for (DataSource datasourceConfig : datasources) {
				datasourceIds.add(datasourceConfig.getId());
				datasourceMaps.put(datasourceConfig.getId(), datasourceConfig);
			}
		}
	}

	public static ConnectionFactory getInstance() {
		if (instance == null) {
			logger.info("to create dataSource instance, config file:{}", configFile);
			instance = xMLInstanceBuilder.buildInstanceFromFile(ConnectionFactory.class, ConnectionFactory.class.getResourceAsStream(configFile));
			instance.init();
			logger.info("load config from xml success...");
		}
		return instance;
	}

	public List<DataSource> getDatasourceConfigs() {
		return datasources;
	}

	public void setDatasourceConfigs(List<DataSource> datasourceConfigs) {
		this.datasources = datasourceConfigs;
	}

	public List<String> getDatasourceIds() {
		return datasourceIds;
	}
}
