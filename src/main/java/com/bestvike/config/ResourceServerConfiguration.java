package com.bestvike.config;


import com.bestvike.config.handler.LoginFailureHandler;
import com.bestvike.config.handler.LoginSuccessHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Created by 许崇雷 on 2017-09-12.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
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
}
