package com.czu.ptaskmanage.service.impl;

import com.czu.ptaskmanage.entity.Tag;
import com.czu.ptaskmanage.entity.Task;
import com.czu.ptaskmanage.mapper.TaskTagMapper;
import com.czu.ptaskmanage.service.TaskTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TaskTagServiceImpl implements TaskTagService {

    @Resource
    private TaskTagMapper taskTagMapper;

    @Override
    public void bindTagsToTask(Integer taskId, List<Integer> tagIds) {
        if (taskId == null || tagIds == null) return;

        // 先删除旧的绑定（update 效果）
        taskTagMapper.deleteTagsByTaskId(taskId);

        // 再插入新的标签绑定
        for (Integer tagId : tagIds) {
            taskTagMapper.insertTaskTag(taskId, tagId);
        }
    }


    @Override
    public List<Tag> getTagsByTaskId(Integer taskId) {
        return taskTagMapper.selectTagsByTaskId(taskId);
    }

    @Override
    public List<Task> getTasksByTagId(Integer tagId) {
        return taskTagMapper.selectTasksByTagId(tagId);
    }

    @Override
    public void removeTagsByTaskId(Integer taskId) {
        taskTagMapper.deleteTagsByTaskId(taskId);
    }
}
