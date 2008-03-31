package net.anotheria.db.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public abstract class RowMapper<T> {
	
	public abstract T map(ResultSet row) throws RowMapperException;
	
	protected String prepareString(String source){
		return source == null ? "" : source.trim();
	}
	
	protected List<Integer> convertToList(int[] objs){
		List<Integer> ret = new ArrayList<Integer>(objs.length);
		for(Object o:objs)
			ret.add((Integer)o);
		return ret;
	}
	
	protected List<Long> convertToList(long[] objs){
		List<Long> ret = new ArrayList<Long>(objs.length);
		for(Long o:objs)
			ret.add(o);
		return ret;
	}
	
	protected List<String> convertToList(String[] objs){
		List<String> ret = new ArrayList<String>(objs.length);
		for(String o:objs)
			ret.add(o);
		return ret;
	}
}
