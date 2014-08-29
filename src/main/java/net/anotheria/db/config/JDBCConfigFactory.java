package net.anotheria.db.config;

import org.configureme.ConfigurationManager;

/**
 * A factory for creation of JDBCConfig instance.
 * @author lrosenberg
 */
public final class JDBCConfigFactory {
	
	/**
	 * Returns the only existing instance. of the JDBCConfig.
	 * @return a JDBCConfig
	 */
	public static JDBCConfig getJDBCConfig(){
		return ConfigHolder.instance;
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
	
	private static final class ConfigHolder{
		/**
		 * The singleton instance.
		 */
		private static final JDBCConfig instance;
		
		static{
			instance = new JDBCConfig();
			ConfigurationManager.INSTANCE.configure(instance);
		}
	}
	 
	/**
	 * Prevent invocation.
	 */
	private JDBCConfigFactory(){ // prevent invocation 
		
	}
}
