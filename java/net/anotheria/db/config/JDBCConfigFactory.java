package net.anotheria.db.config;


public class JDBCConfigFactory {
	private static final JDBCConfig instance = new JDBCConfig();
	
	public static JDBCConfig getJDBCConfig(){
		return instance;
	}
}
