package net.anotheria.db.dao;

import java.sql.SQLException;

public class DAOSQLException extends DAOException {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * SQL vendor specific error code.
	 */
	private int sqlCode;

	/**
	 * SQL State.
	 */
	private String sqlState;

	public DAOSQLException(SQLException e) {
		super(e.getMessage());
		sqlCode = e.getErrorCode();
		sqlState = e.getSQLState();
	}

	public int getSqlCode() {
		return sqlCode;
	}

	public String getSqlState() {
		return sqlState;
	}

}
