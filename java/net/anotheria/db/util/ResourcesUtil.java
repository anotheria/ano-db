package net.anotheria.db.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * Utility class for closing JDBC resources.
 * 
 * @author Alexandr Bolbat
 */
public final class ResourcesUtil {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(ResourcesUtil.class.getName());

	/**
	 * Default constructor.
	 */
	private ResourcesUtil() {
		throw new IllegalAccessError("Can't instantiate utility class.");
	}

	/**
	 * Close {@link Connection} if opened. All {@link SQLException} on closing are ignored.
	 * 
	 * @param conn
	 *            - {@link Connection} object
	 */
	public static void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			LOGGER.warn("close(" + conn + ") fail.", e);
		}
	}

	/**
	 * Close {@link Statement} if opened. All {@link SQLException} on closing are ignored.
	 * 
	 * @param st
	 *            - {@link Statement} object
	 */
	public static void close(Statement st) {
		try {
			if (st != null && !st.isClosed())
				st.close();
		} catch (SQLException e) {
			LOGGER.warn("close(" + st + ") fail.", e);
		}
	}

	/**
	 * Close {@link ResultSet} if opened. All {@link SQLException} on closing are ignored.
	 * 
	 * @param st
	 *            - {@link ResultSet} object
	 */
	public static void close(ResultSet rs) {
		try {
			if (rs != null && !rs.isClosed())
				rs.close();
		} catch (SQLException e) {
			LOGGER.warn("close(" + rs + ") fail.", e);
		}
	}

	/**
	 * Close {@link Connection} if opened. All {@link SQLException} on closing are ignored.
	 * 
	 * @param conn
	 *            - {@link Connection} object
	 */
	public static void release(Connection conn) {
		close(conn);
	}

	/**
	 * Close {@link Statement} if opened. All {@link SQLException} on closing are ignored.
	 * 
	 * @param st
	 *            - {@link Statement} object
	 */
	public static void release(Statement st) {
		close(st);
	}

	/**
	 * Close {@link ResultSet} if opened. All {@link SQLException} on closing are ignored.
	 * 
	 * @param st
	 *            - {@link ResultSet} object
	 */
	public static void release(ResultSet rs) {
		close(rs);
	}

}
