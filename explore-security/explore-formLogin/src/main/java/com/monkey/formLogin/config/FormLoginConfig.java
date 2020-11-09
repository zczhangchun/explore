package com.monkey.formLogin.config;

import com.monkey.formLogin.handler.MyAuthenticationFailureHandler;
import com.monkey.formLogin.handler.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class FormLoginConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    /**
     * 1、配置登陆验证及资源访问的权限规则
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //禁用跨站csrf攻击防御
                .csrf().disable()
                //使用formLogin模式
                .formLogin()
                //登陆页面，一旦用户的请求没有权限就跳转到这个页面
                .loginPage("/login.html")
                //处理认证请求的路径，也就是登陆表单form中的action地址
                .loginProcessingUrl("/login")
                //登陆表单form中的用户名输入框input的name名，不修改默认是username
                .usernameParameter("username")
                //登陆表单form中的密码输入框input的name名，不修改默认是password
                .passwordParameter("password")
                //使用自定义的登陆成功和失败处理逻辑
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                //登陆认证成功后默认跳转的路径
//                .defaultSuccessUrl("/")
//                .failureForwardUrl("/login.html")
                .and()
                .authorizeRequests()
                //不需要登陆验证就可以被访问的资源路径
                .antMatchers("/login.html", "/login").permitAll()
                //user角色和admin角色都可以访问的资源位路径
                .antMatchers("/", "/biz1", "biz2").hasAnyAuthority("ROLE_user", "ROLE_admin")
                //admin角色可以访问的资源路径
                .antMatchers("/syslog", "sysuser").hasAnyRole("admin")
                //"sys:log"或"sys:user"是我们自定义的权限ID，有这个ID的用户可以访问对应的资源
                //.antMatchers("syslog").hasAnyAuthority("sys:log")
                //.antMatchers("sysuser").hasAnyAuthority("sys:user")
                .anyRequest().authenticated();
    }

    /**
     * 2、配置具体的用户
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                // 在内存里面存储用户的身份认证和授权信息。
                .inMemoryAuthentication()
                //配置用户user，角色是user
                .withUser("user")
                .password(passwordEncoder().encode("123456"))
                .roles("user")
                .and()
                //配置用户admin，角色是admin
                .withUser("admin")
                .password(passwordEncoder().encode("123456"))
                //admin用户拥有资源ID对应的资源访问的的权限："/syslog"和"/sysuser"
                //.authorities("sys:log", "sys:user")
                .roles("admin")
                .and()
                //配置BCrypt加密
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 3、静态资源访问
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/fonts/**","/img/**","js/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
