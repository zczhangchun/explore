package com.monkey.security.practice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.monkey.security.practice.model.MyUserDetails;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyUserDetailsMapper extends BaseMapper<MyUserDetails> {

    /**
     * 根据用户名找到角色
     */
    List<String> findRoleCodeByUsername(@Param("username") String username);

    /**
     * 根据用户名找到权限
     */
    List<String> findAuthorityByRoleCodes(@Param("roleCodes") List<String> roleCodes);

}
