package net.anotheria.db.dao;

import java.sql.SQLException;

public class DAOSQLException extends DAOException{
	public DAOSQLException(SQLException e){
		super(e.getMessage());
	}
}
