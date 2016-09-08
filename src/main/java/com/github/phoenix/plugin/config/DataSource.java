package com.github.phoenix.plugin.config;

/**
 * 功能概要：jdbc配置基类
 * 
 * @author linbingwen
 * @since  2015年12月28日 
 */
public class DataSource {
	
	/*数据源id标识,要保证每个数据源id都不一样*/
	private String id;
	
	/*连接的数据库driver*/
	private String driver;

	/*连接的数据库URL*/
	private String url;
	
	/*连接的数据库schema*/
	private String schema;
	 
	/*连接的数据库URL*/
	private String userName;
	
	/*连接的数据库URL*/
	private String passWord;
	
	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	@Override
	public String toString() {
		return "DataSourceConfig [id=" + id + ", driver=" + driver + ", url=" + url + ", schema=" + schema + ", userName=" + userName + ", passWord=" + passWord + "]";
	}

}
