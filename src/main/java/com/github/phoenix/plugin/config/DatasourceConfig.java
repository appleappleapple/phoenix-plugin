package com.github.phoenix.plugin.config;

/**
 * 功能概要：jdbc配置基类
 * 
 * @author linbingwen
 * @since  2015年12月28日 
 */
public class DatasourceConfig {
	
	/*连接的数据库scheme*/
	private String id;
	
	/*连接的数据库driver*/
	private String driver;

	/*连接的数据库URL*/
	private String url;
	 
	/*连接的数据库URL*/
	private String userName;
	
	/*连接的数据库URL*/
	private String passWord;

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
		return "DatasourceConfig [id=" + id + ", driver=" + driver + ", url=" + url + ", userName=" + userName + ", passWord=" + passWord + "]";
	}

}
