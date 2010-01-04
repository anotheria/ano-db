package net.anotheria.db.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.anotheria.db.config.JDBCConfig;
import net.anotheria.db.config.JDBCConfigFactory;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

/**
 * Base persistence service.
 */
public abstract class BasePersistenceServiceJDBCImpl {

	/**
	 * DataSource.
	 */
	private BasicDataSource dataSource;

	/**
	 * Logger.
	 */
	protected Logger log;

	/**
	 * Default constructor.
	 */
	protected BasePersistenceServiceJDBCImpl() {
		log = Logger.getLogger(this.getClass());
		init();
	}

	/**
	 * Initialize service parameters.
	 */
	public void init() {
		dataSource = new BasicDataSource();
		JDBCConfig config = JDBCConfigFactory.getJDBCConfig();
		log.info("Using config: " + config);
		dataSource.setDriverClassName(config.getDriver());
		dataSource.setUrl("jdbc:" + config.getVendor() + "://" + config.getHost() + ":" + config.getPort() + "/" + config.getDb());
		dataSource.setUsername(config.getUsername());
		dataSource.setPassword(config.getPassword());

		if (config.getMaxConnections() != Integer.MAX_VALUE)
			dataSource.setMaxActive(config.getMaxConnections());
	}

	/**
	 * Get connection from pool.
	 * 
	 * @return {@link Connection}
	 * @throws SQLException
	 */
	protected Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	/**
	 * Close {@link Connection} if opened. All {@link SQLException} on closing are ignored.
	 * 
	 * @param conn
	 *            - {@link Connection} object
	 */
	protected void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * Close {@link Statement} if opened. All {@link SQLException} on closing are ignored.
	 * 
	 * @param st
	 *            - {@link Statement} object
	 */
	protected void close(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * Close {@link ResultSet} if opened. All {@link SQLException} on closing are ignored.
	 * 
	 * @param st
	 *            - {@link ResultSet} object
	 */
	protected void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * Close {@link Connection} if opened. All {@link SQLException} on closing are ignored.
	 * 
	 * @param conn
	 *            - {@link Connection} object
	 */
	protected void release(Connection conn) {
		close(conn);
	}

	/**
	 * Close {@link Statement} if opened. All {@link SQLException} on closing are ignored.
	 * 
	 * @param st
	 *            - {@link Statement} object
	 */
	protected void release(Statement st) {
		close(st);
	}

	/**
	 * Close {@link ResultSet} if opened. All {@link SQLException} on closing are ignored.
	 * 
	 * @param st
	 *            - {@link ResultSet} object
	 */
	protected void release(ResultSet rs) {
		close(rs);
	}
}
