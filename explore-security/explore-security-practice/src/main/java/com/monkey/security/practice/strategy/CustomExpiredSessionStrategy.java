package com.monkey.security.practice.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义session过期后的处理策略
 */
public class CustomExpiredSessionStrategy implements SessionInformationExpiredStrategy {

//    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 返回JSON数据
     */
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 403);
        map.put("msg", "您的登录已经超时或者已经在另一台机器登录，您被迫下线。" + event.getSessionInformation().getLastRequest());

        String json = objectMapper.writeValueAsString(map);

        //输出json
        event.getResponse().setContentType("application/json;charset=UTF-8");
        event.getResponse().getWriter().write(json);
    }

    /**
     * 跳转到某个页面
     */
//    @Override
//    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
//        redirectStrategy.sendRedirect(event.getRequest(), event.getResponse(), "某个URL");
//    }


}
