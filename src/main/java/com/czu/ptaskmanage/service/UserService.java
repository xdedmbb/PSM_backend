package com.czu.ptaskmanage.service;

import com.czu.ptaskmanage.entity.User;

public interface UserService {
    User getUserByOpenid(String openid);
    public int addUser(User user);
    public void updateLoginTime(Integer userId);
}
