package com.czu.ptaskmanage.service;

import com.czu.ptaskmanage.entity.User;

public interface UserService {
    User getUserByOpenid(String openid);
    int addUser(User user);
    void updateLoginTime(Integer userId);

    // 可选：如果需要更新用户昵称和头像
    void updateUserProfile(Integer userId, String nickname, String avatarUrl);
}

