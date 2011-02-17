package net.anotheria.db.util;

/**
 * DDLConfig - utility, which describes "data definition language" configurable properties for ano-db.
 *
 * @author h3ll
 */

import org.apache.log4j.Logger;
import org.configureme.ConfigurationManager;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;


@ConfigureMe(name = "ano-db-ddl-config")
public class DDLConfig {

    /**
     * DDLConfig "dbOwnerName", configurable database owner name.
     */
    @Configure
    private String dbOwnerName;

    /**
     * Get instance method.
     *
     * @return {@link DDLConfig}
     */
    public static DDLConfig getInstance() {
        return DDLInstanceHolder.INSTANCE;
    }

    /**
     * Private constructor with default initialisation.
     */
    private DDLConfig() {
        //By default owner set to "postgres"! Use config to override current!
        this.dbOwnerName = "postgres";
    }

    public String getDbOwnerName() {
        return dbOwnerName;
    }

    public void setDbOwnerName(String dbOwnerName) {
        this.dbOwnerName = dbOwnerName;
    }

    /**
     * Holder class for {@link DDLConfig}.
     */
    private static class DDLInstanceHolder {
        /**
         * Instance of DDLConfig.
         */
        private static final DDLConfig INSTANCE;

        /**
         * Init block.
         */
        static {
            INSTANCE = new DDLConfig();
            try {
                ConfigurationManager.INSTANCE.configure(INSTANCE);
            } catch (Exception e) {
                Logger.getLogger(DDLConfig.DDLInstanceHolder.class).error("Configuration failed. Relying on defaults", e);
            }
        }
    }
}
