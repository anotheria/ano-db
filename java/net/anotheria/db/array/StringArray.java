package net.anotheria.db.array;

import java.sql.Array;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import net.anotheria.util.StringUtils;

public class StringArray extends AbstractArray implements Array {
	
	private List<String> array;
	
	public StringArray(List<String> anArray){
		array = anArray;
	}
	
	@Override
	public int getBaseType() throws SQLException {
		return Types.VARCHAR;
	}

	@Override
	public String getBaseTypeName() throws SQLException {
		return "varchar";
	}

	@Override
	public String toString() {		
		return array == null? "{}": StringUtils.surroundWith(StringUtils.concatenateTokens(array, ',', '\"', '\"'), '{', '}');
	}

	public List<String> getArray() {
		return array;
	}

	public void setArray(List<String> array) {
		this.array = array;
	}

}
