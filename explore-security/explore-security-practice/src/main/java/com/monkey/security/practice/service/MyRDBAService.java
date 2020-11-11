package com.monkey.security.practice.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 权限校验逻辑
 */
@Component
public class MyRDBAService {
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 权限校验方法
     * 如果访问的URI包含在这个用户权限列表中，则通过
     */
    public boolean hasPermision(HttpServletRequest request, Authentication authentication){

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails){
            UserDetails userDetails = ((UserDetails)principal);
            List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(request.getRequestURI());

            return userDetails.getAuthorities().contains(authorityList.get(0));
        }
        return false;

    }

}
