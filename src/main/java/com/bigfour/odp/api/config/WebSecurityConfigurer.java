package com.bigfour.odp.api.config;

import com.bigfour.odp.api.security.RedisSecurityContextRepository;
import com.bigfour.odp.api.security.UserDetailsServiceImpl;
import com.bigfour.odp.api.security.rest.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author : zhujiwu
 * @date : 2018/11/8.
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {


    @Autowired
    private RedisSecurityContextRepository securityContextRepository;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public RestLoginConfigurer<HttpSecurity> restLoginConfigurer() {
        RestLoginConfigurer<HttpSecurity> configurer = new RestLoginConfigurer<>(new UsernamePasswordAuthenticationFilter());
        configurer.loginProcessingUrl("/login");
        return configurer;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(wxCodeTokenAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);
        http
                //禁用CSRF保护
                .csrf().disable()
                .authorizeRequests()
                //配置那些路径可以不用权限访问
                .antMatchers("/passport/**", "/wx/**", "/anon/**", "/druid/**").permitAll()
                //任何访问都必须授权
                .anyRequest().authenticated()
                .and()
//                .formLogin()
                .apply(restLoginConfigurer())
                //登陆成功后的处理，因为是API的形式所以不用跳转页面
                .successHandler(new RestAuthenticationSuccessHandler())
                //登陆失败后的处理
                .failureHandler(new RestAuthenticationFailureHandler())
                .and().exceptionHandling()
                //匿名用户访问未授权资源
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                //认证用户访问未授权资源
                .accessDeniedHandler(new RestAccessDeniedHandler())
        ;
//        http.securityContext().securityContextRepository(securityContextRepository);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }


}
