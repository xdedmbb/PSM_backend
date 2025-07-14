package com.czu.ptaskmanage.controller;

import com.czu.ptaskmanage.entity.Tag;
import com.czu.ptaskmanage.entity.Task;
import com.czu.ptaskmanage.service.TaskTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@io.swagger.v3.oas.annotations.tags.Tag(name = "任务-标签关联管理", description = "任务和标签的多对多关系管理接口")
@RestController
@RequestMapping("/api/task-tag")
public class TaskTagController {

    private final TaskTagService taskTagService;

    public TaskTagController(TaskTagService taskTagService) {
        this.taskTagService = taskTagService;
    }

    @Operation(summary = "给任务绑定多个标签", description = "给指定任务绑定多个标签，如果任务已有绑定则先删除旧绑定，重新绑定",
            responses = {
                    @ApiResponse(responseCode = "200", description = "绑定成功")
            })
    @PostMapping("/bind")
    public ResponseEntity<?> bindTags(
            @Parameter(description = "任务ID", required = true)
            @RequestParam Integer taskId,

            @Parameter(description = "标签ID列表", required = true,
                    schema = @Schema(type = "array", implementation = Integer.class))
            @RequestBody List<Integer> tagIds) {
        taskTagService.bindTagsToTask(taskId, tagIds);
        return ResponseEntity.ok("绑定成功");
    }

    @Operation(summary = "查询任务对应的所有标签", description = "根据任务ID查询所有绑定的标签",
            responses = {
                    @ApiResponse(responseCode = "200", description = "查询成功")
            })
    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Tag>> getTagsByTask(
            @Parameter(description = "任务ID", required = true)
            @PathVariable Integer taskId) {
        List<Tag> tags = taskTagService.getTagsByTaskId(taskId);
        return ResponseEntity.ok(tags);
    }

    @Operation(summary = "查询标签对应的所有任务", description = "根据标签ID查询所有绑定的任务",
            responses = {
                    @ApiResponse(responseCode = "200", description = "查询成功")
            })
    @GetMapping("/tag/{tagId}")
    public ResponseEntity<List<Task>> getTasksByTag(
            @Parameter(description = "标签ID", required = true)
            @PathVariable Integer tagId) {
        List<Task> tasks = taskTagService.getTasksByTagId(tagId);
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "删除任务绑定的所有标签", description = "根据任务ID删除所有绑定的标签关系",
            responses = {
                    @ApiResponse(responseCode = "200", description = "删除成功")
            })
    @DeleteMapping("/task/{taskId}")
    public ResponseEntity<?> removeTags(
            @Parameter(description = "任务ID", required = true)
            @PathVariable Integer taskId) {
        taskTagService.removeTagsByTaskId(taskId);
        return ResponseEntity.ok("删除绑定成功");
    }
}
