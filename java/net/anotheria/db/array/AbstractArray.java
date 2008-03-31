package net.anotheria.db.array;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public abstract class AbstractArray implements Array {
	
	
	public Object getArray() throws SQLException {
		return null;
	}

	public Object getArray(Map<String, Class<?>> map) throws SQLException {
		return null;
	}

	public Object getArray(long index, int count) throws SQLException {
		return null;
	}

	public Object getArray(long index, int count, Map<String, Class<?>> map) throws SQLException {
		return null;
	}

	public abstract int getBaseType() throws SQLException;

	public abstract String getBaseTypeName() throws SQLException;

	public ResultSet getResultSet() throws SQLException {
		return null;
	}

	public ResultSet getResultSet(Map<String, Class<?>> map) throws SQLException {
		return null;
	}

	public ResultSet getResultSet(long index, int count) throws SQLException {
		return null;
	}

	public ResultSet getResultSet(long index, int count, Map<String, Class<?>> map) throws SQLException {
		return null;
	}
	
	@Override
	public abstract String toString();
}
