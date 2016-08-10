package net.anotheria.db.dao;

import java.sql.Connection;

/**
 * Describes a DAO in the ano-db framework.
 * @author lrosenberg
 *
 */
public interface DAO {
	/**
	 * Attribute name for created timestamp of a row. Created and Updated are automatically written by DAOs.
	 */
    String ATT_NAME_DAO_CREATED = "dao_created";
	/**
	 * Attribute name for last updated timestamp of a row. Created and Updated are automatically written by DAOs.
	 */
    String ATT_NAME_DAO_UPDATED = "dao_updated";

	/**
	 * Creates initial DB structure.
	 * @param connection
	 * @throws DAOException
	 */
	void createStructure(Connection connection) throws DAOException;
	/**
	 * Deletes DB Structure.
	 * @param connectiion
	 * @throws DAOException
	 */
	void deleteStructure(Connection connectiion) throws DAOException;
}
 