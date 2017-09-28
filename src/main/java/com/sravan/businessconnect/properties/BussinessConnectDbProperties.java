package com.sravan.businessconnect.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="bussinessConnect.hibernate")
public class BussinessConnectDbProperties {
	
	private String driver;
	private String url;
	private String username;
	private String password;
	 
	private String dialect;
	private String hbm2ddl_auto;
	private String show_sql;
	private String format_sql;
	
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDialect() {
		return dialect;
	}
	public void setDialect(String dialect) {
		this.dialect = dialect;
	}
	public String getHbm2ddl_auto() {
		return hbm2ddl_auto;
	}
	public void setHbm2ddl_auto(String hbm2ddl_auto) {
		this.hbm2ddl_auto = hbm2ddl_auto;
	}
	public String getShow_sql() {
		return show_sql;
	}
	public void setShow_sql(String show_sql) {
		this.show_sql = show_sql;
	}
	public String getFormat_sql() {
		return format_sql;
	}
	public void setFormat_sql(String format_sql) {
		this.format_sql = format_sql;
	}

}
