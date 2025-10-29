package com.example.crewstation.config.mybatis.handler;
import com.example.crewstation.common.enumeration.PaymentPhase;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;

@MappedTypes(PaymentPhase.class)
public class PaymentPhaseHandler implements TypeHandler<PaymentPhase> {
    @Override
    public void setParameter(PreparedStatement ps, int i, PaymentPhase parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.getValue(), Types.OTHER);
    }

    @Override
    public PaymentPhase getResult(ResultSet rs, String columnName) throws SQLException {
        return switch (rs.getString(columnName)){
            case "request" -> PaymentPhase.REQUEST;
            case "pending" -> PaymentPhase.PENDING;
            case "refund" -> PaymentPhase.REFUND;
            case "success" -> PaymentPhase.SUCCESS;
            case "received" -> PaymentPhase.RECEIVED;
            case "reviewed" -> PaymentPhase.REVIEWED;
            default -> null;
        };
    }

    @Override
    public PaymentPhase getResult(ResultSet rs, int columnIndex) throws SQLException {
        return switch (rs.getString(columnIndex)){
            case "request" -> PaymentPhase.REQUEST;
            case "pending" -> PaymentPhase.PENDING;
            case "refund" -> PaymentPhase.REFUND;
            case "success" -> PaymentPhase.SUCCESS;
            case "received" -> PaymentPhase.RECEIVED;
            case "reviewed" -> PaymentPhase.REVIEWED;
            default -> null;
        };
    }

    @Override
    public PaymentPhase getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return switch (cs.getString(columnIndex)){
            case "request" -> PaymentPhase.REQUEST;
            case "pending" -> PaymentPhase.PENDING;
            case "refund" -> PaymentPhase.REFUND;
            case "success" -> PaymentPhase.SUCCESS;
            case "received" -> PaymentPhase.RECEIVED;
            case "reviewed" -> PaymentPhase.REVIEWED;
            default -> null;
        };
    }
}
