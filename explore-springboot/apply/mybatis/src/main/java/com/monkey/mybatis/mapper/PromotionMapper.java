package com.monkey.mybatis.mapper;


import com.monkey.mybatis.model.Info;
import com.monkey.mybatis.model.TagGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface PromotionMapper {

    List<Info> queryInfoList(@Param("idList") List<String> idList, @Param("tableName") String tableName, @Param("hasPic") Boolean hasPic, @Param("itemId") Integer itemId);

    Set<String> queryIds();

    List<TagGroup> queryGroups(@Param("mediaUniqueId") String mediaUniqueId, @Param("datatraceId") String datatraceId, @Param("groupType") Integer groupType);

    void update(String datatraceId);

    Set<String> query();

    List<String> query2();

    void updateTjPosition(String datatraceId);

    void insert(String datatraceId);

    Set<Integer> ids();

}
