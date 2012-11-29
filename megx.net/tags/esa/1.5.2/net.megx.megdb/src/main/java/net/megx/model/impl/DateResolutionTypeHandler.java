package net.megx.model.impl;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.megx.model.DateResolution;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class DateResolutionTypeHandler implements TypeHandler<DateResolution>{

	@Override
	public DateResolution getResult(ResultSet rs, String columnName)
			throws SQLException {
		String val = rs.getString(columnName);
		if(val != null) {
			return DateResolution.valueOf(val.toUpperCase());
		}
		return null;
	}

	@Override
	public DateResolution getResult(ResultSet rs, int i)
			throws SQLException {
		String val = rs.getString(i);
		if(val != null) {
			return DateResolution.valueOf(val.toUpperCase());
		}
		return null;
	}

	@Override
	public DateResolution getResult(CallableStatement cs, int i)
			throws SQLException {
		String val = cs.getString(i);
		if(val != null) {
			return DateResolution.valueOf(val.toUpperCase());
		}
		return null;
	}

	@Override
	public void setParameter(PreparedStatement ps, int i,
			DateResolution val, JdbcType jdbcType) throws SQLException {
		if(val != null) {
			ps.setString(i, val.toString().toLowerCase());
		} else {
			//if null ???
		}
	}

}
