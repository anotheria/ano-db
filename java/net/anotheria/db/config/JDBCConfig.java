package net.anotheria.db.config;

import net.java.dev.moskito.core.configuration.ConfigurationServiceFactory;
import net.java.dev.moskito.core.configuration.IConfigurable;

import org.apache.log4j.Logger;

public class JDBCConfig implements IConfigurable{
	
	private static final String PROP_NAME_CONFIG_PREFIX = "asg.config.prefix";
	private static final String PROP_NAME_CONFIG_NAME   = "asg.config.name";
	
	public static final String DEF_CONFIG_PREFIX = "";
	public static final String DEF_CONFIG_NAME = "asg.dbconfig";

	private static Logger log = Logger.getLogger(JDBCConfig.class);
	
	public static final String KEY_DRIVER_NAME = "driver";
	public static final String KEY_VENDOR = "vendor";
	public static final String KEY_DB_NAME = "db";
	public static final String KEY_USER_NAME = "username";
	public static final String KEY_PASSWORD = "password";
	
	public static final String KEY_HOST = "host";
	public static final String KEY_PORT = "port";
	
	private String driverName;
	private String dbName;
	private String userName;
	private String password;
	private String vendor;
	private String host;
	private String port;
	
	private String configName; 
	
	public JDBCConfig(){
		driverName = "";
		dbName     = "";
		userName   = "test";
		password   = "test";
		vendor     = "";
		host       = "localhost";
		port       = "5432";
		
		configName = System.getProperty(PROP_NAME_CONFIG_NAME, DEF_CONFIG_NAME);
		configName = System.getProperty(PROP_NAME_CONFIG_PREFIX, DEF_CONFIG_PREFIX) + configName;

		ConfigurationServiceFactory.getConfigurationService().addConfigurable(this);
	}

	public String getConfigurationName() {
		return configName;
	}

	public void notifyConfigurationFinished() {
		//ignore
	}

	public void notifyConfigurationStarted() {
		//ignore
	}

	public void setProperty(String key, String value) {
		
		log.debug("setting "+key+" to "+value);
		
		if (KEY_DRIVER_NAME.equals(key))
			driverName = value;
		
		if (KEY_DB_NAME.equals(key))
			dbName = value;
		
		if (KEY_USER_NAME.equals(key))
			userName = value;
		
		if (KEY_PASSWORD.equals(key))
			password = value;

		if (KEY_VENDOR.equals(key))
			vendor = value;

		if (KEY_HOST.equals(key))
			host = value;
		
		if (KEY_PORT.equals(key))
			port = value;

	}

	public String getDbName() {
		return dbName;
	}

	public String getDriverName() {
		return driverName;
	}

	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}

	public String getVendor() {
		return vendor;
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	public String toString(){
		return "Driver: "+getDriverName()+", Vendor: "+getVendor()+", DB: "+getDbName()+", Username: "+getUserName()+", Pwd:"+getPassword()+" @ "+getHost()+":"+getPort();
	}
}
