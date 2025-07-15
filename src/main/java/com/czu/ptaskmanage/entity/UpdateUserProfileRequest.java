package com.czu.ptaskmanage.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "更新用户昵称和头像请求参数")
public class UpdateUserProfileRequest {
    @Schema(description = "用户ID", example = "1001")
    @NotNull
    private Integer userId;

    @Schema(description = "用户昵称", example = "张三")
    @NotBlank
    private String nickname;

    @Schema(description = "头像URL", example = "https://example.com/avatar.png")
    @NotBlank
    private String avatarUrl;
}
