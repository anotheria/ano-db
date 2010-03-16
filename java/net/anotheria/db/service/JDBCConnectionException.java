package net.anotheria.db.service;

@SuppressWarnings("serial")
public class JDBCConnectionException extends RuntimeException{
	public JDBCConnectionException(){
		super();
	}
	public JDBCConnectionException(String message){
		super(message);
	}

}