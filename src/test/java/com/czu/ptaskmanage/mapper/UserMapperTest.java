package com.czu.ptaskmanage.mapper;

import com.czu.ptaskmanage.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testFindAll() {
        List<User> users = userMapper.findAll();
        users.forEach(System.out::println);
    }
    @Test
    public void testFindById() {
        User user2 = userMapper.selectByOpenid("test-openid");
        System.out.println(user2);
    }
    @Test
    public void testInsertAndFindByOpenid() {
        User user = new User()
                .setOpenid("test-openid")
                .setNickname("测试用户")
                .setAvatarUrl("http://avatar.png")
                .setPhone("123456789")
                .setRole("user")
                .setUserStatus(1)
                .setLastLoginTime(new Date())
                .setCreateTime(new Date())
                .setUpdateTime(new Date());


        userMapper.insertUser(user);

        User user2 = userMapper.selectByOpenid("test-openid");
        System.out.println(user2);
    }
}
