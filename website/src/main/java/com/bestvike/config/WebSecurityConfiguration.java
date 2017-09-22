package com.bestvike.config;

import com.bestvike.config.handler.LoginFailureHandler;
import com.bestvike.config.handler.LoginSuccessHandler;
import com.bestvike.config.service.DomainUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private DomainUserDetailService userDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 匹配 "/" 路径，不需要权限即可访问
     * 匹配 "/user" 及其以下所有路径，都需要 "USER" 权限
     * 登录地址为 "/login"，登录成功默认跳转到页面 "/user"
     * 退出登录的地址为 "/logout"，退出成功后跳转到页面 "/login"
     * 默认启用 CSRF
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()//开放资源
                .antMatchers("/user/*", "/print").authenticated()//限制资源
                .antMatchers("/admin").hasRole("ADMIN")
                .and()
                .formLogin().loginPage("/login")//登录页
                .successHandler(new LoginSuccessHandler().setSuccessString("{\"head\":{\"retFlag\":\"00000\",\"retMsg\":\"登录成功！\"}}"))//登录成功
                .failureHandler(new LoginFailureHandler().setFailureString("{\"head\":{\"retFlag\":\"00001\",\"retMsg\":\"用户名或密码错误！\"}}").setLockedString("{\"head\":{\"retFlag\":\"00002\",\"retMsg\":\"用户已被冻结！\"}}"))//登录失败
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login")//登出页
                .and()
                .httpBasic();//禁止跨站攻击
    }

    /**
     * 添加 UserDetailsService， 实现自定义登录校验
     */
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .userDetailsService(this.userDetailService)
                .passwordEncoder(this.passwordEncoder);
    }

}
