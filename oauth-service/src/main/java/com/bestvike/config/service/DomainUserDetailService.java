package com.bestvike.config.service;

import com.bestvike.entity.UserInfo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DomainUserDetailService implements UserDetailsService {
    private static final Map<String, UserInfo> userTable = new HashMap<>();

    static {
        insert(new UserInfo("admin", "管理员", "111", false, "ADMIN,USER"));
        insert(new UserInfo("user", "用户1", "123", false, "USER"));
    }

    public static boolean insert(UserInfo userInfo) {
        if (userInfo.getUsername() == null)
            userInfo.setUsername("");
        if (userTable.containsKey(userInfo.getUsername().toLowerCase()))
            return false;
        if (userInfo.getRoles() == null || userInfo.getRoles().length() == 0)
            userInfo.setRoles("USER");
        if (userInfo.getLocked() == null)
            userInfo.setLocked(false);
        userTable.put(userInfo.getUsername().toLowerCase(), userInfo);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null)
            throw new UsernameNotFoundException("用户名不能为空");

        UserInfo userInfo = userTable.get(username.toLowerCase());
        if (userInfo == null)
            throw new UsernameNotFoundException("用户不存在！");
        return new User(userInfo.getUsername(), userInfo.getPassword(), true, true, true, !userInfo.getLocked(), this.getAuths(userInfo.getRoles()));
    }

    /**
     * 权限字符串转化
     * <p>
     * 如 "USER,ADMIN" -> SimpleGrantedAuthority("USER") + SimpleGrantedAuthority("ADMIN")
     *
     * @param roleStr 权限字符串
     */
    private List<SimpleGrantedAuthority> getAuths(String roleStr) {
        String[] roles = roleStr.split(",");
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        for (String role : roles) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        return simpleGrantedAuthorities;
    }
}
