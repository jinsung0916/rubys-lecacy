package com.benope.apple.domain.ranking.mapper;

import com.benope.apple.domain.ranking.bean.FoodRankingData;
import com.benope.apple.domain.ranking.bean.FoodRankingDataConverter;
import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FoodRankingDataTypeHandler extends BaseTypeHandler<FoodRankingData> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, FoodRankingData parameter, JdbcType jdbcType) throws SQLException {
        // Do nothing
    }

    @Override
    public FoodRankingData getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String data = rs.getObject(columnName, String.class);
        return readValue(data);
    }

    @Override
    public FoodRankingData getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String data = rs.getObject(columnIndex, String.class);
        return readValue(data);
    }

    @Override
    public FoodRankingData getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String data = cs.getObject(columnIndex, String.class);
        return readValue(data);
    }

    @SneakyThrows
    private FoodRankingData readValue(String data) {
        return FoodRankingDataConverter.readValue(data);
    }

}
