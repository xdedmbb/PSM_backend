package com.czu.ptaskmanage.service.impl;

import com.czu.ptaskmanage.entity.Task;
import com.czu.ptaskmanage.mapper.TaskMapper;
import com.czu.ptaskmanage.service.TaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskMapper taskMapper;

    @Override
    public int addTask(Task task) {
        return taskMapper.insertTask(task);
    }

    @Override
    public int deleteTask(Integer id) {
        return taskMapper.deleteTaskById(id);
    }

    @Override
    public int updateTask(Task task) {
        return taskMapper.updateTask(task);
    }

    @Override
    public Task getTaskById(Integer id) {
        return taskMapper.selectTaskById(id);
    }

    @Override
    public List<Task> getTasksByUserId(Integer userId) {
        return taskMapper.selectTasksByUserId(userId);
    }
}
