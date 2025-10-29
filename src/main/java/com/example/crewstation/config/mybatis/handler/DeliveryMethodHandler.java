package com.example.crewstation.config.mybatis.handler;
import com.example.crewstation.common.enumeration.DeliveryMethod;
import com.example.crewstation.common.enumeration.Status;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;

@MappedTypes(DeliveryMethod.class)
public class DeliveryMethodHandler implements TypeHandler<DeliveryMethod> {
    @Override
    public void setParameter(PreparedStatement ps, int i, DeliveryMethod parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.getValue(), Types.OTHER);
    }

    @Override
    public DeliveryMethod getResult(ResultSet rs, String columnName) throws SQLException {
        return switch (rs.getString(columnName)){
            case "direct" -> DeliveryMethod.DIRECT;
            case "parcel" -> DeliveryMethod.PARCEL;
            default -> null;
        };
    }

    @Override
    public DeliveryMethod getResult(ResultSet rs, int columnIndex) throws SQLException {
        return switch (rs.getString(columnIndex)){
            case "direct" -> DeliveryMethod.DIRECT;
            case "parcel" -> DeliveryMethod.PARCEL;
            default -> null;
        };
    }

    @Override
    public DeliveryMethod getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return switch (cs.getString(columnIndex)){
            case "direct" -> DeliveryMethod.DIRECT;
            case "parcel" -> DeliveryMethod.PARCEL;
            default -> null;
        };
    }
}
