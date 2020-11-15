package com.monkey.security.jwt.config.jwt;

import com.monkey.security.jwt.config.exception.CustomException;
import com.monkey.security.jwt.config.exception.CustomExceptionType;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import sun.plugin.liveconnect.SecurityContextHelper;

@Service
@AllArgsConstructor
public class JwtAuthService {
    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtTokenUtil jwtTokenUtil;

    /**
     * 登陆，生成token
     */
    public String login(String username, String password){


        try {
            //通过用户名密码生成token
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            //通过token生成权限认证对象
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            //将权限认证对象设置到security上下文
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (AuthenticationException e) {
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR, "用户名或密码不正确");
        }

        //生成JWT
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);

    }

    /**
     * 刷新token
     */
    public String refreshToken(String oldToken){
        return jwtTokenUtil.refreshToken(oldToken);
    }

}
