package net.anotheria.db.service;

import java.sql.Connection;
import java.sql.SQLException;

import net.anotheria.db.config.JDBCConfig;
import net.anotheria.db.config.JDBCConfigFactory;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

public abstract class BasePersistenceServiceJDBCImpl {
	
	private BasicDataSource dataSource; 
	protected Logger log;
	
	protected BasePersistenceServiceJDBCImpl(){
		log = Logger.getLogger(this.getClass()); 
		init();
	}
	
	public void init(){
		dataSource = new BasicDataSource();
		JDBCConfig config = JDBCConfigFactory.getJDBCConfig();
		log.info("Using config: "+config);
		dataSource.setDriverClassName(config.getDriver());
		dataSource.setUrl("jdbc:"+config.getVendor()+"://"+config.getHost()+":"+config.getPort()+"/"+config.getDb());
		dataSource.setUsername(config.getUsername());
		dataSource.setPassword(config.getPassword());
	}
	
	protected Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}
	
	protected void release(Connection c){
		if (c==null)
			return;
		try{
			c.close();
		}catch(SQLException ignored){
			
		}
	}
}
