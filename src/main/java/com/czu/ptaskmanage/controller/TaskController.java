package com.czu.ptaskmanage.controller;

import com.czu.ptaskmanage.entity.Task;
import com.czu.ptaskmanage.service.TaskService;
import com.czu.ptaskmanage.vo.ResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Tag(name = "任务管理接口", description = "提供任务的增删改查功能")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Resource
    private TaskService taskService;

    /**
     * 添加新任务
     * @param task 任务信息，JSON格式传入
     * @return 返回添加结果，成功返回200和任务实体
     */
    @Operation(summary = "添加任务", description = "新增一个任务记录")
    @PostMapping
    public ResultVO<Task> addTask(@RequestBody Task task) {
        int result = taskService.addTask(task);
        if (result == 1) {
            return new ResultVO<>(200, "添加成功", task);
        } else {
            return new ResultVO<>(500, "添加失败", null);
        }
    }

    /**
     * 根据任务ID删除任务
     * @param id 任务ID
     * @return 删除结果，成功返回200
     */
    @Operation(summary = "删除任务", description = "根据ID删除任务")
    @DeleteMapping("/{id}")
    public ResultVO<Void> deleteTask(@PathVariable Integer id) {
        int result = taskService.deleteTask(id);
        if (result == 1) {
            return new ResultVO<>(200, "删除成功", null);
        } else {
            return new ResultVO<>(404, "任务不存在或删除失败", null);
        }
    }

    /**
     * 更新任务信息
     * @param task 任务实体，包含ID和要更新字段
     * @return 更新结果，成功返回200和更新后的任务实体
     */
    @Operation(summary = "更新任务", description = "根据ID更新任务信息")
    @PutMapping
    public ResultVO<Task> updateTask(@RequestBody Task task) {
        int result = taskService.updateTask(task);
        if (result == 1) {
            return new ResultVO<>(200, "更新成功", task);
        } else {
            return new ResultVO<>(404, "任务不存在或更新失败", null);
        }
    }

    /**
     * 根据ID查询任务详情
     * @param id 任务ID
     * @return 任务详情，找不到返回404
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
     * 根据用户ID查询该用户所有任务
     * @param userId 用户ID
     * @return 该用户的任务列表
     */
    @Operation(summary = "查询用户任务列表", description = "根据用户ID获取任务列表")
    @GetMapping("/user/{userId}")
    public ResultVO<List<Task>> getTasksByUserId(@PathVariable Integer userId) {
        List<Task> tasks = taskService.getTasksByUserId(userId);
        return new ResultVO<>(200, "查询成功", tasks);
    }
}
