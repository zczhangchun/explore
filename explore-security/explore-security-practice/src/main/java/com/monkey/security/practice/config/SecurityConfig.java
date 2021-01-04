package com.monkey.security.practice.config;

import com.monkey.security.practice.handler.MyAuthenticationFailureHandler;
import com.monkey.security.practice.handler.MyAuthenticationSuccessHandler;
import com.monkey.security.practice.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;


    /**
     * 1、配置登陆验证及资源访问的权限规则
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .logout()
                //设置触发logout的url
                .logoutUrl("/signout")
                .logoutSuccessUrl("/aftersignout.html")
                .and()
//                .and()
                //记住我功能
//                .rememberMe()
//                .and()
//                .sessionManagement()
//                //固化保护
//                .sessionFixation()
//                //固化保护策略：每次都创建一个新的session
//                .migrateSession()
//                //只允许账号在一个端登陆
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(false)
//                .expiredSessionStrategy(new CustomExpiredSessionStrategy())
//                .and()
//                .and()
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
                // 登陆成功和失败后跳转的页面
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .authorizeRequests()
                //不需要登陆验证就可以被访问的资源路径
                .antMatchers("/login.html", "/login", "/kaptcha").permitAll()
                // index页面必须是authenticated才能访问
                .antMatchers("/index.html").authenticated()
                .anyRequest().access("@myRDBAService.hasPermision(request, authentication)");
//                .anyRequest().authenticated();
    }

    /**
     * 2、配置具体的用户
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService)
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

    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}
