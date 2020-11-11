package com.monkey.mybatis.mapper;

import com.monkey.mybatis.model.Info;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface CacheMapper {



    List<Info> selectAll();

    @Cacheable(value = "info", key = "#id+'_'+#itemId")
    Info queryInfoById(@Param("xxx") String id, @Param("tableName") String tableName, @Param("hasPic") Boolean hasPic, @Param("itemId") Integer itemId);
}
