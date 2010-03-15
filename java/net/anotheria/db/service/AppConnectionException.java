package net.anotheria.db.service;

@SuppressWarnings("serial")
public class AppConnectionException extends RuntimeException{
	public AppConnectionException(){
		super();
	}
	public AppConnectionException(String message){
		super(message);
	}

}