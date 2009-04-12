package net.anotheria.db.config;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

@ConfigureMe(name="asg-dbconfig")
public class JDBCConfig{
	
	public static final String DEF_CONFIG_PREFIX = "";
	public static final String DEF_CONFIG_NAME = "asg.dbconfig";

	@Configure private String driver;
	@Configure private String db;
	@Configure private String username;
	@Configure private String password;
	@Configure private String vendor;
	@Configure private String host;
	@Configure private int port;
	
	
	public JDBCConfig(){
		host       = "localhost";
		port       = 5432;
	}



	public String toString(){
		return "Driver: "+getDriver()+", Vendor: "+getVendor()+", DB: "+getDb()+", Username: "+getUsername()+", Pwd:"+getPassword()+" @ "+getHost()+":"+getPort();
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
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

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
