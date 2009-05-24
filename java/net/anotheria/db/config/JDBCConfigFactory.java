package net.anotheria.db.config;

import org.configureme.ConfigurationManager;

/**
 * A factory for creation of JDBCConfig instance.
 * @author lrosenberg
 */
public final class JDBCConfigFactory {
	/**
	 * The singleton instance.
	 */
	private static final JDBCConfig instance;
	
	static{
		instance = new JDBCConfig();
		ConfigurationManager.INSTANCE.configure(instance);
	}
	
	/**
	 * Returns the only existing instance. of the JDBCConfig.
	 * @return a JDBCConfig
	 */
	public static JDBCConfig getJDBCConfig(){
		return instance;
	}
	
	/**
	 * Prevent invocation.
	 */
	private JDBCConfigFactory(){ // prevent invocation 
		
	}
}
