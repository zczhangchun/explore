package com.monkey.security.jwt.config.jwt;

import com.monkey.security.jwt.config.auth.MyUserDetails;
import com.monkey.security.jwt.config.auth.MyUserDetailsService;
import com.monkey.security.jwt.config.auth.MyUserDetailsServiceMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 在校验密码之前先校验token对不对
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //通过header获取token
        String token = request.getHeader(jwtTokenUtil.getHeader());


        if (!StringUtils.isEmpty(token)){
            return;
        }

        //通过token获取username
        String username = jwtTokenUtil.getUsernameByFromToken(token);

        //通过username获取数据库中的用户信息
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);

        //校验token对不对
        if (!jwtTokenUtil.validateToken(token, userDetails)){
            return;
        }

        //校验成功就把用户的权限放到权限认证对象中，然后放行
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);

    }
}
