package net.anotheria.db.dao;

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
    }

	/**
	 * Creates a new DAOException. 
	 * @param message
	 */
	public DAOException(String message){
		super(message);
	}

}
