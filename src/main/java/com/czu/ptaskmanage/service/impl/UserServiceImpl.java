package com.czu.ptaskmanage.service.impl;

import com.czu.ptaskmanage.entity.User;
import com.czu.ptaskmanage.mapper.UserMapper;
import com.czu.ptaskmanage.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper ;

    public User getUserByOpenid(String openid) {
        return userMapper.selectByOpenid(openid);
    }

    public int addUser(User user) {
        return userMapper.insertUser(user);
    }

    public void updateLoginTime(Integer userId) {
        userMapper.updateLoginTime(userId, new Date());
    }

}
