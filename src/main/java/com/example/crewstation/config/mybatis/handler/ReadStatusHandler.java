package com.example.crewstation.config.mybatis.handler;
import com.example.crewstation.common.enumeration.ReadStatus;
import com.example.crewstation.common.enumeration.Status;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;

@MappedTypes(ReadStatus.class)
public class ReadStatusHandler implements TypeHandler<ReadStatus> {
    @Override
    public void setParameter(PreparedStatement ps, int i, ReadStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.getValue(), Types.OTHER);
    }

    @Override
    public ReadStatus getResult(ResultSet rs, String columnName) throws SQLException {
        return switch (rs.getString(columnName)){
            case "read" -> ReadStatus.READ;
            case "unread" -> ReadStatus.UNREAD;
            default -> null;
        };
    }

    @Override
    public ReadStatus getResult(ResultSet rs, int columnIndex) throws SQLException {
        return switch (rs.getString(columnIndex)){
            case "read" -> ReadStatus.READ;
            case "unread" -> ReadStatus.UNREAD;
            default -> null;
        };
    }

    @Override
    public ReadStatus getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return switch (cs.getString(columnIndex)){
            case "read" -> ReadStatus.READ;
            case "unread" -> ReadStatus.UNREAD;
            default -> null;
        };
    }
}
