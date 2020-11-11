package com.monkey.mybatis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangchun
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagGroup {
    /**
     * 词组ID
     */
    private Integer id;

    /**
     * 标签名称，也是词组名称
     */
    private String groupName;

    /**
     * 词组类型
     */
    private Integer groupType;

    /**
     * 标签ID
     */
    private Integer tagId;
}
