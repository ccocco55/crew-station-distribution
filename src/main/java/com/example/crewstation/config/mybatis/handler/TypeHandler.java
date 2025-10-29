package com.example.crewstation.config.mybatis.handler;
import com.example.crewstation.common.enumeration.Status;
import com.example.crewstation.common.enumeration.Type;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;

@MappedTypes(Type.class)
public class TypeHandler implements org.apache.ibatis.type.TypeHandler<Type> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Type parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.getValue(), Types.OTHER);
    }

    @Override
    public Type getResult(ResultSet rs, String columnName) throws SQLException {
        return switch (rs.getString(columnName)){
            case "sub" -> Type.SUB;
            case "main" -> Type.MAIN;
            default -> null;
        };
    }

    @Override
    public Type getResult(ResultSet rs, int columnIndex) throws SQLException {
        return switch (rs.getString(columnIndex)){
            case "sub" -> Type.SUB;
            case "main" -> Type.MAIN;
            default -> null;
        };
    }

    @Override
    public Type getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return switch (cs.getString(columnIndex)){
            case "sub" -> Type.SUB;
            case "main" -> Type.MAIN;
            default -> null;
        };
    }
}
