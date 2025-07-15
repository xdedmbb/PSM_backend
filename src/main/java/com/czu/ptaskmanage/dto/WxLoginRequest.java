package com.czu.ptaskmanage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "微信登录请求体")
public class WxLoginRequest {
    @Schema(description = "微信临时登录凭证 code")
    private String code;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "微信头像URL")
    private String avatarUrl;
}
