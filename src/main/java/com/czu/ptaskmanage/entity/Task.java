package com.czu.ptaskmanage.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "任务实体类")
public class Task {

    @Schema(description = "任务ID", example = "1")
    private Integer taskId;

    @Schema(description = "所属用户ID", example = "1001")
    private Integer userId;

    @Schema(description = "任务标题", example = "完成实验报告")
    private String title;

    @Schema(description = "任务描述", example = "完成移动应用开发实验的报告撰写")
    private String description;

    @Schema(description = "任务开始时间，格式：yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;

    @Schema(description = "任务结束时间，格式：yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    @Schema(description = "提醒时间，格式：yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime remindTime;

    @Schema(description = "任务状态，0-未完成，1-已完成", example = "0")
    private Integer status;

    @Schema(description = "任务优先级，0-低，1-中，2-高", example = "1")
    private Integer priority;

    @Schema(description = "任务创建时间，格式：yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "任务重复类型，none/daily/weekly/monthly", example = "weekly")
    private String repeatType;

    @Schema(description = "重复任务的具体值，例如 weekly 的值为 '5' 表示周五", example = "5")
    private String repeatValue;

    @Schema(description = "重复任务结束时间，格式：yyyy-MM-dd'T'HH:mm:ss，可能为空")
    private LocalDateTime repeatEnd;

    @Schema(description = "关联的标签列表")
    private List<Tag> tags; // 非数据库字段
}
