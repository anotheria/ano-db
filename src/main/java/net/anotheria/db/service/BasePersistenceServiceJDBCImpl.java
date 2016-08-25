package net.anotheria.db.service;

import net.anotheria.db.config.JDBCConfig;
import net.anotheria.db.config.JDBCConfigFactory;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ConnectException;
import java.net.SocketException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Base persistence service.
 */
public abstract class BasePersistenceServiceJDBCImpl {

	/**
	 * Data source.
	 */
	private BasicDataSource dataSource;

	/**
	 * Logger.
	 */
	protected Logger log = LoggerFactory.getLogger(BasePersistenceServiceJDBCImpl.class);

	/**
	 * PROXY factory.
	 */
	private GenericReconnectionProxyFactory proxyFactory;

	/**
	 * Reconnection flag.
	 */
	private AtomicBoolean isBeingReconnected = new AtomicBoolean(false);

	/**
	 * Name of the configuration. Can be ommited.
	 */
	private String configName;

	/**
	 * Default constructor.
	 */
	protected BasePersistenceServiceJDBCImpl() {
		this(null);
	}

	protected BasePersistenceServiceJDBCImpl(String aConfigName) {
		configName = aConfigName;
		proxyFactory = new GenericReconnectionProxyFactory();
		init();
	}

	/**
	 * Initialize data source.
	 */
	public void init() {
		BasicDataSource newDataSource = new BasicDataSource();
		JDBCConfig config = (configName == null) ? JDBCConfigFactory.getJDBCConfig() : JDBCConfigFactory.getNamedJDBCConfig(configName);
		log.info("Using config: " + config);
		newDataSource.setDriverClassName(config.getDriver());
		if (config.getPreconfiguredJdbcUrl() != null && !config.getPreconfiguredJdbcUrl().isEmpty())
			newDataSource.setUrl(config.getPreconfiguredJdbcUrl());
		else
			newDataSource.setUrl("jdbc:" + config.getVendor() + "://" + config.getHost() + ':' + config.getPort() + '/' + config.getDb());
		newDataSource.setUsername(config.getUsername());
		newDataSource.setPassword(config.getPassword());

		if (config.getMaxConnections() != Integer.MAX_VALUE)
			newDataSource.setMaxTotal(config.getMaxConnections());

		this.dataSource = newDataSource;
	}

	/**
	 * Get connection from pool.
	 * 
	 * @return {@link Connection}
	 * @throws SQLException
	 */
	protected Connection getConnection() throws SQLException {
		if (isBeingReconnected.get())
			throw new JDBCConnectionException("Database connection problem.");

		try {
			return Connection.class.cast(proxyFactory.makeProxy(Connection.class, dataSource.getConnection()));
		} catch (SQLException sqle) {
			handleJDBCConnectionException(sqle);
			throw sqle;
		}
	}

	/**
	 * Close {@link Connection} if it opened. If {@link SQLException} happen on closing it will be logged.
	 * 
	 * @param conn
	 *            - {@link Connection} object
	 */
	protected void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
		}
	}

	/**
	 * Close {@link Statement} if it opened. If {@link SQLException} happen on closing it will be logged.
	 * 
	 * @param st
	 *            - {@link Statement} object
	 */
	protected void close(Statement st) {
		try {
			if (st != null)
				st.close();
		} catch (SQLException e) {
		}
	}

	/**
	 * Close {@link ResultSet} if it opened. If {@link SQLException} happen on closing it will be logged.
	 * 
	 * @param st
	 *            - {@link ResultSet} object
	 */
	protected void close(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
		}
	}

	/**
	 * Close {@link Connection} if it opened. If {@link SQLException} happen on closing it will be logged.
	 * 
	 * @param conn
	 *            - {@link Connection} object
	 */
	protected void release(Connection conn) {
		close(conn);
	}

	/**
	 * Close {@link Statement} if it opened. If {@link SQLException} happen on closing it will be logged.
	 * 
	 * @param st
	 *            - {@link Statement} object
	 */
	protected void release(Statement st) {
		close(st);
	}

	/**
	 * Close {@link ResultSet} if it opened. If {@link SQLException} happen on closing it will be logged.
	 * 
	 * @param st
	 *            - {@link ResultSet} object
	 */
	protected void release(ResultSet rs) {
		close(rs);
	}

	/**
	 * Check exception for connection exception type and throw named runtime exception.
	 * 
	 * @param error
	 *            - {@link Throwable}
	 * @throws JDBCConnectionException
	 */
	private void handleJDBCConnectionException(Throwable error) throws JDBCConnectionException {
		if (isBeingReconnected.get())
			throw new JDBCConnectionException("Database connection problem.");

		if (error instanceof SocketException || error instanceof ConnectException) {
			isBeingReconnected.set(true);
			try {
				init();
			} finally {
				isBeingReconnected.set(false);
			}
			throw new JDBCConnectionException("Database connection problem.", error);
		}

		if (error.getCause() != null)
			handleJDBCConnectionException(error.getCause());
	}

	/**
	 * Factory for creating PROXY for some JDBC layer implementations for handling JDBC connection exceptions and reloading data source.
	 */
	private class GenericReconnectionProxyFactory {

		public static final String CREATE_STATEMENT = "createStatement";
		public static final String PREPARE_STATEMENT = "prepareStatement";
		public static final String PREPARE_CALL = "prepareCall";
		public static final String META_DATA = "getMetaData";
		public static final String CLOSE = "close";
		public static final String IS_CLOSED = "isClosed";

		public final Collection<String> methodNames = new HashSet<>();
		public final Collection<String> classNames = new HashSet<>();

		public GenericReconnectionProxyFactory() {
			methodNames.add(CREATE_STATEMENT);
			methodNames.add(PREPARE_STATEMENT);
			methodNames.add(PREPARE_CALL);
			methodNames.add(META_DATA);
			methodNames.add(CLOSE);
			methodNames.add(IS_CLOSED);

			classNames.add(Connection.class.getName());
			classNames.add(DatabaseMetaData.class.getName());
			classNames.add(Statement.class.getName());
			classNames.add(PreparedStatement.class.getName());
			classNames.add(CallableStatement.class.getName());
			classNames.add(ResultSet.class.getName());
		}

		public Object makeProxy(Class<?> intf, final Object obj) {
			if (classNames.contains(intf.getName()))
				return Proxy.newProxyInstance(obj.getClass().getClassLoader(), new Class[] { intf }, new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						if (classNames.contains(method.getDeclaringClass().getName()) && methodNames.contains(method.getName()))
							return makeProxy(method.getReturnType(), invokeMethod(obj, method, args));

						return invokeMethod(obj, method, args);
					}
				});

			return obj;
		}

		public Object invokeMethod(Object proxy, Method method, Object[] args) throws Throwable {
			if (isBeingReconnected.get())
				throw new JDBCConnectionException("Database connection problem.");

			try {
				return method.invoke(proxy, args);
			} catch (InvocationTargetException e) {
				handleJDBCConnectionException(e);

				throw e.getCause();
			}
		}
	}

}
