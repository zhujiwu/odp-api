package com.bigfour.odp.api.config;

import com.bigfour.odp.api.security.UserDetailsServiceImpl;
import com.bigfour.odp.api.security.jwt.JwtAuthenticationConfigurer;
import com.bigfour.odp.api.security.jwt.JwtAuthenticationProvider;
import com.bigfour.odp.api.security.jwt.JwtForwardAuthenticationSuccessHandler;
import com.bigfour.odp.api.security.jwt.JwtTokenAuthenticationFilter;
import com.bigfour.odp.api.security.rest.RestAccessDeniedHandler;
import com.bigfour.odp.api.security.rest.RestAuthenticationEntryPoint;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.Key;
import java.util.Map;

/**
 * @author : zhujiwu
 * @date : 2018/11/8.
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    /*    @Autowired
        private RedisSecurityContextRepository securityContextRepository;*/

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider jwtAuthenticationProvider(){
        return new JwtAuthenticationProvider();
    }

    @Bean
    public Key jwtSignKey() {
        return Keys.hmacShaKeyFor("e8c0a558d0124143b700084eeb78bdb0".getBytes());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //禁用CSRF保护
                .csrf().disable()
                .authorizeRequests()
                //配置那些路径可以不用权限访问
                .antMatchers("/passport/**", "/wx/**", "/anon/**", "/druid/**").permitAll()
                //任何访问都必须授权
                .anyRequest().authenticated()
                .and()
                .authenticationProvider(jwtAuthenticationProvider())
                .formLogin()
                .successHandler(JwtForwardAuthenticationSuccessHandler.builder().forwardUrl("/login/success").key(jwtSignKey()).build())
                .failureForwardUrl("/login/failure")
                .and()
                .apply(new JwtAuthenticationConfigurer<HttpSecurity>().key(jwtSignKey()).failureForwardUrl("/login/failure"))
                .and().exceptionHandling()
                //匿名用户访问未授权资源
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                //认证用户访问未授权资源
                .accessDeniedHandler(new RestAccessDeniedHandler());
//        http.securityContext().securityContextRepository(jwtSecurityContextRepositoryBean());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }


}
