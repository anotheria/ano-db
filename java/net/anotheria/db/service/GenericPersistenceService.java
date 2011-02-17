package net.anotheria.db.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import net.anotheria.db.util.JDBCUtil;

import org.apache.log4j.Logger;

/**
 * Generic persistence service with additional functional.
 * 
 * @author Alexandr Bolbat
 */
public abstract class GenericPersistenceService extends BasePersistenceServiceJDBCImpl {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(GenericPersistenceService.class.getName());

	/**
	 * Current primary key max id.
	 */
	private AtomicLong id = new AtomicLong();

	/**
	 * Default constructor.
	 */
	public GenericPersistenceService() {
		this(null);
	}

	/**
	 * Public constructor.
	 * 
	 * @param configFile
	 *            - JDBC configuration file name
	 */
	public GenericPersistenceService(String configFile) {
		super(configFile);
		initialize();
	}

	/**
	 * Initialize generic persistence service functional.
	 */
	private void initialize() {
		initializePersistence();
		initializeId();
	}

	/**
	 * Initialize persistence structure.
	 */
	private void initializePersistence() {
		if (isTableExist())
			return;

		Connection conn = null;
		Statement st = null;

		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			st = conn.createStatement();

			for (String ddlQuery : getDDL())
				st.executeUpdate(ddlQuery);

			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException sqle) {
				LOGGER.fatal("initializePersistence() rollback fail.", sqle);
			}
			String message = "initializePersistence() fail.";
			LOGGER.fatal(message, e);
			new RuntimeException(message, e);
		} finally {
			JDBCUtil.release(st);
			JDBCUtil.release(conn);
		}
	}

	private boolean isTableExist() {
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			conn.setAutoCommit(true);

			DatabaseMetaData dmd = conn.getMetaData();
			rs = dmd.getTables(null, null, getTableName(), null);
			if (rs.next())
				return true;
		} catch (SQLException e) {
			String message = "isTableExist() fail.";
			LOGGER.fatal(message, e);
			new RuntimeException(message, e);
		} finally {
			JDBCUtil.release(rs);
			JDBCUtil.release(conn);
		}

		return false;
	}

	/**
	 * Get DDL queries for creating persistence structure.
	 * 
	 * @return {@link List} of {@link String}
	 */
	protected abstract List<String> getDDL();

	/**
	 * Initialize id.
	 * 
	 * @param tableName
	 *            - table name
	 * @param fieldName
	 *            - primary key field name
	 * @return {@link Long} current max id
	 */
	private void initializeId() {
		String tableName = getTableName();
		String fieldName = getPKFieldName();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			conn.setAutoCommit(true);

			st = conn.createStatement();
			rs = st.executeQuery("SELECT MAX(" + fieldName + ") FROM " + tableName);

			if (rs.next())
				id.set(rs.getLong(1));

			LOGGER.debug("initId(" + tableName + ", " + fieldName + ") success. Id: " + id.get());
		} catch (SQLException e) {
			String message = "initId(" + tableName + ", " + fieldName + ") fail.";
			LOGGER.fatal(message, e);
			new RuntimeException(message, e);
		} finally {
			JDBCUtil.release(rs);
			JDBCUtil.release(st);
			JDBCUtil.release(conn);
		}
	}

	/**
	 * Get persistence service table name.
	 * 
	 * @return {@link String}
	 */
	protected abstract String getTableName();

	/**
	 * Get persistence service primary key field name.
	 * 
	 * @return {@link String}
	 */
	protected abstract String getPKFieldName();

	/**
	 * Reserve and get next id.
	 * 
	 * @return {@link Long} next id
	 */
	protected long getNextId() {
		return id.incrementAndGet();
	}

}
