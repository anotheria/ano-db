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
	 * Returns a named copy of a jdbc config. Useful if you need multiple connections in the same vm.
	 * @param name
	 * @return
	 */
	public static JDBCConfig getNamedJDBCConfig(String name){
		JDBCConfig ret = new JDBCConfig();
		ConfigurationManager.INSTANCE.configureAs(ret, name);
		return ret;
	}
	 
	/**
	 * Prevent invocation.
	 */
	private JDBCConfigFactory(){ // prevent invocation 
		
	}
}
