package net.anotheria.db.util;

/**
 * DDLConfig - utility, which describes "data definition language" configurable properties for ano-db.
 *
 * @author h3ll
 */

import org.configureme.ConfigurationManager;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;
import org.slf4j.LoggerFactory;

@ConfigureMe(name = "ano-db-ddl-config")
public class DDLConfig {

	/**
	 * Configuration instance.
	 */
	private static DDLConfig INSTANCE;

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
	public static synchronized DDLConfig getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DDLConfig();

			try {
				ConfigurationManager.INSTANCE.configure(INSTANCE);
			} catch (Exception e) {
				LoggerFactory.getLogger(DDLConfig.class.getName()).error("getInstance() Configuration failed. Configuring with defaults.", e);
			}
		}

		return INSTANCE;
	}

	public String getDbOwnerName() {
		return dbOwnerName;
	}

	public void setDbOwnerName(String dbOwnerName) {
		this.dbOwnerName = dbOwnerName;
	}
}
