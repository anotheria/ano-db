package net.anotheria.db.config;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

@ConfigureMe(name="asg-dbconfig")
public class JDBCConfig{
	
	/**
	 * Name of the driver class.
	 */
	@Configure private String driver;
	/**
	 * Name of the database instance.
	 */
	@Configure private String db;
	/**
	 * Username to connect with.
	 */
	@Configure private String username;
	/**
	 * Password to connect with.
	 */
	@Configure private String password;
	/**
	 * Vendor of the driver
	 */
	@Configure private String vendor;
	/**
	 * DB host.
	 */
	@Configure private String host;
	/**
	 * DB Port.
	 */
	@Configure private int port;
	
	/**
	 * Creates a new config.
	 */
	public JDBCConfig(){
		host       = "localhost";
		port       = 5432;
	}

	@Override public String toString(){
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
