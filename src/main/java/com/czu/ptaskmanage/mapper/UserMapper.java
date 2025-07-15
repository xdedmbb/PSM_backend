package com.czu.ptaskmanage.mapper;

import com.czu.ptaskmanage.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper {

    // 按 openid 查询用户
    @Select("SELECT * FROM user WHERE openid = #{openid}")
    User selectByOpenid(String openid);

    // 插入用户信息
    @Insert("INSERT INTO user(openid, nickname, avatar_url, phone, role, user_status, last_login_time, create_time, update_time)\n" +
            "        VALUES(#{openid}, #{nickname}, #{avatarUrl}, #{phone}, #{role}, #{userStatus}, #{lastLoginTime}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(User user);

    // 更新最后登录时间
    @Update("UPDATE user SET last_login_time = #{time}, update_time = NOW() WHERE user_id = #{userId}")
    int updateLoginTime(@Param("userId") Integer userId, @Param("time") Date time);

    // 查询全部用户
    @Select("SELECT * FROM user")
    List<User> findAll();

    // 根据 ID 删除用户
    @Delete("DELETE FROM user WHERE user_id = #{userId}")
    int deleteById(Integer userId);

    // 更新用户基本信息（可根据实际场景扩展）
    @Update("UPDATE user\n" +
            "        SET nickname = #{nickname}, avatar_url = #{avatarUrl}, phone = #{phone}, role = #{role},\n" +
            "            user_status = #{userStatus}, update_time = NOW()\n" +
            "        WHERE user_id = #{userId}")
    int updateUser(User user);

    @Update("UPDATE user SET nickname = #{nickname}, avatar_url = #{avatarUrl} WHERE user_id = #{userId}")
    int updateUserProfile(User user);
}
