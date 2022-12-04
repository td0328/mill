package com.myth.config;

import com.myth.system.security.CustomizeAbstractSecurityInterceptor;
import com.myth.system.security.CustomizeAccessDecisionManager;
import com.myth.system.security.CustomizeAccessDeniedHandler;
import com.myth.system.security.CustomizeFilterInvocationSecurityMetadataSource;
import com.myth.system.security.filter.JwtAuthenticationTokenFilter;
import com.myth.system.service.imple.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * @author xuqiang
 * @version 1.0
 * @date 2022/11/8 10:27
 * @description  SpringSecurity的配置类
 */
@Configuration
@EnableWebSecurity
public class MyConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private CustomizeAbstractSecurityInterceptor customizeAbstractSecurityInterceptor;

    @Autowired
    private CustomizeAccessDecisionManager customizeAccessDecisionManager;

    @Autowired
    private CustomizeFilterInvocationSecurityMetadataSource customizeFilterInvocationSecurityMetadataSource;

    @Autowired
    private CustomizeAccessDeniedHandler customizeAccessDeniedHandler;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    //@Autowired
    //private MyAuthenticationFilter myAuthenticationFilter;

    /**
     * 对请求进行鉴权的配置
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors()
                .and().csrf().disable();

        http.authorizeRequests().
                withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(customizeAccessDecisionManager);//访问决策管理器
                        o.setSecurityMetadataSource(customizeFilterInvocationSecurityMetadataSource);//安全元数据源
                        return o;
                    }
                });
        //不适用session
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(customizeAccessDeniedHandler)
                .and()
                .formLogin()  // 登录
                .permitAll()  //允许所有用户
                .successHandler(authenticationSuccessHandler)  //登录成功处理逻辑
                .failureHandler(authenticationFailureHandler)  //登录失败处理逻辑
                .and()
                .logout()      // 退出
                .permitAll()   //允许所有用户
                .logoutSuccessHandler(logoutSuccessHandler)  //退出成功处理逻辑
                .deleteCookies("JSESSIONID")   //登出之后删除cookie
                .and()
                .sessionManagement()    //会话管理
                .maximumSessions(1)     //同一账号同时登录最大用户数
                .expiredSessionStrategy(sessionInformationExpiredStrategy);

        http.addFilterBefore(customizeAbstractSecurityInterceptor, FilterSecurityInterceptor.class);
        //http.addFilterBefore(myAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserService);
    }

    /**
     * 默认开启密码加密，前端传入的密码Security会在加密后和数据库中的密文进行比对，一致的话就登录成功
     * 所以必须提供一个加密对象，供security加密前端明文密码使用
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
