package com.monkey.explore.hystrix.filter;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import javax.servlet.*;
import java.io.IOException;

/**
 * Hystrix请求上下文过滤器
 */
public class HystrixRequestContextFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            chain.doFilter(request, response);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            context.shutdown();
        }

    }
}
