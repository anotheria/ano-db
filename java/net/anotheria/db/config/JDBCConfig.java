package net.anotheria.db.config;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * Configuration for jdbc connections.
 */
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
	 * Limit maximum active connections to DB.
	 */	
	@Configure private int maxConnections;
	
	/**
	 * Creates a new config.
	 */
	public JDBCConfig(){
		host			= "localhost";
		port			= 5432;
		maxConnections	= Integer.MAX_VALUE;
	}

	@Override public String toString(){
		return "Driver: "+getDriver()+", Vendor: "+getVendor()+", DB: "+getDb()+", Username: "+getUsername()+", Pwd:"+getPassword()+" @ "+getHost()+":"+getPort();
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(final String aDriver) {
		driver = aDriver;
	}

	public String getDb() {
		return db;
	}

	public void setDb(final String aDb) {
		db = aDb;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String anUsername) {
		username = anUsername;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String aPassword) {
		password = aPassword;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(final String aVendor) {
		vendor = aVendor;
	}

	public String getHost() {
		return host;
	}

	public void setHost(final String aHost) {
		host = aHost;
	}

	public int getPort() {
		return port;
	}

	public void setPort(final int aPort) {
		port = aPort;
	}

	public void setMaxConnections(int aMaxConnections) {
		this.maxConnections = aMaxConnections;
	}

	public int getMaxConnections() {
		return maxConnections;
	}
}
