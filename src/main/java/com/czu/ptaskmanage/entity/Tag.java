package com.czu.ptaskmanage.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "标签实体类")
public class Tag {

    @Schema(description = "标签ID", example = "1")
    private Integer tagId;

    @Schema(description = "所属用户ID", example = "1001")
    private Integer userId;

    @Schema(description = "标签名称", example = "重要")
    private String tagName;

    @Schema(description = "创建时间", example = "2025-07-14 18:00:00")
    private Date createTime;
}
