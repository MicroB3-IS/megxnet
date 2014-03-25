package net.megx.megdb.mgtraits.mappers;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class ArrayTypeMapper implements TypeHandler<Object> {

    public Object valueOf(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Object getResult(ResultSet getter, String arg1) throws SQLException {
        Array array = getter.getArray("pfam");
        if (array != null) {
            Integer[] a = (Integer[]) array.getArray();
            return a;
        } else {
            return null;
        }
    }

    @Override
    public Object getResult(ResultSet getter, int arg1) throws SQLException {
        Array array = getter.getArray("pfam");
        if (array != null) {
            return array.getArray();
        } else {
            return null;
        }
    }

    @Override
    public Object getResult(CallableStatement arg0, int arg1)
            throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");

    }

    @Override
    public void setParameter(PreparedStatement arg0, int arg1, Object arg2,
            JdbcType arg3) throws SQLException {
        throw new UnsupportedOperationException("Not implemented");
    }
}
