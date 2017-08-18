package com.spring4all.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AnyUserDetailsService anyUserDetailsService;

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
                .authorizeRequests()
                .antMatchers("/").permitAll()//开放资源
                .antMatchers("/user/**", "/print").hasRole("USER")//限制资源
                .and()
                .formLogin().loginPage("/login")//登录页
                .successHandler(new LoginSuccessHandler("/user").setSuccessString("{\"head\":{\"retFlag\":\"00000\",\"retMsg\":\"登录成功！\"}}"))//登录成功
                .failureHandler(new LoginFailureHandler().setFailureString("{\"head\":{\"retFlag\":\"00001\",\"retMsg\":\"用户名或密码错误！\"}}"))//登录失败
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login")//登出页
                .and()
                .csrf().disable();//禁止跨站攻击
    }

    /**
     * 添加 UserDetailsService， 实现自定义登录校验
     */
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
//        //数据库验证
//        builder.userDetailsService(anyUserDetailsService);

        //内存验证
        builder
                .inMemoryAuthentication()
                .withUser("user").password("123").roles("USER");
    }
}
