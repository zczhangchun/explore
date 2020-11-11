package com.monkey.security.practice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.monkey.security.practice.mapper.MyUserDetailsMapper;
import com.monkey.security.practice.model.MyUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {


    private final MyUserDetailsMapper myUserDetailsMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //获取用户信息
        QueryWrapper<MyUserDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        MyUserDetails myUserDetails = myUserDetailsMapper.selectOne(queryWrapper);
        if (myUserDetails == null){
            throw new UsernameNotFoundException("用户名不存在");
        }

        //获取用户的角色列表
        List<String> roleCodes = myUserDetailsMapper.findRoleCodeByUsername(username);

        //获取用户的权限列表
        List<String> authorities = myUserDetailsMapper.findAuthorityByRoleCodes(roleCodes);

        //将角色字符串转换成spel表达式，让spring security解析
        roleCodes = roleCodes.stream()
                .map(rc -> "ROLE_" + rc)
                .collect(Collectors.toList());

        //角色是一种特殊的权限，合并
        authorities.addAll(roleCodes);

        //转成用逗号分隔的字符串
        String authritiesText = String.join(",", authorities);

        //为用户设置权限标识
        myUserDetails.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(authritiesText));

        //返回
        return myUserDetails;

    }
}
