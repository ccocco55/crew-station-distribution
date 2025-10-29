package com.example.crewstation.config.mybatis.handler;

import com.example.crewstation.common.enumeration.MemberProvider;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;

@MappedTypes(MemberProvider.class)
public class MemberProviderHandler implements TypeHandler<MemberProvider> {
    @Override
    public void setParameter(PreparedStatement ps, int i, MemberProvider parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.getValue(), Types.OTHER);
    }

    @Override
    public MemberProvider getResult(ResultSet rs, String columnName) throws SQLException {
        return switch (rs.getString(columnName)){
            case "kakao" -> MemberProvider.KAKAO;
            case "google" -> MemberProvider.GOOGLE;
            case "naver" -> MemberProvider.NAVER;
            case "crewstation" -> MemberProvider.CREWSTATION;
            default -> null;
        };
    }

    @Override
    public MemberProvider getResult(ResultSet rs, int columnIndex) throws SQLException {
        return switch (rs.getString(columnIndex)){
            case "kakao" -> MemberProvider.KAKAO;
            case "google" -> MemberProvider.GOOGLE;
            case "naver" -> MemberProvider.NAVER;
            case "crewstation" -> MemberProvider.CREWSTATION;
            default -> null;
        };
    }

    @Override
    public MemberProvider getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return switch (cs.getString(columnIndex)){
            case "kakao" -> MemberProvider.KAKAO;
            case "google" -> MemberProvider.GOOGLE;
            case "naver" -> MemberProvider.NAVER;
            case "crewstation" -> MemberProvider.CREWSTATION;
            default -> null;
        };
    }
}
