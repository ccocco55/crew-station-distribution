package com.example.crewstation.config.mybatis.handler;
import com.example.crewstation.common.enumeration.ProcessStatus;
import com.example.crewstation.common.enumeration.Secret;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;

@MappedTypes(Secret.class)
public class SecretStatusHandler implements TypeHandler<Secret> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Secret parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.getValue(), Types.OTHER);
    }

    @Override
    public Secret getResult(ResultSet rs, String columnName) throws SQLException {
        return switch (rs.getString(columnName)){
            case "public" -> Secret.PUBLIC;
            case "private" -> Secret.PRIVATE;
            default -> null;
        };
    }

    @Override
    public Secret getResult(ResultSet rs, int columnIndex) throws SQLException {
        return switch (rs.getString(columnIndex)){
            case "public" -> Secret.PUBLIC;
            case "private" -> Secret.PRIVATE;
            default -> null;
        };
    }

    @Override
    public Secret getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return switch (cs.getString(columnIndex)){
            case "public" -> Secret.PUBLIC;
            case "private" -> Secret.PRIVATE;
            default -> null;
        };
    }
}
