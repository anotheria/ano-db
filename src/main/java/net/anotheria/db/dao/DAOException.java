package net.anotheria.db.dao;

@SuppressWarnings("serial")
/**
 * Base exception class for all DAO related exceptions.
 * @author lrosenberg
 *
 */
public class DAOException extends Exception{
	/**
	 * Creates a new DAOException. 
	 */
	public DAOException(){
		super();
	}

	/**
	 * Creates a new DAOException. 
	 * @param message
	 */
	public DAOException(String message){
		super(message);
	}

}
