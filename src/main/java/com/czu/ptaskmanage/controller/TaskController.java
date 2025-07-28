package com.czu.ptaskmanage.controller;

import com.czu.ptaskmanage.entity.Task;
import com.czu.ptaskmanage.service.TaskService;
import com.czu.ptaskmanage.vo.ResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "任务管理接口", description = "提供任务的增删改查功能")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Resource
    private TaskService taskService;

    /**
     * 添加新任务
     */
    @Operation(summary = "添加任务", description = "新增一个任务记录")
    @PostMapping
    public ResultVO<Task> addTask(@RequestBody Task task) {
        int result = taskService.addTask(task);
        if (result > 0) {
            return new ResultVO<>(200, "添加成功", task);
        } else {
            return new ResultVO<>(500, "添加失败", null);
        }
    }

    /**
     * 根据任务ID删除任务
     */
    @Operation(summary = "删除任务", description = "根据ID删除任务")
    @DeleteMapping("/{id}")
    public ResultVO<Void> deleteTask(@PathVariable Integer id) {
        int result = taskService.deleteTask(id);
        if (result > 0) {
            return new ResultVO<>(200, "删除成功", null);
        } else {
            return new ResultVO<>(404, "任务不存在或删除失败", null);
        }
    }

    /**
     * 更新任务信息
     */
    @Operation(summary = "更新任务", description = "根据ID更新任务信息")
    @PutMapping
    public ResultVO<Task> updateTask(@RequestBody Task task) {
        int result = taskService.updateTask(task);
        if (result > 0) {
            return new ResultVO<>(200, "更新成功", task);
        } else {
            return new ResultVO<>(404, "任务不存在或更新失败", null);
        }
    }

    /**
     * 根据ID查询任务详情
     */
    @Operation(summary = "查询任务详情", description = "根据ID获取任务详细信息")
    @GetMapping("/{id}")
    public ResultVO<Task> getTaskById(@PathVariable Integer id) {
        Task task = taskService.getTaskById(id);
        if (task != null) {
            return new ResultVO<>(200, "查询成功", task);
        } else {
            return new ResultVO<>(404, "任务不存在", null);
        }
    }

    /**
     * 根据用户ID和日期查询任务列表（核心修改）
     */
    @Operation(summary = "查询用户任务列表", description = "根据用户ID和日期获取任务列表")
    @GetMapping("/user/{userId}")
    public List<Task> getTasksByUserIdAndDate(
            @PathVariable Integer userId,
            @RequestParam(required = false) String date // 接收前端日期参数
    ) {
        // 1. 查询用户所有任务
        List<Task> allTasks = taskService.getTasksByUserId(userId);

        // 2. 如果有日期参数，筛选指定日期的任务
        if (date != null && !date.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate targetDate;
            try {
                targetDate = LocalDate.parse(date, formatter);
            } catch (Exception e) {
                // 日期格式错误时返回所有任务
                return allTasks;
            }

            // 筛选出开始时间在目标日期的任务
            return allTasks.stream()
                    .filter(task -> {
                        if (task.getStartTime() == null) {
                            return false;
                        }
                        LocalDate taskDate = task.getStartTime().toLocalDate();
                        return taskDate.isEqual(targetDate);
                    })
                    .collect(Collectors.toList());
        }

        // 3. 无日期参数时返回所有任务
        return allTasks;
    }
}
