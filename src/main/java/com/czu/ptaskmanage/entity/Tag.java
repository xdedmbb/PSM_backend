package com.czu.ptaskmanage.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

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

    @Schema(description = "关联的任务列表")
    private List<Task> tasks; // 非数据库字段，方便返回用
}
