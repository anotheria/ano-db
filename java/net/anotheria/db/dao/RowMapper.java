package net.anotheria.db.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 * Used by the DAO to map a single row to an object of class T. 
 * @author lrosenberg
 */
public abstract class RowMapper<T> {
	/**
	 * Creates a new object from the current row in the result set.
	 * @param row
	 * @return
	 * @throws RowMapperException
	 */
	public abstract T map(ResultSet row) throws RowMapperException;
	/**
	 * Trims the string for storage.
	 * @param source
	 * @return
	 */
	protected String prepareString(String source){
		return source == null ? "" : source.trim();
	}
	
	protected List<Integer> convertToList(int[] objs){
		if(objs == null)
			return new ArrayList<Integer>();
		List<Integer> ret = new ArrayList<Integer>(objs.length);
		for(Object o:objs)
			ret.add((Integer)o);
		return ret;
	}
	
	protected List<Long> convertToList(long[] objs){
		if(objs == null)
			return new ArrayList<Long>();
		List<Long> ret = new ArrayList<Long>(objs.length);
		for(Long o:objs)
			ret.add(o);
		return ret;
	}
	
	protected List<String> convertToList(String[] objs){
		if(objs == null)
			return new ArrayList<String>();
		List<String> ret = new ArrayList<String>(objs.length);
		for(String o:objs)
			ret.add(o);
		return ret;
	}
}
