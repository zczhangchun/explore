package com.monkey.shiro.config;

import com.monkey.shiro.realm.MyCustomRealm;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by zhangchun
 * @since 2020/12/6 12:17 下午
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        //加入权限控制
        shiroFilterFactoryBean.setFilterChainDefinitions();

        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(){
        final DefaultSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        return securityManager;
    }

    @Bean
    public Realm realm(){
        return new MyCustomRealm();
    }
}
