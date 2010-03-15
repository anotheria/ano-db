package net.anotheria.db.service;

import java.sql.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import net.anotheria.db.config.JDBCConfig;
import net.anotheria.db.config.JDBCConfigFactory;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

/**
 * Base persistence service.
 */
public abstract class BasePersistenceServiceJDBCImpl {

    public static final String CREATE_STATEMENT = "createStatement";
    public static final String PREPARE_STATEMENT = "prepareStatement";
    public static final String PREPARE_CALL = "prepareCall";
    public static final String META_DATA = "getMetaData";
    public static final Map<String, Class> methodNameClass = new HashMap<String, Class>();

    static {
        methodNameClass.put(CREATE_STATEMENT, Statement.class);
        methodNameClass.put(PREPARE_STATEMENT, PreparedStatement.class);
        methodNameClass.put(PREPARE_CALL, CallableStatement.class);
        methodNameClass.put(META_DATA, DatabaseMetaData.class);
    }


    /**
     * DataSource.
     */
    private BasicDataSource dataSource;

    /**
     * Logger.
     */
    protected Logger log;

    /**
     * Proxy Factory
     */
    private GenericReconnectionProxyFactory proxyFactory;


    /**
     * Default constructor.
     */
    protected BasePersistenceServiceJDBCImpl() {
        log = Logger.getLogger(this.getClass());
        init();
        proxyFactory = new GenericReconnectionProxyFactory();
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
        // dataSource.setValidationQuery("select 1 from dual");

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
        Connection result = dataSource.getConnection();
        result = proxyFactory.getProxy(Connection.class, result);
        return result;
    }

    /**
     * Close {@link Connection} if opened. All {@link SQLException} on closing are ignored.
     *
     * @param conn - {@link Connection} object
     */
    protected void close(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
        }
    }

    /**
     * Close {@link Statement} if opened. All {@link SQLException} on closing are ignored.
     *
     * @param st - {@link Statement} object
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
     * @param st - {@link ResultSet} object
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
     * @param conn - {@link Connection} object
     */
    protected void release(Connection conn) {
        close(conn);
    }

    /**
     * Close {@link Statement} if opened. All {@link SQLException} on closing are ignored.
     *
     * @param st - {@link Statement} object
     */
    protected void release(Statement st) {
        close(st);
    }

    /**
     * Close {@link ResultSet} if opened. All {@link SQLException} on closing are ignored.
     *
     * @param st - {@link ResultSet} object
     */
    protected void release(ResultSet rs) {
        close(rs);
    }

    /**
     * Generate Proxy for reconnect strategy
     * Use for wrapping Connect
     */
    private class GenericReconnectionProxyFactory {
        /**
         * Reconnect flag
         */
        private AtomicBoolean isBeingReconnected = new AtomicBoolean(false);

        public <T> T getProxy(Class<T> intf, final T obj) {
            return (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(), new Class[]{intf},
                    new InvocationHandler() {
                        public Object invoke(Object proxy, Method method,
                                             Object[] args) throws Throwable {

                            waitUntilReconnectComplete();

                            try {
                                if (methodNameClass.containsKey(method.getName())) {
                                    return proxyFactory.getProxy(methodNameClass.get(method.getName()), method.invoke(obj, args));
                                }
                                return method.invoke(obj, args);
                            } catch (Exception e) {  //TODO replace with necessary Exception                                 
                                isBeingReconnected.set(true);
                                reconnect();
                                throw e;
                            }
                        }
                    });
        }

        private void waitUntilReconnectComplete() {
            while (isBeingReconnected.get() == true) {
            }
        }

        private synchronized void reconnect() {
            if (isBeingReconnected.get() == true) {
                try {
                    init();
                }
                catch (Exception e) {
                    log.info("Reconnection failed. Probably the db is down");
                }
                finally {
                    isBeingReconnected.set(false);
                }
            }
        }
    }


}
