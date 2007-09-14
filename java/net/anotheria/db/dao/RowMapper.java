package net.anotheria.db.dao;

import java.sql.ResultSet;

public abstract class RowMapper<T> {
	public abstract T map(ResultSet row) throws RowMapperException;
	
	protected String prepareString(String source){
		return source == null ? "" : source.trim();
	}
}
