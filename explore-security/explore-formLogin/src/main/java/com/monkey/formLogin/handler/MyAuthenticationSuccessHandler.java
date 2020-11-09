package com.monkey.formLogin.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * 自定义的登陆成功处理逻辑
 */
@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    //配置文件中自己执行返回的类型
    @Value("${spring.security.logintype}")
    private String loginType;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        if (loginType.equalsIgnoreCase("JSON")){
            //JSON类型返回
            response.setContentType("application/json;charset=UTF-8");
            Map<String, Boolean> result = new HashMap<>();
            result.put("isok", true);
            response.getWriter().write(objectMapper.writeValueAsString(result));
        }else {
            //页面类型
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
