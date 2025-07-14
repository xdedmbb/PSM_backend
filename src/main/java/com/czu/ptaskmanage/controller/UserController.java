package com.czu.ptaskmanage.controller;

import com.czu.ptaskmanage.service.UserService;
import com.czu.ptaskmanage.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.czu.ptaskmanage.entity.User;
import com.czu.ptaskmanage.vo.ResultVO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Value("${wechat.appid}")
    private String appId;

    @Value("${wechat.secret}")
    private String appSecret;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expire}")
    private int jwtExpire;

    //url http://localhost:9663/api/user/wxLogin
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

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> response;
        try {
            response = mapper.readValue(resultStr, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            log.error("微信响应解析失败：" + resultStr, e);
            return new ResultVO(500, "微信响应解析异常", null);
        }

        if (response == null || response.get("openid") == null) {
            return new ResultVO(500, "微信登录失败：" + response, null);
        }

        String openid = (String) response.get("openid");
        String sessionKey = (String) response.get("session_key");

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

    // http://localhost:9663/api/user/testselect
    @RequestMapping("/testselect")
    public ResultVO testselect() {
        log.info("====test001====");
        User test = userService.getUserByOpenid("test");
        System.out.println(test);
        return  new ResultVO<User>(200,"",test);
    }
    // http://localhost:9663/api/user/testinsert
    @RequestMapping("/testinsert")
    public ResultVO testinsert() {
        log.info("====testinsert====");
        Integer count = userService.addUser(new User()
                .setOpenid("testinsert")
                .setCreateTime(new Date()));

        return  new ResultVO<Integer>(200,"",count);
    }
}
