package net.anotheria.db.service;

public class JDBCConnectionException extends RuntimeException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 2856635701712204716L;

	public JDBCConnectionException() {
		super();
	}

	public JDBCConnectionException(String message) {
		super(message);
	}

}