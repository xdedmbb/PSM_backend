package com.czu.ptaskmanage.controller;

import com.czu.ptaskmanage.dto.WxLoginRequest;
import com.czu.ptaskmanage.entity.UpdateUserProfileRequest;
import com.czu.ptaskmanage.service.UserService;
import com.czu.ptaskmanage.entity.User;
import com.czu.ptaskmanage.vo.ResultVO;
import com.czu.ptaskmanage.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/user")
@Tag(name = "用户管理接口", description = "微信登录及用户相关接口")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    JwtUtil jwtUtil;

    @Value("${wechat.appid}")
    private String appId;

    @Value("${wechat.secret}")
    private String appSecret;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expire}")
    private int jwtExpire;

    /**
     * 微信小程序登录接口
     * 通过前端传入的 code 调用微信接口获取 openid 和 session_key，
     * 若用户不存在则新增用户，返回 JWT token 和用户信息。
     *
     * @param@  请求体，必须包含 code 字段
     * @return ResultVO，成功时包含 token 和用户信息
     */
    @Operation(summary = "微信登录", description = "用户通过微信登录，自动注册或更新用户信息")
    @PostMapping("/wxLogin")
    public ResultVO<Map<String, Object>> wxLogin(@RequestBody Map<String, String> body) {
        log.info("==========UserController==============wxLogin=========");

        String code = body.get("code");
        String nickname = body.get("nickname");
        String avatarUrl = body.get("avatarUrl");

        if (code == null || code.isEmpty()) {
            return new ResultVO<>(400, "code不能为空", null);
        }

        String wxApiUrl = "https://api.weixin.qq.com/sns/jscode2session?appid="
                + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";

        RestTemplate restTemplate = new RestTemplate();
        String resultStr;
        try {
            resultStr = restTemplate.getForObject(wxApiUrl, String.class);
        } catch (Exception e) {
            log.error("请求微信API失败", e);
            return new ResultVO<>(500, "微信请求异常", null);
        }

        Map<String, Object> response;
        try {
            response = new com.fasterxml.jackson.databind.ObjectMapper().readValue(resultStr, Map.class);
        } catch (Exception e) {
            log.error("微信响应解析失败：" + resultStr, e);
            return new ResultVO<>(500, "微信响应解析异常", null);
        }

        if (response == null || response.get("openid") == null) {
            return new ResultVO<>(500, "微信登录失败：" + response, null);
        }

        String openid = (String) response.get("openid");

        // 查询或新增用户

        User user = userService.getUserByOpenid(openid);
        if (user == null) {
            user = new User().setOpenid(openid).setNickname(nickname).setAvatarUrl(avatarUrl).setCreateTime(new Date());
            userService.addUser(user);
        } else {
            userService.updateUserProfile(user.getUserId(), nickname, avatarUrl);
        }

        userService.updateLoginTime(user.getUserId());

        // 生成JWT，放openid
        String token = jwtUtil.generateToken(openid);

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);

        return new ResultVO<>(200, "登录成功", data);
    }

    @Operation(summary = "获取用户信息", description = "获取用户昵称和头像")
    @GetMapping("/info")
    public ResultVO<Map<String, Object>> getUserInfo(@RequestHeader("Authorization") String token) {
        log.info("==========UserController==============getUserInfo=========");
        if (token == null || token.isEmpty()) {
            return new ResultVO<>(401, "缺少token", null);
        }

        String openid;
        try {
            openid = jwtUtil.parseToken(token);
        } catch (Exception e) {
            return new ResultVO<>(401, e.getMessage(), null);
        }
        log.info("解析出的 openid = {}", openid);
        User user = userService.getUserByOpenid(openid);
        if (user == null) {
            return new ResultVO<>(404, "用户不存在", null);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("avatarUrl", user.getAvatarUrl());
        result.put("nickname", user.getNickname());

        return new ResultVO<>(200, "成功", result);
    }


    @Operation(summary = "微信登录", description = "更新用户昵称和头像")
    @PostMapping("/updateProfile")
    public String updateUserProfile(@RequestBody UpdateUserProfileRequest request) {
        userService.updateUserProfile(request.getUserId(), request.getNickname(), request.getAvatarUrl());
        return "更新成功";
    }
}
