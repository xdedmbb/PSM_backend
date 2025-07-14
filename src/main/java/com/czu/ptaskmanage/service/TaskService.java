package com.czu.ptaskmanage.service;

import com.czu.ptaskmanage.entity.Task;

import java.util.List;

public interface TaskService {
    int addTask(Task task);
    int deleteTask(Integer id);
    int updateTask(Task task);
    Task getTaskById(Integer id);
    List<Task> getTasksByUserId(Integer userId);
}
