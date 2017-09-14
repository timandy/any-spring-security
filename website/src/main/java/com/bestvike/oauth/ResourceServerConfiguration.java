package com.bestvike.oauth;

import com.bestvike.config.LoginFailureHandler;
import com.bestvike.config.LoginSuccessHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * Created by 许崇雷 on 2017-09-12.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    private final String RESOURCE_ID = "bestvike";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().permitAll()//开放资源
                .antMatchers("/oauth/print").authenticated()//不进行 basic 认证
                .and()
                .formLogin().loginPage("/login")//登录页
                .successHandler(new LoginSuccessHandler().setSuccessString("{\"head\":{\"retFlag\":\"00000\",\"retMsg\":\"登录成功！\"}}"))//登录成功
                .failureHandler(new LoginFailureHandler().setFailureString("{\"head\":{\"retFlag\":\"00001\",\"retMsg\":\"用户名或密码错误！\"}}").setLockedString("{\"head\":{\"retFlag\":\"00002\",\"retMsg\":\"用户已被冻结！\"}}"))//登录失败
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login")//登出页
                .and()
                .csrf().disable();//禁止跨站攻击
    }
}
