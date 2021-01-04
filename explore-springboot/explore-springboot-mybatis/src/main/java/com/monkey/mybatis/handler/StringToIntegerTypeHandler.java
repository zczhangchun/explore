package com.monkey.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes({Integer.class})
public class StringToIntegerTypeHandler extends BaseTypeHandler<Integer> {




    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Integer integer, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public Integer getNullableResult(ResultSet resultSet, String s) throws SQLException {

        String tagId = resultSet.getString(s);
        if (tagId != null){
            return Integer.valueOf(tagId);
        }
        return null;
    }

    @Override
    public Integer getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public Integer getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
