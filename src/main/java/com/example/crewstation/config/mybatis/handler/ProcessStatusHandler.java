package com.example.crewstation.config.mybatis.handler;
import com.example.crewstation.common.enumeration.ProcessStatus;
import com.example.crewstation.common.enumeration.Status;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;

@MappedTypes(ProcessStatus.class)
public class ProcessStatusHandler implements TypeHandler<ProcessStatus> {
    @Override
    public void setParameter(PreparedStatement ps, int i, ProcessStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.getValue(), Types.OTHER);
    }

    @Override
    public ProcessStatus getResult(ResultSet rs, String columnName) throws SQLException {
        return switch (rs.getString(columnName)){
            case "pending" -> ProcessStatus.PENDING;
            case "reject" -> ProcessStatus.REJECT;
            case "resolved" -> ProcessStatus.RESOLVED;
            default -> null;
        };
    }

    @Override
    public ProcessStatus getResult(ResultSet rs, int columnIndex) throws SQLException {
        return switch (rs.getString(columnIndex)){
            case "pending" -> ProcessStatus.PENDING;
            case "reject" -> ProcessStatus.REJECT;
            case "resolved" -> ProcessStatus.RESOLVED;
            default -> null;
        };
    }

    @Override
    public ProcessStatus getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return switch (cs.getString(columnIndex)){
            case "pending" -> ProcessStatus.PENDING;
            case "reject" -> ProcessStatus.REJECT;
            case "resolved" -> ProcessStatus.RESOLVED;
            default -> null;
        };
    }
}
