package com.monkey.security.jwt.config.jwt;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class JwtAuthController {

    private final JwtAuthService jwtAuthService;

    /**
     * 登陆获取JWT的token
     */
    @RequestMapping(value = "/authentication", method = RequestMethod.GET)
    public String getToken(String username, String password){
        return jwtAuthService.login(username, password);
    }

    /**
     * 通过旧token获取新token
     */
    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    public String refreshToken(@RequestHeader("${jwt.header}") String token){
        return jwtAuthService.refreshToken(token);
    }

}
