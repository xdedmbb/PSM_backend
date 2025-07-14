package com.czu.ptaskmanage.controller;

import com.czu.ptaskmanage.service.UserService;
import com.czu.ptaskmanage.entity.User;
import com.czu.ptaskmanage.vo.ResultVO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
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
     * @param body 请求体，必须包含 code 字段
     * @return ResultVO，成功时包含 token 和用户信息
     */
    @Operation(summary = "微信登录", description = "微信小程序登录，获取用户token")
    @PostMapping("/wxLogin")
    public ResultVO wxLogin(@RequestBody Map<String, String> body) {
        log.info("==========UserController==============wxLogin=========");
        String code = body.get("code");
        if (code == null || code.isEmpty()) {
            return new ResultVO(400, "code不能为空", null);
        }

        String wxApiUrl = "https://api.weixin.qq.com/sns/jscode2session?appid="
                + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";

        RestTemplate restTemplate = new RestTemplate();
        String resultStr;
        try {
            resultStr = restTemplate.getForObject(wxApiUrl, String.class);
        } catch (Exception e) {
            log.error("请求微信API失败", e);
            return new ResultVO(500, "微信请求异常", null);
        }

        Map<String, Object> response;
        try {
            response = new com.fasterxml.jackson.databind.ObjectMapper().readValue(resultStr, Map.class);
        } catch (Exception e) {
            log.error("微信响应解析失败：" + resultStr, e);
            return new ResultVO(500, "微信响应解析异常", null);
        }

        if (response == null || response.get("openid") == null) {
            return new ResultVO(500, "微信登录失败：" + response, null);
        }

        String openid = (String) response.get("openid");

        User user = userService.getUserByOpenid(openid);
        if (user == null) {
            user = new User();
            user.setOpenid(openid);
            user.setCreateTime(new Date());
            userService.addUser(user);
        }

        userService.updateLoginTime(user.getUserId());

        String token = Jwts.builder()
                .setSubject(user.getUserId().toString())
                .claim("openid", user.getOpenid())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpire))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);

        return new ResultVO(200, "登录成功", data);
    }

    /**
     * 测试根据 openid 查询用户
     *
     * @return 查询到的用户信息
     */
    @Operation(summary = "测试查询用户", description = "根据openid='test'查询用户")
    @GetMapping("/testselect")
    public ResultVO testselect() {
        log.info("====test001====");
        User test = userService.getUserByOpenid("test");
        System.out.println(test);
        return new ResultVO<User>(200, "", test);
    }

    /**
     * 测试插入用户
     *
     * @return 插入影响的行数
     */
    @Operation(summary = "测试插入用户", description = "插入一个openid为'testinsert'的测试用户")
    @PostMapping("/testinsert")
    public ResultVO testinsert() {
        log.info("====testinsert====");
        Integer count = userService.addUser(new User()
                .setOpenid("testinsert")
                .setCreateTime(new Date()));

        return new ResultVO<Integer>(200, "", count);
    }
}
