package net.anotheria.db.dao;

import java.sql.SQLException;

public class RowMapperException extends DAOException{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	public RowMapperException(SQLException source){
		super("Couldn't map row, because: "+source.getMessage());
	}
}
