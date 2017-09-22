package com.bestvike.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 许崇雷 on 2017-09-14.
 */
@Data
@AllArgsConstructor
public class UserInfo {
    private String username;
    private String nickname;
    private String password;
    private Boolean locked;
    private String roles;
}
