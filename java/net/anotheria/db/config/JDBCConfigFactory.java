package net.anotheria.db.config;

import org.configureme.ConfigurationManager;


public class JDBCConfigFactory {
	private static final JDBCConfig instance;
	
	static{
		instance = new JDBCConfig();
		ConfigurationManager.INSTANCE.configure(instance);
	}
	
	
	public static JDBCConfig getJDBCConfig(){
		return instance;
	}
}
