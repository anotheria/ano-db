package net.anotheria.db.service;

/**
 * Connection that is thrown if something went wrong during connection establishment. 
 * @author lrosenberg
 *
 */
public class JDBCConnectionException extends RuntimeException {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 2856635701712204716L;

	/**
	 * Creates a new JDBCConnectionException.
	 */
	public JDBCConnectionException() {
		super("Database connection problem.");
	}

	/**
	 * Creates a new JDBCConnectionException.
	 * @param message
	 */
	public JDBCConnectionException(String message) {
		super(message);
	}

	/**
	 * Creates a new JDBCConnectionException.
	 * @param message
	 * @param cause
	 */
	public JDBCConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

}