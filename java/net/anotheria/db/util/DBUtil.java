package net.anotheria.db.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.anotheria.db.service.BasePersistenceServiceJDBCImpl;

import org.apache.log4j.Logger;

/**
 * Utility with some operations on database.
 * 
 * @author Alexandr Bolbat
 */
public final class DBUtil extends BasePersistenceServiceJDBCImpl {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(DBUtil.class.getName());

	/**
	 * Database meta data field name for table name field in result set from getTables(...) method.
	 */
	public static final String DMD_F_TABLE_NAME = "TABLE_NAME";

	/**
	 * Instance.
	 */
	private static DBUtil INSTANCE;

	/**
	 * Private constructor.
	 */
	private DBUtil() {
		this(null);
	}

	/**
	 * Private constructor.
	 * 
	 * @param configName
	 *            - file name with database connection configuration
	 */
	private DBUtil(String configName) {
		super(configName);
	}

	/**
	 * Get {@link DBUtil} instance.
	 * 
	 * @return {@link DBUtil}
	 */
	public static synchronized DBUtil getInstace() {
		if (INSTANCE == null)
			INSTANCE = new DBUtil();

		return INSTANCE;
	}

	/**
	 * Get {@link DBUtil} instance configured from file by given file name.
	 * 
	 * @param configFile
	 *            - file name with database connection configuration
	 * @return {@link DBUtil}
	 */
	public static DBUtil getInstance(String configFile) {
		return new DBUtil(configFile);
	}

	/**
	 * Remove all tables from database.
	 */
	public void removeAllTables() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			conn.setAutoCommit(true);
			st = conn.createStatement();

			DatabaseMetaData dmd = conn.getMetaData();
			rs = dmd.getTables(null, null, null, new String[] { "TABLE" });

			while (rs.next() && rs.getString(DMD_F_TABLE_NAME) != null)
				dropTable(conn, rs.getString(DMD_F_TABLE_NAME), true);
		} catch (SQLException e) {
			String message = "removeAllTables() fail.";
			LOGGER.warn(message, e);
			new RuntimeException(message, e);
		} finally {
			JDBCUtil.release(rs);
			JDBCUtil.release(st);
			JDBCUtil.release(conn);
		}
	}

	/**
	 * Drop table in database. If {@link SQLException} happen it will be only logged.
	 * 
	 * @param conn
	 *            - connection to database
	 * @param tableName
	 *            - table name to drop
	 * @param isCascadeDrop
	 *            - is cascaded drop
	 */
	private void dropTable(Connection conn, String tableName, boolean isCascadeDrop) {
		Statement st = null;

		try {
			st = conn.createStatement();
			String dropSQL = "DROP TABLE IF EXISTS " + tableName;
			if (isCascadeDrop)
				dropSQL += " CASCADE";

			st.executeUpdate(dropSQL);
		} catch (SQLException e) {
			String message = "dropTable(conn, " + tableName + "," + isCascadeDrop + ") fail.";
			LOGGER.warn(message, e);
		} finally {
			JDBCUtil.release(st);
		}
	}

}
