package net.anotheria.db.dao;

import java.sql.Connection;

public interface DAO {
	
	public static final String ATT_NAME_DAO_CREATED = "dao_created";
	public static final String ATT_NAME_DAO_UPDATED = "dao_updated";

	
	public void createStructure(Connection connection) throws DAOException;
	
	public void deleteStructure(Connection connectiion) throws DAOException;
}
 