package net.anotheria.db.array;

import java.sql.Array;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import net.anotheria.util.StringUtils;

public class IntegerArray extends AbstractArray implements Array {
	
	private List<Integer> array;
	
	public IntegerArray(List<Integer> anArray){
		array = anArray;
	}
	
	@Override
	public int getBaseType() throws SQLException {
		return Types.INTEGER;
	}

	@Override
	public String getBaseTypeName() throws SQLException {
		return "int4";
	}

	@Override
	public String toString() {		
		return array == null? "{}": StringUtils.surroundWith(StringUtils.concatenateTokens(array, ',', '\"', '\"'), '{', '}');
	}

	public List<Integer> getArray() {
		return array;
	}

	public void setArray(List<Integer> array) {
		this.array = array;
	}

}
