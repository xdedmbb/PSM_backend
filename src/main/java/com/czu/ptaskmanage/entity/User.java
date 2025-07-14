package com.czu.ptaskmanage.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "用户实体类")
public class User {

    @Schema(description = "用户ID", example = "1001")
    private Integer userId;

    @Schema(description = "微信openid", example = "oT1234abcd5678efg")
    private String openid;

    @Schema(description = "昵称", example = "张三")
    private String nickname;

    @Schema(description = "头像URL", example = "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKH...")
    private String avatarUrl;

    @Schema(description = "手机号", example = "13800138000")
    private String phone;

    @Schema(description = "用户角色", example = "user")
    private String role;

    @Schema(description = "用户状态，0-禁用，1-启用", example = "1")
    private Integer userStatus;

    @Schema(description = "最后登录时间", example = "2025-07-14T16:00:00")
    private Date lastLoginTime;

    @Schema(description = "创建时间", example = "2025-07-01T09:00:00")
    private Date createTime;

    @Schema(description = "更新时间", example = "2025-07-10T10:00:00")
    private Date updateTime;
}
