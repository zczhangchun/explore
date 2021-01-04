package com.monkey.mybatis.mapper;

import com.monkey.mybatis.model.Info;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface HelloMapper {
    List<String> hello(@Param("id") Integer id);

    List<Info> hello2(@Param("idList") List<String> idList, @Param("tableName") String tableName, @Param("flag") boolean flag);

    Set<String> hell();
}
