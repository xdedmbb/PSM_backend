package com.czu.ptaskmanage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User {
    private Integer userId;
    private String openid;
    private String nickname;
    private String avatarUrl;
    private String phone;
    private String role;
    private Integer userStatus;
    private Date lastLoginTime;
    private Date createTime;
    private Date updateTime;
}
